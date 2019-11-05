package com.wandao.myapplication.listener;

import android.util.Log;

import com.baidu.idl.facesdk.model.Feature;
import com.baidu.idl.sample.manager.UserInfoManager;
import com.wandao.myapplication.activity.MainActivity;

import java.util.List;


public class UserListListener extends UserInfoManager.UserInfoListener {

    public  static UserListListener instance;
    public static List<Feature> mlistFeatureInfo;
    public synchronized static UserListListener newInstance() {
        if (instance== null) {
            instance= new UserListListener();
        }
        return instance;
    }
    @Override
    public void featureQuerySuccess(List<Feature> listFeatureInfo) {
        super.featureQuerySuccess(listFeatureInfo);
        Log.d("2222222222222",listFeatureInfo.get(0).getUserName().toString());
        mlistFeatureInfo=listFeatureInfo;
//        doorRecycleViewAdapter=new DoorRecycleViewAdapter();
        Log.d("2222222222222","eeeeeeeeeeeeee");
        MainActivity.listFeatureAdapter.clear();
        MainActivity.listFeatureAdapter.addAll(listFeatureInfo);
        Log.d("2222222222222","ffffffffffffffff");
   //     doorRecycleViewAdapter.setListData(listFeatureInfo);
     //   MainActivity.mRecyclerView.setAdapter(doorRecycleViewAdapter);
    //    MainActivity.dAdapter.notifyDataSetChanged();
    }

    @Override
    public void featureQueryFailure(String message) {
        super.featureQueryFailure(message);
    }
}
