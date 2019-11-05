package com.wandao.myapplication.adapter;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RegisterFragmentAdapter extends FragmentStatePagerAdapter {
    private ArrayList<String> tab_title_list;//存放标签页标题
    private ArrayList<Fragment> fragment_list;//存放ViewPager下的Fragment
    public RegisterFragmentAdapter(FragmentManager fm) {
        super(fm);
        this.tab_title_list = tab_title_list;
        this.fragment_list = fragment_list;
    }
    public RegisterFragmentAdapter(FragmentManager fm, ArrayList<String> tab_title_list, ArrayList<Fragment> fragment_list) {
        super(fm);
        this.tab_title_list = tab_title_list;
        this.fragment_list = fragment_list;
    }
    @Override
    public Fragment getItem(int position) {
        return fragment_list.get(position);
    }

    @Override
    public int getCount() {
        return fragment_list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tab_title_list.get(position);
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Parcelable saveState() {
        //     return super.saveState();

        return null;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        //super.restoreState(state, loader);
    }
}
