package com.qijukeji.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.qijukeji.utils.StaticField;
import com.qijukeji.view.OrderSaveActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

public class HomeReceiver extends BroadcastReceiver {
    private String uuid;
    private String source;

    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        System.out.println("收到了自定义消息。消息内容是：" + message);
        String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        System.out.println("收到了自定义消息。extra是：" + extra);
        if (extra == null || extra.equals("")) {
            return;
        }
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(extra);
            uuid = jsonObject.getString("uuid");
            source = jsonObject.getString("source");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            // 在这里可以做些统计，或者做些其他工作
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            // 在这里可以自己写代码去定义用户点击后的行为
            Intent i = new Intent(context, OrderSaveActivity.class);  //自定义打开的界面
            i.putExtra("uuid", uuid);
            i.putExtra("source", source);
            i.putExtra(StaticField.KEY, 2);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } else {
        }
    }
}
