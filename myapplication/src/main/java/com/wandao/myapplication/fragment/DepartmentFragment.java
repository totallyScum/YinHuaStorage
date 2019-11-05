package com.wandao.myapplication.fragment;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.wandao.myapplication.R;
import com.wandao.myapplication.ui.menu.MenuFragment;
import com.wandao.myapplication.utils.SPUtils;
import com.wandao.myapplication.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DepartmentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DepartmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DepartmentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private EditText mDepartment;
    private String department;
    private ListView dListview;
    private Button dSubmit;
    private ArrayAdapter<String> adapter;
    private static ArrayList<String> departmentList = new ArrayList<String>();
private Toolbar toolbar;
    public DepartmentFragment() {
        // Required empty public constructor
    }

    public static DepartmentFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DepartmentFragment fragment = new DepartmentFragment();
        fragment.setArguments(args);
        return fragment;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DepartmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DepartmentFragment newInstance(String param1, String param2) {
        DepartmentFragment fragment = new DepartmentFragment();
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
        return inflater.inflate(R.layout.fragment_department, container, false);
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
        initData();
        initView();
        initListview();
        initToolbar();
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
    private void initData() {
        departmentList = StringUtils.StringToArrayList((String) SPUtils.get(getContext(), "department", "合规风控部,固定收益部,集中交易部,权益投资部,研究部,特定客户资产管理部"));
    }
    private void initView() {
        mDepartment = getActivity().findViewById(R.id.department_name);
        dSubmit = getActivity().findViewById(R.id.department_submit);
        dListview = getActivity().findViewById(R.id.department_Listview);
        dSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                department = mDepartment.getText().toString();
                //   SPUtils.put(this,"department",departmentList.add(department));
                departmentList.add(department);
                removeDuplicate(departmentList);
                ArrayList<String> temp = new ArrayList<>();
                temp.clear();
                temp.addAll(departmentList);
                if (temp.contains("未设置"))
                    temp.remove("未设置");

                SPUtils.put(getContext(), "department", temp.toString());
                Toast.makeText(getContext(), department + "部门已添加", Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }
        });
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
                        if (departmentList.size() > 1) {
                            departmentList.remove(pos);
                            SPUtils.put(getContext(), "department", departmentList.toString());
                            adapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(getContext(),"禁止删除",Toast.LENGTH_LONG).show();
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
    private static void removeDuplicate(List<String> list) {
        HashSet<String> set = new HashSet<String>(list.size());
        List<String> result = new ArrayList<String>(list.size());
        for (String str : list) {
            if (set.add(str)) {
                result.add(str);
            }
        }
        list.clear();
        list.addAll(result);
    }
    private void initListview() {
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, departmentList);
        dListview.setAdapter(adapter);
        dListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//             dListview.getSelectedItem().toString();
                showListDialog(position);

            }
        });
    }
    public void initToolbar(){
        toolbar= (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("部门添加");
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
