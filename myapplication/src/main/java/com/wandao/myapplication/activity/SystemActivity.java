package com.wandao.myapplication.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baidu.idl.sample.ui.BaseActivity;
import com.wandao.myapplication.R;
import com.wandao.myapplication.greendao.User;
import com.wandao.myapplication.utils.DbUtils;
import com.wandao.myapplication.utils.SPUtils;
import com.wandao.myapplication.view.CustomDatePicker;
import com.wandao.myapplication.view.DateFormatUtils;

import java.util.ArrayList;
import java.util.List;

public class SystemActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTvSelectedTime;
    private CustomDatePicker mTimerPicker;
    private String newPassword;     //新密码
    private String oldPassword;     //旧密码
    private String checkPassword;   //新密码确认
    private EditText mNewPassword;
    private EditText mOldPassword;
    private EditText mCheckPassword;
    private String password; //原储存的密码 没有设置为""
    private Button confrim;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    private TimePicker startTimeAM;   //设置上午开始时间
    private TimePicker endTimeAM;     //设置上午结束时间
    private TimePicker startTimePM;   //设置下午开始时间
    private TimePicker endTimePM;     //设置下午结束时间
    private EditText adminID;
    private TextView hintText;
    private ListView adminListView;
    private List<User> users=new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private static ArrayList<String> adminList = new ArrayList<String>();
    private EditText email;
    private Button confirmEmail;
    private TextView hintEmail;
    private TextView hintPassword;
    private Button settingTime;
    private TextView hintTimeStatus;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);
        findViewById(R.id.ll_time).setOnClickListener(this);

        TextView view=(TextView) findViewById(R.id.title);
        view.setText("系统设置");

        mTvSelectedTime = findViewById(R.id.tv_selected_time);
        initTimerPicker();
        initView();
        initData();
        initStartandEndTime();
        editor = getSharedPreferences("password", Context.MODE_PRIVATE).edit();//file_name即为文件名
        preferences = getSharedPreferences("password", Context.MODE_PRIVATE);
      //  password = preferences.getString("password", "");
        password= (String) SPUtils.get(this,"password","");
        Log.d("system_time",MyApplication.startTimeAM+"");
        Log.d("system_time",MyApplication.endTimeAM+"");
    }
