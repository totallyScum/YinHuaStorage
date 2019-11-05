package com.wandao.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.baidu.idl.sample.ui.BaseActivity;
import com.wandao.myapplication.R;
import com.wandao.myapplication.greendao.User;
import com.wandao.myapplication.greendao.UserPersonalTime;
import com.wandao.myapplication.service.IRemoteService;
import com.wandao.myapplication.utils.DbUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserTimeSettingActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private GridView mGridView;
    private SimpleAdapter adapter;
    private MyAdapter myAdapter;
    private List<Map<String, Object>> dataList;
    private int boxTotalNumber;
    private static IRemoteService boxService; //获取当前锁控服务
    private long currentUserID;
    private int selectPosition;
    private List<User> users = new ArrayList<>();
    private TimePicker timeStartAM;
    private TimePicker timeEndAM;
    private TimePicker timeStartPM;
    private TimePicker timeEndPM;
    private TextView timeConfirm;
    String[] from = {"userName", "UserID", "UserStartTimeAM", "userEndTimeAM", "UserStartTimePM", "userEndTimePM"};
    int[] to = {R.id.time_user_name, R.id.time_user_id, R.id.time_start_time_am, R.id.time_end_time_am, R.id.time_start_time_pm, R.id.time_end_time_pm};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_time_setting);
        initData();
        initView();
    }

    void initView() {
        TextView view = (TextView) findViewById(R.id.title);
        view.setText("员工开门时间单独设定");
        mGridView = findViewById(R.id.time_setting_gridView);
        myAdapter=new MyAdapter(from,to,this);
      //  adapter = new SimpleAdapter(this, dataList, R.layout.time_gridview_item, from, to);
        mGridView.setAdapter(myAdapter);
        mGridView.setOnItemClickListener(this);
//        timeStartAM.setIs24HourView(true);
//        timeEndAM.setIs24HourView(true);
//        timeStartPM.setIs24HourView(true);
//        timeEndPM.setIs24HourView(true);
    }

    void initData() {
        users = DbUtils.queryAllUserWithoutAdmin();
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < users.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userName", users.get(i).getBoxId());
            map.put("UserID", users.get(i).getName());
            map.put("UserStartTimeAM", "9:00");
            map.put("UserEndTimeAM", "11:30");
            map.put("UserStartTimePM", "13:00");
            map.put("UserEndTimePM", "16:00");
            dataList.add(map);
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectPosition=position;
        android.app.AlertDialog.Builder setTimeDialog = new android.app.AlertDialog.Builder(this,R.layout.time_select_dialog);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialog);
        //通过setView设置我们自己的布局
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.time_select_dialog, null);
                initDialogView(dialogView);
        UserPersonalTime upt=DbUtils.getInstance().queryUserPersonalTime(users.get(position).getId());
        if (upt==null)
        {
            timeStartAM.setHour(MyApplication.startTimeAM.getHours());
            timeStartAM.setMinute(MyApplication.startTimeAM.getMinutes());
            timeEndAM.setHour(MyApplication.endTimeAM.getHours());
            timeEndAM.setMinute(MyApplication.endTimeAM.getMinutes());
            timeStartPM.setHour(MyApplication.startTimePM.getHours());
            timeStartPM.setMinute(MyApplication.startTimePM.getMinutes());
            timeEndPM.setHour(MyApplication.endTimePM.getHours());
            timeEndPM.setMinute(MyApplication.endTimePM.getMinutes());
        }
        else {

            timeStartAM.setHour(Integer.parseInt(upt.getStartTimeAM().split(":")[0]));
            timeStartAM.setMinute(Integer.parseInt(upt.getStartTimeAM().split(":")[1]));
            timeEndAM.setHour(Integer.parseInt(upt.getEndTimeAM().split(":")[0]));
            timeEndAM.setMinute(Integer.parseInt(upt.getEndTimeAM().split(":")[1]));
            timeStartPM.setHour(Integer.parseInt(upt.getStartTimePM().split(":")[0]));
            timeStartPM.setMinute(Integer.parseInt(upt.getStartTimePM().split(":")[1]));
            timeEndPM.setHour(Integer.parseInt(upt.getEndTimePM().split(":")[0]));
            timeEndPM.setMinute(Integer.parseInt(upt.getEndTimePM().split(":")[1]));
        }

        builder.setView(dialogView);
        final AlertDialog dialog =builder.create();
        dialog.show();
                timeConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbUtils.getInstance().insertUserSelfTime(users.get(selectPosition).getId(),timeStartAM.getHour()+":"+montageTime(timeStartAM.getMinute()),timeEndAM.getHour()+":"+montageTime(timeEndAM.getMinute()) ,timeStartPM.getHour()+":"+montageTime(timeStartPM.getMinute()),timeEndPM.getHour()+":"+montageTime(timeEndPM.getMinute()));
                dialog.dismiss();
                myAdapter.notifyDataSetChanged();
            }
        });
        //此处设置位置窗体大小
        dialog.getWindow().setLayout(1000, 550);

