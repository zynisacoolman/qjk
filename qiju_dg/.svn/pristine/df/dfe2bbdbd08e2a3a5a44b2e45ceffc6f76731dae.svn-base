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
    private String staffUuid;

    public MainModel(Context context, Handler handler, String staffid, String staffUuid) {
        this.context = context;
        this.handler = handler;
        this.staffid = staffid;
        this.staffUuid = staffUuid;
    }

    /**
     * 访问意向
     *
     * @param httpIntention handler 返回值
     */
    public void toIntentionHttp(int httpIntention, int page) {
        JSONObject json = new JSONObject();
        try {
            json.put("status", 0);
            json.put("staffUuid", staffUuid);
            json.put("page", page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("json", json.toString());
        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_INTENTION + "?staffuuid=" + staffUuid + "&staffid=", staffid, json, handler, httpIntention);
    }

    /**
     * 访问成单
     *
     * @param httpFinish handler 返回值
     */
    public void toFinishHttp(int httpFinish, int page) {
        JSONObject json = new JSONObject();
        try {
            json.put("status", 1);
            json.put("staffUuid", staffUuid);
            json.put("page", page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("json", json.toString());
        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_INTENTION + "?staffuuid=" + staffUuid + "&staffid=", staffid, json, handler, httpFinish);
    }


}
