package com.example.lee.leeframe.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme 日期工具类
 */

public class DateUtil {

    /**
     * 获取完整时间（年-月-日-时-分-秒）
     *
     * @param time
     * @return
     */
    public static String getFullTime(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }


    /**
     * String类型时间 转换成 Long类型时间， 需要自己指定时间格式
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static long date2Long(String dateStr, String format) {
        if (dateStr == null || dateStr.equals("")) {
            return 0;
        }
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(dateStr);
        } catch (Exception e) {
        }
        return date.getTime();
    }

    /**
     * Long类型 转换成 String 类型时间，需要自己指定时间格式
     *
     * @param t
     * @param format
     * @return
     */

    public static String long2Date(long t, String format) {
        if (t < 0) {
            return "";
        }
        SimpleDateFormat myFormatter = new SimpleDateFormat(format, Locale.CHINA);
        Date date = null;
        String dateStr = null;
        try {
            date = new Date();
            date.setTime(t);
            dateStr = myFormatter.format(date);
        } catch (Exception e) {
        }
        return dateStr;
    }

    /**
     * 指定格式（年-月-日-时-分-秒）
     *
     * @param dateStr
     * @return
     */
    public static long date2Long(String dateStr) {
        return date2Long(dateStr, "yyyy-MM-dd hh:mm:ss");
    }

    public static String long2Date(long t) {
        return long2Date(t, "yyyy-MM-dd");
    }


    /**
     * 计算相隔几天
     *
     * @param smdate 开始日期
     * @param bdate  结束日期
     * @return
     */
    public static int daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-drd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }


    /**
     * 转换距离多长时间 (如：1天前， 3分钟前...)
     *
     * @param t
     * @return
     */
    public static String getDateHowLongAgo(long t) {
        StringBuffer sb = new StringBuffer();
        long time = System.currentTimeMillis() - t;
        long mill = (long) Math.ceil(time / 1000);//秒前

        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前

        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

        if (day - 4 > 0) {
            return long2Date(t);
        } else if (day - 1 > 0) {
            sb.append((day - 1) + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }

    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);

    }

    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;

    }

    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DATE);

    }

    // 12小时制
    public static int getHourBy12(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR);

    }

    // 24小时制
    public static int getHourBy24(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);

    }

    public static int getCurrentMinutes() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.HOUR_OF_DAY) * 60 + c.get(Calendar.MINUTE);
    }



}
