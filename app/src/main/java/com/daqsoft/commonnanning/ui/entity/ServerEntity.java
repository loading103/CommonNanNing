package com.daqsoft.commonnanning.ui.entity;

/**
 * 服务实体类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/9/18 0018
 * @since JDK 1.8
 */
public class ServerEntity {
    /**
     * 标题
     */
    private String title;
    /**
     * 简介
     */
    private String info;
    /**
     * 内容
     */
    private String content;
    /**
     * 图标
     */
    private int icon;
    /**
     * 标题颜色
     */
    private int titleColor;

    /**
     * 描述字体颜色
     */
    private int infoColor;
    /**
     * 背景颜色
     */
    private int bgImg;

    /**
     * 类型
     */
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getInfoColor() {
        return infoColor;
    }

    public void setInfoColor(int infoColor) {
        this.infoColor = infoColor;
    }

    public int getBgImg() {
        return bgImg;
    }

    public void setBgImg(int bgImg) {
        this.bgImg = bgImg;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public String getContent() {
        return content;
    }

    public int getIcon() {
        return icon;
    }

    public int getTitleColor() {
        return titleColor;
    }
}
