package com.wandao.myapplication.activity.popWindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.wandao.myapplication.R;

public class LeavePopupWindow extends PopupWindow {
    PopupWindow popRootView = new PopupWindow();

    public LeavePopupWindow(Context context) {
        super(context);
    }

    @Override
    public void setContentView(View contentView) {
        super.setContentView(contentView);
        contentView.setMinimumHeight(300);
    }
}
