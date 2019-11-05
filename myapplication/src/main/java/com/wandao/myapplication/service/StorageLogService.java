package com.wandao.myapplication.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.wandao.myapplication.utils.ExcelUtil;
import com.wandao.myapplication.utils.TimeUtil;

import java.util.Calendar;
import java.util.Date;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class StorageLogService extends Service {
    String status=new String();
    private static String fileDayPath = Environment.getExternalStorageDirectory() + "/存储柜统计报表/日报表";
    private static String fileWeekPath = Environment.getExternalStorageDirectory() + "/存储柜统计报表/周报表";
    private static String fileMonthPath = Environment.getExternalStorageDirectory() + "/存储柜统计报表/月报表";
    //   public static AlarmManager am;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAlarmDay();
        initAlarmWeek();
        //     am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);


        IntentFilter filter = new IntentFilter();
        filter.addAction("com.wandao.alarmDayManager");
        StorageLogService.this.registerReceiver(alarmDayReceiver, filter);




        IntentFilter filter2 = new IntentFilter();
        filter2.addAction("com.wandao.alarmWeekManager");
        StorageLogService.this.registerReceiver(alarmWeekReceiver, filter);

        //     initAlarmMonth();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent!=null){
            status = intent.getStringExtra("log");
            if (status!=null) {
                if (status.equals("day")) {

                    Intent dayIntent = new Intent();
                    dayIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    dayIntent.setAction("com.wandao.alarmDayManager");
                    StorageLogService.this.sendBroadcast(dayIntent);    // 重复发送广播
                    try {
                        Log.d("time_peck","000000000");
                        ExcelUtil.exportDayExcel(getApplicationContext(),fileDayPath);
                    }catch (Exception e)
                    {
                        Log.d("time_peck",e.toString());
                    }
                    if (TimeUtil.getInstance().booleanMonthLastDay()){
                        ExcelUtil.exportMonthExcel(getApplicationContext(),fileMonthPath);
                    }
//                    String excelDayFileName = "/万道日报.xls";
//                    ExcelUtil.writeWeekObjListToExcel(DbUtils.weekLog(getThisWeekMonday().getTime(), getThisWeekFriday().getTime()), fileDayPath + excelDayFileName, getApplicationContext());
                }
                if (status.equals("week")) {


                    Intent weekIntent = new Intent();
                    weekIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    weekIntent.setAction("com.wandao.alarmWeekManager");
                    StorageLogService.this.sendBroadcast(weekIntent);    // 重复发送广播
                    ExcelUtil.exportWeekExcel(getApplicationContext(),fileWeekPath);
//                    String excelWeekFileName = "/万道周报.xls";
//                    ExcelUtil.writeWeekObjListToExcel(DbUtils.weekLog(getThisWeekMonday().getTime(), getThisWeekFriday().getTime()), fileWeekPath + excelWeekFileName, getApplicationContext());
//                    mFilePath = filePath + "/万道周报.xls";
//                    path = new String[]{mFilePath};
//                    EmailUtil.autoSendFileMail("万道日志周报", "万道周报", email, path);
                }
                if (status.equals("month")) {
                    ExcelUtil.exportMonthExcel(getApplicationContext(),fileMonthPath);
//                    String excelMonthFileName = "/万道月报.xls";
//                    ExcelUtil.writeMonthObjListToExcel(DbUtils.monthLog(TimeUtil.getInstance().getLastMonthFirstDay(), TimeUtil.getInstance().getLastMonthEndDay()), fileMonthPath + excelMonthFileName, getApplicationContext());
//                    mFilePath = filePath + "/万道月报.xls";
//                    path = new String[]{mFilePath};
//                    EmailUtil.autoSendFileMail("万道日志月报", "万道月报", email, path);
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void initAlarmDay(){             //打开一个时间广播，去进行相关操作
        Intent intent = new Intent(getApplicationContext(), StorageLogService.class);
        intent.putExtra("log","day");
        //     PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), ELAPSED_REALTIME, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent sender = PendingIntent.getService(getApplicationContext(),0,intent,FLAG_UPDATE_CURRENT);
        // Schedule the alarm!
        AlarmManager am = (AlarmManager) getApplicationContext()
                .getSystemService(Context.ALARM_SERVICE);
//        calendar.set(Calendar.SECOND, 00);
//        calendar.set(Calendar.MILLISECOND, 0);
        Date date =new Date(System.currentTimeMillis());
        date.setHours(19);
        date.setMinutes(00);

        // calendar.set(Calendar.DAY_OF_WEEK,1);
        //    am.set(AlarmManager.RTC_WAKEUP,date.getTime(),sender);
        //am.setRepeating(AlarmManager.RTC_WAKEUP,date.getTime(),AlarmManager.INTERVAL_DAY,sender);
        //    am.setExactAndAllowWhileIdle();
        //   am.setRepeating(AlarmManager.RTC_WAKEUP, date.getTime(),AlarmManager.INTERVAL_DAY,sender);
        //     am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,  SystemClock.currentThreadTimeMillis(), sender);
        am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,  date.getTime(), sender);
        Log.d("时间",date.getTime()+"");
    }





    public void initAlarmWeek(){             //打开一个时间广播，去进行相关操作
        Intent intent = new Intent(getApplicationContext(), StorageLogService.class);
        intent.putExtra("log","week");
        PendingIntent sender = PendingIntent.getService(getApplicationContext(), 1, intent, FLAG_UPDATE_CURRENT);
        // Schedule the alarm!
        AlarmManager am = (AlarmManager) getApplicationContext()
                .getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();    //获取周末
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        calendar.add(Calendar.DATE, -day_of_week + 4);
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 0);
        Log.d("boxDayLog_week", calendar.getTimeInMillis()+"");
        //      am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY*7,sender);
        am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,  calendar.getTimeInMillis(), sender);


    }



