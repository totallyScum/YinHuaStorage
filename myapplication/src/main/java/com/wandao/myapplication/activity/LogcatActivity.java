package com.wandao.myapplication.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.idl.sample.ui.BaseActivity;
import com.wandao.myapplication.R;
import com.wandao.myapplication.greendao.User;
import com.wandao.myapplication.utils.EmailUtil;
import com.wandao.myapplication.utils.ExcelUtil;
import com.wandao.myapplication.view.CustomDatePicker;
import com.wandao.myapplication.view.DateFormatUtils;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Session;

import static com.serenegiant.utils.LogUtils.d;


public class LogcatActivity extends BaseActivity implements View.OnClickListener {
    private final static int FEATCH_NORMAL_STATUS = 0;  // 正常开门
    private final static int STORAGE_NORMAL_STATUS = 1;  // 正常关门
    private final static int FEATCH_LATE_STATUS = 2;   //存件晚
    private final static int FEATCH_EXPECTION_STATUS = 3; //紧急取件 （特权取件）

    private TextView mStartTime;
    private TextView mEndTime;
    private CustomDatePicker mTimerPicker;
    private Button exportButton;        //导出到本地src下
    private Button openButton;
    private Button exportMailButton;      //通过邮件导出
    private TextView textView;
    private LinearLayout pickTime;
    private AlertDialog alertDialog;
    private AlertDialog mDialog;
    private RadioButton radioButton;
    private RadioGroup right_group;
    private EditText email;
    private static String mEmail;
    private static  int nowPick=0;
    String[] title_late = {"姓名", "工号", "是否紧急取出", "存机时间", "取件时间", "个人箱号"}; //迟到取件的标题
    String[] title__day={"工号","部门","姓名","柜门号","存入时间","存入状态","取出时间","取出状态","应急","备注"};
    String[] title_week={"工号","部门","姓名","柜门号","正常存入字数","超时存入次数","超时存入日期","未存次数","未存日期","正常取出","应急取出","应急取出日期","二次存取次数","二次存取日期"};
    String[] title_month={"工号","部门","姓名","柜门号","本月汇总","正常存入次数","超时存入次数","超时存入日期","未存次数","未存日期","正常取出","应急取出","应急取出日期","二次存取次数","二次存取日期"};
    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private int REQUEST_PERMISSION_CODE = 1000;
    private static String fileDayPath = Environment.getExternalStorageDirectory() + "/存储柜统计报表/日报表";
    private static String fileWeekPath = Environment.getExternalStorageDirectory() + "/存储柜统计报表/周报表";
    private static String fileMonthPath = Environment.getExternalStorageDirectory() + "/存储柜统计报表/月报表";

    private String filePath = Environment.getExternalStorageDirectory() + "/WDExcel";

