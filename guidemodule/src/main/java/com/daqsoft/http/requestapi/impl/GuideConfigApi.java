package com.daqsoft.http.requestapi.impl;

import android.content.Context;

import com.daqsoft.bean.MapConfigBean;
import com.daqsoft.http.HttpCallBack;
import com.daqsoft.http.HttpClient;
import com.daqsoft.http.HttpRequestParams;
import com.daqsoft.http.requestapi.RequestApiApiManager;
import com.daqsoft.utils.Config;

/**
 * 地图配置API
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-4-18.
 * @since JDK 1.8
 */

public class GuideConfigApi extends RequestApiApiManager<MapConfigBean> {
    private String mapGuideSetId;

    public void setMapGuideSetId(String mapGuideSetId) {
        this.mapGuideSetId = mapGuideSetId;
    }

    public GuideConfigApi(Context context) {
        super(context);
    }

    @Override
    public void request(String url, Object obj, HttpCallBack<MapConfigBean> callBack) {
        if (callBack == null) {
            throw new IllegalArgumentException("callBack必须传入");
        }
        callBack.setRequestApi(this);
        if (this.dialog != null && this.dialog.isShowLoading()) {
            this.showLoading();
        }
        HttpRequestParams params = new HttpRequestParams(url);
        // 地图ID
        params.addParams("mapGuideSetId", mapGuideSetId);
        // 设置站点编码
        params.addParams("siteCode", Config.SITECODE);
        // 设置获取数据的语言
        params.addParams("lang", Config.LANG);
        HttpClient.get(params, callBack);
    }
}
