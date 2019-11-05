package com.wandao.myapplication.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.wandao.myapplication.R;
import com.wandao.myapplication.activity.MyApplication;
import com.wandao.myapplication.greendao.User;
import com.wandao.myapplication.ui.menu.MenuFragment;
import com.wandao.myapplication.utils.DbUtils;
import com.wandao.myapplication.utils.SPUtils;
import com.wandao.myapplication.view.CustomDatePicker;
import com.wandao.myapplication.view.DateFormatUtils;

import java.util.ArrayList;
import java.util.List;

import static com.serenegiant.utils.UIThreadHelper.runOnUiThread;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SystemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SystemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SystemFragment extends Fragment  implements  View.OnClickListener {
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
    private Button submitAdmin;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private android.support.v7.widget.Toolbar toolbar;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SystemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SystemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SystemFragment newInstance(String param1, String param2) {
        SystemFragment fragment = new SystemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        mTvSelectedTime = getActivity().findViewById(R.id.tv_selected_time);
        mNewPassword = getActivity().findViewById(R.id.new_password);
        mOldPassword = getActivity().findViewById(R.id.old_password);
        mCheckPassword = getActivity().findViewById(R.id.new_password_check);

        startTimeAM=getActivity().findViewById(R.id.system_start_time_am);
        endTimeAM=getActivity().findViewById(R.id.system_end_time_am);
        startTimePM=getActivity().findViewById(R.id.system_start_time_pm);
        endTimePM=getActivity().findViewById(R.id.system_end_time_pm);

        adminID=getActivity().findViewById(R.id.admin_id);
        hintText=getActivity().findViewById(R.id.hint_text);
        adminListView=getActivity().findViewById(R.id.admin_listview);
        hintPassword =getActivity().findViewById(R.id.password_hint);

        email=getActivity().findViewById(R.id.email);
        confirmEmail=getActivity().findViewById(R.id.confirm_email);
        hintEmail=getActivity().findViewById(R.id.hint_email);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, adminList);
        adminListView.setAdapter(adapter);
        settingTime=(Button) getActivity().findViewById(R.id.set_time);
        settingTime.setOnClickListener(this);
        hintTimeStatus=getActivity().findViewById(R.id.hint_time_status);
        submitAdmin=getActivity().findViewById(R.id.submit_admin);
        adminListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//             dListview.getSelectedItem().toString();
                showListDialog(position);

            }
        });
        confrim=getActivity().findViewById(R.id.confirm);
        confrim.setOnClickListener(this);
        submitAdmin.setOnClickListener(this);
    }
    public void showListDialog(final int pos) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final android.app.AlertDialog.Builder normalDialog =
                new android.app.AlertDialog.Builder(getActivity());
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
        mTimerPicker = new CustomDatePicker(getContext(), new CustomDatePicker.Callback() {
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static SystemFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SystemFragment fragment = new SystemFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_system, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initToolbar();
        initTimerPicker();
        initStartandEndTime();
        password= (String) SPUtils.get(getContext(),"password","");
    }
private  void initToolbar(){
    toolbar= (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
    toolbar.setTitle("系统设置");
    ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    toolbar.setTitleTextColor(0xFFFFFFFF);
    toolbar.setTitleMarginStart(440);
    toolbar.setNavigationIcon(R.mipmap.ic_back);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //       getActivity().onBackPressed();//销毁自己
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MenuFragment.newInstance())
                    .commitNow();
        }
    });
}
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.ll_date:
//                // 日期格式为yyyy-MM-dd
//                mDatePicker.show(mTvSelectedDate.getText().toString());
//                break;
            case R.id.set_time:{
                if (endTimeAM.getHour()>startTimeAM.getHour()&&endTimePM.getHour()>startTimePM.getHour()) {
                    SPUtils.put(getContext(), "endTimeHourAM", endTimeAM.getHour());
                    SPUtils.put(getContext(), "endTimeMinuteAM", endTimeAM.getMinute());
                    SPUtils.put(getContext(), "startTimeHourAM", startTimeAM.getHour());
                    SPUtils.put(getContext(), "startTimeMinuteAM", startTimeAM.getMinute());


                    SPUtils.put(getContext(), "endTimeHourPM", endTimePM.getHour());
                    SPUtils.put(getContext(), "endTimeMinutePM", endTimePM.getMinute());
                    SPUtils.put(getContext(), "startTimeHourPM", startTimePM.getHour());
                    SPUtils.put(getContext(), "startTimeMinutePM", startTimePM.getMinute());


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
                    SPUtils.put(getContext(),"password",newPassword);
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
                Toast.makeText(getContext(), isValuePassword(oldPassword, checkPassword, oldPassword), Toast.LENGTH_LONG).show();
                ;
                break;
            }
            case R.id.confirm_email:{
                SPUtils.put(getContext(), "email", email.getText().toString());
                hintEmail.setText("邮箱设置为:"+email.getText().toString());
                hintEmail.setTextColor(Color.GREEN);
                break;
            }
            case R.id.submit_admin:{
                submitAdmin();
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



    public void submitAdmin() {
        String id = adminID.getText().toString();
        if (id .equals("")) {
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
                DbUtils.getInstance().addAdmin(Integer.parseInt(id));    //设置超级管理员权限
                adapter.notifyDataSetChanged();
                hintText.setText("用户添加成功");
                hintText.setTextColor(getResources().getColor(R.color.colorPrimary));
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
