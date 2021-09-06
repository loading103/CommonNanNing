package com.daqsoft.commonnanning.ui.robot.entity;

import com.example.tomasyb.baselib.adapter.entity.MultiItemEntity;

import java.util.List;

/**
 * 功能
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-8-24.15:37
 * @since JDK 1.8
 */

public class RobotMultipleItem implements MultiItemEntity {
    // 我
    public static final int ME = 1;
    /**
     * 机器人
     */
    public static final int ROBOT = 2;
    /**
     * 内容
     */
    private String content;
    /**
     * 类型
     */
    private int itemType;
    /**
     * 0:单纯文字
     * 1：列表
     * 2：网格布局
     */
    private int contentType;
    /**
     * 当前页码
     */
    private int curentPage;
    /**
     * 当前问题
     */
    private String curentQuestion;
    private boolean isResources;
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isResources() {
        return isResources;
    }

    public void setResources(boolean resources) {
        isResources = resources;
    }

    public String getCurentQuestion() {
        return curentQuestion;
    }

    public void setCurentQuestion(String curentQuestion) {
        this.curentQuestion = curentQuestion;
    }

    public int getCurentPage() {
        return curentPage;
    }

    public void setCurentPage(int curentPage) {
        this.curentPage = curentPage;
    }

    private List<ScenicType> mScenicList;
    private List<ContentType> mContentTypeList;

    public List<ContentType> getmContentTypeList() {
        return mContentTypeList;
    }

    public void setmContentTypeList(List<ContentType> mContentTypeList) {
        this.mContentTypeList = mContentTypeList;
    }

    public List<ScenicType> getmScenicList() {
        return mScenicList;
    }

    public void setmScenicList(List<ScenicType> mScenicList) {
        this.mScenicList = mScenicList;
    }

    public static class ContentType {
        // 导游导览地址
        private String mapGuideSet;
        /**
         * 每个条目的类型
         */
        private String mScenicType;
        /**
         * 全景地址
         */

        private String fullAddress;
        /**
         * 实景展播
         */
        private String monitor;
        /**
         * 景区名称
         */
        private String content;
        /**
         * ID
         */
        private String id;
        /**
         * 经纬度
         */
        private String lat;
        private String lng;
        private String adress;

        private String phone;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getmScenicType() {
            return mScenicType;
        }

        public void setmScenicType(String mScenicType) {
            this.mScenicType = mScenicType;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getAdress() {
            return adress;
        }

        public void setAdress(String adress) {
            this.adress = adress;
        }

        public String getFullAddress() {
            return fullAddress;
        }

        public void setFullAddress(String fullAddress) {
            this.fullAddress = fullAddress;
        }

        public String getMonitor() {
            return monitor;
        }

        public void setMonitor(String monitor) {
            this.monitor = monitor;
        }

        public String getMapGuideSet() {
            return mapGuideSet;
        }

        public void setMapGuideSet(String mapGuideSet) {
            this.mapGuideSet = mapGuideSet;
        }

        private String name;
        /**
         * 0导游导览
         * 1:：720
         * 2:实景展播
         * 3:导航
         * 4:打电话
         */
        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ScenicType {
        private String id;
        private String type;
        private String phone;
        private String adress;
        private String lat;
        private String lng;
        private String content;
        private String question;
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAdress() {
            return adress;
        }

        public void setAdress(String adress) {
            this.adress = adress;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    /**
     * 详情
     */
    private String answerxq;

    public String getAnswerxq() {
        return answerxq;
    }

    public void setAnswerxq(String answerxq) {
        this.answerxq = answerxq;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public RobotMultipleItem(int itemType_) {
        this.itemType = itemType_;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
