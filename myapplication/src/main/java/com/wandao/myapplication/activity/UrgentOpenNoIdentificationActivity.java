package com.wandao.myapplication.activity;

import android.os.RemoteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.baidu.idl.sample.ui.BaseActivity;
import com.wandao.myapplication.R;
import com.wandao.myapplication.service.IRemoteService;
import com.wandao.myapplication.utils.DbUtils;
import com.wandao.myapplication.utils.DoorUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrgentOpenNoIdentificationActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    private GridView mGridView;
    private SimpleAdapter adapter;
    private static IRemoteService boxService; //获取当前锁控服务
    private static ArrayList<String> userList = new ArrayList<String>();
    private List<Map<String,Object>> lists=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgent_open_no_identification);
        userList.clear();
        initData();
        initView();
    }
    public void initData() {
        boxService = MyApplication.mService;
//        for (int i = 1; i<= DbUtils.getDoorNumber(); i++)
//        {
//            userList.add(i+"号柜门");
//        }
        for (int i = 1; i <= DbUtils.getDoorNumber(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("box", i + "号柜门");
            lists.add(map);
        }
    }
   public void  initView(){
        TextView view=(TextView) findViewById(R.id.title);
        view.setText("紧急处理");
        mGridView=findViewById(R.id.urgent_gridview);
   //     adapter = new ArrayAdapter<String>(UrgentOpenNoIdentificationActivity.this, R.layout.urgent_open_item, userList);
        adapter = new SimpleAdapter(this, lists, R.layout.urgent_open_item, new String[]{"box"}, new int[]{R.id.urgent_open_button});
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {


//            if (position<20)
//                boxService.lockControlOpenDoor(Byte.valueOf(position+1 + ""), Byte.valueOf(0 + ""));
//            if (position>=20)
//                boxService.lockControlOpenDoor(Byte.valueOf(((position-20)+1+"")), Byte.valueOf(15 + ""));

            DoorUtils.getSingleton().openDoor(position + 1);   //银华开门方式

            DbUtils.changeBoxStatus(0,DbUtils.queryBox(position+1),new Date().getTime());            //柜门状态改变，标记为空
        } catch (Exception e) {
            Log.d("RemoteException",e.toString());
            e.printStackTrace();
        }
    }
}
