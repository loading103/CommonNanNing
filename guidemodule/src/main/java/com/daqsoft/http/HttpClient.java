package com.daqsoft.http;

import org.xutils.x;

/**
 * 请求客户端
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-14
 * @since JDK 1.8
 */

public class HttpClient {
    /**
     * 请求参数对象
     */
    private HttpRequestParams params;

    public static void get(HttpRequestParams params, HttpCallBack callBack) {
        x.http().get(params, callBack);
        /*OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.build();
        Call call = client.newCall(null).execute()*/
    }

    public static void post(HttpRequestParams params, HttpCallBack callBack) {
        x.http().post(params, callBack);
    }

}
