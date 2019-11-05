package com.wandao.myapplication.activity;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.baidu.idl.facesdk.model.Feature;
import com.wandao.myapplication.greendao.DaoMaster;
import com.wandao.myapplication.greendao.DaoSession;
import com.wandao.myapplication.network.NetWorkManager;
import com.wandao.myapplication.receiver.AlarmDataReceiver;
import com.wandao.myapplication.service.DoorService;
import com.wandao.myapplication.service.IRemoteService;
import com.wandao.myapplication.service.StorageLogService;
import com.wandao.myapplication.utils.DbUtils;
import com.wandao.myapplication.utils.SPUtils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.AlarmManager.ELAPSED_REALTIME;

public class MyApplication extends android.app.Application {
    private static DaoSession daoSession;
    public static int license=0;
    private final static int FEATCH_NORMAL_STATUS = 0;  // 正常开门
    private final static int STORAGE_NORMAL_STATUS = 1;  // 正常关门
    private final static int FEATCH_LATE_STATUS = 2;   //存件晚
    private final static int FEATCH_EXPECTION_STATUS = 3; //紧急取件 （特权取件）
    public static IRemoteService mService = null;
    public static List<Feature> listFeatureInfo;
    private boolean isRemoteControl = false;
    public Context mContext;
    Intent doorIntent;   //所控板服务
    public static int doorNumber;

    //    private ServiceConnection mConnection = new ServiceConnection() {
//
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//
//            Log.d("RemoteException","NULL222222");
//            mService = IRemoteService.Stub.asInterface(iBinder);
//
//
//
//
//            //保存到全局对象里面
//            try {
//                byte[] bSerialNumber = mService.lockControlQuerySerialNumber(3000, Byte.valueOf(0 + ""));
//
//                if (bSerialNumber != null) {
//
//                    String bReadSerialNumber = new String(bSerialNumber);
//
//                }
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//            Toast.makeText(getApplicationContext(), "锁孔板服务已经连接成功", Toast.LENGTH_SHORT).show();
//
//            try {
//                doorIntent= new Intent(getmContext(), DoorService.class);
//                startService(doorIntent);
//            }catch (Exception e){
//                Toast.makeText(getApplicationContext(), "锁控版服务开启失败", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            mService = null;
//            isServiceConnection = false;
//            Toast.makeText(getApplicationContext(), "服务已经断开",
//                    Toast.LENGTH_SHORT).show();
//            stopService(doorIntent);
//
//        }
//
//
//    };
    public static void  initDoorNumber(){
        doorNumber=DbUtils.getDoorNumber();
    };
    public Context getmContext() {

        return getApplicationContext();
    }
    public void setRemoteService(IRemoteService mService) {
        this.mService = mService;
    }

    public IRemoteService getRemoteService() {
        return mService;
    }
    private static MyApplication singleton;
    public static MyApplication getInstance() {
        singleton=new MyApplication();
        return singleton;
    }
    boolean isServiceConnection = false;
    // public static String beginTime=new String("09:00");     //设定的开始存件时间
    //  public static String stopTime=new  String("15:00");
    public static Time startTime = new Time(9, 0, 0);
    public static Time endTime = new Time(15, 0, 0);




    public static Time startTimeAM = new Time(9, 0, 0);
    public static Time endTimeAM = new Time(11, 30, 0);
    public static Time startTimePM = new Time(13, 0, 0);
    public static Time endTimePM = new Time(17, 0, 0);


    private static final int INTERVAL = 1000 * 60 * 60 * 24;// 24h
    DateFormat format = new SimpleDateFormat("HH:MM:SS");
    //    public static Time stopTime;      //设定的结束存件时间
    @Override
    public void onCreate() {
        super.onCreate();
        initGreenDao();
        Message msg = Message.obtain();
        msg.what = 1;
// 发送消息到handler，通过handlerMessage()方法接收消息
        handler.sendMessage(msg);
        NetWorkManager.getInstance().init();


        doorIntent = new Intent(getmContext(), DoorService.class);
        startService(doorIntent);
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

switch (msg.what){
    case 1:
        initTime();   //初始化开关柜门的时间
//        Intent serviceIntent =
//                new Intent("com.hardware.interface");
//        serviceIntent.setPackage("com.android.hardware");

        // bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
        if (mService == null)
        {
            isRemoteControl = false;
        }else
        {
            isRemoteControl = true;
        }

        initAlarmDataCheck();
        initDoorNumber();
        initStartEndTime();

        Intent StorageIntent = new Intent(getmContext(), StorageLogService.class);   //开启日志自动导出功能
        startService(StorageIntent);
        break;
}
        }
    };
public void initStartEndTime(){

            if (SPUtils.get(this,"endTimeHourAM",15)!=null&&(SPUtils.get(this,"endTimeHourPM",15)!=null))
    {
        startTimeAM.setHours((int) SPUtils.get(this, "startTimeHourAM", startTimeAM.getHours()));
        startTimeAM.setMinutes((int) SPUtils.get(this, "startTimeMinuteAM", startTimeAM.getMinutes()));

        endTimeAM.setHours((int) SPUtils.get(this, "endTimeHourAM", endTimeAM.getHours()));
        endTimeAM.setMinutes((int) SPUtils.get(this, "endTimeMinuteAM", endTimeAM.getMinutes()));

        startTimePM.setHours((int) SPUtils.get(this, "startTimeHourPM", startTimePM.getHours()));
        startTimePM.setMinutes((int) SPUtils.get(this, "startTimeMinutePM", startTimePM.getMinutes()));

        endTimePM.setHours((int) SPUtils.get(this, "endTimeHourPM", endTimePM.getHours()));
        endTimePM.setMinutes((int) SPUtils.get(this, "endTimeMinutePM", endTimePM.getMinutes()));
    }
}
public void initAlarmDataCheck(){             //打开一个时间广播，去进行相关操作
    Intent intent = new Intent(getApplicationContext(), AlarmDataReceiver.class);
    PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), ELAPSED_REALTIME, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    // Schedule the alarm!
    AlarmManager am = (AlarmManager) getApplicationContext()
            .getSystemService(Context.ALARM_SERVICE);
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, 17);
    calendar.set(Calendar.MINUTE, 00);
    calendar.set(Calendar.SECOND, 00);
    calendar.set(Calendar.MILLISECOND, 0);
    am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, sender);
}

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "user.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
    public void initTime() {     //初始化开关箱时间
        //     beginTime=  (Time) SPUtils.get(this,"openDoorTime","");
        //   stopTime=(Time)SPUtils.get(this,"closeDoorTime","");
        try {
            java.util.Date time = (Date) format.parse((String) (SPUtils.get(this, "openDoorTime", "9:00:00")));
            Log.d("Time_beginTime", time.toString());
            //  Log.d("Time_beginTime666666",t.toString());
        } catch (ParseException e) {
            Log.d("Time_beginTime", e + " ");
            e.printStackTrace();
        }
        //  Log.d("Time",""+SPUtils.get(this,"openDoorTime",""));
        //  Log.d("Time",""+SPUtils.get(this,"openDoorTime",""));
        Log.d("Time", "" + SPUtils.get(this, "closeDoorTime", ""));
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

public void  balanceLog(){


}

}
