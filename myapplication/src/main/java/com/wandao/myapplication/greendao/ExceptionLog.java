package com.wandao.myapplication.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ExceptionLog {
    @Id(autoincrement = true)
    Long logId;                 //日志id
    int boxId;             //箱子ID
    long userId;             //用户工号
    int status;         //标记动作状态    1迟到 2早退 3没关柜门
    long time;   //取件时间
    String remark;   //行为记录
    @Generated(hash = 1632668876)
    public ExceptionLog(Long logId, int boxId, long userId, int status, long time,
            String remark) {
        this.logId = logId;
        this.boxId = boxId;
        this.userId = userId;
        this.status = status;
        this.time = time;
        this.remark = remark;
    }
    @Generated(hash = 1357110165)
    public ExceptionLog() {
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
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

}
