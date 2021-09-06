package com.daqsoft.http;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.View.OnClickListener;

import com.daqsoft.utils.Utils;


/**
 * 监听网络状态的广播
 * @author yulh
 * @create 2015-07-20
 *
 */
public class NetBroad extends BroadcastReceiver {
	private View mBtnState;
	
	public NetBroad(View btnState){
		this.mBtnState=btnState;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo  mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo  wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        
        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
        	mBtnState.setVisibility(View.VISIBLE);
        }else {
        	mBtnState.setVisibility(View.GONE);
        }
	}

	/**
	 * 初始化广播的方法
	 * @param context
	 * @param broad
	 */
	public static void initBroad(Context context,BroadcastReceiver broad){
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.daqsoft.android.closepage");
		context.registerReceiver(broad, intentFilter);
	}

	/**
	 * 注册网络监听广播
	 * 
	 * @create 2015-07-21
	 */
	public static void registerNetReceiver(final View view) {
		IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		NetBroad mNetbroad = new NetBroad(view);
		view.getContext().registerReceiver(mNetbroad, filter);

		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Utils.href2Setting(view.getContext());
			}
		});
	}
	
}
