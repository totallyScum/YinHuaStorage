package com.wandao.myapplication.activity;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wandao.myapplication.greendao.DaoMaster;
import com.wandao.myapplication.greendao.DaoSession;
import com.wandao.myapplication.service.StorageLogService;
import com.wandao.myapplication.utils.SPUtils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Application extends android.app.Application {
    private static DaoSession daoSession;
    private final static int FEATCH_NORMAL_STATUS=0;  // 正常开门
    private final static int STORAGE_NORMAL_STATUS=1;  // 正常关门
    private final static int FEATCH_LATE_STATUS=2;   //存件晚
    private final static int FEATCH_EXPECTION_STATUS=3; //紧急取件 （特权取件）
   // public static String beginTime=new String("09:00");     //设定的开始存件时间
  //  public static String stopTime=new  String("15:00");
    public static Time beginTime=new Time(9,0,0);
    public static Time endTime=new Time(15,0,0);
    DateFormat format = new SimpleDateFormat("HH:MM:SS");
    //    public static Time stopTime;      //设定的结束存件时间
    @Override
    public void onCreate() {
        super.onCreate();
        initGreenDao();
        initTime();   //初始化开关柜门的时间

        Intent StorageIntent = new Intent(this, StorageLogService.class);
        startService(StorageIntent);
    }
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "user.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
    public void initTime(){     //初始化开关箱时间
   //     beginTime=  (Time) SPUtils.get(this,"openDoorTime","");
     //   stopTime=(Time)SPUtils.get(this,"closeDoorTime","");

        try {
            java.util.Date time= (Date) format.parse((String)(SPUtils.get(this,"openDoorTime","9:01:00")));
                  Log.d("Time_beginTime",time.toString());
          //  Log.d("Time_beginTime666666",t.toString());
        } catch (ParseException e) {
            Log.d("Time_beginTime",e+" ");
            e.printStackTrace();
        }
      //  Log.d("Time",""+SPUtils.get(this,"openDoorTime",""));
      //  Log.d("Time",""+SPUtils.get(this,"openDoorTime",""));
        Log.d("Time",""+SPUtils.get(this,"closeDoorTime",""));
    }
    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
