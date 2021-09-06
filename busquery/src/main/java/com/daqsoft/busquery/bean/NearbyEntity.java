package com.daqsoft.busquery.bean;

import java.util.List;

/**
 * 附近公交站
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-8-1.10:38
 * @since JDK 1.8
 */

public class NearbyEntity {
    /**
     * 公交站台
     */
    private String busStation;
    /**
     * 距离
     */
    private String destence;
    /**
     * 数据
     */
    private List<BusBean> mBusbean;

    public String getBusStation() {
        return busStation;
    }

    public void setBusStation(String busStation) {
        this.busStation = busStation;
    }

    public String getDestence() {
        return destence;
    }

    public void setDestence(String destence) {
        this.destence = destence;
    }

    public List<BusBean> getmBusbean() {
        return mBusbean;
    }

    public void setmBusbean(List<BusBean> mBusbean) {
        this.mBusbean = mBusbean;
    }

    public static class BusBean{
        /**
         * 公交名称
         */
        private String busName;

        public String getBusName() {
            return busName;
        }

        public void setBusName(String busName) {
            this.busName = busName;
        }
    }

}
