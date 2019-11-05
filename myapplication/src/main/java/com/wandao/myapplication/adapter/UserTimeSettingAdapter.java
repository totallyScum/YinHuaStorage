package com.wandao.myapplication.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.wandao.myapplication.R;
import com.wandao.myapplication.activity.MyApplication;
import com.wandao.myapplication.greendao.User;
import com.wandao.myapplication.greendao.UserPersonalTime;
import com.wandao.myapplication.utils.DbUtils;

import java.text.SimpleDateFormat;
import java.util.List;

public class UserTimeSettingAdapter extends BaseAdapter {
    private String[] from;
    private int[] to;
    private Context mContext;
    private List<User> users;
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    private TimePicker timeStartAM;
    private TimePicker timeEndAM;
    private TimePicker timeStartPM;
    private TimePicker timeEndPM;
    private TextView timeConfirm;
    private int selectPosition;

    public UserTimeSettingAdapter(String[] from, int[] to, Context mContext, List<User> users) {
        this.from = from;
        this.to = to;
        this.mContext = mContext;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users == null ? 0 : users.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(mContext).inflate(R.layout.time_gridview_item, parent, false);
        TextView name = convertView.findViewById(R.id.time_user_name);
        TextView id = convertView.findViewById(R.id.time_user_id);
        TextView startTimeAM = convertView.findViewById(R.id.time_start_time_am);
        TextView endTimeAM = convertView.findViewById(R.id.time_end_time_am);
        TextView startTimePM = convertView.findViewById(R.id.time_start_time_pm);
        TextView endTimePM = convertView.findViewById(R.id.time_end_time_pm);
        UserPersonalTime upt = DbUtils.getInstance().queryUserPersonalTime(users.get(position).getId());
        if (upt != null) {
            name.setText(users.get(position).getName());
            id.setText(users.get(position).getId() + "");
            startTimeAM.setText(upt.getStartTimeAM());
            endTimeAM.setText(upt.getEndTimeAM());
            startTimePM.setText(upt.getStartTimePM());
            endTimePM.setText(upt.getEndTimePM());
        } else {
            name.setText(users.get(position).getName());
            id.setText(users.get(position).getId() + "");
            startTimeAM.setText(formatter.format(MyApplication.startTimeAM));
            endTimeAM.setText(formatter.format(MyApplication.endTimeAM));
            startTimePM.setText(formatter.format(MyApplication.startTimePM));
            endTimePM.setText(formatter.format(MyApplication.endTimePM));
        }


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("BoxInfo_6666", "cccccc");
                selectPosition = position;
                android.app.AlertDialog.Builder setTimeDialog = new android.app.AlertDialog.Builder(mContext, R.layout.time_select_dialog);
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialog);
                //通过setView设置我们自己的布局
                final View dialogView = LayoutInflater.from(mContext).inflate(R.layout.time_select_dialog, null);
                initDialogView(dialogView);
                UserPersonalTime upt = DbUtils.getInstance().queryUserPersonalTime(users.get(position).getId());
                if (upt == null) {
                    timeStartAM.setHour(MyApplication.startTimeAM.getHours());
                    timeStartAM.setMinute(MyApplication.startTimeAM.getMinutes());
                    timeEndAM.setHour(MyApplication.endTimeAM.getHours());
                    timeEndAM.setMinute(MyApplication.endTimeAM.getMinutes());
                    timeStartPM.setHour(MyApplication.startTimePM.getHours());
                    timeStartPM.setMinute(MyApplication.startTimePM.getMinutes());
                    timeEndPM.setHour(MyApplication.endTimePM.getHours());
                    timeEndPM.setMinute(MyApplication.endTimePM.getMinutes());
                } else {

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
                final AlertDialog dialog = builder.create();
                dialog.show();
                timeConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DbUtils.getInstance().insertUserSelfTime(users.get(selectPosition).getId(), timeStartAM.getHour() + ":" + montageTime(timeStartAM.getMinute()), timeEndAM.getHour() + ":" + montageTime(timeEndAM.getMinute()), timeStartPM.getHour() + ":" + montageTime(timeStartPM.getMinute()), timeEndPM.getHour() + ":" + montageTime(timeEndPM.getMinute()));
                        dialog.dismiss();
                        notifyDataSetChanged();
                    }
                });
                //此处设置位置窗体大小
                dialog.getWindow().setLayout(1000, 550);
            }
        });
        return convertView;
    }

    public void initDialogView(View view) {
        timeStartAM = (TimePicker) view.findViewById(R.id.start_dialog_am);
        timeEndAM = (TimePicker) view.findViewById(R.id.end_dialog_am);
        timeStartPM = (TimePicker) view.findViewById(R.id.start_dialog_pm);
        timeEndPM = (TimePicker) view.findViewById(R.id.end_dialog_pm);
        timeConfirm = (TextView) view.findViewById(R.id.time_confirm);
        timeStartAM.setIs24HourView(true);
        timeEndAM.setIs24HourView(true);
        timeStartPM.setIs24HourView(true);
        timeEndPM.setIs24HourView(true);
    }

    public String montageTime(int time) {
        if (time == 0)
            return "00";
        if (time < 10)
            return "0" + time;
        return time + "";
    }

}


