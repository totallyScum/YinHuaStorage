package com.baidu.idl.sample.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 既来之则安之 on 2019/3/1.
 */

public class CustomScrollViewPager extends ViewPager {

    //  是否可以左右滑动？true 可以，像Android原生ViewPager一样。
    //  false 禁止ViewPager左右滑动。
    private boolean scrollable = false;

    public CustomScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return scrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return scrollable;
    }

}
