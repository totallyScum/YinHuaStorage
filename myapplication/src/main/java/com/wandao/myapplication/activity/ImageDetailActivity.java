package com.wandao.myapplication.activity;

import android.annotation.SuppressLint;
import android.app.ZysjSystemManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.baidu.idl.sample.R;
import com.baidu.idl.sample.ui.BaseActivity;
import com.baidu.idl.sample.utils.FileUtils;
import com.bumptech.glide.Glide;

/**
 * 图片详情页
 * Created by v_liujialu01 on 2018/12/17.
 */

public class ImageDetailActivity extends BaseActivity {
    private ImageView mImageView;
    private static final int TIMER = 999;
    private static boolean flag = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        mImageView = findViewById(R.id.image_detail);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            String picName = intent.getStringExtra("pic_name");
            String imgPath = FileUtils.getFacePicDirectory().getAbsolutePath()
                    + "/" + picName;
            Glide.with(this).load(imgPath).into(mImageView);

        }
    }
    private void setTimer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag){
                    try {
                        Thread.sleep(3000); //休眠一秒
                   finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTimer();
        @SuppressLint("WrongConstant") ZysjSystemManager SystemManager= SystemManager=(ZysjSystemManager)getSystemService("zysj");
        SystemManager.ZYSystemBar(1);
    }

    private Handler mHanler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case TIMER:
                    //去执行定时操作逻辑
                    break;
                default:
                    break;
            }
        }
    };

    private void stopTimer(){
        flag = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }
}
