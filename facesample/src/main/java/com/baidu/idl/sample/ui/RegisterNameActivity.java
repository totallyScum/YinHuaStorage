package com.baidu.idl.sample.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.idl.sample.R;
import com.baidu.idl.sample.api.FaceApi;
import com.baidu.idl.sample.common.GlobalSet;
import com.baidu.idl.sample.manager.FaceSDKManager;
import com.baidu.idl.sample.utils.ToastUtils;

public class RegisterNameActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout mLayoutInput;
    private View mViewBg;
    private EditText mNickView;
    private ImageView goBtn;
    private TextView mTextBatchRegister;
    private RegisterNameActivity mContext;
    private String mNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_name);
        mContext = this;
        initView();
        initListener();
    }

    private void initView() {
        mLayoutInput = findViewById(R.id.layout_input);
        mViewBg = findViewById(R.id.view_bg);
        mNickView = findViewById(R.id.nick_name);
        goBtn = findViewById(R.id.go_btn);
        mTextBatchRegister = findViewById(R.id.text_batch_register);
    }

    private void initListener() {
        mTextBatchRegister.setOnClickListener(this);
        goBtn.setOnClickListener(this);
    }

    private boolean getEditText() {
        Editable editable = mNickView.getText();
        if (editable != null) {
            mNickName = mNickView.getText().toString();
            String nameResult = FaceApi.getInstance().isValidName(mNickName);
            if ("0".equals(nameResult)) {
                // 设置注册时的昵称
                FaceSDKManager.getInstance().getFaceLiveness().setRegistNickName(mNickName);
                // 隐藏键盘
                com.baidu.idl.sample.utils.Utils.hideKeyboard((Activity) mContext);
                return true;
            } else {
                ToastUtils.toast(this, nameResult);
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
        intent.putExtra("mNickName", mNickName);
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
        }
    }
}
