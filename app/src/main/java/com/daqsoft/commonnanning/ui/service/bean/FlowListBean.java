package com.daqsoft.commonnanning.ui.service.bean;

import java.util.List;

/**
 * 景区客流量实体类
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2019/12/17 0017
 * @since JDK 1.8
 */
public class FlowListBean {


    /**
     * total : 1
     * currPage : 1
     * pageSize : 10
     * list : [{"lng":"116.070689","receptionPeopleTotal":0,"level":"4A","maxLoad":10000,
     * "comfortLevel":1,"fullName":"贝子庙旅游区","parkTotal":200,"sceneryImgUrl":"",
     * "realTimePeopleTotal":0,"parkUsed":0,"name":"贝子庙旅游区","disasterImgUrl":"",
     * "id":"938250801983492096","region":"152502","lat":"43.950327"}]
     */
    /**
     * 总条数
     */
    private int total;
    /**
     * 当前页码
     */
    private int currPage;
    /**
     * 每页需要的条数
     */
    private int pageSize;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * lng : 116.070689
         * receptionPeopleTotal : 0
         * level : 4A
         * maxLoad : 10000
         * comfortLevel : 1
         * fullName : 贝子庙旅游区
         * parkTotal : 200
         * sceneryImgUrl :
         * realTimePeopleTotal : 0
         * parkUsed : 0
         * name : 贝子庙旅游区
         * disasterImgUrl :
         * id : 938250801983492096
         * region : 152502
         * lat : 43.950327
         */
        /**
         * 纬度
         */
        private String lng;
        /**
         * 累计人数
         */
        private int receptionPeopleTotal;
        /**
         * 等级
         */
        private String level;
        /**
         * 最大承载量
         */
        private int maxLoad;
        /**
         * 舒适等级
         */
        private int comfortLevel;
        /**
         * 简称
         */
        private String fullName;
        /**
         * 停车场总数
         */
        private int parkTotal;
        private String sceneryImgUrl;
        /**
         * 实时人数
         */
        private int realTimePeopleTotal;
        /**
         * 停车场使用数量
         */
        private int parkUsed;
        /**
         * 名称
         */
        private String name;
        private String disasterImgUrl;
        /**
         * ID
         */
        private String id;
        /**
         * 地区编码
         */
        private String region;
        /**
         * 经度
         */
        private String lat;

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public int getReceptionPeopleTotal() {
            return receptionPeopleTotal;
        }

        public void setReceptionPeopleTotal(int receptionPeopleTotal) {
            this.receptionPeopleTotal = receptionPeopleTotal;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public int getMaxLoad() {
            return maxLoad;
        }

        public void setMaxLoad(int maxLoad) {
            this.maxLoad = maxLoad;
        }

        public int getComfortLevel() {
            return comfortLevel;
        }

        public void setComfortLevel(int comfortLevel) {
            this.comfortLevel = comfortLevel;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public int getParkTotal() {
            return parkTotal;
        }

        public void setParkTotal(int parkTotal) {
            this.parkTotal = parkTotal;
        }

        public String getSceneryImgUrl() {
            return sceneryImgUrl;
        }

        public void setSceneryImgUrl(String sceneryImgUrl) {
            this.sceneryImgUrl = sceneryImgUrl;
        }

        public int getRealTimePeopleTotal() {
            return realTimePeopleTotal;
        }

        public void setRealTimePeopleTotal(int realTimePeopleTotal) {
            this.realTimePeopleTotal = realTimePeopleTotal;
        }

        public int getParkUsed() {
            return parkUsed;
        }

        public void setParkUsed(int parkUsed) {
            this.parkUsed = parkUsed;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDisasterImgUrl() {
            return disasterImgUrl;
        }

        public void setDisasterImgUrl(String disasterImgUrl) {
            this.disasterImgUrl = disasterImgUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }
}
