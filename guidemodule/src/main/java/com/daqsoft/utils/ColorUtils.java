package com.daqsoft.utils;

import android.graphics.Color;

import java.util.regex.Pattern;

/**
 * 颜色的转换
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018/5/23
 * @since JDK 1.8
 */
public class ColorUtils {

    /**
     * 将十六进制 颜色代码 转换为 int
     *
     * @return
     */
    public static int HextoColor(String color) {

        // #ff00CCFF
        String reg = "#[a-f0-9A-F]{8}";
        if (!Pattern.matches(reg, color)) {
            color = "#00ffffff";
        }

        return Color.parseColor(color);
    }

    /**
     * 修改颜色透明度
     * @param color 颜色
     * @param alpha
     * @return
     */
    public static int changeAlpha(int color, int alpha) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        return Color.argb(alpha, red, green, blue);
    }

}
