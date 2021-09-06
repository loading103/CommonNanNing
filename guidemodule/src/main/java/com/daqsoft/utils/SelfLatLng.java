package com.daqsoft.utils;

import android.util.Log;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;

import java.lang.reflect.Method;
import java.text.DecimalFormat;

/**
 * 通过反射的方式获取自身的经纬度
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018/8/27 0027
 * @since JDK 1.8
 */
public class SelfLatLng {


    /**
     * 通过反射找到的CommonShare的方法
     */
    private static Method sfWindow;

    /**
     * 通过反射的方式获取自身的经纬度
     *
     * @return
     */
    public static LatLng getSelfLatLng() {
        LatLng latLng = null;
        String shareClassName = "com.daqsoft.commonnanning.utils.Utils";
        try {
            Class sfClass = Class.forName(shareClassName);
            // 反射出该Class类中的show()方法
            sfWindow = sfClass.getDeclaredMethod("getCurrentLocation", String.class);
            // 取消访问私有方法的合法性检查
            sfWindow.setAccessible(true);
            // 调用show()方法
            Object lastLat = sfWindow.invoke(sfClass.newInstance(), "latitude");
            Object lastLng = sfWindow.invoke(sfClass.newInstance(), "longitude");
            if (Utils.isnotNull(lastLat) && Utils.isnotNull(lastLng)) {
                latLng = new LatLng(Double.parseDouble(lastLat + ""), Double.parseDouble(lastLng
                        + ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("反射异常", e.toString());
        }
        return latLng;
    }

    public static void goToPage() {
        LatLng latLng = null;
        String shareClassName = "com.daqsoft.android.common.SpFile";
        try {
            Class sfClass = Class.forName(shareClassName);
            // 反射出该Class类中的show()方法

            //sfWindow = sfClass.getDeclaredMethod("goToPage", null);
            // 取消访问私有方法的合法性检查
            sfWindow.setAccessible(true);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("反射异常", e.toString());
        }
    }

    /**
     * 计算两个点之间的距离，并且小数点后面保留两位
     *
     * @param lat,lng 资源经纬度
     * @return
     */
    public static String CalculateLineDistance(String lat, String lng) {
        String distance = "0.00";
        LatLng selfLatLng = getSelfLatLng();
        if (Utils.isnotNull(selfLatLng) && Utils.isnotNull(lat) && Utils.isnotNull(lng)) {
            LatLng resourceLatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
            distance = new DecimalFormat("0.00").format((AMapUtils.calculateLineDistance
                    (selfLatLng, resourceLatLng)) / 1000);
        }
        return distance + "km";
    }
}
