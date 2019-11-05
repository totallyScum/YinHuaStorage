package com.wandao.myapplication.activity;

import android.annotation.SuppressLint;
import android.app.ZysjSystemManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.wandao.myapplication.R;
import com.wandao.myapplication.utils.NetUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
ListView mListView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter;
    private TextView btnBack;
    private TextView ip;
    String[] from = {"img", "text"};
    public ZysjSystemManager SystemManager;
    int[] to = {R.id.setting_img, R.id.setting_text};
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        SystemManager=(ZysjSystemManager)getSystemService("zysj");
        SystemManager.ZYSystemBar(0);
        TextView view=(TextView) findViewById(R.id.title);
        view.setText("设置");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initData();
        mListView=(ListView) findViewById(R.id.mlistview);
        adapter = new SimpleAdapter(this, dataList, R.layout.setting_list_item, from, to);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
        initView();
    }
    void initView(){
        ip=findViewById(R.id.ip);
        if (NetUtils.isNetworkConnected(getApplicationContext()))
        ip.setText(NetUtils.getLocalIPAddress().toString());
        else
            ip.setText("网络连接断开");
    }

    void initData() {
        //图标
        int icno[] = {R.mipmap.setting_setup, R.mipmap.setting_checklist,R.mipmap.setting_group,R.mipmap.setting_calendar,R.mipmap.setting_open_door,R.mipmap.setting_time,R.mipmap.setting_leave,R.mipmap.setting_back_desktop,};
        //图标下的文字
        String name[] = {"系统设置", "部门管理",
                "人员管理", "打印日志", "柜门管理", "用户独立时间设定", "退出到主界面", "退出到桌面"};
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < icno.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icno[i]);
            map.put("text", name[i]);
            dataList.add(map);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0) {
                    Intent intent = new Intent(getApplicationContext(), SystemActivity.class);
                    ;
                    startActivity(intent);
                }
                if (position==1) {
                    Intent intent = new Intent(getApplicationContext(), DepartmentActivity.class);
                    ;
                    startActivity(intent);
                }
                if (position==2) {
                    Intent intent = new Intent(getApplicationContext(), StaffManagementActivity.class);
                    startActivity(intent);
                }
                if (position==3) {
                    Intent intent = new Intent(getApplicationContext(), LogcatActivity.class);
                    startActivity(intent);
                }
        if (position==4) {
            Intent intent = new Intent(getApplicationContext(), UrgentOpenNoIdentificationActivity.class);
            startActivity(intent);
        }
        if (position==5)
        {
            Intent intent = new Intent(getApplicationContext(), UserTimeSettingActivity.class);
            startActivity(intent);
        }
//        if (position==5) {
//            Intent intent = new Intent(getApplicationContext(), LockScreenActivity.class);
//            startActivity(intent);
//        }
        if (position==6) {
            Intent intent = new Intent(getApplicationContext(), LicenseActivity.class);
            startActivity(intent);
        }
        if (position==7) {
            SystemManager.ZYSystemBar(1);
            Intent intent = new Intent();
// 为Intent设置Action、Category属性
            intent.setAction(Intent.ACTION_MAIN);// "android.intent.action.MAIN"
            intent.addCategory(Intent.CATEGORY_HOME); //"android.intent.category.HOME"
            startActivity(intent);
        }
           }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent=new Intent(getApplicationContext(), LicenseActivity.class);
        startActivity(intent);
    }
}

