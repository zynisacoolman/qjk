package com.qijukeji.model;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.qijukeji.entityModel.Checkorder;
import com.qijukeji.entityModel.Checkorderactivity;
import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.HttpUtil;
import com.qijukeji.utils.JsonToObjUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/5/16.
 */

public class UserDetailsModel {
    private Context context;
    private Handler handler;
    private String staffid;

    public UserDetailsModel(Context context, Handler handler, String staffid) {
        this.context = context;
        this.handler = handler;
        this.staffid = staffid;
    }

    public void toHttpSelect(int httpSelect, String uuid, String source) {
        JSONObject json = new JSONObject();
        try {
            json.put("uuid", uuid);
            json.put("source", source);
            json.put("staffid", staffid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("123", json.toString());
        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_SELECTYXDDXX, staffid, json, handler, httpSelect);
    }

    public void toMoneyHttp(int httpMoney, String activityid, String brandid) {
        JSONObject json = new JSONObject();
        try {
            json.put("activityid", activityid);
            json.put("brandid", brandid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("123", json.toString() + "----------");
        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_ZJE, staffid, json, handler, httpMoney);
    }


    public void toHttpInsert(Checkorder jsonCheckorder, Checkorderactivity chec, int httpInsert) {
        Log.e("123", "----jsonCheckorder-----" + jsonCheckorder.toString() + "-----Checkorderactivity-------" + chec.toString());
        JSONObject json = toJson(jsonCheckorder, chec);
        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_SAVE_URL, staffid, json, handler, httpInsert);
    }

    private JSONObject toJson(Checkorder jsonCheckorder, Checkorderactivity chec) {
        JSONObject json = new JSONObject();
        JSONObject checkorderJson;
        try {
            json.put("sum", 1);
            json.put("1", JsonToObjUtil.objToJson(chec));
            checkorderJson = JsonToObjUtil.objToJson(jsonCheckorder);
            json.put("member", checkorderJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public void toHttpUpdate(Checkorder jsonCheckorder, int httpUpdate) {
        JSONObject json = JsonToObjUtil.objToJson(jsonCheckorder);
        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_XGDDCX, staffid, json, handler, httpUpdate);
    }

    public void toHttpFinish(String uuid, String userid, String activityid, String ordernumber, String staffUuid, String couponid, String staffid, String brandid, String tenantUuid, String companyid, String paymoney, String total, int httpFinish) {
        JSONObject json = new JSONObject();
        try {
            json.put("uuid", uuid);
            json.put("userid", userid);
            json.put("activityid", activityid);
            json.put("ordernumber", ordernumber);
            json.put("staffUuid", staffUuid);
            json.put("couponid", couponid);
            json.put("paymoney", paymoney);
            json.put("totalmoney", total);
            json.put("staffid", staffid);
            json.put("brandid", brandid);
            json.put("tenantUuid", tenantUuid);
            json.put("companyid", companyid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("staffUuid", json.toString());
        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_YXDDCD, staffid, json, handler, httpFinish);
    }

//    public void toHttpSelEvaluate(int i, int httpSelEvaluate) {
//        JSONObject json = new JSONObject();
//        try {
//            json.put("type", i);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Log.e("123", json.toString());
//        HttpUtil.VolleyHttpJsonArrayPost(context, ConstantValues.HTTP_SEL_EVALUATE, staffid, json, handler, httpSelEvaluate);
//    }

//    //保存评价
//    public void toHttpeEvaluate(JSONObject json, int httpInsentEvaluate) {
//        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_INS_EVALUATE, staffid, json, handler, httpInsentEvaluate);
//    }
}
