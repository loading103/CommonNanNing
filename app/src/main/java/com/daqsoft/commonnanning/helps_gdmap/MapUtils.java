package com.daqsoft.commonnanning.helps_gdmap;

import android.app.Activity;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.example.tomasyb.baselib.util.ObjectUtils;
import com.example.tomasyb.baselib.util.ToastUtils;

import java.text.DecimalFormat;

/**
 * 地图工具类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/25 0025
 * @since JDK 1.8
 */
public class MapUtils {


    /**
     * 获取自己的定位
     */
    public static void GoMapNavi(final Activity activity, final String dLat, final String
            dLng, final String dName) {
        HelpMaps.startLocation(new CompleteFuncData() {
            @Override
            public void success(AMapLocation location) {
                HelpMaps.stopLocation();
                if (ObjectUtils.isNotEmpty(location)) {
                    if (ObjectUtils.isNotEmpty(dLat) && ObjectUtils.isNotEmpty
                            (dLng) && ObjectUtils.isNotEmpty(dName)) {
                        MapNaviUtils.isMapNaviUtils(activity, location.getLatitude() + "", location.getLongitude() + "", location.getAddress(), dLat, dLng, dName);
                    }else{
                        ToastUtils.showShortCenter("数据缺失，无法进行导航操作");
                    }
                }else{
                    ToastUtils.showShortCenter("获取位置失败，请检查是否开启定位服务");
                }
            }
        });
    }
    /**
     * 计算两个点之间的直线距离
     *
     * @param lat1 第一个点的纬度
     * @param lng1 第一个点经度
     * @param lat2 第2个点的纬度
     * @param lng2 第2个点经度
     */
    public static String calculateLineDistance(String lat1, String lng1, String lat2, String lng2) {
        String distance = "0.00";
        if (ObjectUtils.isNotEmpty(lat1) && ObjectUtils.isNotEmpty(lng1)
                && ObjectUtils.isNotEmpty(lat2) && ObjectUtils.isNotEmpty(lng2)) {
            LatLng latLng1 = new LatLng(Double.parseDouble(lat1), Double.parseDouble(lng1));
            LatLng latLng2 = new LatLng(Double.parseDouble(lat2), Double.parseDouble(lng2));
            distance = new DecimalFormat("0.00")
                    .format((AMapUtils.calculateLineDistance(latLng1, latLng2)) / 1000);
        }
        return distance + "KM";
    }


}
