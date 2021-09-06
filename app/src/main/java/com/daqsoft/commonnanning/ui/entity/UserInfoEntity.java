package com.daqsoft.commonnanning.ui.entity;

/**
 * 个人信息实体类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/3/21 0021
 * @since JDK 1.8
 */
public class UserInfoEntity {
    /**
     * 头像
     */
    String head;
    /**
     * 昵称
     */
    String nickname;
    /**
     * 姓名
     */
    String name;
    /**
     * QQID
     */
    String qqid;
    /**
     * 微信唯一码
     */
    String unionid;
    /**
     * 生日
     */
    String birthday;
    String account;
    /**
     * 性别
     */
    int gender;
    /**
     * 电话账号
     */
    String phone;
    /**
     * 常发生城市
     */
    CityJson startCityJson;
    /**
     * 所在城市
     */
    CityJson locationCityJson;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public CityJson getStartCityJson() {
        return startCityJson;
    }

    public CityJson getLocationCityJson() {
        return locationCityJson;
    }

    public String getHead() {
        return head;
    }

    public String getNickname() {
        return nickname;
    }

    public String getName() {
        return name;
    }

    public String getQqid() {
        return qqid;
    }

    public String getUnionid() {
        return unionid;
    }

    public String getBirthday() {
        return birthday;
    }

    public int getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    /**
     * 城市
     */
    public class CityJson {
        /**
         * 省region
         */
        String provRegion;
        /**
         * 市region
         */
        String cityRegion;
        /**
         * 县region
         */
        String distRegion;
        /**
         * 省名
         */
        String provRegionName;
        /**
         * 市名
         */
        String cityRegionName;
        /**
         * 县名
         */
        String distRegionName;

        public String getProvRegion() {
            return provRegion;
        }

        public String getCityRegion() {
            return cityRegion;
        }

        public String getDistRegion() {
            return distRegion;
        }

        public String getProvRegionName() {
            return provRegionName;
        }

        public String getCityRegionName() {
            return cityRegionName;
        }

        public String getDistRegionName() {
            return distRegionName;
        }
    }
}
