package com.qijukeji.model;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/5/19.
 */

public class StatementModel {
    private Context context;
    private Handler handler;
    private String staffid;

    public StatementModel(Context context, Handler handler, String staffid) {
        this.context = context;
        this.handler = handler;
        this.staffid = staffid;
    }

    public void toShareTimes(int sharetimesHttp, JSONObject json) {
        Log.e("123", "------json-------" + json.toString());
        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_SHARE_TIMES, staffid, json, handler, sharetimesHttp);
    }

    public void toOrdernum0(int ordernumHttp, JSONObject json) {
        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_OVERORDER_TIMES, staffid, json, handler, ordernumHttp);
    }

    public void toOrdernum1(int ordernumHttp, JSONObject json) {
        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_FIRST_TIMES, staffid, json, handler, ordernumHttp);
    }

    public void toOrdernum2(int ordernumHttp, JSONObject json) {
        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_LAST_TIMES, staffid, json, handler, ordernumHttp);
    }
}
