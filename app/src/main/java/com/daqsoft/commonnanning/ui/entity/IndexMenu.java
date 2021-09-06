package com.daqsoft.commonnanning.ui.entity;

/**
 * 首页菜单数据
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-3-19.11:53
 * @since JDK 1.8
 */

public class IndexMenu {
    /**
     * 功能菜单名称
     */
    private String name;
    /**
     * 功能菜单ICON
     */
    private Integer mImg;
    /**
     * 功能菜单类型
     */
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getmImg() {
        return mImg;
    }

    public void setmImg(Integer mImg) {
        this.mImg = mImg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
