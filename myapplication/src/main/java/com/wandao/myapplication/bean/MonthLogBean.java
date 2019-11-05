package com.wandao.myapplication.bean;

public class MonthLogBean {
    private long id;        //id
    private String department; //部门
    private String name;  //姓名
    private int doorNumber; //柜门号
    private int monthTimeTotal;//本月汇总
    private int normalTimeTotal; //正常存入次数
    private int delayTimeTotal;// 超时存入次数
    private String delayDays;//超时存入日期
    private int notStorageTime;// 未存次数
    private String notStorageDays;// 未存日期

    private int  fetchNormalTime;// 正常取出
    private int  urgentFetchTime;// 应急取出次数
    private String urgentFetchDays;// 应急取出日期
    private int twiceStorageTime;// 二次存取次数
    private String twiceStorageDays;// 二次存取日期

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(int doorNumber) {
        this.doorNumber = doorNumber;
    }

    public int getMonthTimeTotal() {
        return monthTimeTotal;
    }

    public void setMonthTimeTotal(int monthTimeTotal) {
        this.monthTimeTotal = monthTimeTotal;
    }

    public int getNormalTimeTotal() {
        return normalTimeTotal;
    }

    public void setNormalTimeTotal(int normalTimeTotal) {
        this.normalTimeTotal = normalTimeTotal;
    }

    public int getDelayTimeTotal() {
        return delayTimeTotal;
    }

    public void setDelayTimeTotal(int delayTimeTotal) {
        this.delayTimeTotal = delayTimeTotal;
    }

    public String getDelayDays() {
        return delayDays;
    }

    public void setDelayDays(String delayDays) {
        this.delayDays = delayDays;
    }

    public int getNotStorageTime() {
        return notStorageTime;
    }

    public void setNotStorageTime(int notStorageTime) {
        this.notStorageTime = notStorageTime;
    }

    public String getNotStorageDays() {
        return notStorageDays;
    }

    public void setNotStorageDays(String notStorageDays) {
        this.notStorageDays = notStorageDays;
    }

    public int getFetchNormalTime() {
        return fetchNormalTime;
    }

    public void setFetchNormalTime(int fetchNormalTime) {
        this.fetchNormalTime = fetchNormalTime;
    }

    public int getUrgentFetchTime() {
        return urgentFetchTime;
    }

    public void setUrgentFetchTime(int urgentFetchTime) {
        this.urgentFetchTime = urgentFetchTime;
    }

    public String getUrgentFetchDays() {
        return urgentFetchDays;
    }

    public void setUrgentFetchDays(String urgentFetchDays) {
        this.urgentFetchDays = urgentFetchDays;
    }

    public int getTwiceStorageTime() {
        return twiceStorageTime;
    }

    public void setTwiceStorageTime(int twiceStorageTime) {
        this.twiceStorageTime = twiceStorageTime;
    }

    public String getTwiceStorageDays() {
        return twiceStorageDays;
    }

    public void setTwiceStorageDays(String twiceStorageDays) {
        this.twiceStorageDays = twiceStorageDays;
    }
}
