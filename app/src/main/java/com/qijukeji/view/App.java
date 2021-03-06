package com.qijukeji.view;

import android.app.Application;
import android.content.Context;

import com.amap.api.navi.AMapNavi;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.PlatformConfig;

import cn.jpush.android.api.JPushInterface;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AMapNavi.setApiKey(this, "c16376d3cb2198c73e6b2ca9d7b11587");
        PlatformConfig.setWeixin("wx8974a8e5df87e1ff", "69d8cd550259f86fd18629ce26fad7ac");
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
