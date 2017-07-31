package com.qijukeji.model;

import android.content.Context;
import android.os.Handler;

import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/5/16.
 */

public class FindModel {
    private Context context;
    private Handler handler;
    private String staffid;

    public FindModel(Context context, Handler handler, String staffid) {
        this.context = context;
        this.handler = handler;
        this.staffid = staffid;

    }

    public void toHttpFind(int FindHttp, int page) {
        JSONObject json = new JSONObject();
        try {
            json.put("page", page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_SEL_ACTIVITY, staffid, json, handler, FindHttp);
    }
}
