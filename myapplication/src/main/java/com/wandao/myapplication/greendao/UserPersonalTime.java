package com.wandao.myapplication.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserPersonalTime {
    @Unique
    @Id
    Long id;                //设置用户ID
    String startTimeAM;       //上午开始时间
    String endTimeAM;         //结束时间
    String startTimePM;       //上午开始时间
    String endTimePM;         //结束时间
    Boolean defalut;         //是否是默认时间
    @Generated(hash = 608328603)
    public UserPersonalTime(Long id, String startTimeAM, String endTimeAM,
            String startTimePM, String endTimePM, Boolean defalut) {
        this.id = id;
        this.startTimeAM = startTimeAM;
        this.endTimeAM = endTimeAM;
        this.startTimePM = startTimePM;
        this.endTimePM = endTimePM;
        this.defalut = defalut;
    }
    @Generated(hash = 1262430402)
    public UserPersonalTime() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getStartTimeAM() {
        return this.startTimeAM;
    }
    public void setStartTimeAM(String startTimeAM) {
        this.startTimeAM = startTimeAM;
    }
    public String getEndTimeAM() {
        return this.endTimeAM;
    }
    public void setEndTimeAM(String endTimeAM) {
        this.endTimeAM = endTimeAM;
    }
    public String getStartTimePM() {
        return this.startTimePM;
    }
    public void setStartTimePM(String startTimePM) {
        this.startTimePM = startTimePM;
    }
    public String getEndTimePM() {
        return this.endTimePM;
    }
    public void setEndTimePM(String endTimePM) {
        this.endTimePM = endTimePM;
    }
    public Boolean getDefalut() {
        return this.defalut;
    }
    public void setDefalut(Boolean defalut) {
        this.defalut = defalut;
    }

}
