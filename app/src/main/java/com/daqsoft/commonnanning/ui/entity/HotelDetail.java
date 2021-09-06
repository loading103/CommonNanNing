package com.daqsoft.commonnanning.ui.entity;

import java.util.List;

/**
 * 功能
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-3-30.14:11
 * @since JDK 1.8
 */

public class HotelDetail {
    /**
     * 酒店名称
     */
    private String name;
    /**
     * 内容
     */
    private String content;
    /**
     * 评分
     */
    private String commentLevel;
    /**
     * 预定网址
     */
    private String cheap;
    /**
     * 图片
     */
    private String pictureTwoToOne;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 地址
     */
    private String address;
    /**
     * 电话
     */
    private String phone;
    /**
     * 简介
     */
    private String intro;
    /**
     * 全景
     */
    private String panorama;
    /**
     * id
     */
    private String id;
    /**
     * 评分等级
     */
    private String level;

    public String getLevel() {
        return level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 便利设施的code
     */


    private List<RoomFacilitiesCodeBean> roomFacilitiesCode;

    private List<RoomFacilitiesCodeBean> serverIncludeCode;
    private List<RoomFacilitiesCodeBean> networkCode;
    private List<RoomFacilitiesCodeBean> diningServerCode;
    private List<RoomFacilitiesCodeBean> receptionServerCode;
    private List<RoomFacilitiesCodeBean> trafficServerCode;

    public List<RoomFacilitiesCodeBean> getServerIncludeCode() {
        return serverIncludeCode;
    }

    public void setServerIncludeCode(List<RoomFacilitiesCodeBean> serverIncludeCode) {
        this.serverIncludeCode = serverIncludeCode;
    }

    public List<RoomFacilitiesCodeBean> getNetworkCode() {
        return networkCode;
    }

    public void setNetworkCode(List<RoomFacilitiesCodeBean> networkCode) {
        this.networkCode = networkCode;
    }

    public List<RoomFacilitiesCodeBean> getDiningServerCode() {
        return diningServerCode;
    }

    public void setDiningServerCode(List<RoomFacilitiesCodeBean> diningServerCode) {
        this.diningServerCode = diningServerCode;
    }

    public List<RoomFacilitiesCodeBean> getReceptionServerCode() {
        return receptionServerCode;
    }

    public void setReceptionServerCode(List<RoomFacilitiesCodeBean> receptionServerCode) {
        this.receptionServerCode = receptionServerCode;
    }

    public List<RoomFacilitiesCodeBean> getTrafficServerCode() {
        return trafficServerCode;
    }

    public void setTrafficServerCode(List<RoomFacilitiesCodeBean> trafficServerCode) {
        this.trafficServerCode = trafficServerCode;
    }

    public String getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(String commentLevel) {
        this.commentLevel = commentLevel;
    }

    private VoBean vo;
    private List<String> tag;
    private String levelName;
    private List<ImgListBean> imgList;

    public String getCheap() {
        return cheap;
    }

    public void setCheap(String cheap) {
        this.cheap = cheap;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public VoBean getVo() {
        return vo;
    }

    public void setVo(VoBean vo) {
        this.vo = vo;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public List<ImgListBean> getImgList() {
        return imgList;
    }

    public void setImgList(List<ImgListBean> imgList) {
        this.imgList = imgList;
    }

    public static class VoBean {
        /**
         * 收藏状态
         */
        private int enshrine;
        /**
         * 点赞状态
         */
        private int thumb;

        public int getEnshrine() {
            return enshrine;
        }

        public void setEnshrine(int enshrine) {
            this.enshrine = enshrine;
        }

        public int getThumb() {
            return thumb;
        }

        public void setThumb(int thumb) {
            this.thumb = thumb;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<RoomFacilitiesCodeBean> getRoomFacilitiesCode() {
        return roomFacilitiesCode;
    }

    public void setRoomFacilitiesCode(List<RoomFacilitiesCodeBean> roomFacilitiesCode) {
        this.roomFacilitiesCode = roomFacilitiesCode;
    }

    public static class RoomFacilitiesCodeBean {
        /**
         * 便利设施的code
         */
        private String code;
        /**
         * 名称
         */
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public String getPanorama() {
        return panorama;
    }

    public void setPanorama(String panorama) {
        this.panorama = panorama;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getPictureTwoToOne() {
        return pictureTwoToOne;
    }

    public void setPictureTwoToOne(String pictureTwoToOne) {
        this.pictureTwoToOne = pictureTwoToOne;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class ImgListBean {
        /**
         * imgName : 1
         * imgPath : http://file.geeker.com
         * .cn/uploadfile/ptisp/img/1555987466507/747b886f-d7b4-418d-812f-d792294ff7ca.jpg
         */

        private String imgName;
        private String imgPath;

        public String getImgName() {
            return imgName;
        }

        public void setImgName(String imgName) {
            this.imgName = imgName;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }
    }
}
