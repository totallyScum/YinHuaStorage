package com.wandao.myapplication.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zaifeng on 2018/2/28.
 * 返回结果封装
 */

public class Response<T> {


    @SerializedName("status_code")
    private int ret; // 返回的code
    @SerializedName("id")
    private T data; // 具体的数据结果
    @SerializedName("message")
    private String msg; // message 可用来返回接口的说明

    public int getCode() {
        return ret;
    }

    public void setCode(int code) {
        this.ret = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
