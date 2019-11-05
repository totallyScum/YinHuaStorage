package com.wandao.myapplication.network.request;

import com.google.gson.annotations.SerializedName;

public class LogRequestBody {
    @SerializedName("BoxIp")
    public String BoxIp;
    @SerializedName("LogID")
    public String logID;
    @SerializedName("DoorID")
    public String doorID;
    @SerializedName("UserID")
    public String userID;
    @SerializedName("UserName")
    public String userName;
    @SerializedName("DepsName")
    public String depsName;
    @SerializedName("Status")
    public String status;
    @SerializedName("Time")
    public Long time;
    @SerializedName("actionUserName")
    public String actionUserName;


    public String getDepsName() {
        return depsName;
    }

    public void setDepsName(String depsName) {
        this.depsName = depsName;
    }

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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getActionUserName() {
        return actionUserName;
    }

    public void setActionUserName(String actionUserName) {
        this.actionUserName = actionUserName;
    }

    @Override
    public String toString() {
        return "LogRequestBody{" +
                "BoxIp='" + BoxIp + '\'' +
                ", logID='" + logID + '\'' +
                ", doorID='" + doorID + '\'' +
                ", userID='" + userID + '\'' +
                ", userName='" + userName + '\'' +
                ", depsName='" + depsName + '\'' +
                ", status='" + status + '\'' +
                ", time=" + time +
                ", actionUserName='" + actionUserName + '\'' +
                '}';
    }
}