    private  Handler handler=new Handler(){
        @Override
        public void handleMessage(android.os.Message msg1) {
            super.handleMessage(msg1);
switch (msg1.what)
{
    case 0:
        new Thread(new Runnable(){
            @Override
            public void run() {
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.163.com");
                Session session = Session.getInstance(props, null);
                try {
                    String mFilePath=filePath+"/万道周报.xls";
                    String [] path=new String[]{mFilePath};
                    EmailUtil.autoSendFileMail("你好","天津万道科技",mEmail,path);
                } catch (Exception e) {
                    Log.d("mailException",e+"");
                }
            }
        }).start();
        break;
    case 1:
        new Thread(new Runnable(){
            @Override
            public void run() {
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.163.com");
                Session session = Session.getInstance(props, null);
                try {
                    String mFilePath=filePath+"/万道月报.xls";
                    String [] path=new String[]{mFilePath};
                    EmailUtil.autoSendFileMail("你好","天津万道科技",mEmail,path);
                } catch (Exception e) {
                    Log.d("mailException",e+"");
                }
            }
        }).start();
        break;
    case 2:
        new Thread(new Runnable(){
            @Override
            public void run() {
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.163.com");
                Session session = Session.getInstance(props, null);
                try {
                    String mFilePath=filePath+"/万道日报.xls";
                    String [] path=new String[]{mFilePath};
                    EmailUtil.autoSendFileMail("你好","天津万道科技",mEmail,path);
                } catch (Exception e) {
                    Log.d("mailException",e+"");
                }
            }
        }).start();
        break;
    case 3:
        new Thread(new Runnable(){
            @Override
            public void run() {
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.163.com");
                Session session = Session.getInstance(props, null);
                try {
                    String mFilePath=filePath+"/万道自定义时间报表.xls";
                    String [] path=new String[]{mFilePath};
                    EmailUtil.autoSendFileMail("你好","天津万道科技",mEmail,path);
                } catch (Exception e) {
                    Log.d("mailException",e+"");
                }
            }
        }).start();
        break;
}

        }
    };



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logcat);
        radioButton = findViewById(R.id.diy_log);
        right_group = findViewById(R.id.right_group);
        exportMailButton=(Button) findViewById(R.id.export_email);

        right_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.diy_log:
                        nowPick=3;
                        pickTime.setVisibility(View.VISIBLE);
                        break;
                    case R.id.week_log:
                        nowPick=0;
                        break;
                    case R.id.month_log:
                        nowPick=1;
                        break;
                    case R.id.day_log:
                        nowPick=2;
                        break;
                    case R.id.meta_log: {
                        nowPick = 4;
                        break;
                    }
                }


                if (i == R.id.diy_log || i == R.id.meta_log)              //当选择自定义记录时，显示时间选择控件
                {
                    pickTime.setVisibility(View.VISIBLE);
                } else {
                    pickTime.setVisibility(View.INVISIBLE);
                }

            }
        });


        TextView view = (TextView) findViewById(R.id.title);  //设置toolbar样式
        view.setText("设置");


           requestPermission();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        initActivity();
        initTimerPicker(mStartTime);
        initTimerPicker(mEndTime);

    }

    public void initActivity() {
        exportButton = findViewById(R.id.export_excel);
        exportButton.setOnClickListener(this);
        openButton = findViewById(R.id.open_button);
        openButton.setOnClickListener(this);
        textView = findViewById(R.id.textView);
        mStartTime = findViewById(R.id.tv_selected_time_1);
        mEndTime = findViewById(R.id.tv_selected_time_2);
        pickTime = findViewById(R.id.diy_time);
        pickTime.setVisibility(View.INVISIBLE);

        email=(EditText) findViewById(R.id.input_email);
        findViewById(R.id.start_time).setOnClickListener(this);
        findViewById(R.id.end_time).setOnClickListener(this);
        findViewById(R.id.show_time).setOnClickListener(this);
        findViewById(R.id.export_email).setOnClickListener(this);
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT > 23) {
            if (ContextCompat.checkSelfPermission(LogcatActivity.this,
                    permissions[0])
                    == PackageManager.PERMISSION_GRANTED) {
                //授予权限
                Log.i("requestPermission:", "用户之前已经授予了权限！");
            } else {
                //未获得权限
                Log.i("requestPermission:", "未获得权限，现在申请！");
                requestPermissions(permissions
                        , REQUEST_PERMISSION_CODE);
            }
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("onPermissionsResult:", "权限" + permissions[0] + "申请成功");
                // permission was granted, yay! Do the
                // contacts-related task you need to do.

            } else {
                Log.i("onPermissionsResult:", "用户拒绝了权限申请");
                AlertDialog.Builder builder = new AlertDialog.Builder(LogcatActivity.this);
                builder.setTitle("permission")
                        .setMessage("点击允许才可以使用我们的app哦")
                        .setPositiveButton("去允许", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                if (alertDialog != null && alertDialog.isShowing()) {
                                    alertDialog.dismiss();
                                }
                                ActivityCompat.requestPermissions(LogcatActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            }
                        });
                alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        }
    }

    private void showDialogTipUserRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.export_excel:
                exportExcel(this);
                break;
            case R.id.start_time:       //查询开始时间
                // 日期格式为yyyy-MM-dd HH:mm
                mTimerPicker.show(mStartTime.getText().toString());
                break;
            case R.id.end_time: //查询结束时间
                // 日期格式为yyyy-MM-dd HH:mm
                mTimerPicker.show(mEndTime.getText().toString());
                break;
            case R.id.open_button:
                openDir();
                break;
            case R.id.export_email:
                sendMailMy();
            default:
                break;
        }
    }

    private void openDir() {

        File file = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setDataAndType(Uri.fromFile(file), "file/*");
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "没有正确打开文件管理器", Toast.LENGTH_SHORT).show();
        }
    }


    private void exportExcel(Context context) {



        switch (nowPick)
        {
            case 0:
                ExcelUtil.exportWeekExcel(context,fileWeekPath);
                break;
            case 1:
                ExcelUtil.exportMonthExcel(context,fileMonthPath);
                break;
            case 2:
                ExcelUtil.exportDayExcel(context,fileDayPath);
                break;
            case 3:
                ExcelUtil.exportDiyExcel(context,DateFormatUtils.str2Long(mStartTime.getText().toString(),true),DateFormatUtils.str2Long(mEndTime.getText().toString(),true));
                break;
            case 4:
                ExcelUtil.exportMetaExcel(context, DateFormatUtils.str2Long(mStartTime.getText().toString(), true), DateFormatUtils.str2Long(mEndTime.getText().toString(), true));
                break;
        }


//        File file = new File(filePath);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        String excelFileName = "/demo.xls";
//        String[] title_late = {"姓名", "工号", "是否紧急取出", "存机时间", "取件时间", "个人箱号"}; //迟到取件的标题
//        String[] title_day={"工号","部门","姓名","柜门号","存入时间","存入状态","取出时间","取出状态","应急取","备注"};
//        String[] title_week={"工号","部门","姓名","柜门号","正常存入次数","超时存入次数","超时存入日期","未存次数","未存日期","正常取出","应急取出","应急取出日期","二次存取次数","二次存取日期"};
//        String[] title_month={"工号","部门","姓名","柜门号","本月汇总","正常存入次数","超时存入次数","超时存入日期","未存次数","未存日期","正常取出","应急取出","应急取出日期","二次存取次数","二次存取日期"};
//        String sheetName = "万道日志";
//        String sheetNameDay = "万道日志日报";
//        String sheetNameWeek="万道日志周报";
//        String sheetNameMonth="万道日志月报";
//        List<LogBean> logBeanList = new ArrayList<>();
//        LogBean logBean1=new LogBean("陈飞旭",1,false,10000+"",10000+"",1);
//        LogBean logBean2=new LogBean("陈飞旭1",1,false,10000+"",10000+"",1);
//        LogBean logBean3=new LogBean("陈飞旭2",1,false,10000+"",10000+"",1);
//        LogBean logBean4=new LogBean("陈飞旭3",1,false,10000+"",10000+"",1);
//        logBeanList.add(logBean1);
//        logBeanList.add(logBean2);
//        logBeanList.add(logBean3);
//        logBeanList.add(logBean4);;
////        ExcelUtil.initExcel(filePath + excelFileName, sheetName, title_late);
////        ExcelUtil.writeObjListToExcel(logBeanList, filePath+ excelFileName, context);
//        String excelDayFileName = "/dayDemo23333.xls";
//
//        String excelWeekFileName = "/万道周报.xls";
//
//
//        ExcelUtil.initExcel(filePath + excelWeekFileName, sheetNameWeek, title_week);
//        Date dateStart=new Date();
//        dateStart.setHours(0);
//        dateStart.setMinutes(0);
//        dateStart.setSeconds(0);
//        Date dateEnd=new Date();
//        dateEnd.setHours(23);
//        dateEnd.setMinutes(59);
//        dateEnd.setSeconds(59);
//        Log.d("TIME+2333333333",dateStart.getTime()+"               "+dateEnd.getTime());
//
//     //   ExcelUtil.writeDayObjListToExcel(DbUtils.dayLog(dateStart.getTime(),dateEnd.getTime()), filePath+ excelDayFileName, context);
// //       ExcelUtil.writeWeekObjListToExcel(DbUtils.weekLog(getThisWeekMonday().getTime(),getThisWeekFriday().getTime()), filePath+ excelWeekFileName, context);
//        ExcelUtil.writeWeekObjListToExcel(DbUtils.weekLog(getThisWeekMonday().getTime(),getThisWeekFriday().getTime()), filePath+ excelWeekFileName, context);
//        textView.setText("WEEK excel已导出至：" + filePath+ excelWeekFileName);
    }





    private void initTimerPicker(final TextView mTextview) {
        String beginTime = "2018-1-1 18:00";
        String endTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);

        mTextview.setText(endTime);

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mTimerPicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mTextview.setText(DateFormatUtils.long2Str(timestamp, true));
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mTimerPicker.setCancelable(true);
        // 显示时和分
        mTimerPicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        mTimerPicker.setScrollLoop(true);
        // 允许滚动动画
        mTimerPicker.setCanShowAnim(true);
    }

    public void lateLog(Time startTime,Time endTime) {     //
        User user=new User();
        List<User> list=new ArrayList<>();
      //  list= DbUtils.
    //    user.getBoxId();


    }   //迟到的记录


