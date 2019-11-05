package com.wandao.myapplication.network.response;

import com.google.gson.annotations.SerializedName;

public class LogResponse {
    @SerializedName("status_code")
    String statusCode;
    @SerializedName("message")
    String message;
    @SerializedName("id")
    String id;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LogResponse{" +
                "statusCode='" + statusCode + '\'' +
                ", message='" + message + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
