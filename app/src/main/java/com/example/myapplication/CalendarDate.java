package com.example.myapplication;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 日期帮助类  提供基本的日期格式
 *
 * @author i小灰
 */
public class CalendarDate implements Serializable {

    private int year;//年
    private int month;//月
    private int day;//日

    private boolean flag;//日历空位标志 true空位
    private boolean canSelect = true;//不可选标志 true可选

    public String year_month;//2017年01月

    public String month_day;//01月01日

    public String year_month_day;//2017年01月01日

    public String yyyy_MM_dd;//2017-01-01

    public String MM_dd;//01-01

    public String yyyyMMdd;//20170101

    //相等返回0，第一个参数大返回1(相等默认第一个大)，第二个大返回2，其中一个为空不做比较返回-1
    public static int compare(CalendarDate c1, CalendarDate c2) {
        if (c1 == null || c2 == null) {
            return -1;
        }
        if (c1.getYear() == c2.getYear()) {
            if (c1.getMonth() == c2.getMonth()) {
                if (c1.getDay() == c2.getDay()) {
                    return 0;
                } else if (c1.getDay() > c2.getDay()) {
                    return 1;
                } else {
                    return 2;
                }
            } else if (c1.getMonth() > c2.getMonth()) {
                return 1;
            } else {
                return 2;
            }
        } else if (c1.getYear() > c2.getYear()) {
            return 1;
        } else {
            return 2;
        }
    }

    //其中一个为空返回-1,返回两个日期的天数差
    public static int dayDiff(CalendarDate c1, CalendarDate c2) {
        if (c1 == null || c2 == null) {
            return -1;
        }
        if (compare(c1, c2) == 0) {
            return 0;
        } else {
            CalendarDate copy1 = copy(c1);
            CalendarDate copy2 = copy(c2);
            int index = 0;
            if (compare(copy1, copy2) == 2) {
                CalendarDate c = copy2;
                copy2 = copy1;
                copy1 = c;
            }
            while (compare(copy1, copy2) != 0) {
                index++;
                if (copy1.getDay() == 1) {
                    if (copy1.getMonth() == 1) {
                        copy1.setYear(copy1.getYear() - 1);
                        copy1.setMonth(12);
                        copy1.setDay(31);
                    } else {
                        copy1.setMonth(copy1.getMonth() - 1);
                        copy1.setDay(copy1.getCurMonthCount());
                    }
                } else {
                    copy1.setDay(copy1.getDay() - 1);
                }
            }
            return index;
        }
    }

    public static CalendarDate parseDate(String date) {//格式前面是以0000-00-00开头的都行
        String[] strs = null;
        if (date.contains(" ")) {
            strs = date.split(" ");
            if (strs != null && strs.length >= 2) {
                strs = strs[0].split("-");
            }
        } else if (date.contains("-")) {
            strs = date.split("-");
        }
        if (strs == null || strs.length < 3) {
            return null;
        }
        return new CalendarDate(Integer.valueOf(strs[0]), Integer.valueOf(strs[1]), Integer.valueOf(strs[2]));
    }

    public static CalendarDate copy(CalendarDate cd) {//格式前面是以0000-00-00开头的都行
        if (cd == null) {
            return null;
        }
        CalendarDate calendar = new CalendarDate(cd.getYear(), cd.getMonth(), cd.getDay());
        return calendar;
    }

    @Override
    public String toString() {
        return "CalendarDate [year=" + year + ", month=" + month + ", day=" + day + ", flag=" + flag + "]";
    }

    public CalendarDate() {
        Calendar c = Calendar.getInstance();
        this.year = c.get(Calendar.YEAR);//显示当前年
        this.month = c.get(Calendar.MONTH) + 1;//显示当前月，从1开始
        this.day = c.get(Calendar.DAY_OF_MONTH);//显示当前月日
        DealTime();
    }

    public CalendarDate(int year) {
        this.year = year;
        this.month = 1;
        this.day = 1;
        DealTime();
    }

    public CalendarDate(int year, int month) {
        this.year = year;
        this.month = month;
        this.day = 1;
        setMonth();
        DealTime();
    }

    public CalendarDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        setMonth();
        setDay(day);
    }

    public CalendarDate(int year, int month, int day, boolean flag) {
        this.flag = flag;
        this.year = year;
        this.month = month;
        this.day = day;
        setMonth();
        setDay(day);
    }

    public CalendarDate(int year, int month, int day, boolean flag, boolean canSelect) {
        this.flag = flag;
        this.canSelect = canSelect;
        this.year = year;
        this.month = month;
        this.day = day;
        setMonth();
        setDay(day);
    }

    public boolean getCanSelect() {
        return canSelect;
    }

    public void setCanSelect(boolean canSelect) {
        this.canSelect = canSelect;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        DealTime();
    }

    public int getMonth() {
        return month;
    }

    private void setMonth() {
        if (month < 0) {
            return;
        }
        if (month > 12) {
            this.month = month % 12;
            year = year + month / 12;
        }
    }

    public void setMonth(int month) {
        if (month < 0) {
            return;
        }
        this.month = month;
        if (this.month == 0) {
            this.month = 12;
            year = year - 1;
        } else if (month > 0) {
            if (month > 12) {
                this.month = month % 12;
                year = year + month / 12;
            } else {
                this.month = month;
            }
        }
        if (this.day > getCurMonthCount()) {
            this.day = getCurMonthCount();
        }
        DealTime();
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        if (day < 0) {
            return;
        }
        this.day = day;
        if (this.day == 0) {
            month = month - 1;
            if (month == 0) {
                month = 12;
                year = year - 1;
            }
            this.day = getCurMonthCount();
        } else if (this.day > 0) {
            while (this.day > getCurMonthCount()) {
                this.day = this.day - getCurMonthCount();
                month = month + 1;
                if (month > 12) {
                    month = 1;
                    year = year + 1;
                }
            }
        }
        DealTime();
    }

    public int getCurMonthCount() {
        int count = 0;
        if (month == 2) {
            if (year % 4 == 0) {
                count = 29;
            } else {
                count = 28;
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            count = 30;
        } else {
            count = 31;
        }
        return count;
    }

    private void DealTime() {
        year_month = year + "年" + month + "月";
        month_day = month + "月" + day + "日";
        year_month_day = year + "年" + month + "月" + day + "日";
        String m = "";
        String d = "";
        if (month < 10) {
            m = "0" + month;
        } else {
            m = "" + month;
        }
        if (day < 10) {
            d = "0" + day;
        } else {
            d = "" + day;
        }
        MM_dd = m + "-" + d;
        yyyy_MM_dd = year + "-" + m + "-" + d;
        yyyyMMdd = year + m + d;
    }

}
