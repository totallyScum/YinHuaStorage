package com.baidu.idl.sample.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.aip.iminect.ColorSurfaceView;
import com.baidu.idl.facesdk.model.Feature;
import com.baidu.idl.sample.R;
import com.baidu.idl.sample.adapter.CameraPagerAdapter;
import com.baidu.idl.sample.api.FaceApi;
import com.baidu.idl.sample.callback.IFaceRegistCalllBack;
import com.baidu.idl.sample.callback.ILivenessCallBack;
import com.baidu.idl.sample.manager.FaceLiveness;
import com.baidu.idl.sample.manager.FaceSDKManager;
import com.baidu.idl.sample.model.LivenessModel;
import com.baidu.idl.sample.utils.ImageUtils;
import com.baidu.idl.sample.view.BaseCameraView;
import com.baidu.idl.sample.view.CustomScrollViewPager;
import com.hjimi.api.iminect.ImiDevice;
import com.hjimi.api.iminect.ImiDeviceAttribute;
import com.hjimi.api.iminect.ImiDeviceState;
import com.hjimi.api.iminect.ImiFrameMode;
import com.hjimi.api.iminect.ImiNect;
import com.hjimi.api.iminect.ImiPixelFormat;
import com.hjimi.api.iminect.Utils;

import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 华捷艾米镜头 RGB+depth 注册页面
 * Created by v_liujialu01 on 2019/1/4.
 */

public class IminectRegisterActivity extends BaseActivity implements View.OnClickListener, ILivenessCallBack {
    private static final String TAG = IminectRegisterActivity.class.getSimpleName();

    // 摄像头预览相关控件
    private CustomScrollViewPager mViewPager;
    private CameraPagerAdapter mPagerAdapter;
    private BaseCameraView mLayoutOne;
    private RelativeLayout mLayoutTwo;
    private ImageView mBtnGoLeft;
    private ImageView mBtnGoRight;

    // 注册相关控件
//    private RelativeLayout mLayoutInput;
//        private EditText mNickView;
    private RelativeLayout mCameraView;
    private View registResultView;
//    private TextView mTextBatchRegister;

    // textureView用于绘制人脸框等。
    private TextureView mTextureView;
    private ColorSurfaceView mColorSurfaceView;
    private ColorSurfaceView mDepthSurfaceView;
    private RelativeLayout relativeRegisterBg;
    private TextView textYes;
    private TextView textNo;
    private ImageView imageTitle;

    //    private static ImiNect m_ImiNect = null;
    private static MainListener mMainListener = null;
    private ImiDevice mImiDevice0 = null;
    private ImiDeviceAttribute mDeviceAttribute = null;
    private boolean mIsExitLoop = false;
    private ByteBuffer mColorBuffer;
    private ByteBuffer mDepthBuffer;
    private boolean timeClose = false;

    private int expectUserFrameWidth = 640;
    private int expectUserFrameHeight = 480;

    private static final int DEVICE_OPEN_FALIED = 1;
    private static final int DEVICE_DISCONNECT = 2;

