package com.daqsoft.commonnanning.ui.route;

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

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.base.BaseWebActivity;
import com.daqsoft.commonnanning.common.AESEncryptUtil;
import com.daqsoft.commonnanning.common.Constant;
import com.daqsoft.commonnanning.common.SPCommon;
import com.daqsoft.commonnanning.common.SourceConstant;
import com.daqsoft.commonnanning.common.URLConstant;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.web.MWebChromeClient;
import com.example.tomasyb.baselib.web.ProgressWebView;
import com.example.tomasyb.baselib.web.WebUtils;
import com.example.tomasyb.baselib.widget.HeadView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * 行程页面（webActivity）
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/19.
 * @since JDK 1.8
 */
@Route(path = Constant.ACTIVITY_ROUTE)
public class RouteWebActivity extends BaseActivity {

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
    public int getLayoutId() {
        return R.layout.activity_base_web;
    }

    @Override
    public void initView() {
        headView.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
            }
        });
        webView.addJavascriptInterface(new RequestHtmlData(), "appDataConfig");
        WebUtils.setWebInfo(webView, URLConstant.ROUTE_URL, new WebUtils.OnfinishListener() {
            @Override
            public void finish() {
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
                headView.setTitle(ObjectUtils.isNotEmpty(title) ? title : "--");
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
                if (url.startsWith("http:") || url.startsWith("https:") || url.startsWith("file:///")) {
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
         * 获取相关的基础数据
         *
         * @return
         */
        @JavascriptInterface
        public String getBaseInfo() {
            String result = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("token", SPUtils.getInstance().getString(SPCommon.TOKEN));
                LogUtils.e("你的token__>" + SPUtils.getInstance().getString(SPCommon.TOKEN));
                jsonObject.put("url", ProjectConfig.BASE_URL);
                jsonObject.put("siteCode", ProjectConfig.SITECODE);
                jsonObject.put("lat", SPUtils.getInstance().getString(SPCommon.LATITUDE));
                jsonObject.put("lng", SPUtils.getInstance().getString(SPCommon.LONGITUDE));
                jsonObject.put("name", ProjectConfig.CITY_NAME);
                jsonObject.put("zoom", SourceConstant.ZOOM);
                jsonObject.put("region", ProjectConfig.REGION);
                jsonObject.put("theme", ProjectConfig.ROUTE_THEME_COLOR);
                String phone = SPUtils.getInstance().getString(SPCommon.PHONE);
                String aesphone = AESEncryptUtil.Encrypt16(phone);
                jsonObject.put("aesPhone", aesphone);
                result = jsonObject.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }


        @JavascriptInterface
        public void getData(String result) {
            LogUtils.e(result);
            ToastUtils.showShortCenter(result);
        }

        /**
         * 跳转景区详情页面
         *
         * @param scenicId 景区ID
         * @param routId   行程ID
         * @param type     类型，1表示从行程跳转的景区详情
         */
        @JavascriptInterface
        public void goToScenicDetails(String scenicId, String routId, String type, String
                isJourney) {
            LogUtils.e(scenicId + "," + routId + "," + type + "," + isJourney);
            ARouter.getInstance().build(Constant.ACTIVITY_SCENIC_DETAIL).withString("type", type)
                    .withString("routId", routId).withString("isJourney", isJourney).withString
                    ("mId", scenicId).navigation(RouteWebActivity.this, 1);
        }

        @JavascriptInterface
        public void joinRoute() {
            String result = "1234";
            webView.loadUrl("javascript:testFun('123456')");
        }

        @JavascriptInterface
        public void backIndex() {
            finish();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 0) {
            LogUtils.e("------------------");
            webView.loadUrl("javascript:testFun('123456')");
        } else if (requestCode == 1 && resultCode == 2) {
            String mRouteId = data.getStringExtra("mRouteId");
            LogUtils.e("获取的ID-->" + mRouteId);
            webView.loadUrl("javascript:refreshList(" + mRouteId + ")");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent
                .ACTION_DOWN) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
            finish();
        }
        return super.dispatchKeyEvent(event);
    }
}
