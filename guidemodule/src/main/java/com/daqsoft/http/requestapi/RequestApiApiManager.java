package com.daqsoft.http.requestapi;

import android.content.Context;

import com.daqsoft.http.ILoadingDialog;

/**
 * 请求的父类
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-4-18.
 * @since JDK 1.8
 */

public abstract class RequestApiApiManager<T> implements IRequestApi<T> {
    private Context context;
    /**
     * 请求的提示框接口
     */
    protected ILoadingDialog dialog;

    public RequestApiApiManager(Context context) {
        this.context = context;
    }

    @Override
    public void showLoading() {
        if (dialog != null) {
            dialog.showLoading();
        }
    }

    @Override
    public void closeLoading() {
        if (dialog != null && dialog.isLoading()) {
            dialog.closeLoading();
        }
    }
}
