package com.wandao.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.wandao.myapplication.R;
import com.wandao.myapplication.activity.BatchImportActivity;
import com.wandao.myapplication.ui.menu.MenuFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StaffManagementFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StaffManagementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StaffManagementFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Toolbar toolbar;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private String data[] = {"人员信息录入","人员信息查询管理"};//列表信息
    private ListView listView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter;
    String[] from = {"img", "text"};
    int[] to = {R.id.setting_img, R.id.setting_text};
    public StaffManagementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StaffManagementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StaffManagementFragment newInstance(String param1, String param2) {
        StaffManagementFragment fragment = new StaffManagementFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static StaffManagementFragment newInstance() {
        
        Bundle args = new Bundle();
        
        StaffManagementFragment fragment = new StaffManagementFragment();
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
        initData();
        return inflater.inflate(R.layout.fragment_staff_management, container, false);
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
        listView = (ListView) getActivity().findViewById(R.id.staff_listview);//在视图中找到ListView
        adapter = new SimpleAdapter(getContext(), dataList, R.layout.setting_list_item, from, to);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, RegisterInfoFragment.newInstance())
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 1:
                        Intent intent1 = new Intent(getActivity(), BatchImportActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, UserFragment.newInstance())
                                .addToBackStack(null)
                                .commit();
                        break;
                }
            }});
        initToolbar();
    }
    public void initToolbar(){
        toolbar= (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("人员管理");
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
    void initData() {
        //图标
     //   int icno[] = {R.mipmap.department_login,R.mipmap.staff_group};

       int icno[] = {R.mipmap.department_login, R.mipmap.department_manager,R.mipmap.staff_group};
        //图标下的文字
     //   String name[] = {"人员信息录入", "人员信息管理"};
        String name[] = {"人员信息录入","人员信息批量导入", "人员信息管理"};
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < icno.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icno[i]);
            map.put("text", name[i]);
            dataList.add(map);
        }
    }
}
