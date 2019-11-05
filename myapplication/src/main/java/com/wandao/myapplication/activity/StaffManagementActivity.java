package com.wandao.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.baidu.idl.sample.ui.BaseActivity;
import com.baidu.idl.sample.ui.RegisterActivity;
import com.wandao.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffManagementActivity extends BaseActivity  {
    private String data[] = {"人员信息录入","人员信息批量导入","人员信息查询管理"};//列表信息
    private ListView listView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter;
    String[] from = {"img", "text"};
    int[] to = {R.id.setting_img, R.id.setting_text};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_management);
        initData();
        TextView view=(TextView) findViewById(R.id.title);  //设置toolbar样式
        view.setText("人员管理");
         listView = (ListView) findViewById(R.id.staff_listview);//在视图中找到ListView
     //   ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);//新建并配置ArrayAapeter

        adapter = new SimpleAdapter(this, dataList, R.layout.setting_list_item, from, to);


        listView.setAdapter(adapter);
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Intent intent = new Intent(getApplicationContext(), com.wandao.myapplication.activity.RegisterInfoActivity.class);
                startActivity(intent);
                break;
            case 1:
                Intent intent1 = new Intent(getApplicationContext(), BatchImportActivity.class);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2 = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent2);
                break;
        }

    }});

}
    void initData() {
        //图标
        int icno[] = {R.mipmap.department_login, R.mipmap.department_manager,R.mipmap.department_login};
        //图标下的文字
        String name[] = {"人员信息录入","人员信息批量导入", "人员信息管理",
                };
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < icno.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icno[i]);
            map.put("text", name[i]);
            dataList.add(map);
        }
    }
}