//
//
//        //获取界面
//        View dialogView = LayoutInflater.from(this).inflate(R.layout.time_select_dialog, null);
//        UserPersonalTime upt=DbUtils.getInstance().queryUserPersonalTime(users.get(position).getId());
//        initDialogView(dialogView);
//        if (upt==null)
//        {
//            timeStartAM.setHour(9);
//            timeStartAM.setMinute(0);
//            timeEndAM.setHour(11);
//            timeEndAM.setMinute(30);
//            timeStartPM.setHour(0);
//            timeStartPM.setMinute(30);
//            timeEndPM.setHour(16);
//            timeEndPM.setMinute(0);
//        }
//        else {
//
//            timeStartAM.setHour(Integer.parseInt(upt.getStartTimeAM().split(":")[0]));
//            timeStartAM.setMinute(Integer.parseInt(upt.getStartTimeAM().split(":")[1]));
//            timeEndAM.setHour(Integer.parseInt(upt.getEndTimeAM().split(":")[0]));
//            timeEndAM.setMinute(Integer.parseInt(upt.getEndTimeAM().split(":")[1]));
//            timeStartPM.setHour(Integer.parseInt(upt.getStartTimePM().split(":")[0]));
//            timeStartPM.setMinute(Integer.parseInt(upt.getStartTimePM().split(":")[1]));
//            timeEndPM.setHour(Integer.parseInt(upt.getEndTimePM().split(":")[0]));
//            timeEndPM.setMinute(Integer.parseInt(upt.getEndTimePM().split(":")[1]));
//        }
//        //将界面填充到AlertDiaLog容器
//        setTimeDialog.setView(dialogView);
//        setTimeDialog.show();
    }
    public void initDialogView(View view){
        timeStartAM=(TimePicker) view.findViewById(R.id.start_dialog_am);
        timeEndAM=(TimePicker) view.findViewById(R.id.end_dialog_am);
        timeStartPM=(TimePicker) view.findViewById(R.id.start_dialog_pm);
        timeEndPM=(TimePicker) view.findViewById(R.id.end_dialog_pm);
        timeConfirm=(TextView)view.findViewById(R.id.time_confirm);
        timeStartAM.setIs24HourView(true);
        timeEndAM.setIs24HourView(true);
        timeStartPM.setIs24HourView(true);
        timeEndPM.setIs24HourView(true);
    };
    public String montageTime(int time)
    {
        if (time==0)
            return "00";
        if (time<10)
            return "0"+time;
        return time+"";
    }
    public class MyAdapter extends BaseAdapter {
        private String[] from;
        private int[] to;
        private Context mContext;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        public MyAdapter(String[] from, int[] to, Context mContext) {
            this.from = from;
            this.to = to;
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
          return users == null?0:users.size();
        }

        @Override
        public Object getItem(int position) {
            return users.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.time_gridview_item,parent,false);
            TextView name=convertView.findViewById(R.id.time_user_name);
            TextView id=convertView.findViewById(R.id.time_user_id);
            TextView startTimeAM=convertView.findViewById(R.id.time_start_time_am);
            TextView endTimeAM=convertView.findViewById(R.id.time_end_time_am);
            TextView startTimePM=convertView.findViewById(R.id.time_start_time_pm);
            TextView endTimePM=convertView.findViewById(R.id.time_end_time_pm);
            UserPersonalTime upt=DbUtils.getInstance().queryUserPersonalTime(users.get(position).getId());
            if (upt!=null) {
                name.setText(users.get(position).getName());
                id.setText(users.get(position).getId()+"");
                startTimeAM.setText(upt.getStartTimeAM());
                endTimeAM.setText(upt.getEndTimeAM());
                startTimePM.setText(upt.getStartTimePM());
                endTimePM.setText(upt.getEndTimePM());
            }else {
                name.setText(users.get(position).getName());
                id.setText(users.get(position).getId()+"");
                startTimeAM.setText(formatter.format(MyApplication.startTimeAM));
                endTimeAM.setText(formatter.format(MyApplication.endTimeAM));
                startTimePM.setText(formatter.format(MyApplication.startTimePM));
                endTimePM.setText(formatter.format(MyApplication.endTimePM));
            }
            return convertView;
        }
    }
    public void onBackClick(View view)
    {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
