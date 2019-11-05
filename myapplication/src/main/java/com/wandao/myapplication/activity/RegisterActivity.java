package com.wandao.myapplication.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ZysjSystemManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.idl.facesdk.model.Feature;
import com.baidu.idl.sample.R;
import com.baidu.idl.sample.api.FaceApi;
import com.baidu.idl.sample.callback.IFaceRegistCalllBack;
import com.baidu.idl.sample.common.GlobalSet;
import com.baidu.idl.sample.manager.FaceLiveness;
import com.baidu.idl.sample.manager.FaceSDKManager;
import com.baidu.idl.sample.model.LivenessModel;
import com.baidu.idl.sample.ui.BaseActivity;
import com.baidu.idl.sample.view.BinocularView;
import com.baidu.idl.sample.view.MonocularView;
import com.wandao.myapplication.greendao.User;
import com.wandao.myapplication.utils.DbUtils;

import java.lang.ref.WeakReference;

/**
 * Created by litonghui on 2018/11/17.
 */

public class RegisterActivity extends Activity implements View.OnClickListener {
//    private View mLayoutInput;
//    private EditText mNickView;

    private RelativeLayout mCameraView;
    private BinocularView mBinocularView;
    private MonocularView mMonocularView;
    private View registResultView;
    private RelativeLayout relativeRegisterBg;
    private TextView textYes;
    private TextView textNo;
    private ImageView imageTitle;
    private boolean timeClose = false;
    //    private TextView mTextBatchRegister;
    private Context mContext;
    private String mNickName;
    private User user;       //员工对象
    private TextView countDown;
    MyHandler handler = new MyHandler(this);
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            faceRegistCalllBack.onRegistCallBack(1, null, null, null);
        }
    };
    // 注册结果
    private IFaceRegistCalllBack faceRegistCalllBack = new IFaceRegistCalllBack() {

        @Override
        public void onRegistCallBack(int code, LivenessModel livenessModel,
                                     final Bitmap cropBitmap, final Feature feature) {
            handler.removeCallbacks(runnable);
            // 停止摄像头采集
            registResultView.post(new Runnable() {
                @Override
                public void run() {
//                    mLayoutInput.setVisibility(View.GONE);
//                    mTextBatchRegister.setVisibility(View.GONE);
                    if (mBinocularView != null) {
                        mBinocularView.onPause();
                        mCameraView.removeView(mBinocularView);
                    }
                    if (mMonocularView != null) {
                        mMonocularView.onPause();
                        mCameraView.removeView(mMonocularView);
                    }
                }
            });
            switch (code) {
                case 0: {
//                    DbUtils.insertUser(user);
//                    DbUtils.getInstance().bindBox(user.getBoxId());
                    // 设置注册信息
                    registResultView.post(new Runnable() {
                        @Override
                        public void run() {
                            imageTitle.setImageBitmap(cropBitmap);
                            relativeRegisterBg.setVisibility(View.VISIBLE);
                            initRegisterListener(feature);
                            ((ImageView) registResultView.findViewById(R.id.ic_right))
                                    .setBackground(getDrawable(R.mipmap.ic_success));
                            if (cropBitmap != null) {
                                ((ImageView) registResultView.findViewById(R.id.ic_portrait))
                                        .setImageBitmap(cropBitmap);
                            }
                            ((TextView) registResultView.findViewById(R.id.nick_name))
                                    .setText(mNickName);
                            ((TextView) registResultView.findViewById(R.id.result))
                                    .setVisibility(View.VISIBLE);
                            ((TextView) registResultView.findViewById(R.id.complete))
                                    .setText("确定");
                        }
                    });
                }
                break;
                case 1: {
                    registResultView.post(new Runnable() {
                        @Override
                        public void run() {
                            ((ImageView) registResultView.findViewById(R.id.ic_right))
                                    .setVisibility(View.INVISIBLE);
                            ((ImageView) registResultView.findViewById(R.id.ic_portrait))
                                    .setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_track));
                            ((TextView) registResultView.findViewById(R.id.nick_name))
                                    .setText("注册超时");
                            ((TextView) registResultView.findViewById(R.id.result))
                                    .setVisibility(View.GONE);
                            ((TextView) registResultView.findViewById(R.id.complete))
                                    .setText("确定");
                            registResultView.setVisibility(View.VISIBLE);
                        }
                    });
                }
                break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         @SuppressLint("WrongConstant") ZysjSystemManager SystemManager= SystemManager=(ZysjSystemManager)getSystemService("zysj");
         SystemManager.ZYSystemBar(0);
        mContext = this;
        initView();
        countDown = findViewById(R.id.count_down);
        cdTimer.start();
        countDown.bringToFront();
//        setAction();
//        FaceSDKManager.getInstance().getFaceLiveness()
//                .setCurrentTaskType(FaceLiveness.TaskType.TASK_TYPE_REGIST);

    }

    private void initView() {
        mNickName = getIntent().getStringExtra("mNickName");
        user=(User)getIntent().getSerializableExtra("user");
   //     Log.d("23333345",user.getName()+"   "+user.getId()+"   "+user.getBoxId());
//        mLableTxt = findViewById(R.id.title);
//        mLableTxt.setText(R.string.face_regiseter);



//        mLayoutInput = findViewById(R.id.layout_input);
//        findViewById(R.id.go_btn).setOnClickListener(this);
//        mNickView = findViewById(R.id.nick_name);
        relativeRegisterBg = findViewById(R.id.relative_register_bg);
        textYes = findViewById(R.id.text_yes);
        textNo = findViewById(R.id.text_no);
        imageTitle = findViewById(R.id.image_title);
        mCameraView = findViewById(R.id.layout_camera);
        registResultView = findViewById(R.id.regist_result);
        resetView();
//        mTextBatchRegister = findViewById(R.id.text_batch_register);
//        mTextBatchRegister.setOnClickListener(this);
    }

    private void resetView() {


        registResultView.setVisibility(View.GONE);
        mCameraView.setVisibility(View.VISIBLE);

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);


        if (GlobalSet.getLiveStatusValue() == GlobalSet.LIVE_STATUS.RGN_NIR) {
            mBinocularView = new BinocularView(mContext, 1);
            mCameraView.addView(mBinocularView, lp);
            mBinocularView.onResume();
        } else {
            mMonocularView = new MonocularView(mContext);
            mCameraView.addView(mMonocularView, lp);
            mMonocularView.onResume();
        }
    }

    private void setAction() {
        // 注册人脸注册事件
        FaceSDKManager.getInstance().getFaceLiveness().addRegistCallBack(faceRegistCalllBack);

        // 设置完成事件
        registResultView.findViewById(R.id.complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });

        handler.postDelayed(runnable, 1000 * 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GlobalSet.getLiveStatusValue() == GlobalSet.LIVE_STATUS.RGN_NIR) {
            mBinocularView.onResume();
        } else {
            mMonocularView.onResume();
        }
    }

    @Override
    protected void onStop() {
        if (GlobalSet.getLiveStatusValue() == GlobalSet.LIVE_STATUS.RGN_NIR) {
            mBinocularView.onPause();
        } else {
            mMonocularView.onPause();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeClose = true;
        FaceSDKManager.getInstance().getFaceLiveness().removeRegistCallBack(faceRegistCalllBack);
        // 重置状态为默认状态
        FaceSDKManager.getInstance().getFaceLiveness()
                .setCurrentTaskType(FaceLiveness.TaskType.TASK_TYPE_ONETON);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
//        if (id == R.id.go_btn) {
//            Editable editable = mNickView.getText();
//            if (editable != null && editable.length() > 0) {
//                mNickName = mNickView.getText().toString();
//                String nameResult = FaceApi.getInstance().isValidName(mNickName);
//                if ("0".equals(nameResult)) {
//                    // 设置注册时的昵称
//                    FaceSDKManager.getInstance().getFaceLiveness().setRegistNickName(mNickName);
//                    Utils.hideKeyboard((Activity) mContext);
//
//                    mLayoutInput.setVisibility(View.GONE);
//                } else {
//                    ToastUtils.toast(mContext, nameResult);
//                }
//            }
//        }

//        if (id == R.id.text_batch_register) {    // 批量注册
//            Intent intent = new Intent(mContext, BatchImportActivity.class);
//            startActivity(intent);
//        }
    }

    private void initRegisterListener(final Feature feature) {
        timeClose = false;
        final CountDown countDown = new CountDown();
        countDown.start();
        //    mLableTxt.setVisibility(View.INVISIBLE);
        textYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeRegisterBg.setVisibility(View.GONE);
                registResultView.setVisibility(View.VISIBLE);
                DbUtils.insertUser(user);
                if (user.getBoxId()!=0)
                    DbUtils.getInstance().bindBox(user.getBoxId());

//                DbUtils.insertUser(user);
//                DbUtils.bindBox(user.getBoxId());
            }
        });
        textNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeClose = true;
                FaceApi.getInstance().featureDelete(feature);
                relativeRegisterBg.setVisibility(View.GONE);
                FaceSDKManager.getInstance().getFaceLiveness().setRegistNickName(mNickName);  //将ID设置为NickName
 //               FaceSDKManager.getInstance().getFaceLiveness().setRegistNickName(mNickName);  //将ID设置为NickName

