package com.daqsoft.commonnanning.helps_gdmap;

import com.amap.api.location.AMapLocation;


/**
 *数据完成回调
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-4.15:50
 * @since JDK 1.8
 */
public interface CompleteFuncData {
    /**
     * 请求完成后回调
     *
     * @param location 返回的数据 返回2:表示没网,返回3:表示返回数据为""(空)
     */
    void success(AMapLocation location);

}