//    public void initAlarmMonth(){             //打开一个时间广播，去进行相关操作
//        Intent intent = new Intent(getApplicationContext(), StorageLogService.class);
//        intent.putExtra("log","month");
//        PendingIntent sender = PendingIntent.getService(getApplicationContext(), 2, intent, 0);
//        // Schedule the alarm!
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 8);
//        calendar.set(Calendar.MINUTE, 00);
//        calendar.set(Calendar.SECOND, 00);
//        calendar.set(Calendar.MILLISECOND, 0);
//        calendar.set(Calendar.DAY_OF_MONTH,1);
//        Log.d("boxDayLog_month", calendar.getTimeInMillis()+"");
//   //     am.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);
//        am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,sender);
//    }






    private BroadcastReceiver alarmDayReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 重复定时任务
            Intent alarmIntent = new Intent(getApplicationContext(), StorageLogService.class);
            alarmIntent.putExtra("log","day");
            PendingIntent sender = PendingIntent.getService(getApplicationContext(),0,alarmIntent,FLAG_UPDATE_CURRENT);
//            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, SystemClock.currentThreadTimeMillis() + AlarmManager.INTERVAL_DAY, sender);
            AlarmManager am = (AlarmManager) getApplicationContext()
                    .getSystemService(Context.ALARM_SERVICE);

            Log.d("time_peck","1111111111");
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + AlarmManager.INTERVAL_DAY, sender);
        }
    };


    private BroadcastReceiver alarmWeekReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 重复定时任务
            PendingIntent sender = PendingIntent.getService(getApplicationContext(),1,intent,FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager) getApplicationContext()
                    .getSystemService(Context.ALARM_SERVICE);
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, SystemClock.currentThreadTimeMillis() + AlarmManager.INTERVAL_DAY*7, sender);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(alarmDayReceiver);
        unregisterReceiver(alarmWeekReceiver);
    }
}
