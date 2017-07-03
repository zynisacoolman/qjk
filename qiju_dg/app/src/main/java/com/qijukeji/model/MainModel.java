package com.qijukeji.model;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/5/1.
 */

public class MainModel {
    private Context context;
    private Handler handler;
    private String staffid;

    public MainModel() {
    }

    public MainModel(Context context, Handler handler, String staffid) {
        this.context = context;
        this.handler = handler;
        this.staffid = staffid;
    }

    /**
     * 访问意向
     *
     * @param httpIntention handler 返回值
     */
    public void toIntentionHttp(int httpIntention, int page) {
        JSONObject json = new JSONObject();
        try {
            json.put("staffid", staffid);
            json.put("page", page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtil.VolleyHttpJsonArrayPost(context, ConstantValues.HTTP_INTENTION, staffid, json, handler, httpIntention);
    }

    /**
     * 访问成单
     *
     * @param httpFinish handler 返回值
     */
    public void toFinishHttp(int httpFinish, int page) {
        JSONObject json = new JSONObject();
        try {
            json.put("staffid", staffid);
            json.put("page", page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("123", page + "------");
        HttpUtil.VolleyHttpJsonArrayPost(context, ConstantValues.HTTP_FINISH, staffid, json, handler, httpFinish);
    }


}
