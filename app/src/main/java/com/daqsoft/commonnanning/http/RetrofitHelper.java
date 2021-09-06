package com.daqsoft.commonnanning.http;


import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.common.URLConstant;
import com.example.tomasyb.baselib.base.net.Api;

/**
 * 网络请求所有接口的服务类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/19
 * @since JDK 1.8
 */
public class RetrofitHelper {
    public static HttpApiService httpApiService;
    public static HttpApiService httpApiServiceFileUpload;
    public static HttpApiService httpApiServiceYbb;

    public static HttpApiService getApiService() {
        return httpApiService;
    }

    static {
        httpApiService = Api.getApiService(HttpApiService.class, ProjectConfig.BASE_URL,
                HttpApiService.REQUESTMAP);
    }

    public static HttpApiService getHttpApiServiceFileUpload() {
        return httpApiServiceFileUpload;
    }

    static {
        httpApiServiceFileUpload = Api.getApiService(HttpApiService.class, URLConstant.FILE_UPLOAD, null);
    }


    public static HttpApiService getHttpApiServiceYbb() {
        return httpApiServiceYbb;
    }

    static {
        httpApiServiceYbb = Api.getApiService(HttpApiService.class, URLConstant.YBB_URL, null);
    }

}
