package com.wandao.myapplication.utils;

import android.content.Context;

import com.wandao.myapplication.greendao.DaoMaster;
import com.wandao.myapplication.greendao.Log;

import org.greenrobot.greendao.database.Database;

/**
 * @作者:TJ
 * @时间:2018-11-01
 * @描述:数据库辅助类
 */
public class DBHelper extends DaoMaster.DevOpenHelper {


    public DBHelper(Context context) {
        super(context, "greenDao.db", null);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //  2018-11-02  需要进行数据迁移更新的实体类 ，新增的不用加
        //DBMigrationHelper.getInstance().migrate(db, Log.class, LogWithRemark.class);
    }
}
