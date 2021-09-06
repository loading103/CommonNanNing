package com.daqsoft.busquery.buscommon;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-4-10.18:43
 * @since JDK 1.8
 */

public class BusCommon {
    public static final String DEFAULT1 = "yyyy-MM-dd";
    public static final Map<Integer, String> todyMap = new HashMap<Integer, String>();
    public static final Map<Integer, String> weekMap = new HashMap<Integer, String>();
    static {
        weekMap.put(1, "周日");
        weekMap.put(2, "周一");
        weekMap.put(3, "周二");
        weekMap.put(4, "周三");
        weekMap.put(5, "周四");
        weekMap.put(6, "周五");
        weekMap.put(7, "周六");

        todyMap.put(-2, "前天");
        todyMap.put(-1, "昨天");
        todyMap.put(0, "今天");
        todyMap.put(1, "明天");
        todyMap.put(2, "后天");
    }
    /**
     * 酒店详情
     */
    public static final String LIST_BUG = "api/gov/app/bus/busList";
    /**
     * 附近公交
     */
    public static final String BUS_NEARBY = "api/gov/app/bus/around";
    /**
     * 获得全国所有的站点
     */
    public static final String STATION = "api/gov/app/train/getStationName";
    /**
     * 获取热门城市
     */
    public static final String HOT_CITY = "api/gov/app/train/listHotStation";
    /**
     * 车站历史搜索记录
     */
    public static final String STATION_SEARCH = "api/gov/app/train/listSearchRecord";
    /**
     * 火车时刻表
     */
    public static final String TRAIN_LIST = "api/gov/app/train/trainList";
    /**
     * 保存车站记录
     */
    public static final String SAVE_STATION = "api/gov/app/train/saveSearchRecord";
    /**
     * 获得车次详情
     */
    public static final String TRAIN_DETAIL = "api/gov/app/train/queryByTrainNo";

    /**
     * 获得车次编码
     */
    public static final String TRAIN_CODE = "api/gov/app/train/queryO";
    /**
     * 秒转为分
     */
    public static String minutetofen(String miss) {
        int seconds = Integer.parseInt(miss);
        int s = seconds / 60;
        return s + "分钟";
    }

    /**
     * 计算天数差值 相差1就是相差一天
     *
     * @param date
     * @return
     */
    public static int getDaydifference(Date date) {
        long start = getStartTime().getTime();
        long end = getnowEndTime().getTime();
        long dateTime = date.getTime() - start;
        //获得一天的毫秒数
        long difference = end - start;
        //选择的是今天或者以后的日期
        if (dateTime > start) {
            long num = dateTime / difference;
            return (int) num;
        } else {
            long temp = start - date.getTime();
            return 0 - (int) (temp / difference);
        }
    }
    /**
     * 获得当天的开始时间
     *
     * @return
     */
    public static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 获得当天的结束时间
     *
     * @return
     */
    public static Date getnowEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }
    public static String format(Date date, String pattern) {
        if (date == null || pattern == null) {
            return "";
        }
        SimpleDateFormat format0 = new SimpleDateFormat(pattern);
        String time = format0.format(date.getTime());//这个就是把时间戳经过处理得到期望格式的时间
        return time;
    }
}
