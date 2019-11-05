package com.wandao.myapplication.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wandao.myapplication.R;
import com.wandao.myapplication.adapter.RegisterFragmentAdapter;
import com.wandao.myapplication.viewPager.MyViewPager;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String KEY_DEMO = "demo";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TabLayout mTabLayout;
    private OnFragmentInteractionListener mListener;
    private String mTitles[] = {
            "用户注册", "管理员注册"};
    private TabLayout tabLayout;
    private MyViewPager viewPager;
    private ArrayList<String> tab_title_list = new ArrayList<>();//存放标签页标题
    private ArrayList<Fragment> fragment_list = new ArrayList<>();//存放ViewPager下的Fragment
    private   Fragment fragment1, fragment2, fragment3, fragment4;
    private RegisterFragmentAdapter adapter;//适配器
    private Toolbar toolbar;
    private  View view;
    public RegisterInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterInfoFragment newInstance(String param1, String param2) {
        RegisterInfoFragment fragment = new RegisterInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static RegisterInfoFragment newInstance() {
        
        Bundle args = new Bundle();
        
        RegisterInfoFragment fragment = new RegisterInfoFragment();
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
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }
        return inflater.inflate(R.layout.fragment_register_info, container, false);
   //     return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mTabLayout = getActivity().findViewById(R.id.tablayout);
//        for (int i = 0; i < 2; i++) {
//            TabLayout.Tab tab = mTabLayout.newTab();
//            tab.setTag(i);
//            tab.setText(mTitles[i]);
//            mTabLayout.addTab(tab);
            tabLayout = (TabLayout) getActivity().findViewById(R.id.my_tablayout);
            viewPager = (MyViewPager) getActivity().findViewById(R.id.my_viewpager);
            tabLayout.setupWithViewPager(viewPager);//将TabLayout与Viewpager联动起来
 //           tabLayout.setTabsFromPagerAdapter(adapter);//给TabLayout设置适配器
            tabLayout.setSelectedTabIndicatorColor(Color.rgb(16,78,139));

        initToolbar();
    }

    @Override
    public void onResume() {
        super.onResume();
        fragment1 = new UserRegisterFragment();
        fragment2 = new AdminRegisterFragment();
        fragment_list.clear();
        fragment_list.add(fragment1);
        fragment_list.add(fragment2);
        tab_title_list.add("用户注册");
        tab_title_list.add("管理员注册");
        tabLayout.addTab(tabLayout.newTab().setText(tab_title_list.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(tab_title_list.get(1)));

        adapter = new RegisterFragmentAdapter(getChildFragmentManager(), tab_title_list, fragment_list);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);//给ViewPager设置适配器
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.d("211qqqweres","32435");
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
    public void initToolbar(){
        toolbar= (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("注册");
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
                        .replace(R.id.container, StaffManagementFragment.newInstance())
                        .commitNow();
            }
        });
    }



















}
