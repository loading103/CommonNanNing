package com.daqsoft.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.view.WindowManager;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huangx on 2017/11/16.
 * <p>
 * 判断是否为空的一些工具类
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-14
 * @since JDK 1.8
 */

public class Utils {
    private static Context context;
    public static void maoToCenter(AMap aMap, List<Marker> markerList, LatLng mloction) {
        if (markerList.size() == 1 && isnotNull(markerList)) {
            aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerList.get(0).getPosition(),
                    15));
        } else {
            // 存放所有点的经纬度
            LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
            for (int i = 0; i < markerList.size(); i++) {
                // 把所有点都include进去（LatLng类型）
                boundsBuilder.include(markerList.get(i).getPosition());
            }
            if (mloction != null) {
                boundsBuilder.include(mloction);
            }
            // 第二个参数为四周留空宽度
            aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 100));
        }
    }

    public static void windowBackGround(Activity activity, float value) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = value;
        activity.getWindow().setAttributes(lp);
    }


    private static Pattern pattern = Pattern.compile("\\&\\#(\\d+)");

    /**
     * 判断对象内容是否为空
     *
     * @param obj 判断的对象
     * @return 不为空返回true。 为空返回false
     */
    public static boolean isnotNull(Object obj) {
        boolean b = false;
        if (null != obj && !obj.toString().equals("")
                && !obj.toString().toLowerCase().equals("null")
                && "undefined" != obj) {
            b = true;
        }
        return b;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 跳转到拨号界面，手动拨打
     *
     * @param phoneNumber
     */
    public static void justCall(String phoneNumber) {
        if (isnotNull(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
            context.startActivity(intent);
        } else {
            ShowToast.showText(context, "暂无联系电话");
        }
    }


    /**
     * 判断是否连接网络
     */
    private static boolean haveNet = false;

    public static boolean gethaveNet(Context context) {
        boolean success = false;
        // 获得网络连接服务
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState(); // 获取网络连接状态
        if (NetworkInfo.State.CONNECTED == state) { // 判断是否正在使用WIFI网络
            success = true;
        }
        state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState(); // 获取网络连接状态
        if (NetworkInfo.State.CONNECTED == state) { // 判断是否正在使用GPRS网络
            success = true;
        }
        try {
            if (!success) {
                haveNet = false;
                //                Log.e("您的网络连接已中断");
                //				BaseActivity.tvNetWorkState
                //						.setVisibility(BaseActivity.currentIsShowNetWork ? View.VISIBLE
                //								: View.GONE);
            } else {
                //				BaseActivity.tvNetWorkState.setVisibility(View.GONE);
                //                Log.e("您的网络连接成功");
            }
        } catch (Exception e) {
        }
        return success;
    }


    /**
     * 处理乱码
     *
     * @param str
     * @return
     */
    public static String tr(String str) {
        //StringBuilder sb = new StringBuilder(str);
        if (!isnotNull(str)) {
            return "";
        }
        String result = str;
        Matcher m = pattern.matcher(str);
        while (m.find()) {
            String temp = str.substring(m.start(), m.end() + 1);
            result = result.replaceAll(temp, (char) Integer.valueOf(m.group(1)).intValue() + "");
            //sb.append((char)Integer.valueOf(m.group(1)).intValue());
        }
        return result;
    }

    /**
     * 带多个参数进行跳转
     *
     * @param context
     * @param targetActivity
     * @param bundle
     */
    public static void startActivity(Activity context, Activity targetActivity,
                                     Bundle bundle) {
        Intent intent = new Intent(context, targetActivity.getClass());
        intent.putExtras(bundle);
        context.startActivity(intent);
        context.overridePendingTransition(0, 0);// 取消Activity跳转效果
    }

    /**
     * 跳转到下一个页面（不带参数）
     *
     * @param activity
     * @param clss
     */
    public static void goToOtherClass(Activity activity, Class<?> clss) {
        Intent intent = new Intent(activity, clss);
        activity.startActivity(intent);
    }

    /**
     * 跳转至设置页面
     */
    public static void href2Setting(Context context) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 转换
     *
     * @param str
     * @return
     */
    public static String convert(String str) {
        str = (str == null ? "" : str);
        String tmp;
        StringBuffer sb = new StringBuffer(1000);
        char c;
        int i, j;
        sb.setLength(0);
        for (i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            sb.append("\\u");
            j = (c >>> 8); //取出高8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);
            j = (c & 0xFF); //取出低8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);

        }
        return (new String(sb));
    }

    /**
     * 去除带html标签的内容中，去掉<img>图片标签
     *
     * @param content 内容
     * @return
     */
    public static String deleteHtmlImg(String content) {
        if (Utils.isnotNull(content)) {
            // 匹配img标签的正则表达式
            String regxpForImgTag = "<img\\s[^>]+/>";
            Pattern pattern = Pattern.compile(regxpForImgTag);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                String temp = matcher.group();
                content = content.replace(temp, "");
            }
            return Html.fromHtml(content).toString().trim();
        } else {
            return content;
        }
    }

}
