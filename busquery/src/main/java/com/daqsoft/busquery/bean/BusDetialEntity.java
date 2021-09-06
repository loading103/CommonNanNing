package com.daqsoft.busquery.bean;

import java.util.List;

/**
 * 公交详情
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-7-31.18:13
 * @since JDK 1.8
 */

public class BusDetialEntity {
    /**
     * 步行距离+分钟数
     */
    private String walkDicetence;
    /**
     * 公交开始
     */
    private String busStart;
    /**
     * 公交结束
     */
    private String busStop;
    /**
     * 公交路数
     */
    private String busNum;
    /**
     * 公交开往
     */
    private String busXiang;
    /**
     * 公交经过多少站
     */
    private String busTotla;
    /**
     * 公交途径多少分钟
     */
    private String busMinetue;
    private List<busStation> mBusList;

    public List<busStation> getmBusList() {
        return mBusList;
    }

    public void setmBusList(List<busStation> mBusList) {
        this.mBusList = mBusList;
    }

    public static class busStation{
        private String busName;

        public String getBusName() {
            return busName;
        }

        public void setBusName(String busName) {
            this.busName = busName;
        }
    }

    public String getWalkDicetence() {
        return walkDicetence;
    }

    public void setWalkDicetence(String walkDicetence) {
        this.walkDicetence = walkDicetence;
    }

    public String getBusStart() {
        return busStart;
    }

    public void setBusStart(String busStart) {
        this.busStart = busStart;
    }

    public String getBusStop() {
        return busStop;
    }

    public void setBusStop(String busStop) {
        this.busStop = busStop;
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum;
    }

    public String getBusXiang() {
        return busXiang;
    }

    public void setBusXiang(String busXiang) {
        this.busXiang = busXiang;
    }

    public String getBusTotla() {
        return busTotla;
    }

    public void setBusTotla(String busTotla) {
        this.busTotla = busTotla;
    }

    public String getBusMinetue() {
        return busMinetue;
    }

    public void setBusMinetue(String busMinetue) {
        this.busMinetue = busMinetue;
    }
}
