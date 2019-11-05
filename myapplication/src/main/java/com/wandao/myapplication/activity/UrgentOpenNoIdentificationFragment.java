package com.wandao.myapplication.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
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
import android.widget.Toast;

import com.wandao.myapplication.R;
import com.wandao.myapplication.greendao.Box;
import com.wandao.myapplication.service.IRemoteService;
import com.wandao.myapplication.ui.menu.MenuFragment;
import com.wandao.myapplication.utils.DbUtils;
import com.wandao.myapplication.utils.DoorUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UrgentOpenNoIdentificationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UrgentOpenNoIdentificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UrgentOpenNoIdentificationFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GridView mGridView;
    private SimpleAdapter adapter;
    private static IRemoteService boxService; //获取当前锁控服务
    private static ArrayList<String> userList = new ArrayList<String>();
    private List<Map<String, Object>> lists = new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    private Toolbar toolbar;
    private AlertDialog builder;

    final String[] boxTitle = new String[]{"开启柜门", "柜门禁止使用", "柜门开启使用", "柜门解除绑定(慎用)"};

    public UrgentOpenNoIdentificationFragment() {
        // Required empty public constructor
    }

    public static UrgentOpenNoIdentificationFragment newInstance() {

        Bundle args = new Bundle();

        UrgentOpenNoIdentificationFragment fragment = new UrgentOpenNoIdentificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UrgentOpenNoIdentificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UrgentOpenNoIdentificationFragment newInstance(String param1, String param2) {
        UrgentOpenNoIdentificationFragment fragment = new UrgentOpenNoIdentificationFragment();
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

        return inflater.inflate(R.layout.fragment_urgent_open_no_identification, container, false);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userList.clear();
        initData();
        initView();
        initToolbar();
    }

    public void initView() {

        mGridView = getView().findViewById(R.id.urgent_gridview);
        //     adapter = new ArrayAdapter<String>(UrgentOpenNoIdentificationActivity.this, R.layout.urgent_open_item, userList);
        adapter = new SimpleAdapter(getContext(), lists, R.layout.urgent_open_item, new String[]{"box"}, new int[]{R.id.urgent_open_button});
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(this);
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        showBoxDialog(i);

    }


    private void showBoxDialog(int doorNumber) {
        Box box = DbUtils.queryBox(doorNumber + 1);
        builder = new AlertDialog.Builder(getContext()).setIcon(R.mipmap.ic_launcher).setTitle("柜门设置")
                .setItems(boxTitle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                try {


                                    DoorUtils.getSingleton().openDoor(doorNumber);   //银华开门方式
//                                    if (doorNumber < 20)
//                                        boxService.lockControlOpenDoor(Byte.valueOf(doorNumber + 1 + ""), Byte.valueOf(0 + ""));
//                                    if (doorNumber >= 20)
//                                        boxService.lockControlOpenDoor(Byte.valueOf(((doorNumber - 20) + 1 + "")), Byte.valueOf(15 + ""));
//                                    if (DbUtils.queryBox(doorNumber + 1).getStatus()!=5)
//                                    DbUtils.changeBoxStatus(0, DbUtils.queryBox(doorNumber + 1), new Date().getTime());            //柜门状态改变，标记为空
                                } catch (Exception e) {
                                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                                break;
                            case 1:

                                box.setStatus(5);
                                Toast.makeText(getContext(), "柜门已禁止使用", Toast.LENGTH_LONG).show();
                                break;
                            case 2:
                                box.setStatus(0);
                                Toast.makeText(getContext(), "柜门已开启使用", Toast.LENGTH_LONG).show();
                                break;
                            case 3:
                                box.setBind(false);
                                Toast.makeText(getContext(), "柜门已经解除绑定！！！", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                }).create();
//                .setPositiveButton("柜门解除绑定(慎用)", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //ToDo: 你想做的事情
//
//                        Box box= DbUtils.queryBox(doorNumber+1);
//                       box.setBind(false);
//                        Toast.makeText(getContext(), "柜门已经解除绑定！！！", Toast.LENGTH_LONG).show();
//                    }
//                }).setNeutralButton("柜门强制开启", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        try {
//                            if (doorNumber<20)
//                                boxService.lockControlOpenDoor(Byte.valueOf(doorNumber+1 + ""), Byte.valueOf(0 + ""));
//                            if (doorNumber>=20)
//                                boxService.lockControlOpenDoor(Byte.valueOf(((doorNumber-20)+1+"")), Byte.valueOf(15 + ""));
//                            DbUtils.changeBoxStatus(0, DbUtils.queryBox(doorNumber+1),new Date().getTime());            //柜门状态改变，标记为空
//                        } catch (RemoteException e) {
//                            Log.d("RemoteException",e.toString());
//                            e.printStackTrace();
//                        }
//                    }
//                }).setNegativeButton("柜门禁止使用", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //ToDo: 你想做的事情
//                        Box box= DbUtils.queryBox(doorNumber+1);
//                        box.setStatus(5);
//                        Toast.makeText(getContext(), "柜门已禁止使用", Toast.LENGTH_LONG).show();
//                        dialogInterface.dismiss();
//                    }
//                }).setNegativeButton("柜门开启使用", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //ToDo: 你想做的事情
//                        Box box= DbUtils.queryBox(doorNumber+1);
//                        box.setStatus(0);
//                        Toast.makeText(getContext(), "柜门已开启使用", Toast.LENGTH_LONG).show();
//                        dialogInterface.dismiss();
//                    }
//                });
        builder.show();

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
        toolbar.setTitle("柜门管理");
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
