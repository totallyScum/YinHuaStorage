package com.wandao.myapplication.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ZysjSystemManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.idl.facesdk.model.Feature;
import com.baidu.idl.sample.callback.ILivenessCallBack;
import com.baidu.idl.sample.common.GlobalSet;
import com.baidu.idl.sample.db.DBManager;
import com.baidu.idl.sample.manager.UserInfoManager;
import com.baidu.idl.sample.model.LivenessModel;
import com.baidu.idl.sample.ui.BaseActivity;
import com.baidu.idl.sample.utils.DensityUtil;
import com.baidu.idl.sample.utils.FileUtils;
import com.baidu.idl.sample.utils.Utils;
import com.baidu.idl.sample.view.BinocularView;
import com.baidu.idl.sample.view.CircleImageView;
import com.baidu.idl.sample.view.MonocularView;
import com.bumptech.glide.Glide;
import com.wandao.myapplication.MenuActivity;
import com.wandao.myapplication.R;
import com.wandao.myapplication.adapter.DoorRecycleViewAdapter;
import com.wandao.myapplication.greendao.Box;
import com.wandao.myapplication.greendao.BoxDao;
import com.wandao.myapplication.greendao.User;
import com.wandao.myapplication.listener.UserListListener;
import com.wandao.myapplication.loader.GlideImageLoader;
import com.wandao.myapplication.model.Contract;
import com.wandao.myapplication.model.Model;
import com.wandao.myapplication.network.request.LogRequestBody;
import com.wandao.myapplication.network.response.Response;
import com.wandao.myapplication.network.schedulers.SchedulerProvider;
import com.wandao.myapplication.presenter.Presenter;
import com.wandao.myapplication.service.DoorService;
import com.wandao.myapplication.service.IRemoteService;
import com.wandao.myapplication.service.OnServerChangeListener;
import com.wandao.myapplication.service.ServerPresenter;
import com.wandao.myapplication.ui.SelectButton;
import com.wandao.myapplication.utils.DbUtils;
import com.wandao.myapplication.utils.DoorUtils;
import com.wandao.myapplication.utils.ImageUtil;
import com.wandao.myapplication.utils.NetUtils;
import com.wandao.myapplication.utils.SPUtils;
import com.wandao.myapplication.utils.TimeUtil;
import com.wrbug.editspinner.EditSpinner;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.zip.Inflater;

import static android.widget.Toast.LENGTH_LONG;
import static com.wandao.myapplication.utils.DbUtils.booleanFetch;

