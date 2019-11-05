package com.wandao.myapplication.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;

import com.wandao.myapplication.utils.DbUtils;
import com.wandao.myapplication.utils.EmailUtil;
import com.wandao.myapplication.utils.ExcelUtil;
import com.wandao.myapplication.utils.SPUtils;

import java.util.Calendar;

import static android.app.AlarmManager.ELAPSED_REALTIME;
import static com.serenegiant.utils.UIThreadHelper.runOnUiThread;
import static com.wandao.myapplication.activity.LogcatActivity.getThisWeekFriday;
import static com.wandao.myapplication.activity.LogcatActivity.getThisWeekMonday;

public class SendEmailService extends Service {
    String status=new String();
    String mFilePath;
    String [] path;
    private String filePath = Environment.getExternalStorageDirectory() + "/WDExcel";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        initAlarmDay();
        initAlarmWeek();
        initAlarmMonth();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent!=null){
            String email= SPUtils.get(this,"email","NYMCM1215@163.COM").toString();
            status = intent.getStringExtra("log");
            if (status!=null) {
                if (status.equals("day")) {
                    String excelDayFileName = "/万道日报.xls";
                    ExcelUtil.writeWeekObjListToExcel(DbUtils.weekLog(getThisWeekMonday().getTime(), getThisWeekFriday().getTime()), filePath + excelDayFileName, getApplicationContext());
                    mFilePath = filePath + "/万道日报.xls";
                    path = new String[]{mFilePath};
                    EmailUtil.autoSendFileMail("万道日志日报", "万道日报", email, path);
                }
                if (status.equals("week")) {
                    String excelWeekFileName = "/万道周报.xls";
                    ExcelUtil.writeWeekObjListToExcel(DbUtils.weekLog(getThisWeekMonday().getTime(), getThisWeekFriday().getTime()), filePath + excelWeekFileName, getApplicationContext());
                    mFilePath = filePath + "/万道周报.xls";
                    path = new String[]{mFilePath};
                    EmailUtil.autoSendFileMail("万道日志周报", "万道周报", email, path);
                }
                if (status.equals("month")) {
                    String excelMonthFileName = "/万道月报.xls";
                    ExcelUtil.writeWeekObjListToExcel(DbUtils.weekLog(getThisWeekMonday().getTime(), getThisWeekFriday().getTime()), filePath + excelMonthFileName, getApplicationContext());
                    mFilePath = filePath + "/万道月报.xls";
                    path = new String[]{mFilePath};
                    EmailUtil.autoSendFileMail("万道日志月报", "万道月报", email, path);
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void initAlarmDay(){             //打开一个时间广播，去进行相关操作
        Intent intent = new Intent(getApplicationContext(), SendEmailService.class);
        intent.putExtra("log","day");
        PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), ELAPSED_REALTIME, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        // Schedule the alarm!
        AlarmManager am = (AlarmManager) getApplicationContext()
                .getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 0);
       // calendar.set(Calendar.DAY_OF_WEEK,1);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),1000
                ,sender);

    }

    public void initAlarmWeek(){             //打开一个时间广播，去进行相关操作
        Intent intent = new Intent(getApplicationContext(), SendEmailService.class);
        intent.putExtra("log","week");
        PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), ELAPSED_REALTIME, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        // Schedule the alarm!
        AlarmManager am = (AlarmManager) getApplicationContext()
                .getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_WEEK,1);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),1000
                ,sender);

    }
    public void initAlarmMonth(){             //打开一个时间广播，去进行相关操作
        Intent intent = new Intent(getApplicationContext(), SendEmailService.class);
        intent.putExtra("log","month");
        PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), ELAPSED_REALTIME, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        // Schedule the alarm!
        AlarmManager am = (AlarmManager) getApplicationContext()
                .getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),1000
                ,sender);

    }






}
