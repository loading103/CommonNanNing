package com.daqsoft.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.daqsoft.http.DefaultProgressCallBack;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * 文件下载服务
 * @author MouJunFeng
 * @time 2018-3-14
 * @since JDK 1.8
 * @version 1.0.0
 */

public class DownloadFileService extends Service {
    private MyBinder binder = new MyBinder();
    private Callback.Cancelable cancelable;

    private DefaultProgressCallBack<File> callback;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /**
     * 文件下载
     *
     * @param url
     * @param path
     */
    public void download(String url, String path) {
        if (callback == null) {
            return;
        }
        RequestParams requestParams = new RequestParams(url);
        if(!TextUtils.isEmpty(path)){
            requestParams.setSaveFilePath(path);
        }
        cancelable = x.http().get(requestParams, callback);
    }

    /**
     *暂停
     */
    public void pause() {
        if (cancelable != null) {
            cancelable.cancel();
        }
    }

    /**
     * 设置回调
     * @param callback
     */
    public void setCallback(DefaultProgressCallBack<File> callback) {
        this.callback = callback;
    }

    /**
     * 服务绑定
     */
    public class MyBinder extends Binder {
        public DownloadFileService getDownloadFileService() {
            return DownloadFileService.this;
        }
    }
}
