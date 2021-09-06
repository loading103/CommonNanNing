package com.example.tomasyb.baselib.base.net.interceptor;

import com.example.tomasyb.baselib.util.LogUtils;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by yanbo on 2018/4/20.
 * <p>
 * 日志拦截器可以打印json数据
 */

public class LoggingInterceptor implements Interceptor {
    private HashMap<String, String> requestMap = new HashMap();
    /**
     * 公共参数
     *
     * @param map
     */
    public LoggingInterceptor(HashMap<String, String> map) {
        this.requestMap = map;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        // 这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request oldRequest = chain.request();

        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host());
        if (requestMap != null) {
            //第三种：推荐，尤其是容量大时
            for (HashMap.Entry<String, String> entry : requestMap.entrySet()) {
                authorizedUrlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();

        // 打印网络请求链接和数据结果
        LogUtils.e(newRequest.url());
        Response response = chain.proceed(newRequest);
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        LogUtils.e(response.request().url() + "\n" + responseBody.string());
        return response;
    }
}
