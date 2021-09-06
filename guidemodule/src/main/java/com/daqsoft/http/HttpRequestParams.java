package com.daqsoft.http;

import org.xutils.http.RequestParams;

/**
 * Created by huangx on 2018/3/5.
 * 网络请求数据参数的统一参数配置
 * @author MouJunFeng
 * @time 2018-3-14
 * @since JDK 1.8
 * @version 1.0.0
 */

public class HttpRequestParams extends RequestParams {

    public HttpRequestParams(String url){
        super(url);
    }

    public void addParams(String key,String value){
        addBodyParameter(key,value);
    }

}
