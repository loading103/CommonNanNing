package com.daqsoft.busquery.http;

import com.example.tomasyb.baselib.base.app.BaseApplication;
import com.example.tomasyb.baselib.base.net.Api;


/**
 * 网络请求所有接口的服务类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/19
 * @since JDK 1.8
 */
public class BusRetrofitHelper {
    public static BusApiService httpApiService;

    public static BusApiService getApiService() {
        /**
         * 公共参数对象
         */
        httpApiService = Api.getApiService(BusApiService.class, BaseApplication.getBaseUrl(), BaseApplication.getREQUESTMAP());
        return httpApiService;
    }

}
