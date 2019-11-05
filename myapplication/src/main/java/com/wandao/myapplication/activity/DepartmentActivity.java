package com.wandao.myapplication.activity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.idl.sample.ui.BaseActivity;
import com.wandao.myapplication.R;
import com.wandao.myapplication.utils.SPUtils;
import com.wandao.myapplication.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DepartmentActivity extends BaseActivity {
    private EditText mDepartment;
    private String department;
    private ListView dListview;
    private Button dSubmit;
    private ArrayAdapter<String> adapter;
    private static ArrayList<String> departmentList = new ArrayList<String>();
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);


        TextView view=(TextView) findViewById(R.id.title);
        view.setText("部门设置");
        initData();
        initView();
        initListview();
    }

    private void initData() {
         departmentList = StringUtils.StringToArrayList((String) SPUtils.get(this, "department", "合规风控部,固定收益部,集中交易部,权益投资部,研究部,特定客户资产管理部"));
    }

    private void initView() {
        mDepartment = findViewById(R.id.department_name);
        dSubmit = findViewById(R.id.department_submit);
        dListview = findViewById(R.id.department_Listview);
    }

    private void initListview() {
        adapter = new ArrayAdapter<String>(DepartmentActivity.this, android.R.layout.simple_list_item_1, departmentList);
        dListview.setAdapter(adapter);
        dListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//             dListview.getSelectedItem().toString();
                showListDialog(position);

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void submitDepartment(View view)      //提交部门
    {
        department = mDepartment.getText().toString();
        //   SPUtils.put(this,"department",departmentList.add(department));
        departmentList.add(department);
        removeDuplicate(departmentList);
        ArrayList<String> temp = new ArrayList<>();
        temp.clear();
        temp.addAll(departmentList);
        if (temp.contains("未设置"))
            temp.remove("未设置");

        SPUtils.put(this, "department", temp.toString());
        Toast.makeText(this, department + "部门已添加", Toast.LENGTH_LONG).show();
        adapter.notifyDataSetChanged();
    }

    public void showListDialog(final int pos) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final android.app.AlertDialog.Builder normalDialog =
                new android.app.AlertDialog.Builder(DepartmentActivity.this);
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
                            SPUtils.put(getApplicationContext(), "department", departmentList.toString());
                            adapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(getApplicationContext(),"禁止删除",Toast.LENGTH_LONG).show();
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

}

