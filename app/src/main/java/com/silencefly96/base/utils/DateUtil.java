package com.silencefly96.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

//时间相关工具类
public class DateUtil {

    //获得当前详细时间
    public static String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss",Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    //获得当前详细时间
    public static String getYouDate(String like) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(like,Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    //获得当前时间--年.月.日
    public static String yyyy_MM_dd() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd",Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    //获得当前时间--小时:分钟:秒
    public static String HH_mm_ss() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss",Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    //获得当前时间--小时.分钟
    public static String HH_mm() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm",Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    //获取当日时间--年
    public static String getYear(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy",Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);

    }

    //获得当日时间--月
    public static String getMouth(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM",Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    //获得当日时间--日
    public static String getDay(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd",Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    //获得指定时间--年
    public static String getYear(String time){
        SimpleDateFormat old = new SimpleDateFormat("yyyy.MM.dd",Locale.CHINA);
        SimpleDateFormat neww = new SimpleDateFormat("yyyy",Locale.CHINA);
        String year = "year_null";
        try {
            year=neww.format(old.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return year;
    }

    //获得指定时间--月
    public static String getMouth(String time){
        SimpleDateFormat old = new SimpleDateFormat("yyyy.MM.dd",Locale.CHINA);
        SimpleDateFormat neww = new SimpleDateFormat("MM",Locale.CHINA);
        String mouth = "mouth_null";
        try {
            mouth=neww.format(old.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mouth;
    }

    //获得指定时间--当月--第几周
    public static String getWeek(String time){
        SimpleDateFormat old = new SimpleDateFormat("yyyy.MM.dd",Locale.CHINA);
        SimpleDateFormat newww = new SimpleDateFormat("W",Locale.CHINA);
        //newww.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String week = "week_null";
        try {
            week=newww.format(old.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return week;
    }

    //获得指定时间--日
    public static String getDay(String time){
        SimpleDateFormat old = new SimpleDateFormat("yyyy.MM.dd",Locale.CHINA);
        SimpleDateFormat newww = new SimpleDateFormat("dd",Locale.CHINA);
        String day = "day_null";
        try {
            day=newww.format(old.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    //获得北京时间--小时
    public static String getNowHH(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH",Locale.CHINA);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    //获得当前秒数
    public static String getNowMM(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm",Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    //字符串时间转date型
    public static Date str2Date(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //日期往前推一天
    public static String yesterday(String time){

        Calendar calendar =Calendar.getInstance();
        calendar.set(Integer.valueOf(DateUtil.getYear(time))
                ,Integer.valueOf(DateUtil.getMouth(time)) - 1
                ,Integer.valueOf(DateUtil.getDay(time)));
        calendar.add( Calendar.DATE, -1); //向前走一天
        Date date= calendar.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd",Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    //日期往后推一天
    public static String tomorrow(String time){

        Calendar calendar =Calendar.getInstance();
        calendar.set(Integer.valueOf(DateUtil.getYear(time))
                ,Integer.valueOf(DateUtil.getMouth(time)) - 1
                ,Integer.valueOf(DateUtil.getDay(time)));
        calendar.add( Calendar.DATE, 1); //向后走一天
        Date date= calendar.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd",Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    public static String formatSeconds(long seconds) {
        int temp = 0;
        StringBuffer sb = new StringBuffer();
        if (seconds > 3600) {
            temp = (int) (seconds / 3600);
            sb.append((seconds / 3600) < 10 ? "0" + temp + ":" : temp + ":");
            temp = (int) (seconds % 3600 / 60);
            changeSeconds(seconds, temp, sb);
        } else {
            temp = (int) (seconds % 3600 / 60);
            changeSeconds(seconds, temp, sb);
        }
        return sb.toString();
    }

    private static void changeSeconds(long seconds, int temp, StringBuffer sb) {
        sb.append((temp < 10) ? "0" + temp + ":" : "" + temp + ":");
        temp = (int) (seconds % 3600 % 60);
        sb.append((temp < 10) ? "0" + temp : "" + temp);
    }
}
