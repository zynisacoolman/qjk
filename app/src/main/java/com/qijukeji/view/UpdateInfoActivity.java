package com.qijukeji.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.HttpUtil;
import com.qijukeji.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/13.
 */

public class UpdateInfoActivity extends AppCompatActivity {
    @Bind(R.id.ll_user_info_part)
    LinearLayout userInfoPart;
    @Bind(R.id.title_newstyle)
    LinearLayout titleNewstyle;
    @Bind(R.id.et_detail_name)
    EditText etDetailName;
    @Bind(R.id.et_detail_phone)
    EditText etDetailPhone;
    @Bind(R.id.tv_detail_address)
    TextView tvDetailAddress;
    @Bind(R.id.et_detail_address_xq)
    EditText etDetailAddressXq;
    @Bind(R.id.ll_detail_address)
    LinearLayout llDetailAddress;
    @Bind(R.id.et_detail_address)
    EditText etDetailAddress;
    @Bind(R.id.et_detail_remarks)
    EditText etDetailRemarks;
    @Bind(R.id.bt_save_info)
    Button btSaveInfo;
    @BindColor(R.color.gary)
    int gary;
    private static final int INTENT_KEY = 8;
    @Bind(R.id.news_title_back)
    ImageView newsTitleBack;
    @Bind(R.id.news_title_name)
    TextView newsTitleName;
    @Bind(R.id.news_title_right)
    ImageView newsTitleRight;
    private String staffid, staffUuid, uuid;
    private Integer checkOrderState;
    private String kehuname, kehuphone, kehuxiaoqu, kehuaddress, kehuremark;
    private static final int HTTP_SAVE_INFO = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String hasErrors = null;
            String errorMessage = null;
            JSONObject json;
            String data = msg.obj.toString();
            switch (msg.what) {
                case HTTP_SAVE_INFO:
                    try {
                        json = new JSONObject(data);
                        hasErrors = json.getString("hasErrors");
                        errorMessage = json.getString("errorMessage");
                        if (hasErrors.equals("true")) {
                            Utils.setToast(UpdateInfoActivity.this, errorMessage);
                        } else {
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateinfo);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        kehuname = intent.getStringExtra("kehuname");
        kehuphone = intent.getStringExtra("kehuphone");
        kehuxiaoqu = intent.getStringExtra("kehuxiaoqu");
        kehuaddress = intent.getStringExtra("kehuaddress");
        kehuremark = intent.getStringExtra("kehuremark");
        checkOrderState = intent.getIntExtra("checkOrderState",0);
        if(checkOrderState==1){
            userInfoPart.setVisibility(View.GONE);
        }
        uuid = intent.getStringExtra("uuid");
        SharedPreferences preferences = getSharedPreferences("qiju", Context.MODE_PRIVATE);
        staffid = preferences.getString("staffid", "");
        staffUuid = preferences.getString("staffUuid", "");
        etDetailName.setText(kehuname);
        etDetailPhone.setText(kehuphone);
        etDetailAddressXq.setText(kehuxiaoqu);
        etDetailAddress.setText(kehuaddress);
        etDetailRemarks.setText(kehuremark);
        etDetailName.setTextColor(gary);
        etDetailPhone.setTextColor(gary);
        etDetailAddress.setTextColor(gary);
        etDetailAddressXq.setTextColor(gary);
        etDetailRemarks.setTextColor(gary);
        newsTitleRight.setVisibility(View.INVISIBLE);
    }

    @OnClick({R.id.news_title_back, R.id.et_detail_address_xq, R.id.bt_save_info})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.news_title_back:
                finish();
                break;
            case R.id.et_detail_address_xq:
                Intent it = new Intent(UpdateInfoActivity.this, MapActivity.class);
                startActivityForResult(it, INTENT_KEY);
                break;
            case R.id.bt_save_info:
                JSONObject json = new JSONObject();
                etDetailName.getText().toString();
                etDetailPhone.getText().toString();
                etDetailAddressXq.getText().toString();
                etDetailAddress.getText().toString();
                etDetailRemarks.getText().toString();
                try {
                    if(checkOrderState==0){
                        json.put("userName", etDetailName.getText().toString());
                        json.put("userPhone", etDetailPhone.getText().toString());
                        json.put("addressVillage", etDetailAddressXq.getText().toString());
                        json.put("addressUnit", etDetailAddress.getText().toString());
                    }
                    json.put("remark", etDetailRemarks.getText().toString());
                    json.put("uuid", uuid);
                    Log.e("jsonupdate", json.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HttpUtil.VolleyHttpPost(this, ConstantValues.HTTP_SAVE_URL + "?staffuuid=" + staffUuid + "&staffid=", staffid, json, handler, HTTP_SAVE_INFO);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        switch (resultCode) {
            case MapActivity.INTENT_RETURN:
                String str = data.getStringExtra("address");
                etDetailAddressXq.setText(str);
                break;
            default:
                break;
        }
    }
}
