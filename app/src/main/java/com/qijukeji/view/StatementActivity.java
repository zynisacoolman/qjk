package com.qijukeji.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qijukeji.controller.StatementController;
import com.qijukeji.qiju_dg.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/8.
 */

public class StatementActivity extends AppCompatActivity {

    @Bind(R.id.iv_title_right)
    ImageView iv_title_right;
    @Bind(R.id.tv_title_name)
    TextView title;
    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.select_title_show)
    RelativeLayout select_title_show;
    @Bind(R.id.myself_order)
    TextView myselfOrder;
    @Bind(R.id.first_order)
    TextView firstOrder;
    @Bind(R.id.last_order)
    TextView lastOrder;
    private String staffid;
    private String staffUuid;
    private String brandid;
    private String start_time;
    private String end_time;
    private String type;
    private StatementController controller0, controller1, controller2;
    private static final int HTTP_OVER_ORDER_ONE = 0;
    private static final int HTTP_OVER_ORDER_TWO = 1;
    private static final int HTTP_OVER_ORDER_THREE = 2;
    Handler handler0 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String datas = msg.obj.toString();
            Log.e("datas0", datas);
            switch (msg.what) {
                case HTTP_OVER_ORDER_ONE:
                    setOrderOne(datas);
                    break;
            }
        }
    };

    private void setOrderOne(String datas) {
        try {
            JSONObject json = new JSONObject(datas);
            myselfOrder.setText(json.getString("list") + "人");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String datas = msg.obj.toString();
            Log.e("datas1", datas);
            switch (msg.what) {
                case HTTP_OVER_ORDER_TWO:
                    setOrderTwo(datas);
                    break;
            }
        }
    };

    private void setOrderTwo(String datas) {
        try {
            JSONObject json = new JSONObject(datas);
            firstOrder.setText(json.getString("list") + "人");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String datas = msg.obj.toString();
            Log.e("datas2", datas);
            switch (msg.what) {
                case HTTP_OVER_ORDER_THREE:
                    setOrderThree(datas);
                    break;
            }
        }
    };

    private void setOrderThree(String datas) {
        try {
            JSONObject json = new JSONObject(datas);
            lastOrder.setText(json.getString("list") + "人");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        staffid = intent.getStringExtra("staffid");
        staffUuid = intent.getStringExtra("staffUuid");
        brandid = intent.getStringExtra("brandid");
        start_time = intent.getStringExtra("start_time");
        end_time = intent.getStringExtra("end_time");
        type = intent.getStringExtra("type");

        Log.e("start_time", start_time + "end" + end_time + "   " + staffUuid);
        Log.e("type", type);
        initView();
    }

    private void initView() {
        controller0 = new StatementController(this, handler0, staffid);
        controller1 = new StatementController(this, handler1, staffid);
        controller2 = new StatementController(this, handler2, staffid);
        iv_title_right.setVisibility(View.INVISIBLE);
        title.setText("核券详情");
        title.setVisibility(View.VISIBLE);
        select_title_show.setVisibility(View.GONE);
        JSONObject json = new JSONObject();
        try {
            json.put("staffid", staffid);
            json.put("staffUuid", staffUuid);
            json.put("begindateTime", start_time);
            json.put("enddateTime", end_time);
            json.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        controller0.toHttpOrdernum(HTTP_OVER_ORDER_ONE, json);
        controller1.toHttpOrdernum(HTTP_OVER_ORDER_TWO, json);
        controller2.toHttpOrdernum(HTTP_OVER_ORDER_THREE, json);
    }

    @OnClick(R.id.iv_title_back)
    public void OnClick() {
        finish();
    }
}
