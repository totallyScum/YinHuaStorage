package com.wandao.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class putLogBean  implements Parcelable {
    @SerializedName("BoxIp")
    private String BoxIp;
    @SerializedName("LogID")
    private String logID;
    @SerializedName("DoorID")
    private String doorID;
    @SerializedName("UserID")
    private String userID;
    @SerializedName("UserName")
    private String userName;

    @SerializedName("Status")
    private String status;
    @SerializedName("Time")
    private String time;
    @SerializedName("actionUserName")
    private String actionUserName;


    public String getBoxIp() {
        return BoxIp;
    }

    public void setBoxIp(String boxIp) {
        BoxIp = boxIp;
    }

    public String getLogID() {
        return logID;
    }

    public void setLogID(String logID) {
        this.logID = logID;
    }

    public String getDoorID() {
        return doorID;
    }

    public void setDoorID(String doorID) {
        this.doorID = doorID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getActionUserName() {
        return actionUserName;
    }

    public void setActionUserName(String actionUserName) {
        this.actionUserName = actionUserName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
