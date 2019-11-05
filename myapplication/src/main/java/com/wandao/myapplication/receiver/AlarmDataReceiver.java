package com.wandao.myapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wandao.myapplication.utils.DbUtils;


public class AlarmDataReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       DbUtils dbUtils= DbUtils.getInstance();
        dbUtils.DailySummary();
    }
}
