package com.daqsoft.commonnanning.utils;

import android.net.Uri;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.ViewAnimator;

import com.daqsoft.commonnanning.common.URLConstant;
import com.example.tomasyb.baselib.adapter.BaseQuickAdapter;
import com.example.tomasyb.baselib.base.net.entity.BaseResponse;
import com.example.tomasyb.baselib.util.LogUtils;
import com.example.tomasyb.baselib.util.SPUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/21 0021
 * @since JDK 1.8
 */
public class Utils {
    /**
     * 两次点击间隔不能少于1000ms
     */
    private static final int MIN_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
    /**
     * 网络请求地址处理 空格中文处理
     *
     * @param url
     * @return
     */
    public static String strToUrl(String url) {
        String value = "";
        value = Uri.encode(url);
        value = value.replaceAll("%3A", ":");
        value = value.replaceAll("%2F", "/");
        return value;
    }
    /**
     * 判断手机号码是否正确
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobile(String mobiles) {
        Pattern p = Pattern.compile(URLConstant.PHONE_MATCHER);
        Matcher m = p.matcher(mobiles);
        return m.matches();
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
     * 设置一个字符串中部分字体颜色改变
     *
     * @param content    内容
     * @param colorId    颜色
     * @param startIndex 从第几个开始
     * @param endIndex   到第几个结束
     * @return
     */
    public static SpannableString setTextColor(String content, int colorId, int startIndex,
                                               int endIndex) {
        SpannableString result = new SpannableString(content);
        result.setSpan(new ForegroundColorSpan(colorId), startIndex, endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return result;
    }

    /**
     * 获取当前位置
     *
     * @param key key值获取经纬度
     * @return
     */
    public String getCurrentLocation(String key) {
        String value = "";
        value = SPUtils.getInstance().getString(key);
        return value;
    }

    /**
     * 页码相关的处理操作
     */
    public static void pageDeal(int page, ArrayList data, BaseResponse response,
                                BaseQuickAdapter adapter, ViewAnimator viewAnimator) {
        if (page == 1) {
            data.clear();
        }
        if (response.getCode() == 0) {
            if (response.getPage() != null) {
                if (response.getPage().getTotal() == 0) {
                    if (viewAnimator != null) {
                        viewAnimator.setDisplayedChild(1);
                    }
                } else {
                    if (viewAnimator != null) {
                        viewAnimator.setDisplayedChild(0);
                    }
                }
                adapter.loadMoreComplete();
                if (response.getPage().getCurrPage() >= response.getPage().getTotalPage()) {
                    adapter.setEnableLoadMore(false);
                } else {
                    adapter.setEnableLoadMore(true);
                }
            } else {
                viewAnimator.setDisplayedChild(1);
            }
        } else {
            if (viewAnimator != null) {
                viewAnimator.setDisplayedChild(1);
            }
        }

    }

    /**
     * 计算图片高度
     *
     * @param height 原图高
     * @param width  原图宽
     */
    public static int imageHeight(String height, String width) {
        // 设置保留位数

        LogUtils.e( Float.parseFloat(height)/Float.parseFloat(width));
        LogUtils.e( Float.parseFloat(height)%Float.parseFloat(width));
//        float rate = Float.parseFloat(df.format(Integer.parseInt(height) / Integer.parseInt(width)));
//        float rate = Float.parseFloat()
//        int mHeight = (int) (Integer.parseInt(width) * rate);
        return 0;
    }

    /**
     * 检查应用是否安装
     * @return Boolean
     */
    public static boolean isInstallPackage(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }
}
