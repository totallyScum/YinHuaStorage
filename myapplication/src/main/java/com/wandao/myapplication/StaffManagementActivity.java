package com.wandao.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.idl.sample.ui.BaseActivity;
import com.baidu.idl.sample.ui.UserActivity;
import com.wandao.myapplication.activity.RegisterInfoActivity;

public class StaffManagementActivity extends BaseActivity  {
    private String data[] = {"人员信息录入","人员信息查询管理"};//列表信息
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_management);

        TextView view=(TextView) findViewById(R.id.title);  //设置toolbar样式
        view.setText("人员管理");
         listView = (ListView) findViewById(R.id.listview);//在视图中找到ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);//新建并配置ArrayAapeter
        listView.setAdapter(adapter);
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Intent intent = new Intent(getApplicationContext(), RegisterInfoActivity.class);
                startActivity(intent);
                break;
            case 1:
                Intent intent2 = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(intent2);
                break;
        }
    }});









}}
