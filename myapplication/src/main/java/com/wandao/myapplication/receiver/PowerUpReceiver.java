package com.wandao.myapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wandao.myapplication.activity.MainActivity;

public class PowerUpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
final Context context1=context;

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(10000);//休眠10秒
                    Intent intent1=new Intent(context1, MainActivity.class);
                    context1.startActivity(intent1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /**
                 * 要执行的操作
                 */
            }
        }.start();

    }
}
