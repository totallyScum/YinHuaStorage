package com.baidu.idl.face.demo;

import android.app.Application;
import android.util.Log;

import com.baidu.crabsdk.CrabSDK;

/**
 * Created by litonghui on 2018/12/20.
 */

public class MainAppliction extends Application {
    public void onCreate() {
        super.onCreate();
        CrabSDK.init(this, "8bc935ee31c9b769");
        Log.d("233333333333","666666666666666");
    }
}
