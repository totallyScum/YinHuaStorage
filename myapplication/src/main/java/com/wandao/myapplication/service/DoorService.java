package com.wandao.myapplication.service;

import android.app.AppOpsManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.wandao.myapplication.LockController;
import com.wandao.myapplication.R;
import com.wandao.myapplication.activity.MainActivity;
import com.wandao.myapplication.utils.DbUtils;
import com.wandao.myapplication.utils.DoorUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.WindowManager.LayoutParams.TYPE_PHONE;
import static com.serenegiant.utils.UIThreadHelper.runOnUiThread;

public class DoorService extends Service {

    public class MyBinder extends Binder {
        public DoorService getService() {
            return DoorService.this;
        }
    }

    private MyBinder binder = new MyBinder();
    private PopupWindow popupWindow;
    private static View vPopupWindow;

    private static Intent intent = new Intent();
    private static int j = 0;
//    final WindowManager.LayoutParams   params = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);



    @Override
    public IBinder onBind(Intent intent) {

        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final LockController lockController=LockController.getInstance();
        Timer timer = new Timer();
//        final Handler handler =new Handler();
//        final Toast toast=new Toast(getApplicationContext());


        TimerTask timerTask =   new TimerTask() {
            @Override
            public void run() {

                boolean open = false;
                int i = DoorUtils.getSingleton().checkDoorStatus();
                if (i != 0)
                    open = true;
//                for (int i = 0; i < LockController.doorStatus.length; i++) {
//                    if (LockController.doorStatus[i] != 1) {
//                        open20=true;
//                          finalI20 = i + 1;
//
////                        final  int temp=finalI;
////                        Handler handlerThree=new Handler(Looper.getMainLooper());
////                        handlerThree.post(new Runnable(){
////                            public void run(){
////                                Toast.makeText(getApplicationContext() ,temp+"号柜门未关闭",Toast.LENGTH_LONG).show();
////                            }
////                        });
//                    }
//                }     //循环判断锁板1的状态，返回值
                if (i == 0) {
                    intent.putExtra("count", 0);
                    intent.setAction("com.wandao.myapplication");
                    sendBroadcast(intent);
                }
                //     if (i!=0&&open==true){
                if (i != 0) {
                    Intent intent = new Intent();
                    intent.putExtra("count", i);
                    intent.setAction("com.wandao.myapplication");
                    sendBroadcast(intent);

                }
            }

//                if (DbUtils.getDoorNumber() == 40) {
//                    boolean open=false;
//                    int finalI=0;
//                    for (int i = 0; i < LockController.doorStatus.length; i++) {
//                        if (LockController.doorStatus[i] != 1) {
//                            open=true;
//                            finalI = i + 1;
//                        }
//                    }
//                    for (int i = 0; i < LockController.doorStatusBoard2.length; i++) {
//                        if (LockController.doorStatusBoard2[i] != 1) {
//                            open=true;
//                            finalI = i + 1 + 20;
////                             final  int temp=finalI2;
//////                            Handler handlerThree=new Handler(Looper.getMainLooper());
//////                            handlerThree.post(new Runnable(){
//////                                public void run(){
//////                                    Toast.makeText(getApplicationContext() ,temp+"号柜门未关闭",Toast.LENGTH_LONG).show();
//////                                }
//////                            });
//                            }
//                        }
//                    if (open)
//                    {
//                        intent.putExtra("count", finalI);
//                        intent.setAction("com.wandao.myapplication");
//                        sendBroadcast(intent);
//                    }
//                    if (!open){
//                        Intent intent=new Intent();
//                        intent.putExtra("count", 0);
//                        intent.setAction("com.wandao.myapplication");
//                        sendBroadcast(intent);
//                    }
//                    open=false;
//                    }     //循环判断锁板2的状态，返回值
        };
        timer.schedule(timerTask, 0, 1000);
    }

    public static boolean checkFloatPermission(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return true;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try {
                Class cls = Class.forName("android.content.Context");
                Field declaredField = cls.getDeclaredField("APP_OPS_SERVICE");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(cls);
                if (!(obj instanceof String)) {
                    return false;
                }
                String str2 = (String) obj;
                obj = cls.getMethod("getSystemService", String.class).invoke(context, str2);
                cls = Class.forName("android.app.AppOpsManager");
                Field declaredField2 = cls.getDeclaredField("MODE_ALLOWED");
                declaredField2.setAccessible(true);
                Method checkOp = cls.getMethod("checkOp", Integer.TYPE, Integer.TYPE, String.class);
                int result = (Integer) checkOp.invoke(obj, 24, Binder.getCallingUid(), context.getPackageName());
                return result == declaredField2.getInt(cls);
            } catch (Exception e) {
                return false;
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                AppOpsManager appOpsMgr = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                if (appOpsMgr == null)
                    return false;
                int mode = appOpsMgr.checkOpNoThrow("android:system_alert_window", android.os.Process.myUid(), context
                        .getPackageName());
                return mode == AppOpsManager.MODE_ALLOWED || mode == AppOpsManager.MODE_IGNORED;
            } else {
                return Settings.canDrawOverlays(context);
            }
        }
    }

}
