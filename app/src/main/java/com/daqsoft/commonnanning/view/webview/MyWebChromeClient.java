package com.daqsoft.commonnanning.view.webview;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;


/**
 * Used 处理解析，渲染网页等浏览器做的事情。辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
public class MyWebChromeClient extends WebChromeClient {
	private static final String TAG = MyWebChromeClient.class.getSimpleName();
	/**依赖的窗口*/
	private Context context;
	private MyWebView myWebView;

	public MyWebChromeClient(Context context, MyWebView myWebView) {
		this.context = context;
		this.myWebView = myWebView;
	}
	@Override
	public void onProgressChanged(WebView view, int newProgress) {
		// TODO Auto-generated method stub
		super.onProgressChanged(view, newProgress);
	}

	/**
	 * 重写WebChromeClient 的openFileChooser方法
	 * 这里有个漏洞，4.4.x的由于系统内核发生了改变，没法调用以上方法，现在仍然找不到解决办法，唯一的方法就是4.4直接使用手机浏览器打开，这个是可以的。
	 * */
	private ValueCallback<Uri> mUploadMessage;
	private ValueCallback<Uri[]> mUploadCallbackAboveL;

	public void openFileChooser(ValueCallback<Uri> uploadMsg) {
		openFileChooserImpl(uploadMsg);
	}

	public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
		openFileChooserImpl(uploadMsg);
	}
	public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
		openFileChooserImpl(uploadMsg);
	}
	@Override
	public boolean onShowFileChooser(WebView webView,
                                     ValueCallback<Uri[]> filePathCallback,
                                     FileChooserParams fileChooserParams) {
		openFileChooserImplForAndroid5(filePathCallback);
		return true;
	}
	private void openFileChooserImpl(ValueCallback<Uri> uploadMsg) {
		mUploadMessage = uploadMsg;
		dispatchTakePictureIntent();
	}
	private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
		mUploadCallbackAboveL = uploadMsg;
		dispatchTakePictureIntent();
	}

	/**
	 * 拍照或者打开文件管理器
	 */
	private void dispatchTakePictureIntent() {
		if(mUploadMessage!=null){
			Log.w(TAG,"mUploadMessage.toString()="+mUploadMessage.toString());
		}
		if(mUploadCallbackAboveL!=null) {
			Log.w(TAG, "mUploadCallbackAboveL.toString()=" + mUploadCallbackAboveL.toString());
		}
		WebViewJSInterface.getInstance(context,myWebView).chooseFile();
	}

	public ValueCallback<Uri> getmUploadMessage() {
		return mUploadMessage;
	}

	public ValueCallback<Uri[]> getmUploadCallbackAboveL() {
		return mUploadCallbackAboveL;
	}

	public void setmUploadMessage(ValueCallback<Uri> mUploadMessage) {
		this.mUploadMessage = mUploadMessage;
	}

	public void setmUploadCallbackAboveL(ValueCallback<Uri[]> mUploadCallbackAboveL) {
		this.mUploadCallbackAboveL = mUploadCallbackAboveL;
	}
	
}
