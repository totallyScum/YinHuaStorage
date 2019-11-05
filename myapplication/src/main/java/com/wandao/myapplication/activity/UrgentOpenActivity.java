package com.wandao.myapplication.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.RemoteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.idl.sample.ui.BaseActivity;
import com.wandao.myapplication.R;
import com.wandao.myapplication.greendao.User;
import com.wandao.myapplication.model.Contract;
import com.wandao.myapplication.model.Model;
import com.wandao.myapplication.network.request.LogRequestBody;
import com.wandao.myapplication.network.response.Response;
import com.wandao.myapplication.network.schedulers.SchedulerProvider;
import com.wandao.myapplication.presenter.Presenter;
import com.wandao.myapplication.service.IRemoteService;
import com.wandao.myapplication.utils.DbUtils;
import com.wandao.myapplication.utils.DoorUtils;
import com.wandao.myapplication.utils.NetUtils;
import com.wrbug.editspinner.EditSpinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrgentOpenActivity extends BaseActivity implements AdapterView.OnItemClickListener, Contract.View {
    private GridView mGridView;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> dataList;
    private int boxTotalNumber;
    private static IRemoteService boxService; //获取当前锁控服务
    private long currentUserID;
    private List<User> users=new ArrayList<>();
    String[] from = {"userDoorNumber", "UserName","UserID"};
    int[] to = {R.id.box_number, R.id.box_user_name,R.id.box_user_id};
    private View popRootView;         //迟到早退弹窗
    private PopupWindow popupWindow;
    private EditSpinner editSpinner;
    private static LogRequestBody logRequestBody = new LogRequestBody();
    private Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.activity_urgent_open);
        initView();
        Intent intent = getIntent();
         currentUserID = intent.getLongExtra("currentUserID",0);
        presenter = new Presenter(new Model(), this, SchedulerProvider.getInstance());
         Log.d("currentUserID",currentUserID+"");
    }
    void  initView(){
        TextView view=(TextView) findViewById(R.id.title);
        view.setText("紧急处理");
        mGridView=findViewById(R.id.urgent_gridview);
        adapter = new SimpleAdapter(this, dataList, R.layout.urgent_gridview_item, from, to);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(this);
    }
    void initData() {
        boxService = MyApplication.mService;
        boxTotalNumber= MyApplication.doorNumber;//初始化柜门数量
        dataList = new ArrayList<Map<String, Object>>();
        users = DbUtils.queryAllUserWithoutAdmin();
        for (int i = 0; i < users.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userDoorNumber", users.get(i).getBoxId());
            map.put("UserName",users.get(i).getName() );
            map.put("UserID",users.get(i).getId());
            dataList.add(map);
        }
    }

    public void onBackClick(View view)
    {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showLateWindow(position);
        Log.d("item",position+"    "+id);
        Toast.makeText(this,position+"    "+id,Toast.LENGTH_LONG).show();

        try {
                     //获取上一个Activity传过来的值
  //          boxService.lockControlOpenDoor(Byte.valueOf(users.get(position).getBoxId()+""), Byte.valueOf(0 + ""));
//            if (users.get(position).getBoxId()<=20)
//                boxService.lockControlOpenDoor(Byte.valueOf(users.get(position).getBoxId() + ""), Byte.valueOf(0 + ""));
//            if (users.get(position).getBoxId()>20)
//                boxService.lockControlOpenDoor(Byte.valueOf((users.get(position).getBoxId()-20) + ""), Byte.valueOf(15 + ""));


            DoorUtils.getSingleton().openDoor(users.get(position).getBoxId());   //银华开门方式


            logRequestBody.setBoxIp(NetUtils.getLocalIPAddress().toString().split("/")[1] + "");
            logRequestBody.setLogID("2019093001");
            logRequestBody.setDoorID(users.get(position).getBoxId() + "");
            logRequestBody.setUserID(users.get(position).getId() + "");
            logRequestBody.setUserName(users.get(position).getName());
            logRequestBody.setDepsName(users.get(position).getDepartment());
            logRequestBody.setStatus("3");
            logRequestBody.setTime(new Date().getTime());
            logRequestBody.setActionUserName(DbUtils.getInstance().queryUser(currentUserID).getName());
            presenter.putLogRequest(logRequestBody);
            //开门指令
     //       DbUtils.storageLog(3, new Date(),users.get(position).getId());
            DbUtils.storageLogUrgent(3, new Date(),users.get(position).getId(),DbUtils.getInstance().queryUser(currentUserID).getName());
      //S      DbUtils.storageLog(3,new Date(),currentUserID)
            DbUtils.changeBoxStatus(0,DbUtils.queryBox(users.get(position).getBoxId()),new  Date().getTime());            //柜门状态改变，标记为空
        } catch (Exception e) {
            Log.d("UrgentOpen",e.toString());
            e.printStackTrace();
        }
    }

    private void initSpinner() {
        List<String> list = new ArrayList<>();
        list.add("外出");
        list.add("请假");
        list.add("出差");
        editSpinner.setItemData(list);
        editSpinner.setMaxLine(3);
        editSpinner.setItemData(list);
        editSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("EditSpinner", "item " + position + " click");

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    //显示迟到界面
    private void showLateWindow(int position) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UrgentOpenActivity.this);
        builder.setIcon(R.mipmap.main_setting);
        builder.setTitle("请选择早取理由");
        builder.setCancelable(false);
        View view2 = LayoutInflater.from(UrgentOpenActivity.this).inflate(R.layout.late_popup_window, null);
        builder.setView(view2);
//        final EditText password = (EditText) view2.findViewById(R.id.password);
        editSpinner = (EditSpinner) view2.findViewById(R.id.edit_spinner_late);
        List<String> list = new ArrayList<>();
        list.add("请假");
        list.add("出差");
        list.add("开会");
        list.add("外出");
        editSpinner.setItemData(list);
        editSpinner.setMaxLine(3);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editSpinner.getText() != null)
                    DbUtils.storageExceptionLog(0, new Date(), users.get(position).getId(), editSpinner.getText());    //记录晚存
                else if (editSpinner.getText() == null) {
                    DbUtils.storageExceptionLog(0, new Date(), users.get(position).getId(), "未输入早取理由");    //记录晚存

                }
            }
        });
        if (!isFinishing()) {
            builder.show();
        }
    }

    @Override
    public void getDataSuccess(Response<String> msg) {

    }

    @Override
    public void getDataFail(String s) {

    }
}
