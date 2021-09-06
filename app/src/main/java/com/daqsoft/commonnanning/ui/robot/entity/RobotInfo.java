package com.daqsoft.commonnanning.ui.robot.entity;

/**
 * 机器人基础信息
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-11-29.14:19
 * @since JDK 1.8
 */

public class RobotInfo {

    /**
     * responseTime : 1543472276388
     * message : success
     * code : 0
     * data : {"id":11,"topicName":"乌鲁木齐极客机器人","nickName":"小齐","greeting":"主人您好，我是小齐，请问有什么可以帮您？",
     * "logo":"","background":"http://file.geeker.com
     * .cn/uploadfile/ptisp/img/1531897009657/聊天窗口背景图.jpg","org":"100000010000006","lang":"cn"}
     */

    private long responseTime;
    private String message;
    private int code;
    private DataBean data;

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 11
         * topicName : 乌鲁木齐极客机器人
         * nickName : 小齐
         * greeting : 主人您好，我是小齐，请问有什么可以帮您？
         * logo :
         * background : http://file.geeker.com.cn/uploadfile/ptisp/img/1531897009657/聊天窗口背景图.jpg
         * org : 100000010000006
         * lang : cn
         */

        private int id;
        private String topicName;
        private String nickName;
        private String greeting;
        private String logo;
        private String background;
        private String org;
        private String lang;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getGreeting() {
            return greeting;
        }

        public void setGreeting(String greeting) {
            this.greeting = greeting;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public String getOrg() {
            return org;
        }

        public void setOrg(String org) {
            this.org = org;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }
    }
}
