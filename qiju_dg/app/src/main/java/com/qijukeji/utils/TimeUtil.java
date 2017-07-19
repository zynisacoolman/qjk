package com.qijukeji.utils;

import android.app.ProgressDialog;
import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Administrator on 2017/5/16.
 */

public class TimeUtil {
    static Date date;
    static ProgressDialog dia;

    //时间格式转化(毫秒转字符串)
    public static String getFormatedDateTime(String dateTime) {
        long time = Long.parseLong(dateTime);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return sDateFormat.format(new Date(time));
    }

    //获取系统时间
    public static String getDate() {
        date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strtime = dateFormat.format(date);
        return strtime;
    }

    //系统时间之前7天的日期
    public static String getWeek() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -6);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strtime = dateFormat.format(cal.getTime());
        String week = strtime;
        return week;
    }

    //系统时间之前30天的日期
    public static String getMoon() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -29);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strtime = dateFormat.format(cal.getTime());
        String moon = strtime;
        return moon;
    }

    //获取系统时间
    public static String getDateshow() {
        date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("MM月dd日");
        String strtime = dateFormat.format(date);
        return strtime;
    }

    //系统时间之前7天的日期
    public static String getWeekshow() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -6);
        DateFormat dateFormat = new SimpleDateFormat("MM月dd日");
        String strtime = dateFormat.format(cal.getTime());
        String week = strtime;
        return week;
    }

    //系统时间之前7天的日期
    public static String getMoonshow() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -29);
        DateFormat dateFormat = new SimpleDateFormat("MM月dd日");
        String strtime = dateFormat.format(cal.getTime());
        String moon = strtime;
        return moon;
    }

    public static void waitwhile(Context context) {
        dia = new ProgressDialog(context);
        dia.setMessage("loading....");
        dia.setCancelable(false);
        dia.show();
    }

    public static void waitoneover() {
        try {
            Thread.sleep(1500);
            dia.dismiss();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitover() {
        dia.dismiss();
    }
}
