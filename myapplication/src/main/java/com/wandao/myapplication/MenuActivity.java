package com.wandao.myapplication;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

import com.wandao.myapplication.ui.menu.MenuFragment;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container, MenuFragment.newInstance())
//                    .commitNow();
//        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
         //           .replace(R.id.container, MenuFragment.newInstance())
                    .replace(R.id.container,MenuFragment .newInstance())
                    .commitNow();
        }
    }
}
