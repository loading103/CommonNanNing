package com.daqsoft.busquery.bean;

import java.util.List;

/**
 * 火车时刻表
 * @author MouJunFeng
 * @time 2018-4-9.
 * @since JDK 1.8
 * @version 1.0.0
 */

public class TrainDetail {
    private List<TrainDetail> data;
    /**
     * 到达时间
     */
    private String arrive_time;
    /**
     * 站点编号
     */
    private String station_no;
    /**
     * 停留时间
     */
    private String stopover_time;
    /**
     * 站点是否启用
     */
    private boolean isEnabled;
    /**
     * 终点
     */
    private String end_station_name;
    /**
     * 车次
     */
    private String station_train_code;
    /**
     * 类型
     */
    private String service_type;
    /**
     * 出发地点
     */
    private String start_station_name;
    /**
     * 离开时间
     */
    private String start_time;
    /**
     * 火车类型
     */
    private String train_class_name;
    /**
     * 到达地点
     */
    private String station_name;

    public List<TrainDetail> getData() {
        return data;
    }

    public void setData(List<TrainDetail> data) {
        this.data = data;
    }

    public String getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(String arrive_time) {
        this.arrive_time = arrive_time;
    }

    public String getStation_no() {
        return station_no;
    }

    public void setStation_no(String station_no) {
        this.station_no = station_no;
    }

    public String getStopover_time() {
        return stopover_time;
    }

    public void setStopover_time(String stopover_time) {
        this.stopover_time = stopover_time;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getEnd_station_name() {
        return end_station_name;
    }

    public void setEnd_station_name(String end_station_name) {
        this.end_station_name = end_station_name;
    }

    public String getStation_train_code() {
        return station_train_code;
    }

    public void setStation_train_code(String station_train_code) {
        this.station_train_code = station_train_code;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getStart_station_name() {
        return start_station_name;
    }

    public void setStart_station_name(String start_station_name) {
        this.start_station_name = start_station_name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getTrain_class_name() {
        return train_class_name;
    }

    public void setTrain_class_name(String train_class_name) {
        this.train_class_name = train_class_name;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }
}
