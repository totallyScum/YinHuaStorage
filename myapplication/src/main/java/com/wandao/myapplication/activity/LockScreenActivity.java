package com.wandao.myapplication.activity;

import android.os.Environment;
import android.os.Bundle;
import android.widget.ImageView;

import com.baidu.idl.sample.ui.BaseActivity;
import com.bumptech.glide.Glide;
import com.wandao.myapplication.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LockScreenActivity extends BaseActivity {

    String imagePath=Environment.getExternalStorageDirectory() + "/万道壁纸";
    ImageView lockScreenImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     //   List<String> list = getPictures(Environment.getExternalStorageDirectory() + "/万道壁纸");
        setContentView(R.layout.activity_lock_screen);
        lockScreenImage=findViewById(R.id.lock_screen_image);
        List<String> list= getPictures(imagePath);
//        Glide
//                .with(lockScreenImage)
//                .load(url)
//                .centerCrop()
//                .placeholder(R.drawable.loading_spinner)
//                .crossFade()
//                .into(myImageView);


        Glide.with(getApplicationContext())
                .load(list.get(0))
                .into(lockScreenImage);                 //加载彩色头像

    }

    public List<String> getPictures(final String strPath) {
        List<String> list = new ArrayList<String>();
        File file = new File(strPath);
        File[] allfiles = file.listFiles();
        if (allfiles == null) {
            return null;
        }
        for (int k = 0; k < allfiles.length; k++) {
            final File fi = allfiles[k];
            if (fi.isFile()) {
                int idx = fi.getPath().lastIndexOf(".");
                if (idx <= 0) {
                    continue;
                }
                String suffix = fi.getPath().substring(idx);
                if (suffix.toLowerCase().equals(".jpg") ||
                        suffix.toLowerCase().equals(".jpeg") ||
                        suffix.toLowerCase().equals(".bmp") ||
                        suffix.toLowerCase().equals(".png") ||
                        suffix.toLowerCase().equals(".gif")) {
                    list.add(fi.getPath());
                }
            }
        }
        return list;
    }


}
