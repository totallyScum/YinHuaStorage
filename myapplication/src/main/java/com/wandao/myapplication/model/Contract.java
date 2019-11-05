package com.wandao.myapplication.model;
import com.wandao.myapplication.network.request.LogRequestBody;
import com.wandao.myapplication.network.request.Request;
import com.wandao.myapplication.network.response.LogResponse;
import com.wandao.myapplication.network.response.Response;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;


/**
 * Created by Zaifeng on 2018/3/1.
 */

public class Contract {

    public interface Persenter {
        public void getCarList(String userId);
    }

    public interface View {
        void getDataSuccess(Response<String> msg);
        void getDataFail(String s);
    }

    public interface Model {
        public Observable<Response<String>> getLogResponse(LogRequestBody logRequestBody);
    }

}
