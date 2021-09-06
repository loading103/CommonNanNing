package com.daqsoft.commonnanning.ui.service.news;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.ComUtils;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.ParamsCommon;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.ActivityDetailsEntity;
import com.daqsoft.commonnanning.ui.entity.NewsListEntity;
import com.daqsoft.commonnanning.utils.ShareUtils;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.base.net.common.DefaultObserver;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.web.ProgressWebView;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import butterknife.BindView;
import io.agora.yview.dialog.BaseDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 旅游资讯详情页面
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/19.
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_DETAILS_WEB)
public class NewsWebActivity extends BaseActivity {

    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.news_web_title)
    TextView newsWebTitle;
    @BindView(R.id.news_web_time)
    TextView newsWebTime;
    @BindView(R.id.webView)
    ProgressWebView webView;
    @BindView(R.id.no_date_img)
    ImageView noDateImg;
    @BindView(R.id.no_data_content)
    TextView noDataContent;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    @BindView(R.id.animator_view)
    ViewAnimator animatorView;
    /**
     * 标题
     */
    @Autowired(name = "title")
    String title = "";
    /**
     * ID
     */
    @Autowired(name = "id")
    String id = "";
    /**
     * 栏目code
     */
    @Autowired(name = "code")
    String channelCode = "";

    private NewsListEntity data = null;

    private BaseDialog mShareDialog = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_web;
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(webView.getSettings().MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (title == null || title.length() == 0) {
            title = getIntent().getStringExtra("title");
        }
        if (id == null || id.length() == 0) {
            id = getIntent().getStringExtra("id");
        }
        if (channelCode == null || channelCode.length() == 0) {
            channelCode = getIntent().getStringExtra("code");
        }


        headView.setTitle(title);
        if (ParamsCommon.SERVICE_LYZX.equals(channelCode)) {
            headView.setMenuBackGround(R.mipmap.share);
            if (ObjectUtils.isNotEmpty(ProjectConfig.QQ_APPID)
                    && ObjectUtils.isNotEmpty(ProjectConfig.WECHAT_APPID)
                    && ObjectUtils.isNotEmpty(ProjectConfig.SHARE_NEWS_URL)) {
                headView.setMenuHidden(true);
            } else {
                headView.setMenuHidden(false);
            }
            if(ProjectConfig.CITY_NAME.equals("西藏")) {
                headView.setMenuHidden(false);
            }

            headView.setMenuListener(new HeadView.OnMenuListener() {
                @Override
                public void onClickMenu(View v) {
                    if (mShareDialog != null && data != null && ObjectUtils.isNotEmpty(data.getCover()) && ObjectUtils.isNotEmpty(data.getTitle()) && ObjectUtils.isNotEmpty(Html.fromHtml(data.getContent(), Html.FROM_HTML_MODE_COMPACT).toString()) && ObjectUtils.isNotEmpty(data.getId())) {
                        mShareDialog.show();
                    } else {
                        ToastUtils.showShort("数据不全无法分享!");
                    }
                }
            });
            getNewsData();
        } else if (ParamsCommon.ACTIVITY_CHANELCODE.equals(channelCode) || ParamsCommon.RECOMMEND_CHANELCODE.equals(channelCode)) {
            getActivityData();
        }
        headView.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                webView.loadUrl("about:blank");
                NewsWebActivity.super.finish();
            }
        });
    }

    /**
     * 获取数据
     */
    public void getNewsData() {
        LoadingDialog.showDialogForLoading(this, "", true);
        RetrofitHelper.getApiService().getNewsDetails(id, channelCode).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<NewsListEntity>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(BaseResponse<NewsListEntity> response) {
                if (response.getCode() == 0 && ObjectUtils.isNotEmpty(response.getData()) && ObjectUtils.isNotEmpty(response.getData().getContent())) {
                    newsWebTitle.setText(response.getData().getTitle());
                    newsWebTime.setText(response.getData().getCreateTime());
                    animatorView.setDisplayedChild(0);
                    webView.loadDataWithBaseURL(null,
                            ComUtils.getNewContent(response.getData().getContent()), "text/html",
                            "UTF-8", null);
                    data = response.getData();
                    if (data != null) {
                        mShareDialog = ShareUtils.ininShareDialog(NewsWebActivity.this,
                                ProjectConfig.SHARE_NEWS_URL, data.getTitle(), data.getId(),
                                data.getCover(), Html.fromHtml(data.getContent(),
                                        Html.FROM_HTML_MODE_COMPACT).toString());
                    }
                } else {
                    animatorView.setDisplayedChild(1);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                LoadingDialog.cancelDialogForLoading();
            }
        });
    }

    /**
     * 获取数据
     */
    public void getActivityData() {
        LoadingDialog.showDialogForLoading(this, "", true);
        RetrofitHelper.getApiService().getActivityDetails(id).subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<ActivityDetailsEntity>() {
            @Override
            public void onSuccess(BaseResponse<ActivityDetailsEntity> response) {
                if (response.getCode() == 0 && ObjectUtils.isNotEmpty(response.getData()) && ObjectUtils.isNotEmpty(response.getData().getContent())) {
                    newsWebTitle.setText(response.getData().getTitle());
                    newsWebTime.setText(response.getData().getCreateTime());
                    animatorView.setDisplayedChild(0);
                    //                            WebUtils.setWebInfo(webView, response.getData()
                    //                            .getContent());
                    webView.loadDataWithBaseURL(null,
                            ComUtils.getNewContent(response.getData().getContent()), "text/html",
                            "UTF-8", null);
                } else {
                    animatorView.setDisplayedChild(1);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                LoadingDialog.cancelDialogForLoading();
            }
        });
    }


    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
            // 释放资源
            webView = null;
        }
    }
}
