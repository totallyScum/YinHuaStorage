package com.wandao.myapplication.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class Box {
    @Unique
@Id
    Long boxId;
    @NotNull
    long storageTime;     //存物品时间
    long fetchTime;   //取物品时间
    int status;     //0箱内空 1 箱内有手机 2二次存入箱空 3二次存入箱内有手机  4节假日,5  柜门坏
    boolean bind;
    @Generated(hash = 1916468963)
    public Box(Long boxId, long storageTime, long fetchTime, int status,
            boolean bind) {
        this.boxId = boxId;
        this.storageTime = storageTime;
        this.fetchTime = fetchTime;
        this.status = status;
        this.bind = bind;
    }
    @Generated(hash = 1015730713)
    public Box() {
    }
    public Long getBoxId() {
        return this.boxId;
    }
    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }
    public long getStorageTime() {
        return this.storageTime;
    }
    public void setStorageTime(long storageTime) {
        this.storageTime = storageTime;
    }
    public long getFetchTime() {
        return this.fetchTime;
    }
    public void setFetchTime(long fetchTime) {
        this.fetchTime = fetchTime;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public boolean getBind() {
        return this.bind;
    }
    public void setBind(boolean bind) {
        this.bind = bind;
    }


}
