package com.example.tomasyb.baselib.base.net;

import com.example.tomasyb.baselib.base.net.common.RetrofitUtils;

import java.util.HashMap;

import retrofit2.Retrofit;

/**
 * Created by yanbo on 2018/7/2.
 * 通过RetrofitUtils获取ApiService实例
 */

public class Api {
    public static <T> T getApiService(Class<T> cls, String baseurl, HashMap<String, String>
            requestMap) {
        Retrofit retrofit = RetrofitUtils.getRtBuilder(baseurl, requestMap).build();
        return retrofit.create(cls);
    }

    public static <T> T getWeatherApiService(Class<T> cls, String baseurl, HashMap<String,
            String> requestMap) {
        Retrofit retrofit = RetrofitUtils.getRetrofit(baseurl, requestMap);
        return retrofit.create(cls);
    }
}
