package com.wandao.myapplication.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.sql.Time;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class Log {

    private final static int FEATCH_NORMAL_STATUS = 0;  // 正常开门
    private final static int STORAGE_NORMAL_STATUS = 1;  // 正常关门
    private final static int FEATCH_LATE_STATUS = 2;   //存件晚
    private final static int FEATCH_EXPECTION_STATUS = 3; //紧急取件 （特权取件）
    private final static int NOT_STORAGE_STATUS=4;//未存
    private final  static  int TWICE_STORAGE=5;  //两次操作
@Id(autoincrement = true)
     Long logId;                 //日志id
      int boxId;             //箱子ID
     long userId;             //用户工号
     int status;         //标记动作状态
     long time;   //取件时间
    String actionUserName;   //行为人姓名


    @Generated(hash = 586114780)
    public Log(Long logId, int boxId, long userId, int status, long time,
            String actionUserName) {
        this.logId = logId;
        this.boxId = boxId;
        this.userId = userId;
        this.status = status;
        this.time = time;
        this.actionUserName = actionUserName;
    }
    @Generated(hash = 1364647056)
    public Log() {
    }
    public Long getLogId() {
        return this.logId;
    }
    public void setLogId(Long logId) {
        this.logId = logId;
    }
    public int getBoxId() {
        return this.boxId;
    }
    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }
    public long getUserId() {
        return this.userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public Long getTime() {
        return this.time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public String getActionUserName() {
        return this.actionUserName;
    }
    public void setActionUserName(String actionUserName) {
        this.actionUserName = actionUserName;
    }



}
