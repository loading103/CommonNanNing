package com.daqsoft.busquery.bean;

import java.util.List;

/**
 * 功能
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-7-31.10:26
 * @since JDK 1.8
 */

public class BusListEntity {
    /**
     * 分钟
     */
    private String minute;
    /**
     * 距离
     */
    private String dicetence;
    /**
     * 公交
     */
    private List<BusBean> mDatas;

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getDicetence() {
        return dicetence;
    }

    public void setDicetence(String dicetence) {
        this.dicetence = dicetence;
    }

    public List<BusBean> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<BusBean> mDatas) {
        this.mDatas = mDatas;
    }

    public static class BusBean {
        /**
         * bus路数
         */
        private String busnum;

        public String getBusnum() {
            return busnum;
        }

        public void setBusnum(String busnum) {
            this.busnum = busnum;
        }
    }
}
