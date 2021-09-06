package com.daqsoft.commonnanning.ui.entity;

/**
 * 旅行社详情实体类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/7/3 0003
 * @since JDK 1.8
 */
public class TravelDetailsEntity {

    /**
     * latitude : 22.828609
     * headconsumer :
     * video :
     * travelManager : []
     * cover :
     * wbaddress :
     * digest :
     * coverOneToOne :
     * id : 1471
     * views : 0
     * exponent :
     * logobig :
     * longitude : 108.315847
     * bizlicense :
     * coverFourToThree :
     * chewei : 0
     * address : 南宁市兴宁区友爱南路10号
     * coverTwoToOne :
     * coverTwoToThree :
     * mainline :
     * orgcode :
     * logosmall :
     * opentime :
     * praise : 0
     * biztype :
     * 入境旅游业务、国内旅游业务、出境旅游业务、边境旅游业务（凭许可证经营）；旅游咨询、票务代办、会议服务、商务服务（除国家限制项目）、日用百货和工艺品（除金银首饰品）销售。
     * chainstore :
     * phone : 0771-2102456
     * name : 广西运德国际旅行社有限公司（中越边境游）
     * impression :
     */
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 封面图
     */
    private String cover;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 内容
     */
    private String biztype;
    /**
     * 电话
     */
    private String phone;
    /**
     * 名称
     */
    private String name;
    /**
     * 地址
     */
    private String address;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getBiztype() {
        return biztype;
    }

    public void setBiztype(String biztype) {
        this.biztype = biztype;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
