package com.daqsoft.commonnanning.ui.robot.entity;

import java.util.List;

/**
 * 功能
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-11-29.13:55
 * @since JDK 1.8
 */

public class RobotTag2 {

    /**
     * responseTime : 1543470813046
     * message : success
     * code : 0
     * datas : [{"id":51,"name":"必游地"},{"id":52,"name":"好吃的"},{"id":53,"name":"买特产"},{"id":54,
     * "name":"住哪里"},{"id":55,"name":"玩神马"},{"id":56,"name":"超实用"},{"id":71,"name":"其他"}]
     */

    private long responseTime;
    private String message;
    private int code;
    private List<DatasBean> datas;

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

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * id : 51
         * name : 必游地
         */

        private int id;
        private String name;
        private boolean isScelected;

        public boolean isScelected() {
            return isScelected;
        }

        public void setScelected(boolean scelected) {
            isScelected = scelected;
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
    }
}
