package com.wandao.myapplication.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    //创建 SingleObject 的一个对象
    private static TimeUtil instance = new TimeUtil();

    //让构造函数为 private，这样该类就不会被实例化
    private TimeUtil(){}

    //获取唯一可用的对象
    public static TimeUtil getInstance(){
        return instance;
    }

  //  String format = simpleDateFormat.format(new Date());
      public long getLastMonthFirstDay(){
        Calendar cal_1=Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
          Date date= cal_1.getTime();
          date.setHours(0);
          date.setMinutes(0);
          date.setSeconds(0);
          return cal_1.getTime().getTime();
    }
    public boolean booleanMonthLastDay(){

        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));

//        Calendar cal_1=Calendar.getInstance();//获取当前日期
//        cal_1.add(Calendar.MONTH, -1);
//        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        Date dateCal= ca.getTime();
Date date=new Date();
if (dateCal.getDate()==date.getDate())
    return true;
else return false;

    }

    public long getLastMonthEndDay(){
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天
        return cale.getTime().getTime();
    }

    public static long getTodayStart(){
        Date date=new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return date.getTime();
    }
    public static long getTodayEnd(){
        Date date=new Date();
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
        return date.getTime();
    }

    public static void getCurrentTime() {
        Date date = new Date();
    }
}
