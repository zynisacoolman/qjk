package com.qijukeji.model;

import android.content.Context;
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
        Log.e("123", ConstantValues.HTTP_REGISTER_PLTENT);
        HttpUtil.VolleyHttpGetRe(context, ConstantValues.HTTP_REGISTER_PLTENT, handler, msgWhat);
//        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_REGISTER_PLTENT, handler, RegisterActivity.HTTP_PLTEXT);
    }

    public void toHttpStorelist(JSONObject json, int msgWhat) {
        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_REGISTER_STORELIST, "", json, handler, msgWhat);
    }

    public void toHttpRegister(JSONObject json, int httpRegister, String phone) {
        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_REGISTER, phone, json, handler, httpRegister);

    }
}