private void initData(){
        adminList.clear();
    users= DbUtils.getInstance().getAdminUser();
    for (User user:users)
    {
        adminList.add(user.getId()+"");
    }
    adapter.notifyDataSetChanged();
}
    private void initView() {
        mNewPassword = findViewById(R.id.new_password);
        mOldPassword = findViewById(R.id.old_password);
        mCheckPassword = findViewById(R.id.new_password_check);

        startTimeAM=findViewById(R.id.system_start_time_am);
        endTimeAM=findViewById(R.id.system_end_time_am);
        startTimePM=findViewById(R.id.system_start_time_pm);
        endTimePM=findViewById(R.id.system_end_time_pm);

        adminID=findViewById(R.id.admin_id);
        hintText=findViewById(R.id.hint_text);
        adminListView=findViewById(R.id.admin_listview);
        hintPassword =findViewById(R.id.password_hint);

        email=findViewById(R.id.email);
        confirmEmail=findViewById(R.id.confirm_email);
        hintEmail=findViewById(R.id.hint_email);
        adapter = new ArrayAdapter<String>(SystemActivity.this, android.R.layout.simple_list_item_1, adminList);
        adminListView.setAdapter(adapter);
        settingTime=(Button) findViewById(R.id.set_time);
        settingTime.setOnClickListener(this);
        hintTimeStatus=findViewById(R.id.hint_time_status);
        adminListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//             dListview.getSelectedItem().toString();
                showListDialog(position);

            }
        });
        confrim=findViewById(R.id.confirm);
        confrim.setOnClickListener(this);
    }
    public void showListDialog(final int pos) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final android.app.AlertDialog.Builder normalDialog =
                new android.app.AlertDialog.Builder(SystemActivity.this);
        //     normalDialog.setIcon(R.drawable.);
        normalDialog.setTitle("是否删除？");
        normalDialog.setMessage("确定删除当前单位?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        if (adminList.size() > 0) {
                          //  adminList.remove(pos);
                            DbUtils.getInstance().removeAdminUser(Integer.parseInt(adminList.get(pos)));
                            initData();
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
@RequiresApi(api = Build.VERSION_CODES.M)
private void initStartandEndTime(){
    startTimeAM.setIs24HourView(true);
    endTimeAM.setIs24HourView(true);
    startTimePM.setIs24HourView(true);
    endTimePM.setIs24HourView(true);
    startTimeAM.setHour(MyApplication.startTimeAM.getHours());
    startTimeAM.setMinute(MyApplication.startTimeAM.getMinutes());

    endTimeAM.setHour(MyApplication.endTimeAM.getHours());
    endTimeAM.setMinute(MyApplication.endTimeAM.getMinutes());


    startTimePM.setHour(MyApplication.startTimePM.getHours());
    startTimePM.setMinute(MyApplication.startTimePM.getMinutes());
    endTimePM.setHour(MyApplication.endTimePM.getHours());
    endTimePM.setMinute(MyApplication.endTimePM.getMinutes());
}
    private void initTimerPicker() {
        String beginTime = "2018-01-01 18:00";
        String endTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);

        mTvSelectedTime.setText(endTime);

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mTimerPicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mTvSelectedTime.setText(DateFormatUtils.long2Str(timestamp, true));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.ll_date:
//                // 日期格式为yyyy-MM-dd
//                mDatePicker.show(mTvSelectedDate.getText().toString());
//                break;
            case R.id.set_time:{
                if (endTimeAM.getHour()>startTimeAM.getHour()&&endTimePM.getHour()>startTimePM.getHour()) {
                    SPUtils.put(this, "endTimeHourAM", endTimeAM.getHour());
                    SPUtils.put(this, "endTimeMinuteAM", endTimeAM.getMinute());
                    SPUtils.put(this, "startTimeHourAM", startTimeAM.getHour());
                    SPUtils.put(this, "startTimeMinuteAM", startTimeAM.getMinute());


                    SPUtils.put(this, "endTimeHourPM", endTimePM.getHour());
                    SPUtils.put(this, "endTimeMinutePM", endTimePM.getMinute());
                    SPUtils.put(this, "startTimeHourPM", startTimePM.getHour());
                    SPUtils.put(this, "startTimeMinutePM", startTimePM.getMinute());


                    MyApplication.startTimeAM.setHours(startTimeAM.getHour());
                    MyApplication.startTimeAM.setMinutes(startTimeAM.getMinute());
                    MyApplication.endTimeAM.setHours(endTimeAM.getHour());
                    MyApplication.endTimeAM.setMinutes(endTimeAM.getMinute());

                    MyApplication.startTimePM.setHours(startTimePM.getHour());
                    MyApplication.startTimePM.setMinutes(startTimePM.getMinute());
                    MyApplication.endTimePM.setHours(endTimePM.getHour());
                    MyApplication.endTimePM.setMinutes(endTimePM.getMinute());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hintTimeStatus.setText("时间设置成功！");
                            hintTimeStatus.setTextColor(Color.GREEN);
                        }
                    });
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hintTimeStatus.setText("时间间隔不能小于1小时！");
                            hintTimeStatus.setTextColor(Color.RED);
                        }
                    });
                }
                break;
            }
            case R.id.ll_time:
                // 日期格式为yyyy-MM-dd HH:mm
                mTimerPicker.show(mTvSelectedTime.getText().toString());

                break;


            case R.id.confirm: {
                oldPassword = mOldPassword.getText().toString();
                newPassword = mNewPassword.getText().toString();
                checkPassword = mCheckPassword.getText().toString();
                if ("0".equals(isValuePassword(newPassword, checkPassword, oldPassword))) {
                    SPUtils.put(this,"password",newPassword);
               //     editor.putString("password", newPassword);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hintPassword.setText("新密码设置成功！");
                            hintPassword.setTextColor(Color.GREEN);
                        }
                    });
                } else

                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hintPassword.setText("新密码设置失败！");
                            hintPassword.setTextColor(Color.RED);
                        }
                    });
                }
                    Toast.makeText(this, isValuePassword(oldPassword, checkPassword, oldPassword), Toast.LENGTH_LONG).show();
                ;
                break;
            }
            case R.id.confirm_email:{
                SPUtils.put(this, "email", email.getText().toString());
                hintEmail.setText("邮箱设置为:"+email.getText().toString());
                hintEmail.setTextColor(Color.GREEN);
                break;
            }
        }
    }

    public String isValuePassword(String newPassword, String checkPassword, String oldPassword) {
        if (newPassword.equals(checkPassword) && oldPassword.equals(password)) {
            Log.d("password_check","新旧密码正确");

            return "0";
        } else {
            Log.d("password_check","新旧密码不正确");
            return "新旧密码不正确";
        }


    }
    public void submitAdmin(View view) {
        String id = adminID.getText().toString();
        if (id == "") {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hintText.setText("输入为空");
                    hintText.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            });
        }
        try {
            if (DbUtils.getInstance().queryUser(Integer.parseInt(id)).equals(null)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hintText.setText("该用户不存在");
                        hintText.setTextColor(getResources().getColor(R.color.colorAccent));
                    }
                });
            } else {
                hintText.setText("用户添加成功");
                hintText.setTextColor(getResources().getColor(R.color.colorPrimary));
                DbUtils.getInstance().addAdmin(Integer.parseInt(id));    //设置超级管理员权限
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hintText.setText("输入格式错误");
                    hintText.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            });
        }
    }

}
