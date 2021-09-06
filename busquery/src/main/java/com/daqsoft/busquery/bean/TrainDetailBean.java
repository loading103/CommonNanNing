package com.daqsoft.busquery.bean;

import java.util.List;

/**
 * 火车详情
 * @author MouJunFeng
 * @time 2018-4-9
 * @since JDK 1.8
 * @version 1.0.0
 */

public class TrainDetailBean {

    /**
     * responseTime : 1555325554598
     * message : success
     * code : 0
     * data : {"data":[{"station_name":"峨眉山","train_class_name":"动车","start_time":"18:12",
     * "start_station_name":"峨眉山","service_type":"2","station_train_code":"C6314",
     * "end_station_name":"广元","isEnabled":false,"stopover_time":"----","station_no":"01",
     * "arrive_time":"18:12"},{"station_name":"乐山","start_time":"18:30","isEnabled":false,
     * "stopover_time":"2分钟","station_no":"02","arrive_time":"18:28"},{"station_name":"青神",
     * "start_time":"18:45","isEnabled":false,"stopover_time":"2分钟","station_no":"03",
     * "arrive_time":"18:43"},{"station_name":"眉山东","start_time":"18:59","isEnabled":false,
     * "stopover_time":"3分钟","station_no":"04","arrive_time":"18:56"},{"station_name":"双流机场",
     * "start_time":"19:22","isEnabled":false,"stopover_time":"2分钟","station_no":"05",
     * "arrive_time":"19:20"},{"station_name":"成都南","start_time":"19:34","isEnabled":false,
     * "stopover_time":"2分钟","station_no":"06","arrive_time":"19:32"},{"station_name":"成都东",
     * "start_time":"19:51","isEnabled":true,"stopover_time":"8分钟","station_no":"07",
     * "arrive_time":"19:43"},{"station_name":"德阳","start_time":"20:18","isEnabled":true,
     * "stopover_time":"3分钟","station_no":"08","arrive_time":"20:15"},{"station_name":"绵阳",
     * "start_time":"20:38","isEnabled":true,"stopover_time":"3分钟","station_no":"09",
     * "arrive_time":"20:35"},{"station_name":"江油","start_time":"20:57","isEnabled":false,
     * "stopover_time":"2分钟","station_no":"10","arrive_time":"20:55"},{"station_name":"剑门关",
     * "start_time":"21:29","isEnabled":false,"stopover_time":"2分钟","station_no":"11",
     * "arrive_time":"21:27"},{"station_name":"广元","start_time":"21:43","isEnabled":false,
     * "stopover_time":"----","station_no":"12","arrive_time":"21:43"}]}
     */

    private long responseTime;
    private String message;
    private int code;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * station_name : 峨眉山
             * train_class_name : 动车
             * start_time : 18:12
             * start_station_name : 峨眉山
             * service_type : 2
             * station_train_code : C6314
             * end_station_name : 广元
             * isEnabled : false
             * stopover_time : ----
             * station_no : 01
             * arrive_time : 18:12
             */

            private String station_name;
            private String train_class_name;
            private String start_time;
            private String start_station_name;
            private String service_type;
            private String station_train_code;
            private String end_station_name;
            private boolean isEnabled;
            private String stopover_time;
            private String station_no;
            private String arrive_time;

            public String getStation_name() {
                return station_name;
            }

            public void setStation_name(String station_name) {
                this.station_name = station_name;
            }

            public String getTrain_class_name() {
                return train_class_name;
            }

            public void setTrain_class_name(String train_class_name) {
                this.train_class_name = train_class_name;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getStart_station_name() {
                return start_station_name;
            }

            public void setStart_station_name(String start_station_name) {
                this.start_station_name = start_station_name;
            }

            public String getService_type() {
                return service_type;
            }

            public void setService_type(String service_type) {
                this.service_type = service_type;
            }

            public String getStation_train_code() {
                return station_train_code;
            }

            public void setStation_train_code(String station_train_code) {
                this.station_train_code = station_train_code;
            }

            public String getEnd_station_name() {
                return end_station_name;
            }

            public void setEnd_station_name(String end_station_name) {
                this.end_station_name = end_station_name;
            }

            public boolean isIsEnabled() {
                return isEnabled;
            }

            public void setIsEnabled(boolean isEnabled) {
                this.isEnabled = isEnabled;
            }

            public String getStopover_time() {
                return stopover_time;
            }

            public void setStopover_time(String stopover_time) {
                this.stopover_time = stopover_time;
            }

            public String getStation_no() {
                return station_no;
            }

            public void setStation_no(String station_no) {
                this.station_no = station_no;
            }

            public String getArrive_time() {
                return arrive_time;
            }

            public void setArrive_time(String arrive_time) {
                this.arrive_time = arrive_time;
            }
        }
    }
}
