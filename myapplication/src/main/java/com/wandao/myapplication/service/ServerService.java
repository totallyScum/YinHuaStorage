package com.wandao.myapplication.service;

import android.app.Service;
import android.content.Entity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;


import com.bumptech.glide.request.Request;
import com.wandao.myapplication.common.Constants;
import com.wandao.myapplication.handler.DownloadFileHandler;
import com.wandao.myapplication.handler.DownloadMonthFileHandler;
import com.wandao.myapplication.handler.DownloadWeekFileHandler;
import com.wandao.myapplication.utils.NetUtils;
import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;
import com.yanzhenjie.andserver.filter.HttpCacheFilter;
import com.yanzhenjie.andserver.website.AssetsWebsite;
import com.yanzhenjie.andserver.website.WebSite;

import org.apache.httpcore.Header;
import org.apache.httpcore.HeaderElement;
import org.apache.httpcore.HttpEntity;
import org.apache.httpcore.HttpException;
import org.apache.httpcore.HttpRequest;
import org.apache.httpcore.HttpResponse;
import org.apache.httpcore.ParseException;
import org.apache.httpcore.entity.StringEntity;
import org.apache.httpcore.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * 作者：leavesC
 * 时间：2018/4/5 16:30
 * 描述：https://github.com/leavesC/AndroidServer
 * https://www.jianshu.com/u/9df45b87cfdf
 */
public class ServerService extends Service implements OnServerChangeListener {

    private Server server;

    private static final String TAG = "ServerService";

    @Override
    public void onCreate() {

        AssetManager mAssetManager = getAssets();
        WebSite wesite = new AssetsWebsite(mAssetManager, "main.html");

//        wesite.intercept(new Request(),);
        server = AndServer.serverBuilder()
                .inetAddress(NetUtils.getLocalIPAddress())  //服务器要监听的网络地址
                .port(Constants.PORT_SERVER) //服务器要监听的端口
                .timeout(10, TimeUnit.SECONDS) //Socket超时时间
                .registerHandler(Constants.GET_FILE, new DownloadFileHandler()) //注册一个文件下载接口
                .registerHandler(Constants.DAY_LOG, new DownloadFileHandler()) //注册一个文件下载接口
                .registerHandler(Constants.WEEK_LOG, new DownloadWeekFileHandler()) //周报表
                .registerHandler(Constants.MONTH_LOG, new DownloadMonthFileHandler()) //月报表
//                .registerHandler(Constants.GET_IMAGE, new DownloadImageHandler()) //注册一个图片下载接口
//                .registerHandler(Constants.POST_JSON, new JsonHandler()) //注册一个Post Json接口
//                .registerHandler(Constants.MAIN,new MainHandler())
                .filter(new HttpCacheFilter()) //开启缓存支持
                .website(wesite)
                .listener(new Server.ServerListener() {  //服务器监听接口
                    @Override
                    public void onStarted() {
                        String hostAddress = server.getInetAddress().getHostAddress();
                        Log.e(TAG, "onStarted : " + hostAddress);
                        ServerPresenter.onServerStarted(ServerService.this, hostAddress);


                    }

                    @Override
                    public void onStopped() {
                        Log.e(TAG, "onStopped");
                        ServerPresenter.onServerStopped(ServerService.this);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "onError : " + e.getMessage());
                        ServerPresenter.onServerError(ServerService.this, e.getMessage());
                    }
                })
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startServer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopServer();
    }

    private void startServer() {
        //如果Server已启动则不再重复启动，此时只是向外发布已启动的状态
        if (server.isRunning()) {
            InetAddress inetAddress = server.getInetAddress();
            if (inetAddress != null) {
                String hostAddress = inetAddress.getHostAddress();
                if (!TextUtils.isEmpty(hostAddress)) {
                    ServerPresenter.onServerStarted(ServerService.this, hostAddress);
                }
            }
        } else {
            server.startup();
        }
    }

    private void stopServer() {
        if (server != null && server.isRunning()) {
            server.shutdown();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onServerStarted(String ipAddress) {

    }

    @Override
    public void onServerStopped() {

    }

    @Override
    public void onServerError(String errorMessage) {

    }
}
