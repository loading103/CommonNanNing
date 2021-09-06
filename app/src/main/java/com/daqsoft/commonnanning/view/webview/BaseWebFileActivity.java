package com.daqsoft.commonnanning.view.webview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import com.daqsoft.commonnanning.R;
import com.example.tomasyb.baselib.base.mvp.BaseActivity;
import com.example.tomasyb.baselib.base.mvp.IBasePresenter;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.example.tomasyb.baselib.widget.HeadView;
import java.io.File;
import butterknife.BindView;

/**
 * 可访问文件的网页
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
public class BaseWebFileActivity extends BaseActivity {
    @BindView(R.id.headView)
    HeadView headView;
    /**
     * 自定义web
     */
    private MyWebView myWebView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_base_web_file;
    }

    @Override
    public void initView() {
        initViews();
        initDatas();
        initEvents();
    }

    private void initEvents() {

    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        String openUrl = getIntent().getExtras().getString("urlKey");
        if (TextUtils.isEmpty(openUrl)) {
            myWebView.loadLocalUrl("demo.html");
        } else {
            myWebView.loadWebUrl(openUrl);
        }
    }

    private void initViews() {
        String title = getIntent().getExtras().getString("title");
        if (TextUtils.isEmpty(title)) {
            headView.setTitle("在线投诉");
        } else {
            headView.setTitle(title);
        }
        headView.setBackListener(new HeadView.OnBackListener() {
            @Override
            public void onBack(View v) {
                if (myWebView.canGoBack()) {
                    myWebView.goBack();
                } else {
                    myWebView.loadUrl("about:blank");
                    finish();
                }
            }
        });
        myWebView = findViewById(R.id.web_view);
        // 可以返回上一页
        myWebView.setCanBackPreviousPage(true, BaseWebFileActivity.this);
    }

    @Override
    public void onDestroy() {
        // 销毁webview控件
        myWebView.removeAllViews();
        myWebView.destroy();
        super.onDestroy();
    }

    /**
     * 实现webview调用相机、打开文件管理器功能
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // webview界面调用打开本地文件管理器选择文件的回调
            if (requestCode == WebviewGlobals.CHOOSE_FILE_REQUEST_CODE) {
                Uri result = data == null ? null : data.getData();

                // 如果mUploadMessage或者mUploadCallbackAboveL不为空，代表是触发input[type]类型的标签
                if (null != myWebView.getMyWebChromeClient().getmUploadMessage() || null != myWebView.getMyWebChromeClient().getmUploadCallbackAboveL()) {
                    if (myWebView.getMyWebChromeClient().getmUploadCallbackAboveL() != null) {
                        onActivityResultAboveL(requestCode, data);//5.0++
                    } else if (myWebView.getMyWebChromeClient().getmUploadMessage() != null) {
                        myWebView.getMyWebChromeClient().getmUploadMessage().onReceiveValue(result);
                        // 将文件路径返回去，填充到input中
                        myWebView.getMyWebChromeClient().setmUploadMessage(null);
                    }
                } else {
                    // 此处代码是处理通过js方法触发的情况
                    String filePath = GetPathFromUri4kitkat.getPath(BaseWebFileActivity.this, Uri.parse(result.toString()));

                    setUrlPathInput(myWebView, "打开本地相册：" + filePath);
                    // 修改网页输入框文本
                }
            }
            // 因为拍照指定了路径，所以data值为null
            if (requestCode == WebviewGlobals.CAMERA_REQUEST_CODE) {
                File pictureFile = new File(WebViewJSInterface.mCurrentPhotoPath);

                Uri uri = Uri.fromFile(pictureFile);
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(uri);
                BaseWebFileActivity.this.sendBroadcast(intent);
                // 这里我们发送广播让MediaScanner 扫描我们制定的文件
                // 这样在系统的相册中我们就可以找到我们拍摄的照片了【但是这样一来，就会执行MediaScanner服务中onLoadFinished方法，所以需要注意】

                // 拍照
//				String fileName = FileUtils.getFileName(WebViewJSInterface.mCurrentPhotoPath);
                setUrlPathInput(myWebView, "打开相机：" + WebViewJSInterface.mCurrentPhotoPath);
                // 修改网页输入框文本
            }

            // 录音
            if (requestCode == WebviewGlobals.RECORD_REQUEST_CODE) {
                Uri result = data == null ? null : data.getData();
                String filePath = GetPathFromUri4kitkat.getPath(BaseWebFileActivity.this, Uri.parse(result.toString()));
                setUrlPathInput(myWebView, "打开录音：" + filePath);
                // 修改网页输入框文本
            }
        } else if (resultCode == RESULT_CANCELED) {
            // resultCode == RESULT_CANCELED 解决不选择文件，直接返回后无法再次点击的问题
            if (myWebView.getMyWebChromeClient().getmUploadMessage() != null) {
                myWebView.getMyWebChromeClient().getmUploadMessage().onReceiveValue(null);
                myWebView.getMyWebChromeClient().setmUploadMessage(null);
            }
            if (myWebView.getMyWebChromeClient().getmUploadCallbackAboveL() != null) {
                myWebView.getMyWebChromeClient().getmUploadCallbackAboveL().onReceiveValue(null);
                myWebView.getMyWebChromeClient().setmUploadCallbackAboveL(null);
            }
        }
    }

    /**
     * 5.0以上版本，由于api不一样，要单独处理
     * @param requestCode
     * @param data
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, Intent data) {

        if (myWebView.getMyWebChromeClient().getmUploadCallbackAboveL() == null) {
            return;
        }
        Uri result = null;
        if (requestCode == WebviewGlobals.CHOOSE_FILE_REQUEST_CODE) {
            result = data == null ? null : data.getData();
        } else if (requestCode == WebviewGlobals.CAMERA_REQUEST_CODE) {
            File pictureFile = new File(WebViewJSInterface.mCurrentPhotoPath);

            Uri uri = Uri.fromFile(pictureFile);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(uri);
            // 这里我们发送广播让MediaScanner 扫描我们制定的文件
            BaseWebFileActivity.this.sendBroadcast(intent);
            // 这样在系统的相册中我们就可以找到我们拍摄的照片了【但是这样一来，就会执行MediaScanner服务中onLoadFinished方法，所以需要注意】

            result = Uri.fromFile(pictureFile);
        }
        myWebView.getMyWebChromeClient().getmUploadCallbackAboveL().onReceiveValue(new Uri[]{result});
        // 将文件路径返回去，填充到input中
        myWebView.getMyWebChromeClient().setmUploadCallbackAboveL(null);
        return;
    }


    /**
     * 设置网页上的文件路径输入框文本
     * @param webView
     * @param urlPath
     */
    private void setUrlPathInput(WebView webView, String urlPath) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("setInputText('" + urlPath + "')", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {

                }
            });
        } else {
            ToastUtils.showLong("当前版本号小于19，无法支持evaluateJavascript，需要使用第三方库JSBridge");
        }
    }

    @Override
    public IBasePresenter initPresenter() {
        return null;
    }
}
