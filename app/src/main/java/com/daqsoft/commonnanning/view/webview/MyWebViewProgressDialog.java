package com.daqsoft.commonnanning.view.webview;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.daqsoft.commonnanning.R;


/**
 * @Created HaiyuKing
 * @Used  网页加载时的进度对话框
 */
public class MyWebViewProgressDialog extends AlertDialog {

	/**对话框依赖的窗口*/
	private Context context;

	public MyWebViewProgressDialog(Context context) {
		this(context, R.style.mywebview_loading_style);
	}
	/**
	 * 使用指定样式
	 * @param context
	 * @param theme
	 */
	public MyWebViewProgressDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 引用布局文件
		setContentView(R.layout.mywebview_dialog_webviewprogress);
		// 设置为false，按对话框以外的地方不起作用
		setCanceledOnTouchOutside(true);
		// 设置为false，按返回键不能退出
		setCancelable(true);
		
	}
}
