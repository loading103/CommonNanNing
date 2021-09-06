package com.daqsoft.http.requestapi;

import java.util.HashMap;
import java.util.Map;

/**
 * 该类提供API的服务请求
 *  @author MouJunFeng
 * @time 2018-4-18.
 * @since JDK 1.8
 * @version 1.0.0
 */

public class RequestApiProvider {
    //保存服务
    public static final Map<String, IRequestApi> apiMap = new HashMap<String, IRequestApi>();

    /**
     * 注册服务
     * @param url 地址
     * @param requestApi 服务对象
     * @return 服务对象
     */
    public static IRequestApi registerApiService(String url, IRequestApi requestApi) {
        IRequestApi api = apiMap.get(url);
        if (api == null) {
            apiMap.put(url, requestApi);
            api = requestApi;
        }
        return api;

    }

    /**
     * 获得服务
     * @param url
     * @return
     */
    public static IRequestApi getAPI(String url) {
        return apiMap.get(url);
    }
}
