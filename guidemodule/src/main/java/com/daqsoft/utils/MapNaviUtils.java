package com.daqsoft.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.amap.api.maps.model.LatLng;

import java.io.File;

/**
 * 跳转第三方导航
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018/12/11 0011
 * @since JDK 1.8
 */
public class MapNaviUtils {
    public static final String PN_GAODE_MAP = "com.autonavi.minimap"; // 高德地图包名
    public static final String PN_BAIDU_MAP = "com.baidu.BaiduMap"; // 百度地图包名
    public static final String DOWNLOAD_GAODE_MAP = "http://www.autonavi.com/"; // 高德地图下载地址
    public static final String DOWNLOAD_BAIDU_MAP = "http://map.baidu.com/zt/client/index/"; //
    // 百度地图下载地址

    /**
     * 检查应用是否安装
     *
     * @return
     */
    public static boolean isGdMapInstalled() {
        return isInstallPackage(PN_GAODE_MAP);
    }

    public static boolean isBaiduMapInstalled() {
        return isInstallPackage(PN_BAIDU_MAP);
    }

    private static boolean isInstallPackage(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 百度坐标系 (BD-09) 与 火星坐标系 (GCJ-02)的转换
     * 即 百度 转 谷歌、高德
     *
     * @param latLng
     * @returns
     */
    public static LatLng BD09ToGCJ02(LatLng latLng) {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double x = latLng.longitude - 0.0065;
        double y = latLng.latitude - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double gg_lat = z * Math.sin(theta);
        double gg_lng = z * Math.cos(theta);
        return new LatLng(gg_lat, gg_lng);
    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换
     * 即谷歌、高德 转 百度
     *
     * @param latLng
     * @returns
     */
    public static LatLng GCJ02ToBD09(LatLng latLng) {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double z = Math.sqrt(latLng.longitude * latLng.longitude + latLng.latitude * latLng
                .latitude) + 0.00002 * Math.sin(latLng.latitude * x_pi);
        double theta = Math.atan2(latLng.latitude, latLng.longitude) + 0.000003 * Math.cos(latLng
                .longitude * x_pi);
        double bd_lat = z * Math.sin(theta) + 0.006;
        double bd_lng = z * Math.cos(theta) + 0.0065;
        return new LatLng(bd_lat, bd_lng);
    }

    /**
     * 打开高德地图导航功能
     *
     * @param context
     * @param dlat    终点纬度
     * @param dlon    终点经度
     * @param dname   终点名称 必填
     */
    public static void openGaoDeNavi(Context context, double slat, double slon, String sname,
                                     String dlat, String dlon, String dname) {
        String uriString = null;
        StringBuilder builder = new StringBuilder
                ("androidamap://route/plan?sourceApplication=maxuslife");
        builder.append("&sname=").append(sname)
                .append("&slat=").append(slat)
                .append("&slon=").append(slon);
        builder.append("&dlat=").append(dlat)
                .append("&dlon=").append(dlon)
                .append("&dname=").append(dname)
                .append("&dev=0")
                .append("&t=0");
        uriString = builder.toString();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage(PN_GAODE_MAP);
        intent.setData(Uri.parse(uriString));
        context.startActivity(intent);
    }

    /**
     * 打开百度地图导航功能(默认坐标点是高德地图，需要转换)
     *
     * @param context
     * @param dlat    终点纬度
     * @param dlon    终点经度
     * @param dname   终点名称 必填
     */
    public static void openBaiDuNavi(Context context, double slat, double slon, String sname,
                                     double dlat, double dlon, String dname) {
        String uriString = null;
        //终点坐标转换
        LatLng destination = new LatLng(dlat, dlon);
        LatLng destinationLatLng = GCJ02ToBD09(destination);
        dlat = destinationLatLng.latitude;
        dlon = destinationLatLng.longitude;

        StringBuilder builder = new StringBuilder("baidumap://map/direction?mode=driving&");
        //起点坐标转换
        LatLng origin = new LatLng(slat, slon);
        LatLng originLatLng = GCJ02ToBD09(origin);
        slat = originLatLng.latitude;
        slon = originLatLng.longitude;

        builder.append("origin=latlng:")
                .append(slat)
                .append(",")
                .append(slon)
                .append("|name:")
                .append(sname);
        builder.append("&destination=latlng:")
                .append(dlat)
                .append(",")
                .append(dlon)
                .append("|name:")
                .append(dname);
        uriString = builder.toString();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage(PN_BAIDU_MAP);
        intent.setData(Uri.parse(uriString));
        context.startActivity(intent);
    }

    /**
     * 跳转第三方进行导航
     *
     * @param dLat
     * @param dLng
     * @param dName
     */
    static AlertDialog alertDialog1;

    public static void isMapNaviUtils(final Activity activity, final double slat, final double
            slon, final String
                                              sname, final String dLat, final String dLng, final
                                      String dName) {
        final String[] items = {"高德地图", "百度地图", "取消"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);

        alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    // 高德地图
                    if (isGdMapInstalled()) {
                        openGaoDeNavi(activity, slat, slon, sname, dLat, dLng, dName);
                    } else {
                        ShowToast.showText(activity, "您还未安装高德地图！");
                        new AlertDialog.Builder(activity)
                                .setMessage("下载高德地图？")
                                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        activity.startActivity(new
                                                Intent(Intent.ACTION_VIEW, Uri.parse(MapNaviUtils
                                                .DOWNLOAD_GAODE_MAP)));
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();
                    }
                } else if (i == 1) {
                    // 百度地图
                    if (isBaiduMapInstalled()) {
                        openBaiDuNavi(activity, slat, slon, sname, Double.parseDouble(dLat),
                                Double.parseDouble(dLng), dName);
                    } else {
                        ShowToast.showText(activity,
                                "您还未安装百度地图！");
                        new AlertDialog.Builder(activity)
                                .setMessage("下载百度地图？")
                                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        activity.startActivity(new
                                                Intent(Intent.ACTION_VIEW, Uri.parse(MapNaviUtils
                                                .DOWNLOAD_BAIDU_MAP)));
                                    }
                                })
                                .setNegativeButton("取消", null)
                                .show();
                    }
                } else {
                    // 取消
                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = alertBuilder.create();
        alertDialog1.show();
    }
}
