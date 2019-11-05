package com.wandao.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.idl.facesdk.model.Feature;
import com.baidu.idl.sample.utils.FileUtils;
import com.bumptech.glide.Glide;
import com.wandao.myapplication.R;
import com.wandao.myapplication.activity.MainActivity;
import com.wandao.myapplication.activity.MyApplication;
import com.wandao.myapplication.greendao.Box;
import com.wandao.myapplication.listener.UserListListener;
import com.wandao.myapplication.utils.DbUtils;

import java.util.ArrayList;
import java.util.List;

import static com.serenegiant.utils.UIThreadHelper.runOnUiThread;

public class DoorRecycleViewAdapter extends RecyclerView.Adapter<DoorRecycleViewAdapter.MyViewHolder> {
    private List<Box> boxList = new ArrayList<>();
    private Context mContext;
    private List<Feature> mList;
    static String boxStatusImgPath;
    static int doorNumber = DbUtils.getDoorNumber();
    private GridView mGridView;


    public DoorRecycleViewAdapter(Context mContext, RecyclerView recyclerView) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DoorRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//           View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_listview_item,
//                   null);
        boxList = DbUtils.getInstance().loadAllBox();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_listview_item,
                viewGroup, false);
//           view.setScaleX(1f);
//            view.setScaleY(1f);
        if (doorNumber <= 20) {
            view.setPadding(25, 60, 0, 0);
            view.setScaleX(1.2f);
            view.setScaleY(1.2f);
        }
        if (doorNumber <= 40 && doorNumber > 20)
            view.setPadding(25, 0, 0, 0);
        if (doorNumber > 40) {
            view.setScaleX(0.7f);
            view.setScaleY(0.7f);

        }
        DoorRecycleViewAdapter.MyViewHolder holder = new DoorRecycleViewAdapter.MyViewHolder(view);

        return holder;
    }

    int j = 0;
    @Override
    public void onBindViewHolder(@NonNull DoorRecycleViewAdapter.MyViewHolder myViewHolder, int i) {
        if (boxList.size() != 0) {
            int doorNumber = i + 1;
            myViewHolder.tv.setText("柜门号:" + doorNumber);
            myViewHolder.iv.setImageResource(R.mipmap.main_door_icon);
            //  Log.d("box_number_qqaaaooo7", boxList.get(i).getBoxId() + " " + boxList.get(i).getBind() + "  " + boxList.get(i).getStatus());

            if (boxList.get(i).getBind() == true) {
                //          Log.d("box_number_qqaaaooo8", boxList.get(i).getBoxId() + "");
            }
//            if ()
//            if(DbUtils.getInstance().queryBoxIfBind(doorNumber)==true)
//            {
//                Log.d("box_number_qqaaaooo", "qqqq");
//            }

//
//
            if (mList != null && mList.size() != 0) {

                //          if (mList != null && mList.size() != 0) {
////for (int j=0;j<mList.size();i++)
////{}
//        //        if (DbUtils.queryBoxIfBind(i + 1) != null) {
//                    Log.d("box_number_qqaaaa", i + 1 + " ");
//                    //     if (mList.size()!=0&&i<mList.size()) {
////                    j=j++;
////                    j = j%(mList.size()+1);
                for (int j = 0; j < mList.size(); j++) {
                    //            Log.d("22222222566", mList.size()+"");
                    if (DbUtils.queryUser(Integer.parseInt(mList.get(j).getUserName())) != null)
                        if (DbUtils.queryUser(Integer.parseInt(mList.get(j).getUserName())).getBoxId() != 0) {
                            //           Log.d("222222225", DbUtils.queryUser(Integer.parseInt(mList.get(j).getUserName())).getBoxId() + "");


                            if (DbUtils.queryUser(Integer.parseInt(mList.get(j).getUserName())).getBoxId() == doorNumber) {

                                boxStatusImgPath = FileUtils.getFaceCropPicDirectory().getAbsolutePath() + "/" + mList.get(j).getCropImageName();

//                    Log.d("box_number_qq", i + 1 + " ");
                                if (DbUtils.queryBox(DbUtils.queryUser(Integer.parseInt(mList.get(j).getUserName())).getBoxId()).getStatus() == 1) {
                                    Glide.with(mContext)
                                            .load(boxStatusImgPath)
                                            //             .crossFade()
                                            .into(myViewHolder.iv);
                                    //加载彩色头像
                                }


                                if (DbUtils.queryBox(DbUtils.queryUser(Integer.parseInt(mList.get(j).getUserName())).getBoxId()).getStatus() == 0) {
                                    Glide.with(mContext)
                                            .load(R.mipmap.user_attention_blue)
                                            //               .crossFade()
                                            //    .bitmapTransform(new GrayscaleTransformation(getApplicationContext())) //加载灰色头像
                                            .into(myViewHolder.iv);
                                }

                                String userName = DbUtils.queryUser(Integer.parseInt(mList.get(j).getUserName())).getName();
                                String userDepartment = DbUtils.queryUser(Integer.parseInt(mList.get(j).getUserName())).getDepartment();
                                if (userName != null)
                                    myViewHolder.tv.setText(userName);
                                myViewHolder.doorNumber.setText("柜门号:" + doorNumber);
                                myViewHolder.doorDepartment.setText(userDepartment);
                            }
                        }
                }
                //    String userName=DbUtils.queryUser(Long.getLong(mList.get(i).getUserName())).getName();
            }
        }
    }
    public void setListData(List<Feature> listData) {
        mList = listData;
//        Log.d("user_data",mList.get(0).getUserId());
    }

    @Override
    public int getItemCount() {
        return DbUtils.getDoorNumber() != 0 ? doorNumber : 20;
        //return mList != null ? mList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;
        TextView doorNumber;
        TextView doorDepartment;

        public MyViewHolder(View view) {
            super(view);
            iv=view.findViewById(R.id.door_user_image);
            tv = (TextView) view.findViewById(R.id.door_name);
            doorNumber = (TextView) view.findViewById(R.id.door_number);
            doorDepartment = (TextView) view.findViewById(R.id.door_department);
        }
    }
}

