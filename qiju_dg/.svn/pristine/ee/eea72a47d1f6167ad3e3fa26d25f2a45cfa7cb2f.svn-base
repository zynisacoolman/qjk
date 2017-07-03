package com.qijukeji.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.HttpUtil;
import com.qijukeji.utils.IntentUtil;
import com.qijukeji.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class BlankPageActivity extends AppCompatActivity {

    private Context context;
    private String phone;
    private String staffUuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_page);
    }

    private void toHttp(String phone, String password) {
        JSONObject json = new JSONObject();
        try {
            json.put("name", phone);
            json.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtil.VolleyHttpPost(this, ConstantValues.HTTP_LOG_IN, phone, json, handler, 1);
    }

    Handler handler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            switch (msg.what) {
                case 1:
                    try {
                        isLogIn(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case -1:
                    Utils.setToast(context, data);
                    toLogIn();
                    break;
                default:
                    break;
            }
        }
    };


    private void isLogIn(String data) throws JSONException {
        if (data.equals("[]")) {
            Utils.setToast(this, "账号和密码不匹配");
        } else {
            JSONObject json = new JSONObject(data);
            if (json.getString("jg").equals("0")) {
                String brandid = json.getString("brandid");
                String companyid = json.getString("companyid");
                staffUuid = json.getString("staffUuid");
                String tenantUuid = json.getString("tenantUuid");
                Bundle b = new Bundle();
                b.putSerializable("staffid", phone);
                b.putSerializable("staffUuid", staffUuid);
                b.putSerializable("brandid", brandid);
                SharedPreferences sp = getSharedPreferences("qiju", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("staffid", phone);
                editor.putString("companyid", companyid);
                editor.putString("staffUuid", staffUuid);
                editor.putString("brandid", brandid);
                editor.putString("tenantUuid", tenantUuid);
                editor.putString("kehutype", "1");
                editor.putString("tc", "1");
                editor.commit();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                IntentUtil.intentToNull(this, ThemeActivity.class, b);
            } else if (json.getString("jg").equals("1")) {
                Utils.setToast(context, "正在审核，请等待");
            } else {
                Utils.setToast(context, "账号或密码错误");
                IntentUtil.intentToNull(this, LonInActivity.class);
            }
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        context = this;
        SharedPreferences preferences = getSharedPreferences("qiju", Context.MODE_PRIVATE);
        phone = preferences.getString("staffid", "");
        String password = preferences.getString("password", "");
        String tc = preferences.getString("tc", "");
        if (tc.equals("0")) {
            SharedPreferences sp = getSharedPreferences("qiju", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("tc", "1");
            editor.commit();
            finish();
        } else if (tc.equals("1")) {
            if (phone.equals("") || phone == null) {
                toLogIn();
            } else {
                toHttp(phone, password);
            }
        } else if (tc.equals("2")) {
            SharedPreferences sp = context.getSharedPreferences("qiju", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("tc", "");
            editor.commit();
            finish();
        } else {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            toLogIn();
        }
    }


    protected void toLogIn() {
        IntentUtil.intentToNull(this, LonInActivity.class);
    }


}
