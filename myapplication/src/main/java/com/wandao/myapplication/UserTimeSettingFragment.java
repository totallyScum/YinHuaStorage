package com.wandao.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.wandao.myapplication.activity.MyApplication;
import com.wandao.myapplication.activity.UserTimeSettingActivity;
import com.wandao.myapplication.adapter.UserTimeSettingAdapter;
import com.wandao.myapplication.fragment.StaffManagementFragment;
import com.wandao.myapplication.greendao.User;
import com.wandao.myapplication.greendao.UserPersonalTime;
import com.wandao.myapplication.service.IRemoteService;
import com.wandao.myapplication.ui.menu.MenuFragment;
import com.wandao.myapplication.utils.DbUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserTimeSettingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserTimeSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserTimeSettingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GridView mGridView;
    private SimpleAdapter adapter;
    private UserTimeSettingAdapter myAdapter;
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
    private Toolbar toolbar;
    String[] from = {"userName", "UserID", "UserStartTimeAM", "userEndTimeAM", "UserStartTimePM", "userEndTimePM"};
    int[] to = {R.id.time_user_name, R.id.time_user_id, R.id.time_start_time_am, R.id.time_end_time_am, R.id.time_start_time_pm, R.id.time_end_time_pm};


    private OnFragmentInteractionListener mListener;

    public UserTimeSettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserTimeSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserTimeSettingFragment newInstance(String param1, String param2) {
        UserTimeSettingFragment fragment = new UserTimeSettingFragment();
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
        return inflater.inflate(R.layout.fragment_user_time_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
        initToolbar();
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

    void initView() {
        mGridView = getActivity().findViewById(R.id.fragment_time_setting_gridView);
        myAdapter = new UserTimeSettingAdapter(from, to, getContext(), users);
        //  adapter = new SimpleAdapter(this, dataList, R.layout.time_gridview_item, from, to);
        mGridView.setAdapter(myAdapter);
//        mGridView.setOnItemClickListener(this);
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

    public static UserTimeSettingFragment newInstance() {

        Bundle args = new Bundle();

        UserTimeSettingFragment fragment = new UserTimeSettingFragment();
        fragment.setArguments(args);
        return fragment;


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

    public void initToolbar() {
        toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("用户单独时间设定");
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
}
