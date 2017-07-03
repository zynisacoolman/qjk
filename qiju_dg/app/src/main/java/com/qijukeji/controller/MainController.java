package com.qijukeji.controller;

import android.content.Context;
import android.os.Handler;

import com.qijukeji.model.MainModel;

/**
 * Created by Administrator on 2017/5/1.
 */

public class MainController {
    private Context context;
    private MainModel model;
    private Handler handler;


    public MainController(Context context, Handler handler, String staffid) {
        this.context = context;
        this.handler = handler;
        model = new MainModel(context, handler, staffid);
    }

    public void toIntentionHttp(int httpIntention, int page) {
        model.toIntentionHttp(httpIntention, page);

    }

    public void toFinishHttp(int httpFinish, int page) {
        model.toFinishHttp(httpFinish, page);
    }
}
