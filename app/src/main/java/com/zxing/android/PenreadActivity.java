package com.zxing.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.HttpUtil;
import com.qijukeji.utils.IntentUtil;
import com.qijukeji.utils.Utils;
import com.qijukeji.view.OrderSaveActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/21.
 */

public class PenreadActivity extends AppCompatActivity {
    @Bind(R.id.iv_title_back)
    ImageView img_back;
    @Bind(R.id.iv_title_right)
    ImageView img_right;
    @Bind(R.id.tv_title_name)
    TextView title_name;
    @Bind(R.id.select_title_show)
    RelativeLayout select_title_show;
    @Bind(R.id.ed_yhm_read)
    EditText ed_yhm_read;
    @Bind(R.id.bt_yhm_commit)
    Button bt_yhm_commit;
    private String staffid;
    private String staffUuid;
    private String brandid;
    private String companyid;
    private static final int HTTP_READ_YHQ = 2;//查询优惠券信息
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HTTP_READ_YHQ:
                    String data2 = msg.obj.toString();
                    JsonToObj(data2);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penread);
        SharedPreferences preferences = getSharedPreferences("qiju", Context.MODE_PRIVATE);
        staffid = preferences.getString("staffid", "");
        staffUuid = preferences.getString("staffUuid", "");
        brandid = preferences.getString("brandid", "");
        companyid = preferences.getString("companyid", "");
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        title_name.setText("优惠码");
        img_right.setVisibility(View.INVISIBLE);
        title_name.setVisibility(View.VISIBLE);
        select_title_show.setVisibility(View.GONE);
    }

    @OnClick({R.id.iv_title_back, R.id.bt_yhm_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.bt_yhm_commit:
                String yhm_number = ed_yhm_read.getText().toString();
                if (yhm_number.equals("")) {
                    Utils.setToast(this, "请输入优惠码");
                } else {
                    HttpUtil.VolleyHttpGet(this, ConstantValues.SYS_URL + "?brandid=" + brandid + "&staffuuid=" + staffUuid, yhm_number, staffid, handler, HTTP_READ_YHQ);
                }
                break;
            default:
                break;
        }
    }

    private void JsonToObj(String data2) {
        String hasErrors = null;
        String errorMessage = null;
        JSONObject json;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        try {
            json = new JSONObject(data2);
            hasErrors = json.getString("hasErrors");
            errorMessage = json.getString("errorMessage");
            if (hasErrors.equals("true")) {
                builder.setTitle("温馨提示").setMessage(errorMessage);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                }).show();
            } else {
                toIntent(data2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void toIntent(String data) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        IntentUtil.intentToNull(this, OrderSaveActivity.class, bundle);
        finish();
    }
}
