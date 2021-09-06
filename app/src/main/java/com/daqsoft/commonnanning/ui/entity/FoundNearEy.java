package com.daqsoft.commonnanning.ui.entity;

/**
 * 身边游
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-10-15.16:06
 * @since JDK 1.8
 */

public class FoundNearEy {
    /**
     * 图标
     */
    private String img;
    /**
     * 标题
     */
    private String title;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 距离
     */
    private String distance;
    /**
     * 是否是限制
     */
    private boolean isshowxq;
    /**
     * tag
     */
    private String tag;
    /**
     * 经纬度
     */
    private String lat;
    private String lon;
    /**
     * id
     */
    private String id;
    /**
     * 资源id
     */
    private String resid;
    /**
     * 地址
     */
    private String adress;
    /**
     * 类型
     */
    private String type;
    /**
     * 等级
     */
    private String levelname;
    /**
     * 等级
     */
    private String commentLevel;

    public String getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(String commentLevel) {
        this.commentLevel = commentLevel;
    }

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid;
    }

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public boolean isIsshowxq() {
        return isshowxq;
    }

    public void setIsshowxq(boolean isshowxq) {
        this.isshowxq = isshowxq;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
