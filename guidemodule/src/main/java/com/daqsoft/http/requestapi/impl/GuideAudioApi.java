package com.daqsoft.http.requestapi.impl;

import android.content.Context;

import com.daqsoft.http.HttpCallBack;
import com.daqsoft.http.HttpClient;
import com.daqsoft.http.HttpRequestParams;
import com.daqsoft.http.requestapi.RequestApiApiManager;
import com.daqsoft.utils.Config;

import org.xutils.common.util.LogUtil;

/**
 * 获得音频文件请求
 * @author MouJunFeng
 * @time 2018-4-27
 * @since JDK 1.8
 * @version 1.0.0
 */

public class GuideAudioApi extends RequestApiApiManager<String> {
    public GuideAudioApi(Context context) {
        super(context);
    }
    public String id;

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void request(String url, Object obj, HttpCallBack<String> callBack) {
        if (callBack == null) {
            throw new IllegalArgumentException("callBack必须传入");
        }
        callBack.setRequestApi(this);
        if (this.dialog != null && this.dialog.isShowLoading()) {
            this.showLoading();
        }
        HttpRequestParams params = new HttpRequestParams(url);
        params.setConnectTimeout(60*1000);
        params.addParams("id", id);//地图ID
        params.addParams("siteCode", Config.SITECODE);//设置站点编码
        params.addParams("lang", Config.LANG);//设置获取数据的语言
        LogUtil.e(params.toString());
        HttpClient.get(params, callBack);
    }
}