//                DbUtils.insertUser(user);

                resetView();
                Log.d("12345","注册否");

//
//                timeClose = true;
//                FaceApi.getInstance().featureDelete(feature);
//                relativeRegisterBg.setVisibility(View.GONE);
//                FaceSDKManager.getInstance().getFaceLiveness().setRegistNickName(mNickName);
//                resetView();


//                Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
//                intent.putExtra("mNickName", mNickName);
//                startActivity(intent);
//                finish();
            }
        });
    }

    private class CountDown extends Thread {
        private int countTime = 5;

        @Override
        public void run() {
            try {
                while (!timeClose) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (countTime == 0) {
                                textYes.setText("确认注册(" + countTime + ")");
                                relativeRegisterBg.setVisibility(View.GONE);
                                registResultView.setVisibility(View.VISIBLE);
                                DbUtils.insertUser(user);
                                if (user.getBoxId()!=0)
                                DbUtils.getInstance().bindBox(user.getBoxId());
                            } else if (countTime == 3 || countTime == 2 || countTime == 1||countTime == 4 ||countTime == 5 ) {
                                textYes.setText("确认注册(" + countTime + ")");
                            } else {
                                textYes.setText("确认注册");
                            }
                        }
                    });
                    Thread.sleep(1000);
                    countTime--;
                }
            } catch (InterruptedException e) {
                Log.e("chenjianping", e.getMessage());
            }
        }
    }

    private static final class MyHandler extends Handler {
        private WeakReference<? extends Activity> mReference;

        public MyHandler(Activity activity) {
            mReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            // 消息处理
            Activity activity = mReference.get();
        }
    }

    private CountDownTimer cdTimer = new CountDownTimer(5000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            countDown.setText((millisUntilFinished / 1000) + "");

            if (millisUntilFinished < 1000) {
                countDown.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onFinish() {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    /**
                     *要执行的操作
                     */
                    setAction();
                    FaceSDKManager.getInstance().getFaceLiveness()
                            .setCurrentTaskType(FaceLiveness.TaskType.TASK_TYPE_REGIST);
                }
            }, 0);//0秒后执行Runnable中的run方法

        }
    };



}