public class MainActivity extends BaseActivity implements ILivenessCallBack, View.OnTouchListener, Contract.View {
    private Context mContext;
    private SelectButton button;
    private RelativeLayout mCameraView;
    private BinocularView mBinocularView;
    private MonocularView mMonocularView;
    private View mLayoutCamera;
    private ImageView mImageTrack;
    private TextView mNickNameTv;
    private RelativeLayout mLayoutInfo;
    private CircleImageView mImage;
    private Bitmap mBitmap;
    private String mUserName;
    private DBManager dbManager;
    private SharedPreferences preferences;
    private String mPassword;
    private ArrayList<Integer> imagePath;
    private Button mUrgentButton;
    private Button mOpenButton;
    private Button mLockButton;
    private ListView statusList;       //显示柜门当前状态的list
    private static User currentUser;  //当前用户
    private static Box currentBox = null;
    private static IRemoteService boxService; //获取当前锁控服务
    public  RecyclerView mRecyclerView;
    private final static int FEATCH_NORMAL_STATUS = 0;  // 正常开门
    private final static int FEATCH_LATE_STATUS = 1;   //异常开门
    private final static int FEATCH_EXPECTION_STATUS = 2; //紧急取件 （特权取件）
    private static MyApplication myApplication = MyApplication.getInstance();
    private HomeAdapter mAdapter;
    public static DoorRecycleViewAdapter doorAdapter;
    public static List<Feature> listFeatureAdapter=new ArrayList<Feature>();
    static UserListListener mUserInfoListener;
    static List<Feature> featureList;
    public ServerPresenter serverPresenter;
   private List<Box> boxList=new ArrayList<>();
    private int doorNumber;
    private ImageView lockScreenImage;
    public ZysjSystemManager SystemManager;
    private PopupWindow popupWindow;
    String LockImagePath= Environment.getExternalStorageDirectory() + "/万道壁纸";
    private View popRootView;         //迟到早退弹窗
    private EditSpinner editSpinner;
    private Presenter presenter;
    private static LogRequestBody logRequestBody = new LogRequestBody();
    private static int statusCount = -1;
    private static WindowManager windowManager;
    private static View parentView;
    private static List<Integer> doorStatus = new ArrayList<>();
    private  Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        //    mCameraView.setVisibility(View.INVISIBLE);
            switch (msg.what)
            {
                case 1:{

                    if (DbUtils.getDoorNumber() <= 40) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 10);
                        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
                        mRecyclerView.setLayoutManager(gridLayoutManager);
                    }
                    if (DbUtils.getDoorNumber() > 40) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 15);
                        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
                        mRecyclerView.setLayoutManager(gridLayoutManager);

                    }


                    doorAdapter = new DoorRecycleViewAdapter(getBaseContext(), mRecyclerView);
                    mRecyclerView.setAdapter(doorAdapter);
                    initData();
                    break;
                }
            }
        }
    };
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("user_size",DbUtils.getInstance().getUserNumber()+"");
        setContentView(R.layout.activity_main);
        presenter = new Presenter(new Model(), this, SchedulerProvider.getInstance());

        initDatas();        //创建door适配器数据
      //  initBanner();
        initActivity();
     //   initLicence();
        DbUtils.initBoxNumber(60);
