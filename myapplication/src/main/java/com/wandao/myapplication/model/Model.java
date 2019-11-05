package com.wandao.myapplication.model;


import com.wandao.myapplication.network.NetWorkManager;
import com.wandao.myapplication.network.request.LogRequestBody;
import com.wandao.myapplication.network.response.LogResponse;
import com.wandao.myapplication.network.response.Response;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Created by Zaifeng on 2018/3/1.
 */

public class Model implements Contract.Model {
    @Override
    public Observable<Response<String>> getLogResponse(LogRequestBody logRequestBody) {
        return NetWorkManager.getRequest().newLog(logRequestBody);
    }


}
