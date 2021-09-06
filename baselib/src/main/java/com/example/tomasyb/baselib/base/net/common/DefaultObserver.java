package com.example.tomasyb.baselib.base.net.common;

import com.example.tomasyb.baselib.R;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.base.net.exception.NoDataExceptionException;
import com.example.tomasyb.baselib.base.net.exception.ServerResponseException;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.ToastUtils;
import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by yanb on 2017/4/18.
 * 处理服务器响应
 */

public abstract class DefaultObserver<T> implements Observer<BaseResponse<T>> {
    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(BaseResponse<T> response) {
        LogUtils.d(response.toString());
        if (2 == response.getCode()) {
            SPUtils.getInstance().put("token", "");
            onError(response);
        } else if (response.getCode() == 0) {
            onSuccess(response);
        } else {
            onFail(response.getMessage());
        }
        onFinish();
    }

    public void onError(BaseResponse<T> response) {

    }

    @Override
    public void onError(Throwable e) {
        LogUtils.e("Retrofit", e.getMessage());
        if (e instanceof HttpException) {
            //   连接错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            //   连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {
            //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //  解析错误
            onException(ExceptionReason.PARSE_ERROR);
        } else if (e instanceof ServerResponseException) {
            onFail(e.getMessage());
        } else if (e instanceof NoDataExceptionException) {
            onSuccess(null);
        } else {
            onException(ExceptionReason.UNKNOWN_ERROR);
        }

        ProgressUtils.dismissProgress();
        onFinish();
    }

    @Override
    public void onComplete() {
        //        ProgressUtils.dismissProgress();
    }

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    abstract public void onSuccess(BaseResponse<T> response);

    /**
     * 服务器返回数据，但响应码不为200
     *
     */
    /**
     * 服务器返回数据，但响应码不为1000
     */
    public void onFail(String message) {
        ToastUtils.showLongCenter(message);
    }

    public void onFinish() {
        ProgressUtils.dismissProgress();
    }

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtils.showLongCenter(R.string.b_connect_error);
                break;

            case CONNECT_TIMEOUT:
                ToastUtils.showLongCenter(R.string.b_connect_timeout);
                break;

            case BAD_NETWORK:
                ToastUtils.showLongCenter(R.string.b_bad_network);
                break;

            case PARSE_ERROR:
                ToastUtils.showLongCenter(R.string.b_parse_error);
                break;

            case UNKNOWN_ERROR:
            default:
//                ToastUtils.showLongCenter(R.string.b_unknown_error);
                break;
        }
    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
        /**
         * 服务器异常
         */
        SERVICE_ERROR,
    }
}

