package com.qijukeji.controller;

import android.content.Context;
import android.os.Handler;

import com.qijukeji.model.FindModel;

/**
 * Created by Administrator on 2017/5/15.
 */

public class FindController {
    private Context context;
    private FindModel findModel;
    private Handler handler;

    public FindController(Context context, Handler handler, String staffid) {
        this.context = context;
        this.handler = handler;
        findModel = new FindModel(context, handler, staffid);
    }

    public void toFindHttp(int FindHttp,int page) {
        findModel.toHttpFind(FindHttp,page);
    }
}
