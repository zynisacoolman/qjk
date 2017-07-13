package com.qijukeji.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.HttpUtil;
import com.qijukeji.view.RegisterActivity;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/5/5.
 */

public class RegisterModel {
    private Context context;
    private Handler handler;

    public RegisterModel(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    public void toHttpPltext(int msgWhat) {
        SharedPreferences preferences = context.getSharedPreferences("qiju", Context.MODE_PRIVATE);
        String staffid = preferences.getString("staffid", "");
        String staffUuid = preferences.getString("staffUuid", "");
        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_REGISTER_PLTENT + "?staffuuid=" + staffUuid + "&staffid=", staffid, null, handler, msgWhat);
    }

    public void toHttpStorelist(JSONObject json, int msgWhat) {
        SharedPreferences preferences = context.getSharedPreferences("qiju", Context.MODE_PRIVATE);
        String staffid = preferences.getString("staffid", "");
        String staffUuid = preferences.getString("staffUuid", "");
        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_REGISTER_STORELIST + "?staffuuid=" + staffUuid + "&staffid=" + staffid, "", json, handler, msgWhat);
    }

    public void toHttpRegister(JSONObject json, int httpRegister, String phone) {
        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_REGISTER, phone, json, handler, httpRegister);

    }
}
