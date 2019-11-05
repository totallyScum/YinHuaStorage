package com.wandao.myapplication.network.request;

import com.wandao.myapplication.network.response.LogResponse;
import com.wandao.myapplication.network.response.Response;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by Zaifeng on 2018/2/28.
 * 封装请求的接口
 */

public interface Request {

    public static String HOST = "http://192.168.3.201:30135/";
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @PUT("BoxsLogCollect/NewLog")
    Observable<Response<String>> newLog(@Body LogRequestBody logRequestBody);

}
