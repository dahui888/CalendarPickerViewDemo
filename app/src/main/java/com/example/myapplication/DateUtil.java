package com.example.myapplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    /**
     * HH:mm
     */
    public static final String pattern1 = "HH:mm";
    /**
     * yyyy-MM-dd
     */
    public static final String pattern2 = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String pattern3 = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy年MM月dd日 HH:mm
     */
    public static final String pattern4 = "yyyy年MM月dd日 HH:mm";
    /**
     * yyyy年MM月dd日 HH:mm:ss
     */
    public static final String pattern5 = "yyyy年MM月dd日 HH:mm:ss";
    /**
     * yyyy-MM-ddTHH:mm:ss
     */
    public static final String pattern6 = "yyyy-MM-dd\'T\'HH:mm:ss";
    /**
     * yyyy-MM-ddTHH:mm:ss.SSSZ
     */
    public static final String pattern7 = "yyyy-MM-dd\'T\'HH:mm:ss.SSSZ";
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String pattern8 = "yyyy-MM-dd HH:mm";
    /**
     * yyyy-MM
     */
    public static final String pattern9 = "yyyy-MM";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String pattern10 = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * yyyy年MM月dd日
     */
    public static final String pattern11 = "yyyy年MM月dd日";
    /**
     * yyyy年MM月
     */
    public static final String pattern12 = "yyyy年MM月";
    /**
     * dd
     */
    public static final String pattern13 = "dd日";
    /**
     * yyyyMMddHHmmss
     */
    public static final String pattern14 = "yyyyMMddHHmmss";
    /**
     * yyyyMMdd
     */
    public static final String pattern15 = "yyyyMMdd";
    /**
     * MM-dd
     */
    public static final String pattern16 = "MM-dd";
    /**
     * MM月dd日
     */
    public static final String pattern17 = "MM月dd日";
    /**
     * MM月dd日 HH:mm
     */
    public static final String pattern18 = "MM月dd日 HH:mm";

    public static SimpleDateFormat format;

    static {
        if (format == null) {
            synchronized (DateUtil.class) {
                if (format == null) {
                    format = new SimpleDateFormat();
                }
            }
        }
    }

    public static String format(String time, String parsePattern, String pattern) {
        try {
            format.applyPattern(parsePattern);
            Date parse = format.parse(time);
            format.applyPattern(pattern);
            return format.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String format(long time, String pattern) {
        format.applyPattern(pattern);
        return format.format(new Date(time));
    }

    public static Date parseDate(String time, String pattern) {
        Date date = null;
        try {
            format.applyPattern(pattern);
            date = format.parse(time);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String utcParseLocal(String time, String utcPattern, String localPattern) {
        try {
            format.applyPattern(utcPattern);
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date parse = format.parse(time);
            format.applyPattern(localPattern);
            format.setTimeZone(TimeZone.getDefault());
            return format.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getLong(String time, String pattern) {
        try {
            format.applyPattern(pattern);
            return format.parse(time).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param date 日期格式  0000-00-00 00:00:00
     * @return 时间毫秒数
     */
    public static long getMesc(String date) {
        return getMesc(date, pattern3);
    }

    /**
     * @param date
     * @param pattern
     * @return 时间毫秒数
     */
    public static long getMesc(String date, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param date 日期格式  0000-00-00 00:00:00
     * @return 0000-00-00
     */
    public static String getDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern3);
            return format(sdf.parse(date).getTime(), pattern2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param date 日期格式  0000-00-00 00:00:00
     * @return 0000-00-00
     */
    public static String getDate(String date, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return format(sdf.parse(date).getTime(), pattern2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param date 日期格式  0000-00-00 00:00:00
     * @return 00:00
     */
    public static String getTime(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern3);
            return format(sdf.parse(date).getTime(), pattern1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param date 日期格式  0000-00-00 00:00:00
     * @return 00:00
     */
    public static String getTime(String date, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return format(sdf.parse(date).getTime(), pattern1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDayofweek(CalendarDate calendarDate) {
        return getDayofweek(calendarDate.getYear(), calendarDate.getMonth(), calendarDate.getDay());
    }

    //得到当天星期几（注意month是几就传几，比如7月份。就传7）
    public static String getDayofweek(int year, int month, int day) {
        String[] strs = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        if (month > 0) {
            cal.set(year, month - 1, day);
        } else {
            cal.set(year - 1, 11, day);
        }
        return strs[cal.get(Calendar.DAY_OF_WEEK) - 1];
    }

    public static String isNext(CalendarDate c) {
        return isNext(c, new CalendarDate());
    }

    public static String isNext(CalendarDate isNext, CalendarDate now) {
        CalendarDate curCd = new CalendarDate(now.getYear(), now.getMonth(), now.getDay());
        if (curCd.getYear() == isNext.getYear() && curCd.getMonth() == isNext.getMonth() && curCd.getDay() == isNext.getDay()) {
            return "今天";
        }
        curCd.setDay(curCd.getDay() + 1);
        if (curCd.getYear() == isNext.getYear() && curCd.getMonth() == isNext.getMonth() && curCd.getDay() == isNext.getDay()) {
            return "明天";
        }
        curCd.setDay(curCd.getDay() + 1);
        if (curCd.getYear() == isNext.getYear() && curCd.getMonth() == isNext.getMonth() && curCd.getDay() == isNext.getDay()) {
            return "后天";
        }
        return "";
    }

    /**
     * 将时间戳转换为时间 年
     */
    public static String getYear(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为时间 月
     */
    public static String getMonth(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为时间 日
     */
    public static String getDay(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

}