    private static final int REQUEST_CAMERA_CODE = 0x007;
    private static final int DEPTH_NEED_PERMISSION = 33;
    private ImiDeviceState deviceState = ImiDeviceState.IMI_DEVICE_STATE_CONNECT;
    private Context mContext;
    private String mNickName;
    private boolean mIsCameraPermission;
    private boolean mInitOk;
    private MyHandler mMainHandler = new MyHandler(this);
    private Thread mThread = null;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            faceRegistCalllBack.onRegistCallBack(1, null, null, null);
        }
    };

    private static final class MyHandler extends Handler {
        private WeakReference<? extends Activity> mReference;

        public MyHandler(Activity activity) {
            mReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            // 消息处理
            IminectRegisterActivity activity = (IminectRegisterActivity) mReference.get();
            switch (msg.what) {
                case DEVICE_OPEN_FALIED:
                    activity.showMessageDialog();
                    break;
                case DEVICE_DISCONNECT:
                    activity.showMessageDialog();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iminect_register);
        mContext = this;
        initView();
        setAction();
        initData();
    }

    @Override
    protected void onPause() {
        FaceSDKManager.getInstance().getFaceLiveness().removeRegistCallBack(faceRegistCalllBack);
        // 重置状态为默认状态
        FaceSDKManager.getInstance().getFaceLiveness()
                .setCurrentTaskType(FaceLiveness.TaskType.TASK_TYPE_ONETON);
        super.onPause();
    }

    @Override
    protected void onRestart() {
        FaceSDKManager.getInstance().getFaceLiveness().addRegistCallBack(faceRegistCalllBack);
        FaceSDKManager.getInstance().getFaceLiveness()
                .setCurrentTaskType(FaceLiveness.TaskType.TASK_TYPE_REGIST);
        super.onRestart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mIsExitLoop = true;
        timeClose = true;

        if (mImiDevice0 != null) {
            mImiDevice0.close();
            mImiDevice0 = null;
        }
        if (mContext != null) {
            mContext = null;
        }

        ImiDevice.destroy();
        ImiNect.destroy();

        finish();
        release();
    }

    private void initView() {
        mNickName = getIntent().getStringExtra("mNickName");
        mLableTxt = findViewById(R.id.title);
        mLableTxt.setText(R.string.face_regiseter);

//        mLayoutInput = findViewById(R.id.layout_input);
//        mNickView = findViewById(R.id.nick_name);
        registResultView = findViewById(R.id.regist_result);
//        mTextBatchRegister = findViewById(R.id.text_batch_register);
        mCameraView = findViewById(R.id.layout_camera);
        mCameraView.setVisibility(View.VISIBLE);
//        findViewById(R.id.go_btn).setOnClickListener(this);
//        mTextBatchRegister.setOnClickListener(this);
        relativeRegisterBg = findViewById(R.id.relative_register_bg);
        textYes = findViewById(R.id.text_yes);
        textNo = findViewById(R.id.text_no);
        imageTitle = findViewById(R.id.image_title);

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mViewPager = findViewById(R.id.viewpager);
        mBtnGoLeft = findViewById(R.id.btn_go_left);
        mBtnGoRight = findViewById(R.id.btn_go_right);
        mLayoutOne = new BaseCameraView(mContext);
        mLayoutTwo = new RelativeLayout(mContext);
        List<View> viewList = new ArrayList<>();
        viewList.add(mLayoutOne);
        viewList.add(mLayoutTwo);
        mPagerAdapter = new CameraPagerAdapter(viewList);
        mViewPager.setAdapter(mPagerAdapter);
        mBtnGoLeft = findViewById(R.id.btn_go_left);
        mBtnGoLeft.setOnClickListener(this);
        mBtnGoRight = findViewById(R.id.btn_go_right);
        mBtnGoRight.setOnClickListener(this);

        mTextureView = new TextureView(mContext);
        mTextureView.setOpaque(false);
        mTextureView.setKeepScreenOn(true);
        mColorSurfaceView = new ColorSurfaceView(mContext);

        // 创建一个布局放入mTextureView
        RelativeLayout rl = new RelativeLayout(mContext);
        rl.setGravity(Gravity.CENTER);
        rl.addView(mTextureView, lp);
        rl.addView(mColorSurfaceView, lp);
        // 将创建的布局放入mLayoutOne
        mLayoutOne.addView(rl, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        // 调用BaseCameraView添加人脸识别框
        mLayoutOne.initFaceFrame(mContext);
        // mTextureView的绘制完毕监听，用于将左边距传入BaseCameraView
        mTextureView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int left = mTextureView.getLeft();
                int top = mTextureView.getTop();
                mLayoutOne.leftDisparity(left, top);
            }
        });
        mDepthSurfaceView = new ColorSurfaceView(mContext);
        mLayoutTwo.addView(mDepthSurfaceView, lp);
    }

    private void setAction() {
        // 注册人脸注册事件
        FaceSDKManager.getInstance().getFaceLiveness().addRegistCallBack(faceRegistCalllBack);
        mInitOk = true;

        // 设置完成事件
        registResultView.findViewById(R.id.complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mMainHandler.postDelayed(runnable, 1000 * 30);
    }

    private void initData() {
        FaceSDKManager.getInstance().getFaceLiveness()
                .setCurrentTaskType(FaceLiveness.TaskType.TASK_TYPE_REGIST);

//        m_ImiNect = ImiNect.create(mContext);
//        mMainListener = new MainListener();
//        m_ImiNect.Device().addDeviceStateListener(mMainListener);
//        if (isCameraPermission(IminectRegisterActivity.this, REQUEST_CAMERA_CODE)) {
//            // 打开摄像头
//            m_ImiNect.Camera().open(mMainListener);
//        }
        new Thread(new OpenDeviceRunnable()).start();
        FaceSDKManager.getInstance().getFaceLiveness().setLivenessCallBack(this);
    }

    private class OpenDeviceRunnable implements Runnable {
        @Override
        public void run() {
            // get iminect instance.
            ImiNect.initialize();
            mImiDevice0 = ImiDevice.getInstance();
            mMainListener = new MainListener();
            ImiFrameMode colorMode = new ImiFrameMode(ImiPixelFormat.IMI_PIXEL_FORMAT_IMAGE_RGB24, 640, 480, 30);
            mImiDevice0.setFrameMode(ImiDevice.ImiStreamType.COLOR, colorMode);
            ImiFrameMode depthMode = new ImiFrameMode(ImiPixelFormat.IMI_PIXEL_FORMAT_DEP_16BIT, 640, 480, 30);
            mImiDevice0.setFrameMode(ImiDevice.ImiStreamType.DEPTH, depthMode);
            mImiDevice0.open(mContext, ImiDevice.ImiStreamType.COLOR.toNative() |
                    ImiDevice.ImiStreamType.DEPTH.toNative(), mMainListener);
        }
    }

    public boolean isCameraPermission(Activity context, int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
                Toast.makeText(this, "requestPermissions Camera Permission",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        Toast.makeText(this, "Already have Camera Permission", Toast.LENGTH_SHORT).show();
        return true;
    }

    private void showMessageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("The device is not connected!!!");
        builder.setPositiveButton("quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_go_left) {
            if (mViewPager != null) {
                mViewPager.setCurrentItem(0);
            }

        } else if (id == R.id.btn_go_right) {
            if (mViewPager != null) {
                mViewPager.setCurrentItem(1);
            }
        }

//        if (id == R.id.go_btn) {
//            Editable editable = mNickView.getText();
//            if (editable != null && editable.length() > 0) {
//                mNickName = mNickView.getText().toString();
//                String nameResult = FaceApi.getInstance().isValidName(mNickName);
//                if ("0".equals(nameResult)) {
//                    // 设置注册时的昵称
//                    FaceSDKManager.getInstance().getFaceLiveness().setRegistNickName(mNickName);
//                    // 隐藏键盘
//                    com.baidu.idl.sample.utils.Utils.hideKeyboard((Activity) mContext);
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

    @Override
    public void onTip(int code, String msg) {

    }

    @Override
    public void onCanvasRectCallback(LivenessModel livenessModel) {

    }

    @Override
    public void onCallback(int code, LivenessModel livenessModel) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == DEPTH_NEED_PERMISSION) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mContext, "Permission Grant", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 注册结果
    private IFaceRegistCalllBack faceRegistCalllBack = new IFaceRegistCalllBack() {

        @Override
        public void onRegistCallBack(int code, LivenessModel livenessModel,
                                     final Bitmap cropBitmap, final Feature feature) {
            if (mMainHandler != null) {
                mMainHandler.removeCallbacks(runnable);
            }

            // 停止摄像头采集
            registResultView.post(new Runnable() {
                @Override
                public void run() {
                    release();   // 释放
//                    mLayoutInput.setVisibility(View.GONE);
//                    mTextBatchRegister.setVisibility(View.GONE);
                }
            });
            switch (code) {
                case 0:
                    // 注册成功，显示注册信息
                    registResultView.post(new Runnable() {
                        @Override
                        public void run() {
                            imageTitle.setImageBitmap(cropBitmap);
                            relativeRegisterBg.setVisibility(View.VISIBLE);
                            initRegisterListener(feature);
                            mCameraView.setVisibility(View.GONE);
                            (registResultView.findViewById(R.id.ic_right))
                                    .setBackground(getDrawable(R.mipmap.ic_success));
                            if (cropBitmap != null) {
                                ((ImageView) registResultView.findViewById(R.id.ic_portrait))
                                        .setImageBitmap(cropBitmap);
                            }
                            ((TextView) registResultView.findViewById(R.id.nick_name))
                                    .setText(mNickName);
                            (registResultView.findViewById(R.id.result))
                                    .setVisibility(View.VISIBLE);
                            ((TextView) registResultView.findViewById(R.id.complete))
                                    .setText("确定");
                        }
                    });
                    break;
                case 1: // 注册超时
                    registResultView.post(new Runnable() {
                        @Override
                        public void run() {
                            mCameraView.setVisibility(View.GONE);
                            (registResultView.findViewById(R.id.ic_right))
                                    .setVisibility(View.INVISIBLE);
                            ((ImageView) registResultView.findViewById(R.id.ic_portrait))
                                    .setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_track));
                            ((TextView) registResultView.findViewById(R.id.nick_name))
                                    .setText("注册超时");
                            (registResultView.findViewById(R.id.result))
                                    .setVisibility(View.GONE);
                            ((TextView) registResultView.findViewById(R.id.complete))
                                    .setText("确定");
                            registResultView.setVisibility(View.VISIBLE);
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };

    private void initRegisterListener(final Feature feature) {
        timeClose = false;
        final CountDown countDown = new CountDown();
        countDown.start();
        textYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeRegisterBg.setVisibility(View.GONE);
                registResultView.setVisibility(View.VISIBLE);
            }
        });
        textNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeClose = true;
                relativeRegisterBg.setVisibility(View.GONE);
                mCameraView.setVisibility(View.VISIBLE);
                FaceApi.getInstance().featureDelete(feature);
                FaceSDKManager.getInstance().getFaceLiveness().setRegistNickName(mNickName);
                restartCamera();
            }
        });
    }

    private class CountDown extends Thread {
        private int countTime = 3;

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
                            } else if (countTime == 3 || countTime == 2 || countTime == 1) {
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

    private class MainListener implements ImiDevice.OpenDeviceListener,
            ImiDevice.DeviceStateListener {

        @Override
        public void onOpenDeviceSuccess() {

//            mImiDevice0 = m_ImiNect.Device();
            mDeviceAttribute = mImiDevice0.getAttribute();
            mImiDevice0.setImageRegistration(true); // set image registration.

//            try {
//                setUserExpectMode(expectUserFrameWidth, expectUserFrameHeight);
//            } catch (Exception e) {
//                Log.e(TAG, "setUserExpectMode: falied, invalid frame mode");
//            }

            if (mThread == null) {
                mThread = new Thread(new ColorViewRefreshRunnable());
            }
            mThread.start();
        }

        @Override
        public void onOpenDeviceFailed(String errorMsg) {
            mMainHandler.sendEmptyMessage(DEVICE_OPEN_FALIED);
        }

        @Override
        public void onDeviceStateChanged(String deviceUri, ImiDeviceState state) {
            if (state == ImiDeviceState.IMI_DEVICE_STATE_DISCONNECT) {
                if (mDeviceAttribute != null && mDeviceAttribute.getUri().equals(deviceUri)) {
                    deviceState = ImiDeviceState.IMI_DEVICE_STATE_DISCONNECT;
                    mMainHandler.sendEmptyMessage(DEVICE_DISCONNECT);
                }
            } else if (state == ImiDeviceState.IMI_DEVICE_STATE_CONNECT) {
                if (mDeviceAttribute != null && mDeviceAttribute.getUri().equals(deviceUri)) {
                    deviceState = ImiDeviceState.IMI_DEVICE_STATE_CONNECT;
                }
            }
        }
    }

    private class ColorViewRefreshRunnable implements Runnable {

        @Override
        public void run() {
//            try {
//                ImiCameraFrameMode frameMode = new ImiCameraFrameMode(
//                        ImiCameraPixelFormat.IMI_CAMERA_PIXEL_FORMAT_RGB888,
//                        640, 480, 30);
//                mCamera.startPreview(frameMode);
//
//                ImiFrameMode depthFrameMode = new ImiFrameMode(ImiPixelFormat.IMI_PIXEL_FORMAT_DEP_16BIT,
//                        640, 480);
//                mImiDevice0.setFrameMode(ImiFrameType.DEPTH, depthFrameMode);
//                mImiDevice0.openStream(ImiFrameType.DEPTH);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return;
//            }
            ImiDevice.ImiFrame colorFrame = null;
            ImiDevice.ImiFrame depthFrame = null;

            while (!mIsExitLoop) {
                ImiDevice imiDevice = ImiDevice.getInstance();

                colorFrame = imiDevice.readNextFrame(ImiDevice.ImiStreamType.COLOR, 30);
                if (null != colorFrame) {
                    mColorBuffer = colorFrame.getData();
                }

                depthFrame = imiDevice.readNextFrame(ImiDevice.ImiStreamType.DEPTH, 30);
                if (null != depthFrame) {
                    ByteBuffer dep = depthFrame.getData();
                } else {
                    Log.e(">>>", "read dep FAIL ");
                }

                if (null == depthFrame || null == colorFrame) {
                    continue;
                }

                mColorBuffer = colorFrame.getData();
                mDepthBuffer = depthFrame.getData();
                ByteBuffer depthframeData;
                depthframeData = Utils.depth2RGB888(depthFrame, true, false);
                int rgbLen = mColorBuffer.remaining();
                byte[] rgbByte = new byte[rgbLen];
                mColorBuffer.get(rgbByte);
                final Bitmap bitmap = ImageUtils.RGB2Bitmap(rgbByte, 640, 480);
                mColorSurfaceView.updateVertices(mColorBuffer);
                mDepthSurfaceView.updateVertices(depthframeData);
                int depthLen = mDepthBuffer.remaining();
                byte[] depthByte = new byte[depthLen];
                mDepthBuffer.get(depthByte);
                FaceSDKManager.getInstance().getFaceLiveness().setRgbBitmap(bitmap);
                FaceSDKManager.getInstance().getFaceLiveness().setDepthData(depthByte);
                FaceSDKManager.getInstance().getFaceLiveness().livenessCheck(640, 480, 0X0101);
                colorFrame = null;
                depthFrame = null;
            }
        }
    }

//    private void setUserExpectMode(int width, int height) {
//        ImiFrameMode frameMode = new ImiFrameMode(ImiPixelFormat.IMI_PIXEL_FORMAT_DEP_16BIT, width, height);
//        mImiDevice0.setFrameMode(ImiFrameType.DEPTH, frameMode);
//    }

    private void restartCamera() {
        mIsExitLoop = false;
        initView();
        setAction();
        if (mThread == null) {
            mThread = new Thread(new ColorViewRefreshRunnable());
        }
        mThread.start();
    }


    /**
     * 释放
     */
    private void release() {
        if (mInitOk) {
            mIsExitLoop = true;
            if (mThread != null) {
                mThread.interrupt();
                mThread = null;
            }

            FaceSDKManager.getInstance().getFaceLiveness().release();
            // 摄像头重启情况之前记录状态
            FaceSDKManager.getInstance().getFaceLiveness().clearInfo();
            mInitOk = false;
        }
    }
}
