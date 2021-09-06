package com.daqsoft.commonnanning.utils;

/**
 * <p>Title: 时间和日期的工具类</p>
 * <p>Description: DateUtil类包含了标准的时间和日期格式，以及这些格式在字符串及日期之间转换的方法</p>
 * <p>Copyright: Copyright (c) 2007 advance,Inc. All Rights Reserved</p>
 *
 * @author 黄熙
 * @version 1.0.0
 * @date 2018.05.07
 * @since JDK 1.8
 */

import com.example.tomasyb.baselib.util.ObjectUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    public static String datePattern = "yyyy-MM-dd HH:mm:ss";
    public static String dateYMD = "yyyy-MM-dd";
    public static String YMD = "yyyy年MM月dd日";
    public static String MD = "MM月dd日";
    public static String YM = "yyyy-MM";
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 精确到毫秒
     */
    public static final String MS_FORMART = "yyyy-MM-dd HH:mm:ss SSS";

    private static String timePattern = datePattern + " HH:MM a";
    public static final String YMDHM = "yyyy-MM-dd HH:mm";

    // ~ Methods
    // ================================================================

    /**
     * Return default datePattern (MM/dd/yyyy)
     *
     * @return a string representing the date pattern on the UI
     */
    public static String getDatePattern() {
        return datePattern;
    }

    /**
     * This method attempts to convert an Oracle-formatted date in the form
     * dd-MMM-yyyy to mm/dd/yyyy.
     *
     * @param aDate date from database as a string
     * @return formatted string for the ui
     */
    public static final String getDate(Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(datePattern);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    public static final String date2Str(Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(datePattern);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    public static final String date2Str(String pattern, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(pattern);
            returnValue = df.format(aDate);
        }
        return (returnValue);
    }

    public static String date2Str(Calendar c, String format) {
        if (c == null) {
            return null;
        }
        return date2Str(c.getTime(), format);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     * 返回相应的数据格式的数据
     */
    public static String date2Str(Date d, String format) {

        if (d == null) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(d);
        return s;
    }

    /**
     * 根据字符串2018-05-01获取日
     *
     * @param date
     * @return
     */
    public static int dateFormDay(String date) {
        int day = 0;
        String[] days = date.split("-");
        if (ObjectUtils.isNotEmpty(days) && days.length > 0) {
            String s = days[days.length - 1];
            s = s.startsWith("0") ? s.replace("0", "") : s;
            day = Integer.parseInt(s);
        }
        return day;
    }

    public static Date str2DateXMPP(String str, String format) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(str);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;

    }

    /**
     * This method generates a string representation of a date/time in the
     * format you specify on input
     *
     * @param aMask   the date pattern the string is in
     * @param strDate a string representation of a date
     * @return a converted Date object
     * @throws ParseException
     * @see SimpleDateFormat
     */
    public static final Date convertStringToDate(String aMask, String strDate)
            throws ParseException {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);
        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            return null;
        }

        return (date);
    }

    public static final Date str2Date(String aMask, String strDate)
            throws ParseException {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            return null;
        }

        return (date);
    }

    /**
     * This method returns the current date time in the format: MM/dd/yyyy HH:MM
     * a
     *
     * @param theTime the current time
     * @return the current date/time
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(timePattern, theTime);
    }

    /**
     * This method returns the current date in the format: MM/dd/yyyy
     *
     * @return the current date
     * @throws ParseException
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(datePattern);

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));

        return cal;
    }

    /**
     * This method generates a string representation of a date based on the
     * System Property 'dateFormat' in the format you specify on input
     *
     * @param aDate A date to convert
     * @return a string representation of the date
     */
    public static final String convertDateToString(Date aDate) {
        return getDateTime(datePattern, aDate);
    }


    /**
     * This method generates a string representation of a date's date/time in
     * the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param aDate a date object
     * @return a formatted string representation of the date
     * @see SimpleDateFormat
     */
    public static final String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
            System.out.print("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    public static final String convertDateToString(Date aDate, String format) {
        return getDateTime(format, aDate);
    }

    public static final long convertDateToLong(Date aDate) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        aDate = sdf.parse(sdf.format(aDate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(aDate);
        long time = cal.getTimeInMillis();
        return time;
    }

    public static final String convertDateToStringOnlyYMD(Date aDate) {
        return getDateTime(dateYMD, aDate);
    }

    /**
     * This method converts a String to a date using the datePattern
     *
     * @param strDate the date to convert (in format MM/dd/yyyy)
     * @return a date object
     * @throws ParseException
     */
    public static Date convertStringToDate(String strDate)
            throws ParseException {
        Date aDate = null;
        try {
            aDate = convertStringToDate(datePattern, strDate);
        } catch (ParseException pe) {
            // log.error("Could not convert '" + strDate
            // + "' to a date, throwing exception");
            pe.printStackTrace();
            return null;

        }
        return aDate;
    }

    // 日期格式转换成时间戳
    public static long getTimeStamp(String pattern, String strDate) {
        long returnTimeStamp = 0;
        Date aDate = null;
        try {
            aDate = convertStringToDate(pattern, strDate);
        } catch (ParseException pe) {
            aDate = null;
        }
        if (aDate == null) {
            returnTimeStamp = 0;
        } else {
            returnTimeStamp = aDate.getTime();
        }
        return returnTimeStamp;
    }

    // 获取当前日期的邮戳
    public static long getNowTimeStamp() {
        long returnTimeStamp = 0;
        Date aDate = null;
        try {
            aDate = convertStringToDate("yyyy-MM-dd HH:mm:ss", getNowDateTime());
        } catch (ParseException pe) {
            aDate = null;
        }
        if (aDate == null) {
            returnTimeStamp = 0;
        } else {
            returnTimeStamp = aDate.getTime();
        }
        return returnTimeStamp;
    }

    /**
     * 得到格式化后的系统当前日期
     *
     * @param strScheme 格式模式字符串
     * @return 格式化后的系统当前时间，如果有异常产生，返回空串""
     */
    public static final String getNowDateTime(String strScheme) {
        String strReturn = null;
        Date now = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(strScheme);
            strReturn = sdf.format(now);
        } catch (Exception e) {
            strReturn = "";
        }
        return strReturn;
    }

    public static final String getNowDate(String res) {
        String strReturn = null;
        Date now = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(res);
            strReturn = sdf.format(now);
        } catch (Exception e) {
            strReturn = "";
        }
        return strReturn;
    }

    public static final String getDateLongToString(long mill, String strScheme) {
        String strReturn = null;
        Date now = new Date(mill);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(strScheme);
            strReturn = sdf.format(now);
        } catch (Exception e) {
            strReturn = "";
        }
        return strReturn;
    }

    public static final String getNowDateTime() {
        String strReturn = null;
        Date now = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            strReturn = sdf.format(now);
        } catch (Exception e) {
            strReturn = "";
        }
        return strReturn;
    }

    public static final String getNowDateTimeYMD() {
        String strReturn = null;
        Date now = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            strReturn = sdf.format(now);
        } catch (Exception e) {
            strReturn = "";
        }
        return strReturn;
    }

    /**
     * 转化日期格式"MM/dd/YY、MM.dd.YY、MM-dd-YY、MM/dd/YY"，并输出为正常的格式yyyy-MM-dd
     *
     * @param strDate 待转换的日期格式
     * @return 格式化后的日期，如果有异常产生，返回空串""
     */
    public static final String convertNormalDate(String strDate) {
        String strReturn = null;
        // 先把传入参数分格符转换成java认识的分格符
        String[] date_arr = strDate.split("\\.|\\/|\\-");
        try {
            if (date_arr.length == 3) {
                if (date_arr[2].length() != 4) {
                    String nowYear = getNowDateTime("yyyy");
                    date_arr[2] = nowYear.substring(0, 2) + date_arr[2];
                }
                strReturn = DateUtil.getDateTime("yyyy-MM-dd",
                        convertStringToDate(combineStringArray(date_arr, "/")));
            }

        } catch (Exception e) {
            return strReturn;
        }
        return strReturn;
    }

    /**
     * 将字符串数组使用指定的分隔符合并成一个字符串。
     *
     * @param array 字符串数组
     * @param delim 分隔符，为null的时候使用""作为分隔符（即没有分隔符）
     * @return 合并后的字符串
     * @since 0.4
     */
    public static final String combineStringArray(String[] array, String delim) {
        int length = array.length - 1;
        if (delim == null) {
            delim = "";
        }
        StringBuffer result = new StringBuffer(length * 8);
        for (int i = 0; i < length; i++) {
            result.append(array[i]);
            result.append(delim);
        }
        result.append(array[length]);
        return result.toString();
    }

    public static final int getWeekNum(String strWeek) {
        int returnValue = 0;
        if (strWeek.equals("Mon")) {
            returnValue = 1;
        } else if (strWeek.equals("Tue")) {
            returnValue = 2;
        } else if (strWeek.equals("Wed")) {
            returnValue = 3;
        } else if (strWeek.equals("Thu")) {
            returnValue = 4;
        } else if (strWeek.equals("Fri")) {
            returnValue = 5;
        } else if (strWeek.equals("Sat")) {
            returnValue = 6;
        } else if (strWeek.equals("Sun")) {
            returnValue = 0;
        } else if (strWeek == null) {
            returnValue = 0;
        }

        return returnValue;
    }

    /**
     * 得到格式化后的指定日期
     *
     * @param strScheme 格式模式字符串
     * @param date      指定的日期值
     * @return 格式化后的指定日期，如果有异常产生，返回空串""
     */
    public static final String getDateTime(Date date, String strScheme) {
        String strReturn = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(strScheme);
            strReturn = sdf.format(date);
        } catch (Exception e) {
            strReturn = "";
        }

        return strReturn;
    }

    /**
     * 获取日期
     *
     * @param timeType 时间类型，譬如：Calendar.DAY_OF_YEAR
     * @param timenum  时间数字，譬如：-1 昨天，0 今天，1 明天
     * @return 日期
     */
    public static final Date getDateFromNow(int timeType, int timenum) {
        Calendar cld = Calendar.getInstance();
        cld.set(timeType, cld.get(timeType) + timenum);
        return cld.getTime();
    }

    /**
     * 获取当前日期
     *
     * @param timeType      时间类型，譬如：Calendar.DAY_OF_YEAR
     * @param timenum       时间数字，譬如：-1 昨天，0 今天，1 明天
     * @param format_string 时间格式，譬如："yyyy-MM-dd HH:mm:ss"
     * @return 字符串
     */
    public static final String getDateFromNow(int timeType, int timenum,
                                              String format_string) {
        if ((format_string == null) || (format_string.equals("")))
            format_string = "yyyy-MM-dd HH:mm:ss";
        Calendar cld = Calendar.getInstance();
        Date date = null;
        DateFormat df = new SimpleDateFormat(format_string);
        cld.set(timeType, cld.get(timeType) + timenum);
        date = cld.getTime();
        return df.format(date);
    }

    /**
     * 获取当前日期的字符串
     *
     * @param format_string 时间格式，譬如："yyyy-MM-dd HH:mm:ss"
     * @return 字符串
     */
    public static final String getDateNow(String format_string) {
        if ((format_string == null) || (format_string.equals("")))
            format_string = "yyyy-MM-dd HH:mm:ss";
        Calendar cld = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat(format_string);
        return df.format(cld.getTime());
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
     */
    public static String getNextDay(String nowdate, String delay) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String mdate = "";
            Date d = strToDate(nowdate);
            long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24
                    * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = format.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将长时间格式字符串转换为Long值的时间戳
     *
     * @param strDate yyyy-MM-dd HH:mm:ss.0的时间字符串
     * @return long的时间
     */
    public static long str2Long(String strDate) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = df.parse(strDate);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将长时间格式字符串转换为Long值的时间戳
     *
     * @param strDate yyyy-MM-dd HH:mm:ss.0的时间字符串
     * @return long的时间
     */
    public static long shortStr2Long(String strDate) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = df.parse(strDate);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(String smdate, String bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date sDate, eDate;
        try {
            sDate = sdf.parse(smdate);
            eDate = sdf.parse(bdate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sDate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(eDate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(long smdate, long bdate) {
        long between_days = (bdate - smdate) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }


    /**
     * 根据日期 找到对应日期的 星期
     */
    public static String getDayOfWeekByDate(Date date) {
        String dayOfweek = "-1";
        try {
            Date today = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("E");
            String str = formatter.format(date);
            dayOfweek = str;

            if (today.toString().equals(date.toString())) {
                dayOfweek = "今天";
            }

        } catch (Exception e) {
            System.out.println("错误!");
        }
        return dayOfweek;
    }


    public static String getDayOfWeekByDate(long time) {
        String dayOfweek = "-1";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("E");
            String str = formatter.format(time);
            dayOfweek = str;
        } catch (Exception e) {
            System.out.println("错误!");
        }
        return dayOfweek;
    }


    /**
     * 判断现在时刻是否是白天
     *
     * @return
     */
    public static boolean isDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String hour = sdf.format(new Date());
        int k = Integer.parseInt(hour);
        if ((k >= 0 && k < 6) || (k >= 18 && k < 24)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 时间戳转换为字符串
     *
     * @return
     */
    public static String timeTo(String simple, long time) {
        SimpleDateFormat format = new SimpleDateFormat(simple);
        String d = format.format(time);
        return d;
    }

    /**
     * 传入两个时间戳，计算两个时间戳之间的差的秒数
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long timeCountDown(String time1, String time2) {
        long time = -1;
        if (ObjectUtils.isNotEmpty(time1)
                && ObjectUtils.isNotEmpty(time2)) {
            time = Long.parseLong(time1) - Long.parseLong(time2);
            if (time > 0) {
                return time / 1000;
            }
        }
        return time;
    }

    /**
     * 半小时倒计时
     *
     * @param l 时间
     * @return 时:分:秒
     */
    public static String formatLongToTimeStr(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = l.intValue();
        if (second > 60) {
            // 取整
            minute = second / 60;
            // 取余
            second = second % 60;
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        String hour1 = (hour + "").length() == 1 ? ("0" + hour) : hour + "";
        String minute1 = (minute + "").length() == 1 ? ("0" + minute) : minute + "";
        String second1 = (second + "").length() == 1 ? ("0" + second) : second + "";
        String strtime = hour1 + ":" + minute1 + ":" + second1;
        return strtime;

    }

    /**
     * 判断当前日期是否在选中的两个日期之间
     *
     * @param startDate
     * @param startDate
     * @return
     */
    public static boolean COMPARE_DATE(String date, String startDate, String endDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startTime = df.parse(startDate);
            Date endTime = df.parse(endDate);
            Date time = df.parse(date);
            if (time.getTime() > startTime.getTime()
                    && time.getTime() < endTime.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

}
