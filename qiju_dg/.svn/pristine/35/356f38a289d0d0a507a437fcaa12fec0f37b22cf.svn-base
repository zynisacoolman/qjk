package com.qijukeji.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.HttpUtil;
import com.qijukeji.view.RegisterActivity;

import org.json.JSONException;
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

    public void toHttpPltext(int msgWhat, JSONObject json) {
        SharedPreferences preferences = context.getSharedPreferences("qiju", Context.MODE_PRIVATE);
        String staffid = preferences.getString("staffid", "");
        String staffUuid = preferences.getString("staffUuid", "");
        if (json == null) {
            HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_REGISTER_PLTENT, staffid, json, handler, msgWhat);
        } else {
            HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_UUID_PLTENT + "?staffuuid=" + staffUuid + "&staffid=", staffid, json, handler, msgWhat);
        }
    }

    public void toHttpStorelist(JSONObject json, int msgWhat) {
        SharedPreferences preferences = context.getSharedPreferences("qiju", Context.MODE_PRIVATE);
        String staffid = preferences.getString("staffid", "");
        String staffUuid = preferences.getString("staffUuid", "");
        int num = json.length();
        if (num < 2) {
            HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_REGISTER_STORELIST, staffid, json, handler, msgWhat);
        } else {
            HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_UUID_STORELIST + "?staffuuid=" + staffUuid + "&staffid=", staffid, json, handler, msgWhat);
        }
    }

    public void toHttpRegister(JSONObject json, int httpRegister, String phone) {
        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_REGISTER, phone, json, handler, httpRegister);

    }
}
