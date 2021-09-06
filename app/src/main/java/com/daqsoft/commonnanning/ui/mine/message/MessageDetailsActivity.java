package com.daqsoft.commonnanning.ui.mine.message;

import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.ComUtils;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.example.tomasyb.baselib.base.BaseActivity;
import com.example.tomasyb.baselib.base.net.common.DefaultObserver;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.web.ProgressWebView;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @version 1.0.1
 * @Description: 消息提示详情页面
 * @Author 黄熙
 * @CreateDate 2019-12-17 9:33
 * @since jdk1.8.0_201
 */
public class MessageDetailsActivity extends BaseActivity {

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
     * 消息ID
     */
    String id = "";


    @Override
    public int getLayoutId() {
        return R.layout.activity_message_details;
    }


    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        id= getIntent().getStringExtra("id");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(webView.getSettings().MIXED_CONTENT_ALWAYS_ALLOW);
        }
        headView.setTitle("消息详情");
        getMeMessageDetail();
        readMessage();
        headView.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                webView.loadUrl("about:blank");
                MessageDetailsActivity.super.finish();
            }
        });
    }

    /**
     * 获取数据
     */
    public void getMeMessageDetail() {
        LoadingDialog.showDialogForLoading(this, "", true);
        RetrofitHelper.getApiService().getMeMessageDetail(id).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<MessageBean>() {
            @Override
            public void onSuccess(BaseResponse<MessageBean> response) {
                if (response.getCode() == 0 && ObjectUtils.isNotEmpty(response.getData()) && ObjectUtils.isNotEmpty(response.getData().getContent())) {
                    newsWebTitle.setText(response.getData().getTitle());
                    newsWebTime.setText(response.getData().getCreatetime());
                    animatorView.setDisplayedChild(0);
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

    public void readMessage(){
        RetrofitHelper.getApiService().readMessage(id).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver() {
            @Override
            public void onSuccess(BaseResponse response) {
                if (response.getCode() == 0 ) {
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
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
