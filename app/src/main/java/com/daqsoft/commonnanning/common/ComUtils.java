package com.daqsoft.commonnanning.common;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daqsoft.android.ProjectConfig;
import com.daqsoft.commonnanning.R;
import com.daqsoft.commonnanning.ui.service.FoundNearNewActivity;
import com.daqsoft.commonnanning.view.ExpandTextView;
import com.daqsoft.guide.MapInformationActivity;
import com.example.tomasyb.baselib.util.PhoneUtils;
import com.example.tomasyb.baselib.util.ScreenUtils;
import com.example.tomasyb.baselib.util.SizeUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.agora.yview.dialog.BaseDialog;

/**
 * 公共类
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-3-25.15:09
 * @since JDK 1.8
 */

public class ComUtils {

    /**
     * @date:2017/11/10/010
     * @author:平sir
     * @desc: 设置tab指示器的宽度
     */
    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip,
                Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip,
                Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout
                    .LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    /**
     * 跳入身边游
     *
     * @param context
     */
    public static void goToNearMap(String currentLat, String currentLon, String name, String
            address, Context context) {
        // 周边好玩
        Bundle bundle3 = new Bundle();
        bundle3.putString("lat", currentLat);
        bundle3.putString("lng", currentLon);
        bundle3.putString("scenicname", name);
        bundle3.putString("scenicadress", address);
        bundle3.putInt("type", 1);
        Intent intent3 = new Intent(context, FoundNearNewActivity.class);
        intent3.putExtras(bundle3);
        context.startActivity(intent3);
    }

    /**
     * 跳入身边游
     *
     * @param context
     */
    public static void goToNearMapWitnType(String currentLat, String currentLon, String name,
                                           String address, Context context, int type) {
        // 周边好玩
        Bundle bundle3 = new Bundle();
        bundle3.putString("lat", currentLat);
        bundle3.putString("lng", currentLon);
        bundle3.putString("scenicname", name);
        bundle3.putString("scenicadress", address);
        bundle3.putInt("type", 1);
        bundle3.putInt("curentType", type);
        Intent intent3 = new Intent(context, FoundNearNewActivity.class);
        intent3.putExtras(bundle3);
        context.startActivity(intent3);
    }

    /**
     * 跳入导游导览
     *
     * @param guideId
     * @param context
     */
    public static void goToGuide(int guideId, Context context, boolean isFirst) {
        Intent intent = new Intent(context, MapInformationActivity.class);
        //                Intent intent = new Intent(context, MainActivity
        // .class);
        // 网络请求根地址
        intent.putExtra("url", ProjectConfig.BASE_URL);
        // 语言编码
        intent.putExtra("lang", ProjectConfig.LANG);
        // 站点编码
        intent.putExtra("sitecode", ProjectConfig.SITECODE);
        // 地区编码
        intent.putExtra("region", ProjectConfig.REGION);
        // 微信的账号APPID
        intent.putExtra("appid", ProjectConfig.APPID);
        // 地区名称
        intent.putExtra("city", ProjectConfig.CITY_NAME);
        // 当前地区的经度
        intent.putExtra("lat", ProjectConfig.COMMON_LAT);
        // 当前地区的纬度
        intent.putExtra("lng", ProjectConfig.COMMON_LNG);
        // 当前地区的相关介绍信息
        intent.putExtra("about", "");
        intent.putExtra("ID", guideId);
        intent.putExtra("isFirst", isFirst);
        context.startActivity(intent);
    }

    /**
     * 设置张开的数据
     *
     * @param tv
     * @param content
     */
    public static void setExpandTextview(ExpandTextView tv, String content, String WebContent) {
        // 设置TextView可展示的宽度 ( 父控件宽度 - 左右margin - 左右padding）
        int whidth = ScreenUtils.getScreenWidth() - SizeUtils.dp2px(16 * 2);
        tv.initWidth(whidth);
        tv.setMaxLines(3);
        tv.setCloseText(content);
        tv.setWebContent(WebContent);
    }

    /**
     * 去除带html标签的内容中，去掉<img>图片标签
     *
     * @param content 内容
     * @return
     */
    public static String deleteHtmlImg(String content) {
        // 匹配img标签的正则表达式
        String regxpForImgTag = "<img\\s[^>]+/>";
        Pattern pattern = Pattern.compile(regxpForImgTag);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String temp = matcher.group();
            content = content.replace(temp, "");
        }
        return Html.fromHtml(content).toString().trim();
    }

    /**
     * 判断现在时刻是否是白天
     *
     * @return
     */
    public static boolean isDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String hour = sdf.format(new Date());
        int k = Integer.parseInt(hour);
        if ((k >= 0 && k < 6) || (k >= 18 && k < 24)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 从当前页面隐藏输入框
     *
     * @param activity 上下文
     */
    public static void hideInputWindow(Activity activity) {
        InputMethodManager imm1 = (InputMethodManager) activity.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        imm1.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        //从控件所在的窗口中隐藏
    }

    /**
     * webView加载解决乱码问题
     * "text/html; charset=UTF-8"
     */
    public static final String WEBVIEW_TYPE = "text/html; charset=UTF-8";

    /**
     * 让WebView的内容宽度，自适应手机屏幕的宽度
     *
     * @param htmltext
     * @return
     */
    public static String getNewContent(String htmltext) {

        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        return doc.toString();
    }


    /**
     * 拨打电话弹框
     *
     * @param context 上下文
     * @param phone   电话
     */
    public static void showQueryCancleDialogPhone(final Context context, final String phone) {
        final BaseDialog dialog = new BaseDialog(context);
        dialog.contentView(R.layout.dialog_base_center).gravity(0).canceledOnTouchOutside(true)
                .show();
        dialog.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[]
                    // permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the
                    // documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                PhoneUtils.call(phone);
            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        TextView title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        title.setText("确定拨打:" + phone);
    }

}
