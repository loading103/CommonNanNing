package com.daqsoft.commonnanning.ui.service;

import android.os.Build;
import android.view.View;
import android.widget.ViewAnimator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.ComUtils;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.ParamsCommon;
import com.daqsoft.commonnanning.common.URLConstant;
import com.daqsoft.commonnanning.http.RetrofitHelper;
import com.daqsoft.commonnanning.ui.entity.AgreeMentEntity;
import com.daqsoft.commonnanning.ui.entity.ChannelDetailsEntity;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
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
 * 加载内容的webview
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/19.
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_CONTENT_WEB)
public class ContentWebActivity extends BaseActivity {

    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.webView)
    ProgressWebView webView;
    @BindView(R.id.animator_view)
    ViewAnimator animatorView;
    /**
     * 页面类型
     * 4 非遗栏目
     */
    @Autowired(name = "pageType")
    int pageType;
    /**
     * 标题
     */
    @Autowired(name = "mTitle")
    String mTitle = "";
    /**
     * 内容
     */
    @Autowired(name = "webContent")
    String webContent = "";


    @Override
    public int getLayoutId() {
        return R.layout.activity_content_web;
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
            // 注意安卓5.0以上的权限
            webView.getSettings().setMixedContentMode(webView.getSettings()
                    .MIXED_CONTENT_ALWAYS_ALLOW);
        }

        headView.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                webView.loadUrl("about:blank");
                ContentWebActivity.super.finish();
            }
        });
        // 用户注册协议
        if (pageType == 1) {
            getAgreeMent();
        } else if (pageType == 2) {
            getData(ParamsCommon.SERVICE_JTZN);
            // 机器人问答显示
        } else if (pageType == 3) {
            animatorView.setDisplayedChild(0);
            webView.loadDataWithBaseURL(null, ComUtils.getNewContent(webContent), "text/html",
                    "UTF-8", null);
            headView.setTitle(mTitle);
            // 非遗名录
        } else if (pageType==4){
            getData(ParamsCommon.SERVICE_FYML);
        }else {
            headView.setTitle(mTitle);
        }
    }

    /**
     * 获取用户登录注册协议内容
     */
    public void getAgreeMent() {
        LoadingDialog.showDialogForLoading(this);
        RetrofitHelper.getApiService().getSiteAgreement(URLConstant.LOGIN_AGREEMENT).subscribeOn
                (Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposable(disposable);
            }
        }).subscribe(new Consumer<BaseResponse<AgreeMentEntity>>() {
            @Override
            public void accept(BaseResponse<AgreeMentEntity> bean) throws Exception {
                LoadingDialog.cancelDialogForLoading();
                if (bean.getCode() == 0) {
                    if (ObjectUtils.isNotEmpty(bean.getData().getContent())) {
                        animatorView.setDisplayedChild(0);
                        webView.loadDataWithBaseURL(null, ComUtils.getNewContent(bean.getData()
                                .getContent()), "text/html", "UTF-8", null);
                        headView.setTitle(bean.getData().getTitle());
                    } else {
                        animatorView.setDisplayedChild(1);
                    }
                } else {
                    animatorView.setDisplayedChild(1);
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                animatorView.setDisplayedChild(1);
                LoadingDialog.cancelDialogForLoading();
            }
        });
    }

    /**
     * 获取数据
     */
    public void getData(String chalCode) {
        LoadingDialog.showDialogForLoading(this, "", true);
        RetrofitHelper.getApiService().getChannelDetails(chalCode).subscribeOn
                (Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BaseResponse<ChannelDetailsEntity>>() {
            @Override
            public void accept(BaseResponse<ChannelDetailsEntity> bean) throws Exception {
                LoadingDialog.cancelDialogForLoading();
                if (bean.getCode() == 0) {
                    // 非遗名录
                    if (pageType==4){
                        if (ObjectUtils.isNotEmpty(bean.getData().getUrl())) {
                            animatorView.setDisplayedChild(0);
                            webView.loadUrl(bean.getData().getUrl());
                            headView.setTitle("非遗名录");
                        } else {
                            animatorView.setDisplayedChild(1);
                            headView.setTitle("非遗名录");
                        }
                    }else {
                        if (ObjectUtils.isNotEmpty(bean.getData().getContent())) {
                            animatorView.setDisplayedChild(0);
                            webView.loadDataWithBaseURL(null, ComUtils.getNewContent(bean.getData()
                                    .getContent()), "text/html", "UTF-8", null);
                            headView.setTitle("交通指南");
                        } else {
                            animatorView.setDisplayedChild(1);
                            headView.setTitle("交通指南");
                        }
                    }
                } else {
                    // 非遗名录
                    if (pageType==4){
                        headView.setTitle("非遗名录");
                    }else {
                        headView.setTitle("交通指南");
                    }
                    animatorView.setDisplayedChild(1);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (pageType==4){
                    headView.setTitle("非遗名录");
                }else {
                    headView.setTitle("交通指南");
                }
                animatorView.setDisplayedChild(1);
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
            webView.loadUrl("about:blank");
            webView.destroy();
            // 释放资源
            webView = null;
        }
    }
}
