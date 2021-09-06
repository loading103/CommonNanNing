package com.daqsoft.busquery.bean;
import java.io.Serializable;
import java.util.List;

/**
 * 火车列表
 * @author MouJunFeng
 * @time 2018-4-8
 * @since JDK 1.8
 * @version 1.0.0
 */

public class TrainListBean  implements Serializable {
    /**
     * 票务信息列表
     */
    private List<TrainListBean> trainInfos;// |7		array<object>
    /**
     * 出发日期
     */
    private String deptDate;//
    /**
     * 到达时间点
     */
    private String arrTime;//
    /**
     * 最高票价
     */
    private String maxPrice;//
    /**
     * 最低票价
     */
    private String minPrice;//
    /**
     *
     */
    private boolean sellOut;
    private boolean stop;
    private boolean canBuy;
    /**
     * 出发站名字
     */
    private String deptStationName;
    /**
     * 硬卧价格
     */
    private String ywXiaPrice;
    /**
     * 列车状态
     */
    private int trainStatus;
    /**
     * 是否是终点站
     */
    private boolean endStation;
    /**
     * 运行时长
     */
    private String runTime;
    /**
     * 是否是出发站
     */
    private boolean startStation;
    /**
     * 目的地名字
     */
    private String arrStationName;
    /**
     * 开始时间
     */
    private String startSaleTime;
    /**
     * 出发站简码
     */
    private String deptStationCode;
    /**
     * 资源
     */
    private String source;

    private List<Seat> seatList;
    /**
     * 车次号
     */
    private String trainCode;
    /**
     * 到达站简码
     */
    private String arrStationCode;
    /**
     * 出发时间点
     */
    private String deptTime;
    /**
     * 列车类型
     */
    private String trainType;
    /**
     * 到达日期
     */
    private String arrDate;
    /**
     * 行程天数描述
     */
    private String arriveDays;
    /**
     * 软卧票价
     */
    private String rwXiaPrice;

    public class Seat implements Serializable {
        private String seatPrice;

        private String seatName;

        private String seatCode;

        private int showButton;

        private String seatNum;

        public String getSeatPrice() {
            return seatPrice;
        }

        public void setSeatPrice(String seatPrice) {
            this.seatPrice = seatPrice;
        }

        public String getSeatName() {
            return seatName;
        }

        public void setSeatName(String seatName) {
            this.seatName = seatName;
        }

        public String getSeatCode() {
            return seatCode;
        }

        public void setSeatCode(String seatCode) {
            this.seatCode = seatCode;
        }

        public int getShowButton() {
            return showButton;
        }

        public void setShowButton(int showButton) {
            this.showButton = showButton;
        }

        public String getSeatNum() {
            return seatNum;
        }

        public void setSeatNum(String seatNum) {
            this.seatNum = seatNum;
        }
    }

    public List<TrainListBean> getTrainInfos() {
        return trainInfos;
    }

    public void setTrainInfos(List<TrainListBean> trainInfos) {
        this.trainInfos = trainInfos;
    }

    public String getDeptDate() {
        return deptDate;
    }

    public void setDeptDate(String deptDate) {
        this.deptDate = deptDate;
    }

    public String getArrTime() {
        return arrTime;
    }

    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public boolean isSellOut() {
        return sellOut;
    }

    public void setSellOut(boolean sellOut) {
        this.sellOut = sellOut;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean isCanBuy() {
        return canBuy;
    }

    public void setCanBuy(boolean canBuy) {
        this.canBuy = canBuy;
    }

    public String getDeptStationName() {
        return deptStationName;
    }

    public void setDeptStationName(String deptStationName) {
        this.deptStationName = deptStationName;
    }

    public String getYwXiaPrice() {
        return ywXiaPrice;
    }

    public void setYwXiaPrice(String ywXiaPrice) {
        this.ywXiaPrice = ywXiaPrice;
    }

    public int getTrainStatus() {
        return trainStatus;
    }

    public void setTrainStatus(int trainStatus) {
        this.trainStatus = trainStatus;
    }

    public boolean isEndStation() {
        return endStation;
    }

    public void setEndStation(boolean endStation) {
        this.endStation = endStation;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public boolean isStartStation() {
        return startStation;
    }

    public void setStartStation(boolean startStation) {
        this.startStation = startStation;
    }

    public String getArrStationName() {
        return arrStationName;
    }

    public void setArrStationName(String arrStationName) {
        this.arrStationName = arrStationName;
    }

    public String getStartSaleTime() {
        return startSaleTime;
    }

    public void setStartSaleTime(String startSaleTime) {
        this.startSaleTime = startSaleTime;
    }

    public String getDeptStationCode() {
        return deptStationCode;
    }

    public void setDeptStationCode(String deptStationCode) {
        this.deptStationCode = deptStationCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }

    public String getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    public String getArrStationCode() {
        return arrStationCode;
    }

    public void setArrStationCode(String arrStationCode) {
        this.arrStationCode = arrStationCode;
    }

    public String getDeptTime() {
        return deptTime;
    }

    public void setDeptTime(String deptTime) {
        this.deptTime = deptTime;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public String getArrDate() {
        return arrDate;
    }

    public void setArrDate(String arrDate) {
        this.arrDate = arrDate;
    }

    public String getArriveDays() {
        return arriveDays;
    }

    public void setArriveDays(String arriveDays) {
        this.arriveDays = arriveDays;
    }

    public String getRwXiaPrice() {
        return rwXiaPrice;
    }

    public void setRwXiaPrice(String rwXiaPrice) {
        this.rwXiaPrice = rwXiaPrice;
    }
}
