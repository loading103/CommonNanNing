package com.daqsoft.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间工具类
 *
 *@author MouJunFeng
 * @time 2018-3-14
 * @since JDK 1.8
 * @version 1.0.0
 */

public class TimerUtils {
    /**
     * 星期集合
     */
    public static final Map<Integer, String> weekMap = new HashMap<Integer, String>();
    /**
     * 相对今天的集合
     */
    public static final Map<Integer, String> todyMap = new HashMap<Integer, String>();

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
     * 时间戳转成时间数
     *
     * @param mss
     * @return
     */
    public static String formatDuring(long mss) {
        if (mss < 1000) {
            return "00:00";
        }
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        String minutesValue = minutes + "";
        String secondsValue = seconds + "";
        String hoursValue = hours + "";
        if (seconds < 10) {
            secondsValue = "0" + seconds;
        }
        if (minutes < 10) {
            minutesValue = "0" + minutes;
        }
        if (hours < 10) {
            hoursValue = "0" + hours;
        }
        if (hours == 0) {
            return minutesValue + " : " + secondsValue;
        }
        if (days == 0) {
            return hoursValue + " : " + minutesValue + " : " + secondsValue;
        }
        return days + " : " + hoursValue + " : " + minutesValue + " : "
                + secondsValue;
    }
    /**
     * 时间戳转成时间数
     *
     * @return
     */
    public static String format(String value) {
        try{
            long mss = Long.parseLong(value)*1000;

            if (mss < 1000) {
                return "00:00";
            }
            long days = mss / (1000 * 60 * 60 * 24);
            long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
            long seconds = (mss % (1000 * 60)) / 1000;
            String minutesValue = minutes + "";
            String secondsValue = seconds + "";
            String hoursValue = hours + "";
            if (seconds < 10) {
                secondsValue = seconds+"";
            }
            if (minutes < 10) {
                minutesValue = minutes+"";
            }
            if (hours < 10) {
                hoursValue = hours+"";
            }
            if (hours == 0) {
                return minutesValue + " 分鈡 ";
            }
            if (days == 0) {
                return hoursValue + " 小时 " + minutesValue + " 分钟 ";
            }
            return days + " 天 " + hoursValue + " 小时 " + minutesValue + " 分钟 ";
        }catch (Exception e){
            return "";
        }

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

    /**
     * 格式化日期
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        if(date == null || pattern == null){
            return "";
        }
        SimpleDateFormat format0 = new SimpleDateFormat(pattern);
        //这个就是把时间戳经过处理得到期望格式的时间
        String time = format0.format(date.getTime());
        return time;
    }
}
