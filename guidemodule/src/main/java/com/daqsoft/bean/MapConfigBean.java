package com.daqsoft.bean;

import com.daqsoft.http.HttpResultBean;

/**
 * map配置bean
 * @author MouJunFeng
 * @time 2018-4-18.
 * @since JDK 1.8
 * @version 1.0.0
 */

public class MapConfigBean extends HttpResultBean<MapConfigBean> {
    /**
     * 数据类型标识
     */
    private String skey;
    /**
     * 线路
     */
    private String name;
    /**
     * 字体样式
     */
    private String iconFont;
    /**
     * 字体样式
     */
    private String iconFontAndroid;
    /**
     * 颜色值
     */
    private String color;

    private boolean ischeck = false;

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconFont() {
        return iconFont;
    }

    public void setIconFont(String iconFont) {
        this.iconFont = iconFont;
    }

    public String getIconFontAndroid() {
        return iconFontAndroid;
    }

    public void setIconFontAndroid(String iconFontAndroid) {
        this.iconFontAndroid = iconFontAndroid;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
