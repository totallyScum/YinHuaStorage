package com.baidu.idl.sample.ui;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.baidu.idl.sample.R;
import com.baidu.idl.sample.common.GlobalSet;
import com.baidu.idl.sample.view.CustomRadioGroup;


/**
 * @Time: 2018/12/4
 * @Author: v_chaixiaogang
 * @Description: 摄像头预览角度设置页面
 */
public class SettingCameraMirrorActivity extends BaseActivity implements View.OnClickListener ,View.OnTouchListener {

    private CustomRadioGroup rgPreviewAngle;
    private RadioButton openMirror;
    private RadioButton closeMirror;
    private Button btConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_mirror);
        initView();
        int mirrorStatus = GlobalSet.getMirrorStatus();
        defaultMirrorStatus(mirrorStatus);
    }

    private void initView() {
        mLableTxt = findViewById(R.id.title);
        mLableTxt.setText(R.string.setting_camera_mirror);
        rgPreviewAngle = findViewById(R.id.rg_preview_angle);
        openMirror = findViewById(R.id.open_mirror_btn);
        closeMirror = findViewById(R.id.close_mirror_btn);
        btConfirm = findViewById(R.id.confirm_btn);
        btConfirm.setOnClickListener(this);
        btConfirm.setOnTouchListener(this);
        rgPreviewAngle.setOnCheckedChangeListener(new CustomRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CustomRadioGroup group, int checkedId) {
                if (checkedId == R.id.open_mirror_btn) {
                    GlobalSet.setMirrorStatus(1);
                } else if (checkedId == R.id.close_mirror_btn) {
                    GlobalSet.setMirrorStatus(0);
                }
            }
        });
    }

    private void defaultMirrorStatus(int mirrorStatus) {
        if (mirrorStatus == 0) {
            closeMirror.setChecked(true);
        } else if (mirrorStatus == 1) {
            openMirror.setChecked(true);
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.confirm_btn) {
            finish();

        } else {
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (view.getId() == R.id.confirm_btn) {
                    ((Button) view).setTextColor(getResources().getColor(R.color.white));
                }
                break;
            case MotionEvent.ACTION_UP:
                if (view.getId() == R.id.confirm_btn) {
                    ((Button) view).setTextColor(getResources().getColor(R.color.btnColor));
                }
                break;
        }
        return false;
    }
}
