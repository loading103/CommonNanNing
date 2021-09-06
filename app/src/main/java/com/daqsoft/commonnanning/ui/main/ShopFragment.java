package com.daqsoft.commonnanning.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.http.CommonRequest;
import com.example.tomasyb.baselib.base.mvp.BaseFragment;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.web.ProgressWebView;
import com.example.tomasyb.baselib.widget.HeadView;
import com.example.tomasyb.baselib.widget.LoadingDialog;

import butterknife.BindView;

/**
 * 商城fragment
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
public class ShopFragment extends BaseFragment {
    @BindView(R.id.headView)
    HeadView headView;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_web;
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }

    /**
     * 是否第一次
     */
    public boolean isFirst = false;

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        headView.setTitle("预订");
        headView.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                }
            }
        });
        headView.setBackHidden(false);
        setWebSetting();
    }

    /**
     * 设置配置信息
     */
    private void setWebSetting() {
        WebSettings webSettings = webView.getSettings();
        if (Build.VERSION.SDK_INT >= 19) {
            // 图片自动缩放 打开
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            // 图片自动缩放 关闭
            webSettings.setLoadsImagesAutomatically(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // 软件解码
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        // 硬件解码
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        // 设置支持javascript脚本
        webSettings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具 是否使用WebView内置的缩放组件，由浮动在窗口上的缩放控制和手势缩放控制组成，默认false
        webSettings.setBuiltInZoomControls(true);
        // 隐藏缩放工具
        webSettings.setDisplayZoomControls(false);
        // 扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        // 自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setSavePassword(true);
        webSettings.setDomStorageEnabled(true);
        webView.setSaveEnabled(true);
        webView.setKeepScreenOn(true);
        // 设置setWebChromeClient对象
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                super.onProgressChanged(view, newProgress);
                webView.setProgress(newProgress);
            }
        });
        // 设置此方法可在WebView中打开链接，反之用浏览器打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (webView.canGoBack()) {
                    headView.setBackHidden(true);
                } else {
                    headView.setBackHidden(false);
                }
                super.onPageFinished(view, url);
                if (!webView.getSettings().getLoadsImagesAutomatically()) {
                    webView.getSettings().setLoadsImagesAutomatically(true);
                }

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                if (webView.canGoBack()) {
                    headView.setBackHidden(true);
                } else {
                    headView.setBackHidden(false);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (webView.canGoBack()) {
                    headView.setBackHidden(true);
                } else {
                    headView.setBackHidden(false);
                }
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    WebView.HitTestResult hitTestResult = view.getHitTestResult();
                    if (!TextUtils.isEmpty(url) && hitTestResult == null) {
                        view.loadUrl(url);
                        if (url.equals("https://m.ctrip.com/webapp/vacations/tour/vacations")) {

                        }
                        return true;
                    }
                    return super.shouldOverrideUrlLoading(view, url);
                }

                // Otherwise allow the OS to handle things like tel, mailto, etc.
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

    }

    @Override
    protected void initData() {

    }


    /**
     * 当前页面是否隐藏， true为显示，false为隐藏
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setData();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirst) {
            setData();
        }
    }

    /**
     * 加载页面
     */
    public void setData() {
        CommonRequest.checkedToken(new CommonRequest.OnStateCallBack() {
            @Override
            public void result(int value) {
                LoadingDialog.cancelDialogForLoading();
                // 0未失效
                if (0 == value) {
                    if (ObjectUtils.isNotEmpty(SPUtils.getInstance().getString(SPCommon.TOKEN))
                            && ObjectUtils.isNotEmpty(SPUtils.getInstance().getString(SPCommon
                            .UC_ID)) && ObjectUtils.isNotEmpty(SPUtils.getInstance().getString
                            (SPCommon.UC_TOKEN))) {
                        String htmlUrl = ProjectConfig.BASE_HTML_MALL;
                        String params = "token=" + SPUtils.getInstance().getString(SPCommon
                                .UC_TOKEN) + "&unid=" + SPUtils.getInstance().getString(SPCommon
                                .UC_ID) + "&linkFrom=app";
                        htmlUrl = htmlUrl + "?" + params;
                        animatorView.setDisplayedChild(0);
                        webView.loadUrl(htmlUrl);
                        isFirst = false;
                    } else {
                        ToastUtils.showShortCenter("账号异常，请稍后再试");
                    }
                } else {
                    ARouter.getInstance().build(Constant.ACTIVITY_LOGIN).navigation(getActivity()
                            , 1);
                    ToastUtils.showShortCenter("请先登录");
                    isFirst = true;
                }
            }
        });
    }

    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void dismissLoadingDialog() {

    }
}
