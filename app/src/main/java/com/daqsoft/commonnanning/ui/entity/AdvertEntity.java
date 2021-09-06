package com.daqsoft.commonnanning.ui.entity;

import java.util.List;

/**
 * Created by huangx on 2018/3/7.
 * 广告图、banner图的实体类
 */

public class AdvertEntity {

    /**
     * id : 44
     * lang : cn
     * title : 超好玩
     * url :
     * pos_id : null
     * description : 微网站超好玩
     * pics : ["http://file.geeker.com.cn/uploadfile/ptisp/img/1525509606315/c.jpg"]
     * videos :
     * indexOrder : 999
     * beginTime : 2018-05-05 16:40:01
     * endTime : 2019-05-05 16:40:02
     * clickCount : 0
     * site_id : null
     * createTime : 2018-05-05 16:40:10
     * updateTime : 2018-09-20 19:59:53
     * lastOpUser : admin
     * status : 1
     */

    private String id;
    private String lang;
    private String title;
    private String url;
    private Object pos_id;
    private String description;
    private String videos;
    private String indexOrder;
    private String beginTime;
    private String endTime;
    private String clickCount;
    private Object site_id;
    private String createTime;
    private String updateTime;
    private String lastOpUser;
    private int status;
    private List<String> pics;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getPos_id() {
        return pos_id;
    }

    public void setPos_id(Object pos_id) {
        this.pos_id = pos_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getIndexOrder() {
        return indexOrder;
    }

    public void setIndexOrder(String indexOrder) {
        this.indexOrder = indexOrder;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getClickCount() {
        return clickCount;
    }

    public void setClickCount(String clickCount) {
        this.clickCount = clickCount;
    }

    public Object getSite_id() {
        return site_id;
    }

    public void setSite_id(Object site_id) {
        this.site_id = site_id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getLastOpUser() {
        return lastOpUser;
    }

    public void setLastOpUser(String lastOpUser) {
        this.lastOpUser = lastOpUser;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }
}
