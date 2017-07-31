package com.qijukeji.controller;

import android.content.Context;
import android.os.Handler;

import com.google.gson.JsonObject;
import com.qijukeji.model.StatementModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/5/19.
 */

public class StatementController {
    private Context context;
    private StatementModel statementModel;
    private Handler handler;

    public StatementController(Context context, Handler handler, String staffid) {
        this.context = context;
        this.handler = handler;
        statementModel = new StatementModel(context, handler, staffid);
    }

    public void toHttpSharetimes(int SharetimesHttp, JSONObject json) {
        statementModel.toShareTimes(SharetimesHttp, json);
    }

    public void toHttpOrdernum(int ordernumHttp, JSONObject json) {
        if (ordernumHttp == 0) statementModel.toOrdernum0(ordernumHttp, json);
        if (ordernumHttp == 1) statementModel.toOrdernum1(ordernumHttp, json);
        if (ordernumHttp == 2) statementModel.toOrdernum2(ordernumHttp, json);
    }
}
