package com.qijukeji.controller;

import android.content.Context;
import android.os.Handler;

import com.qijukeji.entityModel.Checkorder;
import com.qijukeji.entityModel.Checkorderactivity;
import com.qijukeji.model.UserDetailsModel;

/**
 * Created by Administrator on 2017/5/16.
 */

public class UserDetailsController {
    private UserDetailsModel model;
    private Context context;
    private Handler handler;
    private String staffid;

    public UserDetailsController(Context context, Handler handler, String staffid) {
        this.context = context;
        this.handler = handler;
        this.staffid = staffid;
        model = new UserDetailsModel(context, handler, staffid);
    }

    public void toHttpSelect(int httpSelect, String uuid, String source) {
        model.toHttpSelect(httpSelect, uuid, source);
    }

    public void toMoneyHttp(int httpMoney, String activityid, String brandid) {
        model.toMoneyHttp(httpMoney, activityid, brandid);
    }

    public void toHttpInsert(Checkorder jsonCheckorder, Checkorderactivity chec, int httpInsert) {
        model.toHttpInsert(jsonCheckorder, chec, httpInsert);
    }

    public void toHttpUpdate(Checkorder jsonCheckorder, int httpUpdate) {
        model.toHttpUpdate(jsonCheckorder, httpUpdate);
    }

    public void toHttpFinish(String uuid, String userid, String activityid, String ordernumber, String staffUuid, String couponid, String staffid, String brandid, String tenantUuid, String companyid, String paymoney, String total, int httpFinish) {
        model.toHttpFinish(uuid, userid, activityid, ordernumber, staffUuid, couponid, staffid, brandid, tenantUuid, companyid, paymoney, total, httpFinish);
    }

//    public void toHttpSelEvaluate(int i, int httpSelEvaluate) {
//        model.toHttpSelEvaluate(i,httpSelEvaluate);
//    }
//
//    public void toHttpeEvaluate(JSONObject json, int httpInsentEvaluate) {
//        model.toHttpeEvaluate(json, httpInsentEvaluate);
//    }

    public void toreturnOrder(String brandid, String time, String total, Double discount, String ordernumber) {

    }
}
