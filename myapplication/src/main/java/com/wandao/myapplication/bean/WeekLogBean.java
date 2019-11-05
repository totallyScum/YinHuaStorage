package com.wandao.myapplication.bean;

public class WeekLogBean {
    private long id;        //id
    private String department; //部门
    private String name;  //姓名
    private int doorNumber; //柜门号
    private int storageNormalTimeTotal;//正常存入次数
    private int delayStorageTimeTotal; //超时存入次数
    private String delayDays;//超时存入日期
    private int notStorageTime;// 未存次数
    private String NotStorageDays;// 未存入日期
    private int fetchNormalTimeTotal;//正常取出总次数
    private int fetchUrgentTimeTotal;//应急取出次数
    private String fetchUrgentDays;//应急取出日期
    private int twiceFetchTime;//两次存取次数
private String twiceFetchDays; //两次存取日期

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

    public int getStorageNormalTimeTotal() {
        return storageNormalTimeTotal;
    }

    public void setStorageNormalTimeTotal(int storageNormalTimeTotal) {
        this.storageNormalTimeTotal = storageNormalTimeTotal;
    }

    public int getDelayStorageTimeTotal() {
        return delayStorageTimeTotal;
    }

    public void setDelayStorageTimeTotal(int delayStorageTimeTotal) {
        this.delayStorageTimeTotal = delayStorageTimeTotal;
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
        return NotStorageDays;
    }

    public void setNotStorageDays(String notStorageDays) {
        NotStorageDays = notStorageDays;
    }

    public int getFetchNormalTimeTotal() {
        return fetchNormalTimeTotal;
    }

    public void setFetchNormalTimeTotal(int fetchNormalTimeTotal) {
        this.fetchNormalTimeTotal = fetchNormalTimeTotal;
    }

    public int getFetchUrgentTimeTotal() {
        return fetchUrgentTimeTotal;
    }

    public void setFetchUrgentTimeTotal(int fetchUrgentTimeTotal) {
        this.fetchUrgentTimeTotal = fetchUrgentTimeTotal;
    }

    public String getFetchUrgentDays() {
        return fetchUrgentDays;
    }

    public void setFetchUrgentDays(String fetchUrgentDays) {
        this.fetchUrgentDays = fetchUrgentDays;
    }

    public int getTwiceFetchTime() {
        return twiceFetchTime;
    }

    public void setTwiceFetchTime(int twiceFetchTime) {
        this.twiceFetchTime = twiceFetchTime;
    }

    public String getTwiceFetchDays() {
        return twiceFetchDays;
    }

    public void setTwiceFetchDays(String twiceFetchDays) {
        this.twiceFetchDays = twiceFetchDays;
    }
}
