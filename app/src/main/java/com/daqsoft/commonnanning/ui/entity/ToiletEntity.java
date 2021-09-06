package com.daqsoft.commonnanning.ui.entity;

/**
 * 厕所列表
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/25 0025
 * @since JDK 1.8
 */
public class ToiletEntity {
    /**
     * id
     */
    String id;
    /**
     * 距离
     */
    String distance;
    /**
     * 厕所地址
     */
    String address;
    /**
     * 经度
     */
    String lon;
    /**
     * 地区
     */
    String region;
    /**
     * 纬度
     */
    String lat;
    /**
     * 厕所名称
     */
    String name;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getLongitude() {
        return lon;
    }

    public String getRegion() {
        return region;
    }

    public String getLatitude() {
        return lat;
    }

    public String getName() {
        return name;
    }
}
