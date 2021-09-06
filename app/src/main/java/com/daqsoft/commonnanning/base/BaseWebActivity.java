package com.daqsoft.commonnanning.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.helps_gdmap.CompleteFuncData;
import com.daqsoft.commonnanning.helps_gdmap.HelpMaps;
import com.daqsoft.commonnanning.helps_gdmap.MapNaviUtils;
import com.example.tomasyb.baselib.base.api.ApiConstants;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.web.ProgressWebView;
import com.example.tomasyb.baselib.web.WebUtils;
import com.example.tomasyb.baselib.widget.HeadView;

import java.text.DecimalFormat;

import butterknife.BindView;

/**
 * 基础网页
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/20 0020
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_BASEWEB)
public class BaseWebActivity extends BaseActivity {

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
    @BindView(R.id.headView)
    HeadView headView;
    @Autowired(name = "HTMLURL")
    String mHtmlUrl;
    @Autowired(name = "HTMLTITLE")
    String mHtmlTitle;
    public  Boolean isAgreePrivate ; //是否统一隐私协议
    private boolean isNeedTitle = false;
    /**
     * 获取自己位置
     */
    AMapLocation mapLocation = null;


    @Override
    public int getLayoutId() {
        return R.layout.activity_base_web;
    }

    @Override
    public void initView() {
        isAgreePrivate = SPUtils.getInstance().getBoolean(ApiConstants.IS_FIRST_AGREE, false);
        ARouter.getInstance().inject(this);
        isNeedTitle = getIntent().getBooleanExtra("isNeedTitle", false);
        if (isNeedTitle) {
            headView.setTitle(mHtmlTitle);
        }
        headView.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    webView.loadUrl("about:blank");
                    finish();
                }
            }
        });
        if (ObjectUtils.isNotEmpty(mHtmlUrl)) {
            animatorView.setDisplayedChild(0);
            LogUtils.e(mHtmlUrl + "---" + mHtmlTitle);
            WebUtils.setWebInfo2(webView, mHtmlUrl, this, new WebUtils.OnfinishListener() {
                @Override
                public void finish() {
                }
            });
        } else {
            animatorView.setDisplayedChild(1);
        }
        LogUtils.e(mHtmlUrl);
        if(isAgreePrivate){
            getLocation();
        }
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
        webView.addJavascriptInterface(new RequestHtmlData(), "js");
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
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
        webSettings.setBlockNetworkImage(false);//解决图片不显示
        webSettings.setSavePassword(true);
        webSettings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setSaveEnabled(true);
        webView.setKeepScreenOn(true);
        // 设置setWebChromeClient对象
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (isNeedTitle) {
                    headView.setTitle(mHtmlTitle);
                } else {
                    headView.setTitle(ObjectUtils.isNotEmpty(title) ? title : "--");
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                super.onProgressChanged(view, newProgress);
                if (webView != null) {
                    webView.setProgress(newProgress);
                }
            }
        });
        // 设置此方法可在WebView中打开链接，反之用浏览器打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                try {
                    if (webView != null) {
                        if (!webView.getSettings().getLoadsImagesAutomatically()) {
                            webView.getSettings().setLoadsImagesAutomatically(true);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.onPageFinished(view, url);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.e("跳转的链接--" + url);
                if (url.startsWith("http:") || url.startsWith("https:") || url.startsWith("file" + ":///")) {
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
    public IBasePresenter initPresenter() {
        return null;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
            finish();
        }
        return super.dispatchKeyEvent(event);
    }


    /**
     * 获取位置
     */
    public void getLocation() {
        HelpMaps.startLocation(new CompleteFuncData() {
            @Override
            public void success(AMapLocation location) {
                mapLocation = location;
                HelpMaps.stopLocation();
            }
        });
    }

    /**
     * 网页交互的实体类
     *
     * @author 黄熙
     * @version 1.0.0
     * @date 2018/12/10 0010
     * @since JDK 1.8
     */
    public class RequestHtmlData {


        /**
         * 获取自己的定位
         */
        @JavascriptInterface
        public void GoMapNavi(final String dLat, final String dLng, final String dName) {
            HelpMaps.startLocation(new CompleteFuncData() {
                @Override
                public void success(AMapLocation location) {
                    HelpMaps.stopLocation();
                    if (ObjectUtils.isNotEmpty(location)) {
                        if (ObjectUtils.isNotEmpty(dLat) && ObjectUtils.isNotEmpty(dLng) && ObjectUtils.isNotEmpty(dName)) {
                            MapNaviUtils.isMapNaviUtils(BaseWebActivity.this,
                                    location.getLatitude() + "", location.getLongitude() + "",
                                    location.getAddress(), dLat, dLng, dName);
                        } else {
                            ToastUtils.showShortCenter("数据缺失，无法进行导航操作");
                        }
                    } else {
                        ToastUtils.showShortCenter("获取位置失败，请检查是否开启定位服务");
                    }
                }
            });
        }

        /**
         * 计算两个点之间的直线距离
         *
         * @param lat2 第2个点的纬度
         * @param lng2 第2个点经度
         */
        @JavascriptInterface
        public String calculateLineDistance(String lat2, String lng2) {
            final String[] distance = {"0.00"};
            if (ObjectUtils.isNotEmpty(mapLocation)) {
                if (ObjectUtils.isNotEmpty(lat2) && ObjectUtils.isNotEmpty(lng2)) {
                    LatLng latLng1 = new LatLng(mapLocation.getLatitude(),
                            mapLocation.getLongitude());
                    LatLng latLng2 = new LatLng(Double.parseDouble(lat2), Double.parseDouble(lng2));
                    distance[0] =
                            new DecimalFormat("0.00").format((AMapUtils.calculateLineDistance(latLng1, latLng2)) / 1000);
                    return distance[0] + "KM";
                }
            } else {
                ToastUtils.showShortCenter("获取位置失败，请检查是否开启定位服务");
            }
            return distance[0] + "KM";
        }


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
