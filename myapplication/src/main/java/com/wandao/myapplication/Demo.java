package com.wandao.myapplication;

import android.content.Context;




public enum Demo {

  BASIC(R.string.demo_title_basic, R.layout.fragment_register_info);

  public final int titleResId;
  public final int layoutResId;

  Demo(int titleResId, int layoutResId) {
    this.titleResId = titleResId;
    this.layoutResId = layoutResId;
  }

  public static int[] tab10() {
    return new int[] {
        R.string.demo_tab_1,
        R.string.demo_tab_2,
        R.string.demo_tab_3,
        R.string.demo_tab_4,
        R.string.demo_tab_5,
        R.string.demo_tab_6,
        R.string.demo_tab_7,
        R.string.demo_tab_8,
        R.string.demo_tab_9,
        R.string.demo_tab_10
    };
  }

  public static int[] tab3() {
    return new int[] {
        R.string.demo_tab_8,
        R.string.demo_tab_9,
        R.string.demo_tab_10
    };
  }

  public void startActivity(Context context) {
//    DemoActivity.startActivity(context, this);
  }


  public int[] tabs() {
    return tab10();
  }

}
