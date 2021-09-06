package com.example.tomasyb.baselib.web;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.widget.LoadingTip;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * web的初始化设置工具类
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018.05.07
 * @since JDK 1.8
 */

public class WebUtils {
    public interface OnfinishListener{
        void finish();
    }

    /**
     * 让WebView的内容宽度，自适应手机屏幕的宽度
     *
     * @param htmltext
     * @return
     */
    public static String getNewContent(String htmltext) {

        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }

        android.util.Log.d("VACK", doc.toString());
        return doc.toString();
    }

    /**
     * @param mWebView 加载网页的地址
     * @param loadUrl  加载的网页
     */
    public static void setWebInfo(final ProgressWebView mWebView, String loadUrl,OnfinishListener listener) {
        // web设置
        mWebView.requestFocus();
        //  获取WebView的设置
        WebSettings webSettings = mWebView.getSettings();
        // 不使用缓存：
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 将JavaScript设置为可用，这一句话是必须的，不然所做一切都是徒劳的
        webSettings.setJavaScriptEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        // 设置加载进来的页面自适应手机屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // 给webview添加JavaScript接口
        // 设置WebView支持手势
        mWebView.requestFocusFromTouch();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(true);
        }
        // 设置js可访问文件
        webSettings.setAllowFileAccess(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        String userAgent = webSettings.getUserAgentString();
        webSettings.setUserAgentString(userAgent + " mobile daqsoft.com");
        CookieManager.getInstance().setAcceptCookie(true);
        mWebView.setWebViewClient(new MyWebViewClient(new MyWebViewClient.OnWebviewPageFinished() {
            @Override
            public void onFinished() {
                listener.finish();
            }
        }));
        mWebView.setWebChromeClient(new MWebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                //                if (title.contains("undefined")) {
                //                    return;
                //                }
                //                tvTitleName.setText(title.contains("找不到网页") || title.contains
                // ("无法访问") || title.contains("服务器") ? getString(R.string.app_name) : title);
                //                try {
                //                    super.onReceivedTitle(view, title);
                //                } catch (Exception e) {
                //                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mWebView.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });

        // 通过webview加载html页面
        if (loadUrl.startsWith("http://") || loadUrl.startsWith("https://") || loadUrl.startsWith
                ("file:///")) {
            mWebView.loadUrl(loadUrl);
        } else {
            mWebView.loadData(getNewContent(loadUrl), "text/html; charset=UTF-8", null);
        }
    }
    /**
     * @param mWebView 加载网页的地址
     * @param loadUrl  加载的网页
     */
    public static void setWebInfo3(final ProgressWebView mWebView, String loadUrl, LoadingTip loadingTip) {
        // web设置
        mWebView.requestFocus();
        //  获取WebView的设置
        WebSettings webSettings = mWebView.getSettings();
        // 不使用缓存：
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 将JavaScript设置为可用，这一句话是必须的，不然所做一切都是徒劳的
        webSettings.setJavaScriptEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        // 设置加载进来的页面自适应手机屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // 给webview添加JavaScript接口
        // 设置WebView支持手势
        mWebView.requestFocusFromTouch();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.setAllowFileAccessFromFileURLs(true);
        }
        // 设置js可访问文件
        webSettings.setAllowFileAccess(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        String userAgent = webSettings.getUserAgentString();
        webSettings.setUserAgentString(userAgent + " mobile daqsoft.com");
        CookieManager.getInstance().setAcceptCookie(true);
        mWebView.setWebViewClient(new MyWebViewClient(new MyWebViewClient.OnWebviewPageFinished() {
            @Override
            public void onFinished() {
                try {
                    if (ObjectUtils.isNotEmpty(loadingTip)){
                        loadingTip.setLoadingTip(LoadingTip.LoadStatus.finish);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }));
        mWebView.setWebChromeClient(new MWebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                //                if (title.contains("undefined")) {
                //                    return;
                //                }
                //                tvTitleName.setText(title.contains("找不到网页") || title.contains
                // ("无法访问") || title.contains("服务器") ? getString(R.string.app_name) : title);
                //                try {
                //                    super.onReceivedTitle(view, title);
                //                } catch (Exception e) {
                //                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mWebView.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });

        // 通过webview加载html页面
        if (loadUrl.startsWith("http://") || loadUrl.startsWith("https://") || loadUrl.startsWith
                ("file:///")) {
            mWebView.loadUrl(loadUrl);
        } else {
            mWebView.loadData(getNewContent(loadUrl), "text/html; charset=UTF-8", null);
        }
    }
    /**
     * 加载网页配置，微信支付拦截
     * @param webBase
     * @param loadUrl
     * @param context
     */
    public static void setWebInfo2(final ProgressWebView webBase, String loadUrl, Context context,OnfinishListener listener) {
        WebSettings webSettings = webBase.getSettings();
      /*  if (Build.VERSION.SDK_INT >= 19) {
            // 加载缓存否则网络
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }*/
        if (Build.VERSION.SDK_INT >= 19) {
            // 图片自动缩放 打开
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            // 图片自动缩放 关闭
            webSettings.setLoadsImagesAutomatically(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // 软件解码
            webBase.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        // 硬件解码
        webBase.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        // 不使用缓存：
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
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
        webBase.setSaveEnabled(true);
        webBase.setKeepScreenOn(true);
        // 设置setWebChromeClient对象
        webBase.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                super.onProgressChanged(view, newProgress);
                webBase.setProgress(newProgress);
            }
        });
        // 设置此方法可在WebView中打开链接，反之用浏览器打开
        webBase.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                listener.finish();
                super.onPageFinished(view, url);
                if (!webBase.getSettings().getLoadsImagesAutomatically()) {
                    webBase.getSettings().setLoadsImagesAutomatically(true);
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
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.startsWith("http:") || url.startsWith("https:")) {
                    WebView.HitTestResult hitTestResult = view.getHitTestResult();
                    if (!TextUtils.isEmpty(url) && hitTestResult == null){
                        view.loadUrl(url);
                        if (url.equals("https://m.ctrip.com/webapp/vacations/tour/vacations")){

                        }
                        return true;
                    }
                    return super.shouldOverrideUrlLoading(view, url);
                }

                // Otherwise allow the OS to handle things like tel, mailto, etc.
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
        // 通过webview加载html页面
        if (loadUrl.startsWith("http://") || loadUrl.startsWith("https://") || loadUrl.startsWith
                ("file:///")) {
            webBase.loadUrl(loadUrl);
        } else {
            webBase.loadData(getNewContent(loadUrl), "text/html; charset=UTF-8", null);
        }

    }

}
