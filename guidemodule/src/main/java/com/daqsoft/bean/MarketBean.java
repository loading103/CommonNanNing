package com.daqsoft.bean;

import android.view.View;

import com.daqsoft.http.HttpResultBean;

import java.io.Serializable;

/**
 * 获取地图下某类基础资源的所有点位数据
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-4-18.
 * @since JDK 1.8
 */

public class MarketBean extends HttpResultBean<MarketBean> implements Serializable {
    /**
     * 资源类型
     */
    private String sourceType;
    /**
     * 简介
     */
    private String summary;
    /**
     * 站点id
     */
    private String siteId;
    /**
     * 名称
     */
    private String name;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 地图id
     */
    private int mapGuideSetId;
    /**
     * 语言
     */
    private String lang;
    /**
     * 站点代码
     */
    private String siteCode;
    /**
     * 状态
     */
    private int status;
    /**
     * id
     */
    private int id;
    /**
     * 概述
     */
    private String introduce;
    /**
     * 语言地址
     */
    private String audioPath;
    private String  address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 图片地址
     */
    private String cover;
    /**
     * 手绘地图访问地址类型（1：系统选择，2：外部链接）
     */
    private String linkType;
    /**
     * linkType为1时，返回绑定的地图导览id，为2时，返回外部链接地址
     */
    private String link;
    /**
     * 景区资源的等级
     */
    private String resourceLevel;
    /**
     * 线路对应标点数量
     */
    private int linePointNum;

    public int getLinePointNum() {
        return linePointNum;
    }

    public void setLinePointNum(int linePointNum) {
        this.linePointNum = linePointNum;
    }

    public String getResourceLevel() {
        return resourceLevel;
    }

    public void setResourceLevel(String resourceLevel) {
        this.resourceLevel = resourceLevel;
    }

    public class Market {
        private View view;
        private boolean isShow;

        public View getView() {
            return view;
        }

        public void setView(View view) {
            this.view = view;
        }

        public boolean isShow() {
            return isShow;
        }

        public void setShow(boolean show) {
            isShow = show;
        }
    }

    public Market getMarket() {
        return new Market();
    }


    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getMapGuideSetId() {
        return mapGuideSetId;
    }

    public void setMapGuideSetId(int mapGuideSetId) {
        this.mapGuideSetId = mapGuideSetId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    @Override
    public String toString() {
        return "MarketBean{" +
                "sourceType='" + sourceType + '\'' +
                ", summary='" + summary + '\'' +
                ", siteId='" + siteId + '\'' +
                ", name='" + name + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", mapGuideSetId=" + mapGuideSetId +
                ", lang='" + lang + '\'' +
                ", siteCode='" + siteCode + '\'' +
                ", status=" + status +
                ", id=" + id +
                ", audioPath='" + audioPath + '\'' +
                ", cover='" + cover + '\'' +
                ", linkType='" + linkType + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
