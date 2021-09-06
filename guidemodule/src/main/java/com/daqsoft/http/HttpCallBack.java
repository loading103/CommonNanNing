package com.daqsoft.http;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.LinearLayout;

import com.daqsoft.dialog.ShowDialog;
import com.daqsoft.http.requestapi.IRequestApi;
import com.daqsoft.utils.ShowToast;
import com.daqsoft.utils.Utils;
import com.google.gson.Gson;

import org.xutils.common.Callback;

/**
 * Created by huangx on 2018/3/5.
 * <p>
 * 网络请求回调的二次框架
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-14
 * @since JDK 1.8
 */

public abstract class HttpCallBack<T> implements Callback.CacheCallback<String> {

    private Class<?> clazz;
    private Context context;
    private IRequestApi requestApi;

    public void setRequestApi(IRequestApi requestApi) {
        this.requestApi = requestApi;
    }

    public HttpCallBack(Class<?> clazz, Context context) {
        loading(context);
        this.clazz = clazz;
        this.context = context;
    }

    public HttpCallBack(Context context) {
        loading(context);
        this.context = context;
    }

    @Override
    public void onSuccess(String result) {
        Gson gson = new Gson();
        HttpResultBean<T> bean = null;
        if (clazz == null) {
            bean = (HttpResultBean<T>) gson.fromJson(result, HttpResultBean.class);
        } else {
            bean = (HttpResultBean<T>) gson.fromJson(result, clazz);
        }

        if (bean.getCode() == 0) {
            success(bean);

        } else if (bean.getCode() == 2) {
            ShowToast.showText(context, "登录已失效，请重新登录！");
        } else if (bean.getCode() == 1) {
            error(bean.getCode() + "");

        }

    }

    //错误回调
    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        //        Log.e(ex.toString());
        //        error(ex.toString());
    }

    @Override
    public void onFinished() {
        dismiss();
        requestApi.closeLoading();
    }

    @Override
    public boolean onCache(String result) {
        return false;
    }

    //取消
    @Override
    public void onCancelled(CancelledException cex) {

    }

    public abstract void success(HttpResultBean<T> bean);

    public void error(String e) {
    }


    /**
     * 进度加载框
     */
    private AlertDialog alertDialog = null;

    public void loading(Context context) {
        if (null == alertDialog) {
            alertDialog = ShowDialog.getDialog(context, "数据加载中~").create();
        }
        if (!alertDialog.isShowing()) {
            alertDialog.show();
            alertDialog.getWindow().setLayout(Utils.dip2px(context, 150), LinearLayout
                    .LayoutParams.WRAP_CONTENT);
        }
    }


    /**
     * 隐藏加载框
     */
    public void dismiss() {
        if (null != alertDialog && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
}
