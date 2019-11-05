package com.wandao.myapplication.presenter;

import android.util.Log;

import com.wandao.myapplication.model.Contract;
import com.wandao.myapplication.network.request.LogRequestBody;
import com.wandao.myapplication.network.response.LogResponse;
import com.wandao.myapplication.network.response.ResponseTransformer;
import com.wandao.myapplication.network.schedulers.BaseSchedulerProvider;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zaifeng on 2018/3/1.
 */

public class Presenter {

    private Contract.Model model;

    private Contract.View view;

    private BaseSchedulerProvider schedulerProvider;

    private CompositeDisposable mDisposable;
    private LogResponse logResponse2;

    public Presenter(Contract.Model model,
                     Contract.View view,
                     BaseSchedulerProvider schedulerProvider) {
        this.model = model;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        mDisposable = new CompositeDisposable();
    }

    public void despose() {
        mDisposable.dispose();
    }


    public void putLogRequest(LogRequestBody logRequestBody) {
        Disposable disposable = model.getLogResponse(logRequestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(carBeans -> {
                    // 处理数据 直接获取到List<JavaBean> carBeans
                    view.getDataSuccess(carBeans);
                }, throwable -> {
                    // 处理异常
                    view.getDataFail(throwable.getMessage());
                });
        mDisposable.add(disposable);
}
}