//        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//        intent.setData(Uri.parse("package:" + getPackageName()));
//        startActivityForResult(intent,100);
        //注册广播接收器

        requestOverlayPermission();
    }

    @Override
    public void getDataSuccess(Response<String> msg) {
        Log.d("vvvvv", msg.getMsg() + "6666");
    }

    @Override
    public void getDataFail(String s) {
        Log.d("vvvvv", s + "23333");
    }


    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            int count = bundle.getInt("count");
            if (!DbUtils.getInstance().getDoorHealthStatus(count))
                count = 0;
            //         doorStatus.add(count);
            Log.d("wwweeee66", count + "");

            if (count == 0) {
                //      doorStatus.clear();

                if (parentView != null && windowManager != null) {
                    if (parentView.getParent() != null)
                        windowManager.removeView(parentView);
                    Log.d("wwweeee", "233333333");
                }
            }
            //         if (doorStatus.size()!=0)


            if (count != statusCount) {
//                if (count==0)
//                {
////                    if (statusCount==0)
////                    {
//                        if (parentView!=null&&windowManager!=null)
//                        {
//                            windowManager.removeViewImmediate(parentView);
////                            statusCount=-1;
//                            Log.d("wwweeee","233333333");
//                        }
//   //                 }
//                }else
//
                if (count != 0) {
                    // Toast.makeText(getApplicationContext(),count+"号柜门未关闭",Toast.LENGTH_SHORT).show();
                    if (parentView != null && windowManager != null) {
                        if (parentView.getParent() != null)
                            windowManager.removeViewImmediate(parentView);
                    }
                    showFloatingWindow(count, getApplicationContext());
                }
            }
            statusCount = count;
        }
    }



    public void initActivity() {
        mContext = this;
        mCameraView = findViewById(R.id.layout_camera);
        mLayoutCamera = findViewById(com.baidu.idl.sample.R.id.layout_camera);
        mImageTrack = findViewById(R.id.image_track);
        mImage = findViewById(R.id.image);
        mLayoutInfo = findViewById(R.id.layout_info);
        mNickNameTv = findViewById(R.id.tv_nick_name);
        mUrgentButton = (Button) findViewById(R.id.urgent_button);
        mOpenButton = (Button) findViewById(R.id.open_button);
        mLockButton = (Button) findViewById(R.id.lock_button);
        mRecyclerView = (RecyclerView) findViewById(R.id.status_recyclerView);
        lockScreenImage=findViewById(R.id.lock_screen_image);
        mUrgentButton.setOnTouchListener(this);
        mOpenButton.setOnTouchListener(this);
        mLockButton.setOnTouchListener(this);
        calculateCameraView();


        Intent serviceIntent =
                new Intent("com.hardware.interface");
        serviceIntent.setPackage("com.android.hardware");
        //      boxService = MyApplication.getInstance().getRemoteService();
        boxService = myApplication.mService;
        if (boxService != null) {
            Log.d("boxService", "Notnull");
        } else {
            Log.d("boxService", "null");
        }



        //      Intent AlarmIntent = new Intent(MainActivity.this, SendEmailService.class);
        //      startService(AlarmIntent);
        //bindService(intent,serviceConnection, Service.BIND_AUTO_CREATE);

//设置adapter

     //   initData();

        // 创建一个message对象
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = 1;
// 发送消息到handler，通过handlerMessage()方法接收消息
                handler.sendMessage(msg);
            }
        }).start();


        serverPresenter = new ServerPresenter(this, new OnServerChangeListener() {
            @Override
            public void onServerStarted(String ipAddress) {

                Toast.makeText(MainActivity.this, ipAddress, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onServerStopped() {
                Toast.makeText(MainActivity.this, "服务器停止", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onServerError(String errorMessage) {
                Toast.makeText(MainActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
            }
        });
////
//
//
        serverPresenter.startServer(getApplicationContext());
     //   DbUtils.initBoxNumber(20);

    }

    public void initPassword() {
        mPassword=SPUtils.get(this,"password","").toString();

        Log.d("password_init",mPassword);
//        preferences = getSharedPreferences("password", Context.MODE_PRIVATE);
//        mPassword = preferences.getString("password", "");
//        Log.d("password_0001",mPassword+"   ");
//        SharedPreferences setting = getSharedPreferences("wandao", 0);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onStart() {
        super.onStart();
        initLicence();
        SystemManager=(ZysjSystemManager)getSystemService("zysj");
        SystemManager.ZYSystemBar(0);
    }

    public void initBanner() {
        imagePath = new ArrayList<>();
        imagePath.add(R.mipmap.banner01);
        imagePath.add(R.mipmap.banner01);
        imagePath.add(R.mipmap.banner01);
        imagePath.add(R.mipmap.banner01);
        Banner banner = (Banner) findViewById(R.id.banner);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(imagePath);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    public void Setting(View view) {
//        showAnimation();

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
        builder.setIcon(R.mipmap.main_setting);
        builder.setTitle("请输入管理员密码");
        View view2 = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_layout, null);
        builder.setView(view2);
        final EditText password = (EditText) view2.findViewById(R.id.password);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String b = password.getText().toString().trim();
                //    将输入的用户名和密码打印出来
                if (b.equals(mPassword)) {
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "密码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (!isFinishing()) {
            builder.show();
        }
    }
    /**
     * 计算并适配显示图像容器的宽高
     */
    private void calculateCameraView() {
        String newPix;
        if (GlobalSet.getLiveStatusValue() == GlobalSet.LIVE_STATUS.RGN_NIR) {
            newPix = DensityUtil.calculateCameraOrbView(mContext);
        } else {
            newPix = DensityUtil.calculateCameraView(mContext);
        }
        String[] newPixs = newPix.split(" ");
        int newWidth = Integer.parseInt(newPixs[0]);
        int newHeight = Integer.parseInt(newPixs[1]);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(newWidth, newHeight);

        if (GlobalSet.getLiveStatusValue() == GlobalSet.LIVE_STATUS.RGN_NIR) {
            mBinocularView = new BinocularView(mContext, 2);
            mBinocularView.setImageView(mImageTrack);
            mBinocularView.setLivenessCallBack(this);
            mCameraView.addView(mBinocularView, layoutParams);
        } else {

            mMonocularView = new MonocularView(mContext);
            mMonocularView.setImageView(mImageTrack);
            mMonocularView.setLivenessCallBack(this);
            mCameraView.addView(mMonocularView, layoutParams);
        }
    }


    private void initLicence() {
        if (GlobalSet.FACE_AUTH_STATUS != 0) {
            startActivity(new Intent(MainActivity.this, LicenseActivity.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isFirstBoot();
        if (doorAdapter != null)
        initData();
        boxService = myApplication.mService;
        calculateCameraView();
        if (GlobalSet.getLiveStatusValue() == GlobalSet.LIVE_STATUS.RGN_NIR) {
            mBinocularView.onResume();
        } else {
            mMonocularView.onResume();
        }


        MyReceiver receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.wandao.myapplication");
        MainActivity.this.registerReceiver(receiver, filter);


    }

    private void initData() {
//        FaceSDKManager.getInstance().setFeature();
        mUserInfoListener=UserListListener.newInstance();
        // 初始化数据库
        DBManager.getInstance().init(getApplicationContext());

        // 读取数据库信息
        UserInfoManager.getInstance().getFeatureInfo(null, mUserInfoListener);

        doorAdapter.setListData(listFeatureAdapter);
        doorAdapter.notifyDataSetChanged();
     //   featureList=mUserInfoListener.mlistFeatureInfo;

       // mRecyclerView.setAdapter(dAdapter);
     //   dAdapter.notifyDataSetChanged();


     //   int num = FaceSDKManager.getInstance().setFeature();
  //      Log.d("底库人脸数:",num+"");
        boxList=DbUtils.getInstance().loadAllBox();
        doorNumber=DbUtils.getDoorNumber()!=0?DbUtils.getDoorNumber():20;
        initPassword();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serverPresenter.unregister(this);
        serverPresenter = null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        initActivity();
        if (doorAdapter != null)
           initData();
        if (GlobalSet.getLiveStatusValue() == GlobalSet.LIVE_STATUS.RGN_NIR) {
            mBinocularView.onResume();
        } else {
            mMonocularView.onResume();
        }
    }

    private void showCamera() {
        if (GlobalSet.getLiveStatusValue() == GlobalSet.LIVE_STATUS.NO
                || GlobalSet.getLiveStatusValue() == GlobalSet.LIVE_STATUS.RGB
                || GlobalSet.getLiveStatusValue() == GlobalSet.LIVE_STATUS.RGN_NIR
        ) {
            int number = Camera.getNumberOfCameras();
            if (true) {
                mLayoutCamera.setVisibility(View.VISIBLE);
                //      mLayoutHandle.setVisibility(View.GONE);
            } else {
                mLayoutCamera.setVisibility(View.GONE);
                //      mLayoutHandle.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void onTip(int code, String msg) {

    }

    @Override
    public void onCanvasRectCallback(LivenessModel livenessModel) {

    }

    @Override
    public void onCallback(final int code, final LivenessModel livenessModel) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (livenessModel == null) {
                    currentUser = null;
                    mLayoutInfo.setVisibility(View.INVISIBLE);
                    if (mRunnable == null) {
                        mRunnable = new MyRunnable();
                        //mRunnable.run();
                        handler.postDelayed(mRunnable, 3000);
                    }

                    List<String> lockImagePath=ImageUtil.getInstance().getPictures(LockImagePath);

                    Glide.with(MainActivity.this)
                            .load(lockImagePath.get(0))
                            .crossFade()
                            .into(lockScreenImage);
                    //加载彩色头像
                } else {
                    mCameraView.setVisibility(View.VISIBLE);
                    mLayoutCamera.setVisibility(View.VISIBLE);
                    mLayoutInfo.setVisibility(View.VISIBLE);
                    handler.removeCallbacks(mRunnable);
                    mRunnable=null;
                    if (code == 0) {

                        handler.removeCallbacks(mRunnable);
                        mRunnable=null;
                        mLayoutCamera.setVisibility(View.VISIBLE);
                        mCameraView.setVisibility(View.VISIBLE);
                        mImage.setImageBitmap(mBitmap);
                        Feature feature = livenessModel.getFeature();
                        feature.getUserId();
                        if (DbUtils.getInstance().queryUser(Long.valueOf(feature.getUserName()))!=null)
                           currentUser = DbUtils.getInstance().queryUser(Long.valueOf(feature.getUserName()));
                        Log.d("currentUser_UserInfo", currentUser.getBoxId() + "");
                        if (currentUser.getBoxId()!=0)
                        currentBox = DbUtils.queryBox(Long.valueOf(currentUser.getBoxId()));
                        else {
                            currentBox=new Box((long) 0, 0, 0, 0, true);
                        }
                        Log.d("currentUser_BoxInfo", currentBox.getBoxId() + "");
                        mNickNameTv.setText(String.format("%s，你好!", currentUser.getName()));
                        if (!TextUtils.isEmpty(mUserName) && feature.getUserName().equals(mUserName)) {
                            mImage.setImageBitmap(mBitmap);
                        } else {
                            String imgPath = FileUtils.getFaceCropPicDirectory().getAbsolutePath()
                                    + "/" + feature.getCropImageName();
                            Bitmap bitmap = Utils.getBitmapFromFile(imgPath);
                            mImage.setImageBitmap(bitmap);
                            mBitmap = bitmap;
                            mUserName = feature.getUserName();
                        }
                    } else {
                        currentUser = null;
                        mNickNameTv.setText("陌生访客");
                        mImage.setImageResource(R.mipmap.preview_image_angle);
                    }
                }
            }
        });
    }

    @Override
    protected void onStop() {
   //     SystemManager.ZYSystemBar(0);
        if (GlobalSet.getLiveStatusValue() == GlobalSet.LIVE_STATUS.RGN_NIR) {
            mBinocularView.onPause();
        } else {
            mMonocularView.onPause();
        }
        super.onStop();
        countDownTimer.cancel();
    }

    @Override

    public boolean onKeyDown(int keyCode, KeyEvent

            event) {

        return false;

    }

    public void isFirstBoot() {                //判断是否是第一次启动
        SharedPreferences setting = getSharedPreferences("wandao", 0);
        Boolean user_first = setting.getBoolean("FIRST", true);
        if (user_first) {//第一次
            setting.edit().putBoolean("FIRST", false).commit();
            Toast.makeText(MainActivity.this, "第一次", LENGTH_LONG).show();
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
            builder.setIcon(R.mipmap.icon_setting);
            builder.setTitle("请输入箱门数");
            View view2 = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_layout, null);
            builder.setView(view2);
            builder.setCancelable(false);
            final EditText password = (EditText) view2.findViewById(R.id.password);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String b = password.getText().toString().trim();
                    if (b != null) {
                        DbUtils.initBoxNumber(Integer.parseInt(b));
                        doorAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), "未输入箱门数", LENGTH_LONG).show();
                    }
                }
            });



            builder.show();


        } else {
         //   Toast.makeText(MainActivity.this, "不是第一次", LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (view.getId()) {
            case R.id.urgent_button: {
                if (currentUser == null) {
                    break;
                } else {
                    if (currentUser.getController()==true) {
                        //        DbUtils.storageLog(3,null,1);
                        Intent intent = new Intent();
                        Log.d("currentUserID", currentUser.getId() + "");
                        intent.putExtra("currentUserID", currentUser.getId());
                        intent.setClass(MainActivity.this, UrgentOpenActivity.class);
                        MainActivity.this.startActivity(intent);
                    } else {
                        Toast.makeText(this, "非管理人员，请勿继续操作！", LENGTH_LONG).show();
                    }
                    break;
                }
            }
            case R.id.open_button: {
                if (currentUser == null) {
                    Toast.makeText(this, "非本公司人员请勿操作！", LENGTH_LONG).show();
                    break;
                } else {
                    if (currentBox.getStatus() == 0&&currentBox.getBoxId()!=0) {
                        try {
                            //     boxService.lockControlOpenDoor(Byte.valueOf(1+""), Byte.valueOf(0 + ""));
                            Log.d("getBoxId", currentBox.getBoxId() + "");
//                            if (currentBox.getBoxId()<=20)
//                                boxService.lockControlOpenDoor(Byte.valueOf(currentBox.getBoxId() + ""), Byte.valueOf(0 + ""));
//                            if (currentBox.getBoxId()>20)
//                                boxService.lockControlOpenDoor(Byte.valueOf((currentBox.getBoxId()-20) + ""), Byte.valueOf(15 + ""));

                            DoorUtils.getSingleton().openDoor(Byte.valueOf(currentBox.getBoxId() + ""));   //银华开门方式

//                            Toast.makeText(this, "当前不是可取手机时间！", LENGTH_LONG).show();
                            DbUtils.storageLog(DbUtils.checkStorageStatus(new Date(),currentUser.getId()), new Date(), currentUser.getId());    //记录存柜事件
                            DbUtils.changeBoxStatus(1, currentBox, new Date().getTime());            //柜门状态改变，标记为非空

                            Log.d("2222222zxxxcvx", DbUtils.checkStorageStatus(new Date(), currentUser.getId()) + "");

                            if (DbUtils.checkStorageStatus(new Date(), currentUser.getId()) == 2) {
                                showLateWindow(getApplicationContext(), currentUser.getId());
                                Log.d("2222222zxxxcvx", "222333");
                            }


                            logRequestBody.setBoxIp(NetUtils.getLocalIPAddress().toString().split("/")[1] + "");
                            //    logRequestBody.setBoxIp("192.168.3.21");
                            Log.d("2222222zxxxcvxqq", NetUtils.getLocalIPAddress().toString().split("/")[1] + "");
//                            logRequestBody.setLogID("2019093001");
//                            logRequestBody.setDoorID(currentBox.getBoxId()+"");
//                            logRequestBody.setUserID(currentUser.getId()+"");
//                            logRequestBody.setUserName(currentUser.getName());
//                            logRequestBody.setDepsName(currentUser.getDepartment());
//                            logRequestBody.setStatus("0");
//                            logRequestBody.setTime(Long.parseLong(new Date().toString()));
//                            logRequestBody.setActionUserName(currentUser.getName());
//                            Log.d("2222222zxxxcvxqq",logRequestBody.toString());

                            currentBox = DbUtils.queryBox(Long.valueOf(currentUser.getBoxId()));
                            doorAdapter.notifyDataSetChanged();

                            logRequestBody.setBoxIp(NetUtils.getLocalIPAddress().toString().split("/")[1] + "");
                            logRequestBody.setLogID("2019093001");
                            logRequestBody.setDoorID(currentUser.getBoxId() + "");
                            logRequestBody.setUserID(currentUser.getId() + "");
                            logRequestBody.setUserName(currentUser.getName());
                            logRequestBody.setDepsName(currentUser.getDepartment());
                            logRequestBody.setStatus("0");
                            logRequestBody.setTime(new Date().getTime());
                            logRequestBody.setActionUserName(currentUser.getName());
                            presenter.putLogRequest(logRequestBody);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(this, e.toString(), LENGTH_LONG).show();
                        }
                    }
                    if (currentBox.getStatus() == 1&&currentBox.getBoxId()!=0) {
                        Toast.makeText(this, "请勿重复进行存操作！", LENGTH_LONG).show();
                    }
                    if (currentBox.getBoxId()==0)
                    {
                        Toast.makeText(this, "当前用户为管理员用户！", LENGTH_LONG).show();
                    }
                    break;
                }
            }
            case R.id.lock_button: {
                if (currentUser == null) {
                    break;
                } else {
                    if (currentBox.getStatus() == 1) {
                        if (booleanFetch(this,currentUser)) {          //判断当前是否是取件时间
                            try {
//                                if (currentBox.getBoxId()<=20)
////                                    boxService.lockControlOpenDoor(Byte.valueOf(currentBox.getBoxId() + ""), Byte.valueOf(0 + ""));
////                                if (currentBox.getBoxId()>20)
////                                    boxService.lockControlOpenDoor(Byte.valueOf((currentBox.getBoxId()-20) + ""), Byte.valueOf(15 + ""));

                                DoorUtils.getSingleton().openDoor(Byte.valueOf(currentBox.getBoxId() + ""));   //银华开门方式


                                //开门指令
                                DbUtils.storageLog(1, new Date(), currentUser.getId());
                                DbUtils.changeBoxStatus(0, currentBox, new Date().getTime());            //柜门状态改变，标记为空
                                logRequestBody.setBoxIp(NetUtils.getLocalIPAddress().toString().split("/")[1] + "");
                                logRequestBody.setLogID("2019093001");
                                logRequestBody.setDoorID(currentUser.getBoxId() + "");
                                logRequestBody.setUserID(currentUser.getId() + "");
                                logRequestBody.setUserName(currentUser.getName());
                                logRequestBody.setDepsName(currentUser.getDepartment());
                                logRequestBody.setStatus("1");
                                logRequestBody.setTime(new Date().getTime());
                                logRequestBody.setActionUserName(currentUser.getName());
                                presenter.putLogRequest(logRequestBody);
                                currentBox = DbUtils.queryBox(Long.valueOf(currentUser.getBoxId()));
                                doorAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {

                        }
                    } else {
                        Toast.makeText(this, "柜中没有放置物品", LENGTH_LONG).show();
                    }
                    break;
                }
            }

        }
        return false;
    }

    private List<String> mDatas;

    protected void initDatas() {

//        List<User> users = DbUtils.queryAllUser();
//        if (users != null) {
//            for (User user : users) {
//                mDatas.add(user.getName());
//            }
//        } else {
            mDatas = new ArrayList<String>();
            for(int i=1;i<=MyApplication.doorNumber;i++)
            {
                mDatas.add("柜门" +i);
            }
//        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 100) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (Settings.canDrawOverlays(this)) {
//                    WindowManager.LayoutParams params = new WindowManager.LayoutParams();
//                    params.type = WindowManager.LayoutParams.TYPE_PHONE;
//                    params.format = PixelFormat.RGBA_8888;
//                    View parentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.late_popup_window, null);
//                    params.width = 1000;
//                    params.height = 400;
//                    params.x = 300;
//                    params.y = 300;
//                //    windowManager.addView(parentView, params);
//                } else {
//                    Toast.makeText(this, "ACTION_MANAGE_OVERLAY_PERMISSION权限已被拒绝", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }

    private void showFloatingWindow(int i, Context context) {
        if (Settings.canDrawOverlays(context) && statusCount != -1) {
            Toast.makeText(getApplicationContext(), "第" + i + "号柜未关闭", Toast.LENGTH_SHORT).show();
            // 获取WindowManager服务
            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            presenter = new Presenter(new Model(), this, SchedulerProvider.getInstance());
            windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
            // 设置LayoutParam
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            }
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
            params.format = PixelFormat.RGBA_8888;
            parentView = getLayoutInflater().inflate(R.layout.stop_operate_view, null);
            params.width = 1030;
            params.height = 210;
//            params.x = 300;
            params.y = 350;
            // 将悬浮窗控件添加到WindowManager
            TextView textView = parentView.findViewById(R.id.door_number);
            textView.setText(i + "");
            Log.d("21111111", "333333333333");
            windowManager.addView(parentView, params);
            countDownTimer.start();
        }
    }


    //显示迟到界面
    private void showLateWindow(Context context, Long userID) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
        builder.setIcon(R.mipmap.main_setting);
        builder.setTitle("请先关闭柜门");
        builder.setCancelable(false);
        View view2 = LayoutInflater.from(MainActivity.this).inflate(R.layout.late_popup_window, null);
        builder.setView(view2);
//        final EditText password = (EditText) view2.findViewById(R.id.password);
        editSpinner = (EditSpinner) view2.findViewById(R.id.edit_spinner_late);
        List<String> list = new ArrayList<>();
        list.add("请假");
        list.add("出差");
        list.add("迟到");
        list.add("外出");
        editSpinner.setItemData(list);
        editSpinner.setMaxLine(3);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editSpinner.getText() != null)
                    DbUtils.storageExceptionLog(0, new Date(), userID, editSpinner.getText());    //记录晚存
                else if (editSpinner.getText() == null) {
                    DbUtils.storageExceptionLog(0, new Date(), userID, "用户未选择晚存理由");    //记录晚存
                }
            }
        });


