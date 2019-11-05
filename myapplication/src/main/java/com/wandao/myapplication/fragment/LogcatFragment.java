package com.wandao.myapplication.fragment;

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
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wandao.myapplication.R;
import com.wandao.myapplication.ui.menu.MenuFragment;
import com.wandao.myapplication.utils.ExcelUtil;
import com.wandao.myapplication.view.CustomDatePicker;
import com.wandao.myapplication.view.DateFormatUtils;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LogcatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LogcatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogcatFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView mStartTime;
    private TextView mEndTime;
    private TextView mPickTime;
    private CustomDatePicker mTimerPicker;
    private CustomDatePicker mStartTimerPicker;
    private CustomDatePicker mEndTimerPicker;
    private CustomDatePicker mDatePicker;
    private Button exportButton;        //导出到本地src下
    private Button openButton;
    private Button exportMailButton;      //通过邮件导出
    private TextView textView;
    private LinearLayout pickTime;
    private LinearLayout pickDate;
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
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private android.support.v7.widget.Toolbar toolbar;
    private OnFragmentInteractionListener mListener;
    private NumberPicker weekPicker;
    public LogcatFragment() {
        // Required empty public constructor
    }

    public static LogcatFragment newInstance() {
        
        Bundle args = new Bundle();
        
        LogcatFragment fragment = new LogcatFragment();
        fragment.setArguments(args);
        return fragment;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogcatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogcatFragment newInstance(String param1, String param2) {
        LogcatFragment fragment = new LogcatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logcat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initActivity();
        initRadioButton();


    }
    public void initRadioButton(){
        radioButton = getActivity().findViewById(R.id.diy_log);
        right_group = getActivity().findViewById(R.id.right_group);
        exportMailButton=(Button) getActivity().findViewById(R.id.export_email);
//        exportMailButton=(Button) getActivity().findViewById(R.id.diy_month_log);



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
                    case R.id.meta_log:

                        nowPick=4;
                        break;
                    case R.id.diy_week_log:
                        nowPick=5;
                        break;
                    case R.id.diy_day_log:
                        nowPick=6;
                        break;

                }

                if (i == R.id.diy_log||i == R.id.meta_log)              //当选择自定义记录时，显示时间选择控件
                {

                    pickTime.setVisibility(View.VISIBLE);
                } else {
                    pickTime.setVisibility(View.INVISIBLE);
                }
                if (i == R.id.diy_week_log)              //当选择自定义记录时，显示时间选择控件
                {
                    weekPicker.setMaxValue(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));
                    //设置最小值
                    weekPicker.setMinValue(1);
                    //设置当前值
                    weekPicker.setValue(1);
                    weekPicker.setVisibility(View.VISIBLE);
                } else {
                    weekPicker.setVisibility(View.INVISIBLE);
                }


                if (i == R.id.diy_day_log)              //当选择自定义记录时，显示时间选择控件
                {
                    pickDate.setVisibility(View.VISIBLE);
                } else {
                    pickDate.setVisibility(View.INVISIBLE);
                }



            }
        });



        requestPermission();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        initActivity();
        initStartTimerPicker(mStartTime);
        initEndTimerPicker(mEndTime);
        initPickDaterPicker(mPickTime);
    }

    public void initToolbar(){
        toolbar= (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("打印日志");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitleMarginStart(440);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(0xFFFFFFFF);
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public void initActivity() {
        exportButton = getActivity().findViewById(R.id.export_excel);
        exportButton.setOnClickListener(this);
        openButton = getActivity().findViewById(R.id.open_button);
        openButton.setOnClickListener(this);
        textView = getActivity().findViewById(R.id.textView);
        mStartTime = getActivity().findViewById(R.id.tv_selected_time_1);
        mEndTime = getActivity().findViewById(R.id.tv_selected_time_2);
        mPickTime = getActivity().findViewById(R.id.tv_selected_time_3);
        pickTime = getActivity().findViewById(R.id.diy_time);
        pickDate = getActivity().findViewById(R.id.day_time_picker);






        weekPicker=getActivity().findViewById(R.id.week_picker);

        pickTime.setVisibility(View.INVISIBLE);
        weekPicker.setVisibility(View.INVISIBLE);
        pickDate.setVisibility(View.INVISIBLE);


        email=(EditText) getActivity().findViewById(R.id.input_email);
        getActivity().findViewById(R.id.start_time).setOnClickListener(this);
        getActivity().findViewById(R.id.end_time).setOnClickListener(this);
        getActivity().findViewById(R.id.day_time_picker).setOnClickListener(this);




        getActivity().findViewById(R.id.show_time).setOnClickListener(this);
        getActivity().findViewById(R.id.export_email).setOnClickListener(this);
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT > 23) {
            if (ContextCompat.checkSelfPermission(getActivity(),
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("permission")
                        .setMessage("点击允许才可以使用我们的app哦")
                        .setPositiveButton("去允许", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                if (alertDialog != null && alertDialog.isShowing()) {
                                    alertDialog.dismiss();
                                }
                                ActivityCompat.requestPermissions(getActivity(),
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
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.export_excel:
                exportExcel(getContext());
                break;
            case R.id.start_time:       //查询开始时间
                // 日期格式为yyyy-MM-dd HH:mm
                mStartTimerPicker.show(mStartTime.getText().toString());
                break;
            case R.id.end_time: //查询结束时间
                // 日期格式为yyyy-MM-dd HH:mm
                mEndTimerPicker.show(mEndTime.getText().toString());
                break;
            case R.id.day_time_picker:
                mDatePicker.show(mPickTime.getText().toString());
                break;
            case R.id.open_button:
                openDir();
                break;
            case R.id.export_email:
             //   sendMailMy();
                break;
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
            Toast.makeText(getActivity(), "没有正确打开文件管理器", Toast.LENGTH_SHORT).show();
        }
    }
        private void exportExcel (Context context){


            switch (nowPick) {
                case 0:
                    ExcelUtil.exportWeekExcel(context, fileWeekPath);
                    break;
                case 1:
                    ExcelUtil.exportMonthExcel(context, fileMonthPath);
                    break;
                case 2:
                    ExcelUtil.exportDayExcel(context, fileDayPath);
                    break;
                case 3:
                    ExcelUtil.exportDiyExcel(context, DateFormatUtils.str2Long(mStartTime.getText().toString(), true), DateFormatUtils.str2Long(mEndTime.getText().toString(), true));
                    break;
                case  4:
                    ExcelUtil.exportMetaExcel(context,DateFormatUtils.str2Long(mStartTime.getText().toString(),true),DateFormatUtils.str2Long(mEndTime.getText().toString(),true));
                    Log.d("Time_my",DateFormatUtils.str2Long(mStartTime.getText().toString(),true)+"");
                    Log.d("Time_my",DateFormatUtils.str2Long(mEndTime.getText().toString(),true)+"");
                    textView.setText("元 excel已导出至：" + filePath+ "元数据");
                    break;
                case 5:
                    ExcelUtil.exportDiyWeekExcel(context,weekPicker.getValue());
                    textView.setText("WEEK excel已导出至：" + filePath+ "周数据");
                    break;
                case 6:
                    ExcelUtil.exportDiyDayExcel(context,DateFormatUtils.str2Long(mPickTime                                                                                                                       .getText().toString(),true));
                    textView.setText("DAY excel已导出至：" + filePath+ "日数据");
                    break;

            }


        }
        private void initTimerPicker(final TextView mTextview) {
            String beginTime = "2018-1-1 18:00";
            String endTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);

            mTextview.setText(endTime);

            // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
            mTimerPicker = new CustomDatePicker(getActivity(), new CustomDatePicker.Callback() {
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
    private void initStartTimerPicker(final TextView mTextview) {
        String beginTime = "2018-1-1 18:00";
        String endTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);

        mTextview.setText(beginTime);

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mStartTimerPicker = new CustomDatePicker(getActivity(), new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mTextview.setText(DateFormatUtils.long2Str(timestamp, true));
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mStartTimerPicker.setCancelable(true);
        // 显示时和分
        mStartTimerPicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        mStartTimerPicker.setScrollLoop(true);
        // 允许滚动动画
        mStartTimerPicker.setCanShowAnim(true);
    }
    private void initEndTimerPicker(final TextView mTextview) {
        String beginTime = "2019-1-1 18:00";
        String endTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);

        mTextview.setText(endTime);

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mEndTimerPicker = new CustomDatePicker(getActivity(), new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mTextview.setText(DateFormatUtils.long2Str(timestamp, true));
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mEndTimerPicker.setCancelable(true);
        // 显示时和分
        mEndTimerPicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        mEndTimerPicker.setScrollLoop(true);
        // 允许滚动动画
        mEndTimerPicker.setCanShowAnim(true);
    }

    private void initPickDaterPicker(final TextView mTextview) {
        String beginTime = "2019-1-1 18:00";
        String endTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);

        mTextview.setText(endTime);

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mDatePicker = new CustomDatePicker(getActivity(), new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mTextview.setText(DateFormatUtils.long2Str(timestamp, true));
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mDatePicker.setCancelable(true);
        // 显示时和分
        mDatePicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        mDatePicker.setScrollLoop(true);
        // 允许滚动动画
        mDatePicker.setCanShowAnim(true);
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
