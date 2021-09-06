package com.daqsoft.commonnanning.ui.entity;

import java.util.List;

/**
 * 景区详情
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-3-28.9:45
 * @since JDK 1.8
 */

public class ScenicDetail {

    private TipsBean  tips;
    private String id;
    /**
     * 热度
     */
    private String heat;
    /**
     * 公共设施
     */
    private String serverFacilities;
    /**
     * 最佳拍摄位置
     */
    private String bestShooting;
    /**
     * 语音导览路径
     */
    private String audioPath;
    /**
     * 预定
     */
    private String onlineBooking;
    /**
     * 介绍富文本
     */
    private String introduce;

    /**
     * 等级
     */
    private String levelName;
    private VoBean vo;
    private List<String> tagNames;

    private String picture;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public void setMonitorPath(String monitorPath) {
        this.monitorPath = monitorPath;
    }

    /**
     * streagy : {"enshrine":0,"title":"游记","commentNum":0,"addtime":1530081220000,"recommend":0,
     * "cover":"http://file.geeker.com.cn/uploadfile/travelGuides/1530081219392/imageFileName
     * .png","id":47}
     */


    private StreagyBean streagy;

    public String getHeat() {
        return heat;
    }

    public void setHeat(String heat) {
        this.heat = heat;
    }

    public String getBestShooting() {
        return bestShooting;
    }


    public void setBestShooting(String bestShooting) {
        this.bestShooting = bestShooting;
    }

    public String getLevelName() {
        return levelName;
    }

    public String getServerFacilities() {
        return serverFacilities;
    }

    public void setServerFacilities(String serverFacilities) {
        this.serverFacilities = serverFacilities;
    }

    public VoBean getVo() {
        return vo;
    }

    public void setVo(VoBean vo) {
        this.vo = vo;
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    public StreagyBean getStreagy() {
        return streagy;
    }

    public void setStreagy(StreagyBean streagy) {
        this.streagy = streagy;
    }

    public static class VoBean{
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

    public TipsBean getTips() {
        return tips;
    }

    public void setTips(TipsBean tips) {
        this.tips = tips;
    }

    public String getOnlineBooking() {
        return onlineBooking;
    }

    public void setOnlineBooking(String onlineBooking) {
        this.onlineBooking = onlineBooking;
    }

    /**
     * 小贴士
     */
    public static class TipsBean{
        private String ticket;
        private String opentime;
        private String trafficInformation;
        private List<NoticeBean>  notice;

        public String getTicket() {
            return ticket;
        }

        public void setTicket(String ticket) {
            this.ticket = ticket;
        }

        public String getOpentime() {
            return opentime;
        }

        public void setOpentime(String opentime) {
            this.opentime = opentime;
        }

        public String getTrafficInformation() {
            return trafficInformation;
        }

        public void setTrafficInformation(String trafficInformation) {
            this.trafficInformation = trafficInformation;
        }

        public List<NoticeBean> getNotice() {
            return notice;
        }

        public void setNotice(List<NoticeBean> notice) {
            this.notice = notice;
        }

        public static class NoticeBean{
            private String content;
            private String summary;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }
        }
    }

    /**
     * 地址
     */
    private String address;
    /**
     * 简介
     */
    private String summary;
    /**
     * 导游导览ID
     */
    private String mapGuideId;

    public String getMapGuideId() {
        return mapGuideId;
    }

    public void setMapGuideId(String mapGuideId) {
        this.mapGuideId = mapGuideId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 电话
     */
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 纬度
     */
    private String latitude;
    /**
     * 经度
     */
    private String longitude;

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

    /**
     * 名字
     */
    private String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 视频监控地址
     */
    private String monitorPath;

    public String getMonitorPath() {
        return monitorPath;
    }

    /**
     * 720地址
     */
    private String panoramaPathApp;

    public String getPanoramaPathApp() {
        return panoramaPathApp;
    }

    public void setPanoramaPathApp(String panoramaPathApp) {
        this.panoramaPathApp = panoramaPathApp;
    }

    /**
     * 封面图
     */
    private String pictureTwoToOne;
    /**
     * 天气
     */
    private curDateWeatherBean curDateWeather;

    public curDateWeatherBean getCurDateWeather() {
        return curDateWeather;
    }

    public void setCurDateWeather(curDateWeatherBean curDateWeather) {
        this.curDateWeather = curDateWeather;
    }

    public static class curDateWeatherBean{
        /**
         * 晚上图片
         */
        private String pic_n;
        /**
         * 白天图片
         */
        private String pic_d;
        /**
         * 最小温度
         */
        private String min;
        /**
         * 最大温度
         */
        private String max;
        /**
         * 晚上
         */
        private String txt_n;
        /**
         * 白天
         */
        private String txt_d;

        public String getPic_n() {
            return pic_n;
        }

        public void setPic_n(String pic_n) {
            this.pic_n = pic_n;
        }

        public String getPic_d() {
            return pic_d;
        }

        public void setPic_d(String pic_d) {
            this.pic_d = pic_d;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getTxt_n() {
            return txt_n;
        }

        public void setTxt_n(String txt_n) {
            this.txt_n = txt_n;
        }

        public String getTxt_d() {
            return txt_d;
        }

        public void setTxt_d(String txt_d) {
            this.txt_d = txt_d;
        }
    }

    public String getPictureTwoToOne() {
        return pictureTwoToOne;
    }

    public void setPictureTwoToOne(String pictureTwoToOne) {
        this.pictureTwoToOne = pictureTwoToOne;
    }

    /**
     * 图片集
     */
    private List<PicsBean> pics;

    public List<PicsBean> getPics() {
        return pics;
    }

    public void setPics(List<PicsBean> pics) {
        this.pics = pics;
    }

    public static class PicsBean{
        /**
         * 图片名字
         */
        private String imgName;
        /**
         * 图片路径
         */
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

    public static class StreagyBean {
        /**
         * enshrine : 0
         * title : 游记
         * commentNum : 0
         * addtime : 1530081220000
         * recommend : 0
         * cover : http://file.geeker.com.cn/uploadfile/travelGuides/1530081219392/imageFileName.png
         * id : 47
         */

        private int enshrine;
        private String title;
        private int commentNum;
        private long addtime;
        private int recommend;
        private String cover;
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getEnshrine() {
            return enshrine;
        }

        public void setEnshrine(int enshrine) {
            this.enshrine = enshrine;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public long getAddtime() {
            return addtime;
        }

        public void setAddtime(long addtime) {
            this.addtime = addtime;
        }

        public int getRecommend() {
            return recommend;
        }

        public void setRecommend(int recommend) {
            this.recommend = recommend;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }
    }
}
