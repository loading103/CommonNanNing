package com.daqsoft.http.requestapi.impl;

import android.content.Context;

import com.daqsoft.bean.MapConfigureBean;
import com.daqsoft.http.HttpCallBack;
import com.daqsoft.http.HttpClient;
import com.daqsoft.http.HttpRequestParams;
import com.daqsoft.http.requestapi.RequestApiApiManager;
import com.daqsoft.utils.Config;

import org.xutils.common.util.LogUtil;


/**
 * 获取地图配置API
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-4-18.
 * @since JDK 1.8
 */

public class MapGuideApi extends RequestApiApiManager<MapConfigureBean> {

    private String id;

    public MapGuideApi(Context context) {
        super(context);
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public void request(String url, Object obj, HttpCallBack<MapConfigureBean> callBack) {
        if (callBack == null) {
            throw new IllegalArgumentException("callBack必须传入");
        }
        callBack.setRequestApi(this);
        if (this.dialog != null && this.dialog.isShowLoading()) {
            this.showLoading();
        }
        HttpRequestParams params = new HttpRequestParams(url);
        params.addParams("id", id);
        // 设置站点编码
        params.addParams("siteCode", Config.SITECODE);
        // 设置获取数据的语言
        params.addParams("lang", Config.LANG);
        LogUtil.e(params.toString());
        HttpClient.get(params, callBack);
    }

}
