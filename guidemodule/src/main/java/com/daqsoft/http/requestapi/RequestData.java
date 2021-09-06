package com.daqsoft.http.requestapi;

import com.daqsoft.http.HttpCallBack;
import com.daqsoft.http.HttpClient;
import com.daqsoft.http.HttpRequestParams;
import com.daqsoft.utils.Config;
import com.daqsoft.utils.Constant;

import org.xutils.common.util.LogUtil;

/**
 * 数据请求类
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-5-15
 * @since JDK 1.8
 */

public class RequestData {
    /**
     * 获得路线列表
     *
     * @param mapGuideSetId 地图ID
     */
    public static void requestGuideWalk(String mapGuideSetId, HttpCallBack callBack) {
        HttpRequestParams params = new HttpRequestParams(Config.BASEURL + Constant.GUIDE_WALK);
        // 设置站点编码
        params.addParams("siteCode", Config.SITECODE);
        // 设置获取数据的语言
        params.addParams("lang", Config.LANG);
        // 设置获取数据的语言
        params.addParams("mapGuideSetId", mapGuideSetId);
        LogUtil.e(params.toString());
        HttpClient.get(params, callBack);
    }

    /**
     * 获得路线Bean对象
     *
     * @param mapGuideLineId 路线ID
     */
    public static void requestGuideWalkBean(String mapGuideLineId, HttpCallBack callBack) {
        HttpRequestParams params = new HttpRequestParams(Config.BASEURL + Constant
                .GUIDE_WALK_DETAIL);
        // 设置站点编码
        params.addParams("siteCode", Config.SITECODE);
        // 设置获取数据的语言
        params.addParams("lang", Config.LANG);
        //设置获取数据的语言
        params.addParams("mapGuideLineId", mapGuideLineId);
        LogUtil.e(params.toString());
        HttpClient.get(params, callBack);
    }
}
