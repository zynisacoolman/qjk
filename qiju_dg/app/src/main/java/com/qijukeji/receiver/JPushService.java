package com.qijukeji.receiver;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Administrator on 2017/6/29.
 */

public class JPushService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences preferences = getSharedPreferences("qiju", Context.MODE_PRIVATE);
        String staffUuid = preferences.getString("staffUuid", "");
        JPushInterface.setAlias(this, staffUuid, new TagAliasCallback() {
            @Override
            public void gotResult(int code, String s, Set<String> set) {
                switch (code) {
                    case 0:
                        Log.e("pushinfo", "success");
                        break;
                }
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
