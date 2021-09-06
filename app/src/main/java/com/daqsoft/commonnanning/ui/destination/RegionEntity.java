package com.daqsoft.commonnanning.ui.destination;

import com.example.tomasyb.baselib.base.net.entity.BaseResponse;

/**
 * 站点地区信息
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018.05.07
 * @since JDK 1.8
 */

public class RegionEntity extends BaseResponse<RegionEntity> {


    /**
     * name : 凯里市
     * region : 522601
     * latitude : 26.573731
     * longitude : 107.98687
     */

    private String name;
    private String region;
    private String latitude;
    private String longitude;

    public RegionEntity() {
    }

    public RegionEntity(String name, String region, String latitude, String longitude) {
        this.name = name;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


}
