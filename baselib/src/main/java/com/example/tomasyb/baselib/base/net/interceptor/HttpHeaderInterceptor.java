package com.example.tomasyb.baselib.base.net.interceptor;

import android.annotation.SuppressLint;

import com.example.tomasyb.baselib.base.api.ApiConstants;
import com.example.tomasyb.baselib.util.PhoneUtils;
import com.example.tomasyb.baselib.util.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhpan on 2018/3/21.
 */

public class HttpHeaderInterceptor implements Interceptor {

    public  boolean isAgreePrivate ; //是否统一隐私协议
    @Override
    public Response intercept(Chain chain) throws IOException {

        String deviceSn="";
        isAgreePrivate = SPUtils.getInstance().getBoolean(ApiConstants.IS_FIRST_AGREE,false);
        if (isAgreePrivate ) {
            deviceSn=PhoneUtils.getSerial();
        } else {
            deviceSn="";
        }

        //  配置请求头
        @SuppressLint("MissingPermission") Request request = chain.request().newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "application/json")
                .header("Connection", "close")
                .addHeader("Accept-Encoding", "identity")
                .addHeader("Accept-Language", "zh-cn,zh;q=0.9")
                .addHeader("deviceSn",deviceSn )
                .build();
        return chain.proceed(request);
    }
}