//        Button button=(Button) view2.findViewById(R.id.submit_leave_reason);
//                                button.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Log.d("12333333", editSpinner.getText());
//                                if (editSpinner.getText()!=null)
//                                DbUtils.storageExceptionLog(0, new Date(), currentUser.getId(),editSpinner.getText());    //记录晚存
//                                else if (editSpinner.getText()==null)
//                                {
//                                    DbUtils.storageExceptionLog(0, new Date(), currentUser.getId(),"用户未选择晚存理由");    //记录晚存
//                                }
//                                    builder.
//                            }});
        if (!isFinishing()) {
            builder.show();
        }
    }


    CountDownTimer countDownTimer = new CountDownTimer(100000, 1000) {//第一个参数表示总时间，第二个参数表示间隔时间。

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            if (parentView != null && windowManager != null) {
                if (parentView.getParent() != null)
                    windowManager.removeView(parentView);

            }
            //登记坏锁

        }
    };


    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                        MainActivity.this).inflate(R.layout.main_listview_item, parent,
                        false));
                return holder;
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, int position) {
                holder.tv.setText(mDatas.get(position));
            }

            @Override
            public int getItemCount() {
                return mDatas.size();
            }

            class MyViewHolder extends RecyclerView.ViewHolder {

                TextView tv;

                public MyViewHolder(View view) {
                    super(view);
                    tv = (TextView) view.findViewById(R.id.door_name);
                }
            }
        }

    private class MyRunnable implements Runnable {
        @Override
        public void run() {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mCameraView.setVisibility(View.INVISIBLE);
                }
            }, 1000*3);
        }

    }
    public static  MyRunnable mRunnable;

    private void showAnimation() {
//        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View vPopupWindow = inflater.inflate(R.layout.delay_popup_window, null, false);//引入弹窗布局
//        popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
//        //设置背景透明
//     //   addBackground();
//
//        //设置进出动画
//        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
//
//        //引入依附的布局
//        View parentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.delay_popup_window, null);
//        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
//        popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
    }

    private void initSpinner() {

//        editSpinner= (EditSpinner)findViewById(R.id.edit_spinner_late);
//        List<String> list = new ArrayList<>();
//        list.add("请假");
//        list.add("出差");
//        list.add("迟到");
//        list.add("外出");
//        editSpinner.setItemData(list);
//        editSpinner.setMaxLine(3);
//        editSpinner.setItemData(list);
//        editSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.i("EditSpinner", "item " + position + " click");
//            }
//        });
    }


    private void requestOverlayPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(MainActivity.this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 4444);
            } else {

            }
        }
    }


}

