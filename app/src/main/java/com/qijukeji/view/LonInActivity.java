package com.qijukeji.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.HttpUtil;
import com.qijukeji.utils.IntentUtil;
import com.qijukeji.utils.Utils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LonInActivity extends AppCompatActivity {
    @Bind(R.id.activity_lon_in)
    RelativeLayout login_in;
    @Bind(R.id.check_time)
    RelativeLayout check_time;
    @Bind(R.id.close_check)
    Button close_check;
    @Bind(R.id.tv_to_register)
    TextView tv_to_register;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.bt_log_in)
    Button btLogIn;
    private Context context;
    private String phone, password;
    private UMShareAPI mShareAPI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lon_in);
        ButterKnife.bind(this);
        mShareAPI = UMShareAPI.get(this);
        context = this;
    }

    @OnClick(R.id.tv_to_register)
    public void onViewClicked() {
        IntentUtil.intentToNull(this, RegisterActivity.class);
    }

    @OnClick(R.id.bt_log_in)
    public void onClickLogIn() {
        phone = etPhone.getText().toString();
        password = etPassword.getText().toString();
        if (phone.equals("") || phone == null) {
            Utils.setToast(context, "请输入账号");
            return;
        }
        if (password.equals("") || password == null) {
            Utils.setToast(context, "请输入密码");
            return;
        }
        JSONObject json = new JSONObject();
        try {
            json.put("name", phone);
            json.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtil.VolleyHttpPost(this, ConstantValues.HTTP_LOG_IN, phone, json, handler, 0);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (data.get("openid").equals("") || data.get("openid") == null) {
                Utils.setToast(context, "");
            } else {
                getUserInfo(platform);
            }

            Toast.makeText(getApplicationContext(), "Authorize succeed" + data.toString(), Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 获取用户信息
     *
     * @param platform
     */
    private void getUserInfo(SHARE_MEDIA platform) {
        mShareAPI.getPlatformInfo(this, platform,
                new UMAuthListener() {

                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        //获取用户信息
                        Log.e("123", map.toString());
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {

                    }
                });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            switch (msg.what) {
                case 0:
                    try {
                        toMain(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void toMain(String data) throws JSONException {
        JSONObject json = new JSONObject(data);
        if (json.getString("jg").equals("0")) {
            String brandid = json.getString("brandid");
            String staffUuid = json.getString("staffUuid");
            String companyid = json.getString("companyid");
            String tenantUuid = json.getString("tenantUuid");
            Bundle b = new Bundle();
            b.putSerializable("staffid", phone);
            b.putSerializable("staffUuid", staffUuid);
            b.putSerializable("brandid", brandid);
            IntentUtil.intentToNull(this, ThemeActivity.class, b);
            SharedPreferences sp = getSharedPreferences("qiju", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("brandid", brandid);
            editor.putString("companyid", companyid);
            editor.putString("staffid", phone);
            editor.putString("staffUuid", staffUuid);
            editor.putString("tenantUuid", tenantUuid);
            editor.putString("kehutype", "1");
            editor.putString("password", password);
            editor.putString("tc", "1");
            editor.commit();
        } else if (json.getString("jg").equals("1")) {
            login_in.setBackgroundResource(R.drawable.blur_bg2);
            check_time.setVisibility(View.VISIBLE);
        } else if (json.getString("jg").equals("2")) {
            Utils.setToast(context, "账号或密码错误");
        } else {
            Utils.setToast(context, "此账号未注册");
        }
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Utils.exitBy2Click(this); // 调用双击退出函数
        }
        return false;
    }

    @OnClick(R.id.close_check)
    public void exit_check() {
        login_in.setBackgroundResource(R.color.white);
        check_time.setVisibility(View.GONE);
    }
}
