package com.wandao.myapplication.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ZysjSystemManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.baidu.idl.sample.api.FaceApi;
import com.baidu.idl.sample.common.GlobalSet;
import com.baidu.idl.sample.manager.FaceSDKManager;
import com.baidu.idl.sample.ui.BaseActivity;
import com.baidu.idl.sample.ui.BatchImportActivity;
import com.baidu.idl.sample.ui.IminectRegisterActivity;
import com.baidu.idl.sample.ui.OrbbecMiniRegisterActivity;
import com.baidu.idl.sample.ui.OrbbecProRegisterActivity;
import com.baidu.idl.sample.utils.ToastUtils;
import com.wandao.myapplication.R;
import com.wandao.myapplication.greendao.User;
import com.wandao.myapplication.utils.DbUtils;
import com.wandao.myapplication.utils.SPUtils;
import com.wandao.myapplication.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterInfoActivity extends BaseActivity implements View.OnClickListener, NumberPicker.OnValueChangeListener {

    private RelativeLayout mLayoutInput;
    private View mViewBg;
    private EditText mNickView;
    private Button goBtn;
    private Button goRegister;
    private TextView mTextBatchRegister;
    private RegisterInfoActivity mContext;
    private String mNickName;
    private EditText userID;
    private EditText userTel;
    private EditText userEmail;
    private EditText userDepartment;
    private EditText userBoxNumber;
    private Spinner userDepartmentSpinner;
    private String mId;
    private String mTel;
    private String mEmail;
    private String mDepartment;
    private int mBoxNumber;
    private User user;
    private NumberPicker mNumberPicker;
    private CheckBox selectAdmin;
    public boolean isCheckedAdmin=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
        @SuppressLint("WrongConstant") ZysjSystemManager SystemManager= SystemManager=(ZysjSystemManager)getSystemService("zysj");
        SystemManager.ZYSystemBar(0);

        TextView view = (TextView) findViewById(R.id.title);
        view.setText("用户注册");
        mContext = this;
        initView();
        initListener();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                RegisterInfoActivity.this, R.layout.register_spinner_item,
                getSpinnerData());
        userDepartmentSpinner.setAdapter(adapter);
    }


    private List<String> getSpinnerData() {
        // 数据源
        List<String> dataList = new ArrayList<String>();

        //dataList = StringUtils.StringToArrayList((String) SPUtils.get(this, "department", "未设置"));
        dataList = StringUtils.StringToArrayList((String) SPUtils.get(this, "department", "合规风控部,固定收益部,集中交易部,权益投资部,研究部,特定客户资产管理部"));

        return dataList;
    }

    private void initView() {
        mLayoutInput = findViewById(R.id.layout_input);
        mViewBg = findViewById(R.id.view_bg);
        mNickView = findViewById(R.id.nick_name);
        goBtn = findViewById(R.id.go_btn);
        //     goRegister=findViewById(R.id.go_register);
        userID = findViewById(R.id.user_id);
        userTel = findViewById(R.id.user_tel);
        mTextBatchRegister = findViewById(R.id.text_batch_register);
        //   mEmail = findViewById(R.id.user);
        userDepartment = findViewById(R.id.user_department);
        userDepartmentSpinner = findViewById(R.id.user_department_spinner);
        userBoxNumber = findViewById(R.id.user_box_number);
        mNumberPicker = (NumberPicker) findViewById(R.id.mNumberPicker);
        selectAdmin=findViewById(R.id.select_admin);
        selectAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    isCheckedAdmin=true;
                    findViewById(R.id.box_select).setVisibility(View.INVISIBLE);
                }
                else {
                    isCheckedAdmin=false;
                    findViewById(R.id.box_select).setVisibility(View.VISIBLE);
                }
            }
        });
