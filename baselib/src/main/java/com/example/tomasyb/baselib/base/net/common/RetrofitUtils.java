package com.example.tomasyb.baselib.base.net.common;

import com.example.tomasyb.baselib.base.BaseConfig;
import com.example.tomasyb.baselib.base.net.interceptor.HttpCacheInterceptor;
import com.example.tomasyb.baselib.base.net.interceptor.HttpHeaderInterceptor;
import com.example.tomasyb.baselib.base.net.interceptor.LoggingInterceptor;
import com.example.tomasyb.baselib.util.SPUtils;
import com.example.tomasyb.baselib.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


/**
 * Retrofit工具类
 * 构建Retrofit.Builder，并对Okhttp配置
 * 1、 日志log打印json数据
 * 2. 设置Http请求头。防止每次输入
 * 3. 为OkHttp配置缓存。（包括其最大生命和缓存过期时间）
 * 4. 如果采用https，可在此处理证书校验以及服务器校验。
 * 5. 为Retrofit添加GsonConverterFactory
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-7-2.17:17
 * @since JDK 1.8
 */

public class RetrofitUtils {
    /**
     * @return 获取OkHttpClient.Builder
     */
    public static OkHttpClient.Builder getOkBuilder(HashMap<String, String> requestMap) {
        // 缓存文件
        File cacheFile = new File(Utils.getApp().getCacheDir(), "cache");
        // 100Mb
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .readTimeout(BaseConfig.READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(BaseConfig.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggingInterceptor(requestMap))
                // 配置请求头统一配置信息
                .addInterceptor(new HttpHeaderInterceptor())
                .addNetworkInterceptor(new HttpCacheInterceptor())
                // .sslSocketFactory(SslContextFactory.getSSLSocketFactoryForTwoWay())
                // https认证 如果要使用https且为自定义证书
                // 可以去掉这两行注释，并自行配制证书。
                // .hostnameVerifier(new SafeHostnameVerifier())
                .cache(cache)
                // 保持SESSIONID一致
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {//可以做保存cookies操作
                        if (SessionIdSingleton.getSingleton().string == null) {
                            if (cookies != null & cookies.size() > 0) {
                                SessionIdSingleton.getSingleton().string = cookies.get(0).value();
                            }
                        }
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {//加载新的cookies
                        ArrayList<Cookie> cookies = new ArrayList<>();
                        Cookie cookie = new Cookie.Builder()
                                .hostOnlyDomain(url.host())
                                .name("JSESSIONID").value(SPUtils.getInstance().getString(SessionIdSingleton.getSingleton().string))
                                .build();
                        cookies.add(cookie);
                        return cookies;
                    }
                });
    }

    /**
     * @param baseUrl 根地址
     * @return 获取Retrofit.Builder
     */
    public static Retrofit.Builder getRtBuilder(String baseUrl, HashMap<String, String>
            requestMap) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls()
                .create();
        OkHttpClient client = RetrofitUtils.getOkBuilder(requestMap).build();
        return new Retrofit.Builder()
                .client(client)
                // 自定义处理返回数据
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl);
    }


    /**
     * 单一获取Retrofit不结合Rxjava
     *
     * @param baseurl
     * @return
     */
    public static Retrofit getRetrofit(String baseurl, HashMap<String, String> requestMap) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls()
                .create();
        OkHttpClient client = RetrofitUtils.getOkBuilder(requestMap).build();
        return new Retrofit.Builder()
                .client(client)
                // 自定义处理返回数据
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create(gson))
                .baseUrl(baseurl).build();
    }


}
