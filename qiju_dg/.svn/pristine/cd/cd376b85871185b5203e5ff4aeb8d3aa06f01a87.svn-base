package com.qijukeji.controller;

import android.content.Context;
import android.os.Handler;

import com.qijukeji.model.RegisterModel;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/5/5.
 */

public class RegisterController {
    private Context context;
    private RegisterModel model;
    private Handler handler;

    public RegisterController(Context context, Handler handler) {
        this.handler = handler;
        this.context = context;
        model = new RegisterModel(context, handler);
    }

    public void toHttpPltext(int msgWhat) {
        model.toHttpPltext(msgWhat);
    }

    public void toHttpStorelist(JSONObject json, int msgWhat) {
        model.toHttpStorelist(json, msgWhat);
    }

    public void toHttpRegister(JSONObject json, int httpRegister, String phone) {
        model.toHttpRegister(json, httpRegister, phone);
    }
}