//    public int  checkTheTime(Time time) {     //检查当前时间是否是取件时间          设置的测试时间
//         if (time.before(Application.t))
//         {
//             return 1;
//         }
//
//         return 1;
//    }


    private void sendMailMy(){


        mEmail=email.getText().toString();
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.163.com");
        Session session = Session.getInstance(props, null);
        android.os.Message message=handler.obtainMessage();
        switch (nowPick)
        {
            case 0:
                ExcelUtil.exportWeekExcel(getApplicationContext(),fileWeekPath);
                message.what=0;
                handler.sendMessage(message);
                break;
            case 1:
                ExcelUtil.exportMonthExcel(getApplicationContext(),fileMonthPath);
                message.what=1;
                handler.sendMessage(message);
                break;
            case 2:
                ExcelUtil.exportDayExcel(getApplicationContext(),fileDayPath);
                message.what=2;
                handler.sendMessage(message);
                break;
            case 3:
                ExcelUtil.exportDiyExcel(getApplicationContext(),DateFormatUtils.str2Long(mStartTime.getText().toString(),true),DateFormatUtils.str2Long(mEndTime.getText().toString(),true));
                message.what=3;
                handler.sendMessage(message);
                break;
        }

        //     message.obj="倒计时：";



    }



    public static Date getThisWeekMonday() {
     //   SimpleDateFormat 格式=new SimpleDateFormat("y年M月d日 E H时m分s秒", Locale.CHINA);
        Calendar calendar=Calendar.getInstance(Locale.CHINA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        //当前时间，貌似多余，其实是为了所有可能的系统一致
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        System.out.println("当前时间:"+格式.format(calendar.getTime()));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
         Date  date =calendar.getTime();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        //System.out.println("周一时间:"+格式.format(calendar.getTime()));
        Log.d("TIME+2333333333655555",date.getTime()+"  ");
        return date;
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
//        System.out.println("周五时间:"+格式.format(calendar.getTime()));
    }

    public static Date getThisWeekFriday() {
        Calendar calendar=Calendar.getInstance(Locale.CHINA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        Date  date =calendar.getTime();
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

        Log.d("TIME+2333333333655555",date.getTime()+"  ");

        return date;
      //  System.out.println("周五时间:"+格式.format(calendar.getTime()));
    }




}
