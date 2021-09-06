package com.daqsoft.http.requestapi.impl;

import android.content.Context;

import com.daqsoft.bean.MarketBean;
import com.daqsoft.http.HttpCallBack;
import com.daqsoft.http.HttpClient;
import com.daqsoft.http.HttpRequestParams;
import com.daqsoft.http.requestapi.RequestApiApiManager;
import com.daqsoft.utils.Config;

import org.xutils.common.util.LogUtil;


/**
 * 获取地图下某类基础资源的所有点位数据
 * @author MouJunFeng
 * @time 2018-4-18.
 * @since JDK 1.8
 * @version 1.0.0
 */

public class GuideMarketSourceApi extends RequestApiApiManager<MarketBean> {
    private String mapGuideid;
    private String sourceType;


    public GuideMarketSourceApi(Context context) {
        super(context);
    }

    public void setMapGuideid(String mapGuideid) {
        this.mapGuideid = mapGuideid;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    @Override
    public void request(String url, Object obj, HttpCallBack<MarketBean> callBack) {
        if (callBack == null) {
            throw new IllegalArgumentException("callBack必须传入");
        }
        callBack.setRequestApi(this);
        if (this.dialog != null && this.dialog.isShowLoading()) {
            this.showLoading();
        }
        HttpRequestParams params = new HttpRequestParams(url);
//        params.setCacheMaxAge(1000 * 60);
        params.addParams("mapGuideSetId", mapGuideid);//地图ID
        params.addParams("siteCode", Config.SITECODE);//设置站点编码
        params.addParams("lang", Config.LANG);//设置获取数据的语言
        params.addParams("sourceType", sourceType);//设置获取数据的语言
        LogUtil.e(params.toString());
        HttpClient.get(params, callBack);
    }
}
