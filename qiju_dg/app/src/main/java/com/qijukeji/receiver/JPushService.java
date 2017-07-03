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

    public static final int HTTP_JPUSH = 0;
    private Context context;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HTTP_JPUSH:
                    new Thread(new JPushService.JPush()).start();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        handler.sendEmptyMessage(HTTP_JPUSH);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class JPush implements Runnable {

        @Override
        public void run() {
            SharedPreferences preferences = getSharedPreferences("qiju", Context.MODE_PRIVATE);
            String staffUuid = preferences.getString("staffUuid", "");
            JPushInterface.setAlias(context, staffUuid, new TagAliasCallback() {
                @Override
                public void gotResult(int code, String s, Set<String> set) {
                    switch (code) {
                        case 0:
                            Log.e("pushinfo", "success");
                            break;
                    }
                }
            });
//            handler.sendEmptyMessageDelayed(HTTP_JPUSH, 1000);
        }
    }
}
