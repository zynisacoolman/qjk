package com.qijukeji.utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.qijukeji.customView.TakePhotoPopWin;
import com.qijukeji.entityModel.Comment;
import com.qijukeji.qiju_dg.R;
import com.qijukeji.view.ThemeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */

public class EvaluateUtils {
    private TakePhotoPopWin takePhotoPopWin;
    private String typeEvaluate, uuid, staffid, userid, content;
    private List<Object> listEvaluate;
    private Handler handler;
    private int httpInsentEvaluate;
    private Context context;

    public EvaluateUtils(String uuid, String staffid, String userid, Handler handler, int httpInsentEvaluate, Context context) {

        this.uuid = uuid;
        this.staffid = staffid;
        this.userid = userid;
        this.handler = handler;
        this.httpInsentEvaluate = httpInsentEvaluate;
        this.context = context;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public EvaluateUtils(String staffid, Handler handler, int httpInsentEvaluate, Context context) {

        this.staffid = staffid;
        this.handler = handler;
        this.httpInsentEvaluate = httpInsentEvaluate;
        this.context = context;
    }


    //调用底部评价
    public void totEvaluate(String data, View view) {
        List<Object> listComment = JsonToObjUtil.jsonArrayToListObj(data, Comment.class);
        takePhotoPopWin = new TakePhotoPopWin(context, onClickListener, listComment, staffid);
        takePhotoPopWin.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    /**
     * 点击评价按钮
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_submit:
                    try {
                        bt_submit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 提交按钮
     */
    private void bt_submit() throws JSONException {
        JSONObject json = new JSONObject();
        typeEvaluate = takePhotoPopWin.getType();
        json.put("type", typeEvaluate);
        json.put("uuid", uuid);
        json.put("staffid", staffid);
        json.put("userid", userid);
        if (typeEvaluate.equals("3")) {
            content = takePhotoPopWin.getContent();
            json.put("content", content);
            if (content.equals("")) {
                return;
            }
        } else if (typeEvaluate.equals("2") || (typeEvaluate.equals("1"))) {
            listEvaluate = new ArrayList<>();
            HashMap hm = takePhotoPopWin.gethm();
            List<Object> listComment = takePhotoPopWin.getList();

            Iterator iter = hm.keySet().iterator();

            json.put("sum", hm.size() + "");
            if (hm.size() == 0) {
                return;
            }
            int i = 1;
            while (iter.hasNext()) {
                Object key = iter.next();
                int val = (int) hm.get(key);
                Comment c = (Comment) listComment.get(val);
                listEvaluate.add(c);
                json.put(i + "", c.getUuid());
                i++;
            }
        }
        Log.e("评价内容", json.toString());
        takePhotoPopWin.dismiss();
        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_INS_EVALUATE, staffid, json, handler, httpInsentEvaluate);
    }

    public void toHttpSelEvaluate(int i, int httpSelEvaluate) {
        JSONObject json = new JSONObject();
        try {
            json.put("type", i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtil.VolleyHttpJsonArrayPost(context, ConstantValues.HTTP_SEL_EVALUATE, staffid, json, handler, httpSelEvaluate);
    }


}
