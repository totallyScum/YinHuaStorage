package com.wandao.myapplication.ui.menu;

import android.annotation.SuppressLint;
import android.app.ZysjSystemManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.wandao.myapplication.UserTimeSettingFragment;
import com.wandao.myapplication.fragment.DepartmentFragment;
import com.wandao.myapplication.fragment.LogcatFragment;
import com.wandao.myapplication.R;
import com.wandao.myapplication.fragment.StaffManagementFragment;
import com.wandao.myapplication.fragment.SystemFragment;
import com.wandao.myapplication.activity.LicenseActivity;
import com.wandao.myapplication.activity.UrgentOpenNoIdentificationFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuFragment extends Fragment implements  AdapterView.OnItemClickListener {
    ListView mListView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter;
    private TextView btnBack;
    private TextView ip;
    String[] from = {"img", "text"};
    public ZysjSystemManager SystemManager;
    int[] to = {R.id.setting_img, R.id.setting_text};
    private MenuViewModel mViewModel;
    private android.support.v7.widget.Toolbar toolbar;
    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.menu_fragment, container, false);
        return view;

    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
        initData();
        mListView = (ListView) getActivity().findViewById(R.id.menu_listview);
        adapter = new SimpleAdapter(getContext(), dataList, R.layout.setting_list_item, from, to);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
        initToolbar();


        SystemManager = (ZysjSystemManager) getActivity().getSystemService("zysj");


        // TODO: Use the ViewModel
    }

    void initToolbar() {
        toolbar= (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("开始菜单");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitleMarginStart(500);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setTitleTextColor(0xFFFFFFFF);

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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i)
        {
            case 0:{
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, SystemFragment.newInstance())
                        .commitNow();
                break;
            }

        case 1:{
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DepartmentFragment.newInstance())
                    .commitNow();
            break;
        }
            case 2:{
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, StaffManagementFragment.newInstance())
                        .commitNow();
                break;
            }
            case 3:{
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, LogcatFragment.newInstance())
                        .commitNow();
                break;
            }
            case 4:{
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, UrgentOpenNoIdentificationFragment.newInstance())
                        .commitNow();
                break;
            }
            case 5:{
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, UserTimeSettingFragment.newInstance())
                        .commitNow();
                break;
            }
            case 6:{         //退出到主界面
                Intent intent = new Intent(getContext(), LicenseActivity.class);
                startActivity(intent);
                break;
            }
            case 7:{        //退出到桌面
                SystemManager.ZYSystemBar(1);


                Intent intent = new Intent();
// 为Intent设置Action、Category属性
                intent.setAction(Intent.ACTION_MAIN);// "android.intent.action.MAIN"
                intent.addCategory(Intent.CATEGORY_HOME); //"android.intent.category.HOME"
                startActivity(intent);
                break;
            }
        }

//        if (i==2) {
//            Intent intent = new Intent(getApplicationContext(), StaffManagementActivity.class);
//            startActivity(intent);
//        }
//        if (i==3) {
//            Intent intent = new Intent(getApplicationContext(), LogcatActivity.class);
//            startActivity(intent);
//        }
//        if (i==4) {
//            Intent intent = new Intent(getApplicationContext(), UrgentOpenNoIdentificationActivity.class);
//            startActivity(intent);
//        }
//        if (i==5)
//        {
//            Intent intent = new Intent(getApplicationContext(), UserTimeSettingActivity.class);
//            startActivity(intent);
//        }
////        if (position==5) {
////            Intent intent = new Intent(getApplicationContext(), LockScreenActivity.class);
////            startActivity(intent);
////        }
//        if (i==6) {
//            Intent intent = new Intent(getApplicationContext(), LicenseActivity.class);
//            startActivity(intent);
//        }
//        if (i==7) {
//            SystemManager.ZYSystemBar(1);
//            Intent intent = new Intent();
//// 为Intent设置Action、Category属性
//            intent.setAction(Intent.ACTION_MAIN);// "android.intent.action.MAIN"
//            intent.addCategory(Intent.CATEGORY_HOME); //"android.intent.category.HOME"
//            startActivity(intent);
//        }




    }
}
