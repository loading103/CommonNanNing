package com.daqsoft.commonnanning.ui.entity;



/**
 * 导游导览
 * @author MouJunFeng
 * @time 2018-4-2
 * @since JDK 1.8
 * @version 1.0.0
 */

public class GuideBean  {
    /**
     * 景区id
     */
    private String sceneryId;
    /**
     * 景区名称
     */
    private String sceneryName;
    /**
     * 地区编码
     */
    private String region;
    /**
     * 类型
     */
    private int type;
    /**
     * 全景id
     */
    private int  id	;
    /**
     * 名称
     */
    private String name	;
    /**
     * 图片
     */
    private String coverTwoToOne	;

    public String getCoverTwoToOne() {
        return coverTwoToOne;
    }

    public void setCoverTwoToOne(String coverTwoToOne) {
        this.coverTwoToOne = coverTwoToOne;
    }

    /**
     * 地区名称
     */
    private String regionName;
    /**
     * 路径
     */
    private String cover	;
    /**
     * 简介
     */
    private String summary;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSceneryId() {
        return sceneryId;
    }

    public void setSceneryId(String sceneryId) {
        this.sceneryId = sceneryId;
    }

    public String getSceneryName() {
        return sceneryName;
    }

    public void setSceneryName(String sceneryName) {
        this.sceneryName = sceneryName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getPath() {
        return cover;
    }

    public void setPath(String path) {
        this.cover = path;
    }
}
