package com.wandao.myapplication.bean;

public class LogBean {
    private String name;  //姓名
    private long id;        //id
    private boolean unusual;   //是否异常取件
    private String storeTime;   //存件时间
    private String pickTime;     //取件时间
    private int boxNumber;       //柜号

    public LogBean(String name, long id, boolean unusual, String storeTime, String pickTime, int boxNumber) {
        this.name = name;
        this.id = id;
        this.unusual = unusual;
        this.storeTime = storeTime;
        this.pickTime = pickTime;
        this.boxNumber = boxNumber;
    }

    public int getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(int boxNumber) {
        this.boxNumber = boxNumber;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isUnusual() {
        return unusual;
    }

    public void setUnusual(boolean unusual) {
        this.unusual = unusual;
    }

    public String getStoreTime() {
        return storeTime;
    }

    public void setStoreTime(String storeTime) {
        this.storeTime = storeTime;
    }

    public String getPickTime() {
        return pickTime;
    }

    public void setPickTime(String pickTime) {
        this.pickTime = pickTime;
    }
}