//        mNumberPicker.setMaxValue(20);
//        mNumberPicker.setMinValue(1);
//        mNumberPicker.setValue(1);

        ArrayList<String> list = DbUtils.getUnbindNumber();

        if (DbUtils.getUnbindNumber() != null&&getIntent().getStringExtra("id")==null) {
            if (DbUtils.getUnbindNumber().size() == 0) {
                mNumberPicker.setVisibility(View.INVISIBLE);
            //    goBtn.setVisibility(View.INVISIBLE);
            } else {
                String[] temp = new String[DbUtils.getUnbindNumber().size()];
                for (int i = 0; i < list.size(); i++) {
                    temp[i] = list.get(i);
                }
                mNumberPicker.setDisplayedValues(temp);
                mNumberPicker.setMaxValue(list.size()-1);
            }
            Log.d("qwertyu","666666666666");
        }


        if(getIntent().getStringExtra("id")!=null)
        {
          //  mNickView.setText(getIntent().getStringExtra("id"));
            String id =getIntent().getStringExtra("id");
            userID.setText(id);
            mNickView.setText(DbUtils.queryUser(Integer.parseInt(id)).getName());
            mNumberPicker.setVisibility(View.INVISIBLE);

        };
    }

    private void initListener() {
        mTextBatchRegister.setOnClickListener(this);
        goBtn.setOnClickListener(this);
//        goRegister.setOnClickListener(this);
    }

    private boolean getEditText() {
        Editable editable = mNickView.getText();
        Editable editable1 = userID.getText();
        Editable editable2 = userTel.getText();
        Editable editable3 = userDepartment.getText();

        if (editable != null && editable1 != null  && editable3 != null) {
            mId = userID.getText().toString();
            mNickName = mNickView.getText().toString();
            mDepartment = userDepartmentSpinner.getSelectedItem().toString();
            mTel = userTel.getText().toString() + "";
            if (getIntent().getStringExtra("id")!=null)
            {
                mBoxNumber=DbUtils.queryUser(Integer.parseInt(getIntent().getStringExtra("id"))).getBoxId();
            }else {
                mBoxNumber = Integer.parseInt(mNumberPicker.getDisplayedValues()[mNumberPicker.getValue()]);
            }
            Log.d("qwertytuy",mBoxNumber+"");
//             if (mNumberPicker.getValue()!=0)
//             else {
//                 mBoxNumber=0;                                                          // 柜门号0为保存字符串
//             }
            String nameResult = FaceApi.getInstance().isValidName(mNickName);      //姓名转化
            String idResult = isValidID(mId);
   //         String telResult = isMobileNo(mTel);
            if ("0".equals(nameResult) && "0".equals(idResult) ) {
                // 设置注册时的昵称
                FaceSDKManager.getInstance().getFaceLiveness().setRegistNickName(mId);
                // 隐藏键盘
                com.baidu.idl.sample.utils.Utils.hideKeyboard((Activity) mContext);
                if (isCheckedAdmin==true)
                    user = new User(Long.parseLong(mId), mNickName, mTel, mEmail, mDepartment, true, 0, 0);  //注册为管理员

                else
                user = new User(Long.parseLong(mId), mNickName, mTel, mEmail, mDepartment, false, 0, mBoxNumber);
                return true;
            } else {
                ToastUtils.toast(this, nameResult);
                ToastUtils.toast(this, idResult);
             //   ToastUtils.toast(this, telResult);

            }
        }
        return false;
    }

    private void jumpRegister(Intent intent) {
        // name填写完毕，去注册人脸
        if (GlobalSet.getLiveStatusValue() == GlobalSet.LIVE_STATUS.RGB_DEPTH) {
            GlobalSet.STRUCTURED_LIGHT structuredLightValue = GlobalSet.getStructuredLightValue();
            if (structuredLightValue == GlobalSet.STRUCTURED_LIGHT.OBI_ASTRA_PRO
                    || structuredLightValue == GlobalSet.STRUCTURED_LIGHT.OBI_ATLAS
                    || structuredLightValue == GlobalSet.STRUCTURED_LIGHT.OBI_ASTRA_PRO_s1
                    || structuredLightValue == GlobalSet.STRUCTURED_LIGHT.OBI_ASTRA_DB
                    || structuredLightValue == GlobalSet.STRUCTURED_LIGHT.OBI_DEEYEA) {
                intent = new Intent(this, OrbbecProRegisterActivity.class);
            } else if (structuredLightValue == GlobalSet.STRUCTURED_LIGHT.OBI_ASTRA_MINI) {
                intent = new Intent(this, OrbbecMiniRegisterActivity.class);
            } else if (structuredLightValue == GlobalSet.STRUCTURED_LIGHT.HUAJIE_AMY_MINI) {
                intent = new Intent(this, IminectRegisterActivity.class);
            }
        } else {
            intent = new Intent(this, RegisterActivity.class);
        }
        intent.putExtra("mNickName", mId);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = null;
        if (id == R.id.go_btn) {
            if (getEditText()) {
                jumpRegister(intent);
            }
        } else if (id == R.id.text_batch_register) {
            intent = new Intent(this, BatchImportActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.go_btn) {
            if (getEditText()) {
                jumpRegister(intent);

            }
        }
    }


    //检查电话号是否正确
    public static String isMobileNo(String mobiles) {
        boolean flag = false;
        try {

            // 13********* ,15********,18*********
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

            Matcher m = p.matcher(mobiles);
            if (m.matches())
                return "0";

        } catch (Exception e) {
            flag = false;
            return "电话格式错误";
        }

        return "电话格式错误";
    }

    public static String isValidID(String id) {
        if (id == null || "".equals(id.trim())) {
            return "ID为空";
        }

        // 姓名过长
        if (id.length() > 20) {
            return "ID过长";
        }

        // 含有特殊符号
        String regex = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）—"
                + "—+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(id);
        if (m.find()) {
            return "ID含有特殊符号";
        }

        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(id);
        if( !isNum.matches() ){
            return "ID不为纯数字";
        }
      //  if(DbUtils.queryUser(Integer.parseInt(id))!=null)
        if(false)
            return "ID已存在";
        return "0";
    }


    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        mBoxNumber=newVal;

        Log.i("111111111111111116", "onValueChange: 原来的值 " + oldVal + "--新值: "
                + newVal);

    }
}
