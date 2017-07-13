package com.qijukeji.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.HttpUtil;
import com.qijukeji.utils.IntentUtil;
import com.qijukeji.utils.PopupWindowHelper;
import com.qijukeji.utils.StaticField;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/10.
 */

public class OrderSaveActivity extends AppCompatActivity {

    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.tv_title_name)
    TextView tvTitleName;
    @Bind(R.id.iv_title_right)
    ImageView ivTitleRight;
    @Bind(R.id.weixin_touxiang)
    ImageView weixinTouxiang;
    @Bind(R.id.tv_kehu_name)
    TextView tvKehuName;
    @Bind(R.id.tv_kehu_address)
    TextView tvKehuAddress;
    @Bind(R.id.ll_address_view)
    LinearLayout llAddressView;
    @Bind(R.id.tv_kehu_phone)
    TextView tvKehuPhone;
    @Bind(R.id.img_call_kehu)
    ImageButton imgCallKehu;
    @Bind(R.id.activity_img)
    ImageView activityImg;
    @Bind(R.id.activity_theme)
    TextView activityTheme;
    @Bind(R.id.lv_fanxian_send)
    ListView lvFanxianSend;
    @Bind(R.id.ll_fanxian_send)
    LinearLayout llFanxianSend;
    @Bind(R.id.edit_totalmoney)
    EditText editTotalmoney;
    @Bind(R.id.bt_next_page)
    Button btNextPage;
    @Bind(R.id.ll_money_view)
    RelativeLayout llMoneyView;
    @Bind(R.id.tv_first_totalmoney)
    TextView tvFirstTotalmoney;
    @Bind(R.id.rl_money_view)
    RelativeLayout rlMoneyView;
    private PopupWindowHelper popwindows;
    private View popView;
    private String staffid, staffUuid, uuid;
    private String kehuname, kehuphone, kehuxiaoqu, kehuaddress, kehuremark;
    private static final int HTTP_SELECT_NEXT = 1;
    private static final int HTTP_SELECT_YXCD = 2;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String hasErrors = null;
            String errorMessage = null;
            JSONObject json;
            String data = msg.obj.toString();
            switch (msg.what) {
                case HTTP_SELECT_NEXT:
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderSaveActivity.this);
                    try {
                        json = new JSONObject(data);
                        hasErrors = json.getString("hasErrors");
                        errorMessage = json.getString("errorMessage");
                        if (hasErrors.equals("true")) {
                            builder.setTitle("温馨提示").setMessage(errorMessage);
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            }).show();
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("data", data);
                            IntentUtil.intentToNull(OrderSaveActivity.this, OrderNextActivity.class, bundle);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case HTTP_SELECT_YXCD:
                    getOrderinfo(data);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        ButterKnife.bind(this);
        initPop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    private void getOrderinfo() {
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        try {
            JSONObject json = new JSONObject(data);
            JSONObject info = json.getJSONObject("data");
            uuid = info.getString("checkOrderUuid");
            Glide.with(this)
                    .load(info.getString("userHeadImageUrl"))
                    .placeholder(R.drawable.tubiao)
                    .error(R.drawable.tubiao)
                    .into(weixinTouxiang);
            kehuname = info.getString("userName");
            kehuxiaoqu = info.getString("userAddressVillage");
            kehuaddress = info.getString("useraddressunit");
            kehuphone = info.getString("userPhone");
            if (kehuname == null)
                kehuname = "";
            if (kehuphone == null)
                kehuphone = "";
            if (kehuxiaoqu == null)
                kehuxiaoqu = "";
            if (kehuaddress == null)
                kehuaddress = "";
            tvKehuName.setText(kehuname);
            tvKehuAddress.setText(kehuxiaoqu + kehuaddress);
            tvKehuPhone.setText(kehuphone);
            Glide.with(this)
                    .load(info.getString("activityShareImage"))
                    .placeholder(R.drawable.tubiao)
                    .error(R.drawable.tubiao)
                    .into(activityImg);
            activityTheme.setText(info.getString("activityName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        Intent intent = getIntent();
        uuid = intent.getStringExtra("uuid");
        int key = intent.getIntExtra(StaticField.KEY, 0);
        SharedPreferences preferences = getSharedPreferences("qiju", Context.MODE_PRIVATE);
        staffid = preferences.getString("staffid", "");
        staffUuid = preferences.getString("staffUuid", "");
        if (key == 2) {
            JSONObject json = new JSONObject();
            try {
                json.put("staffUuid", staffUuid);
                json.put("uuid", uuid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpUtil.VolleyHttpPost(this, ConstantValues.HTTP_SELECTYXDDXX + "?staffuuid=" + staffUuid + "&staffid=", staffid, json, handler, HTTP_SELECT_YXCD);
        } else {
            getOrderinfo();
        }
    }

    private void getOrderinfo(String data) {
        try {
            JSONObject json = new JSONObject(data);
            JSONObject info = json.getJSONObject("data");
            uuid = info.getString("checkOrderUuid");
            int status = info.getInt("checkOrderStatus");
            Glide.with(this)
                    .load(info.getString("userHeadImageUrl"))
                    .placeholder(R.drawable.tubiao)
                    .error(R.drawable.tubiao)
                    .into(weixinTouxiang);
            kehuname = info.getString("userName");
            kehuphone = info.getString("userPhone");
            kehuxiaoqu = info.getString("userAddressVillage");
            kehuaddress = info.getString("userAddressUnit");
            kehuremark = info.getString("remark");
            if (kehuname == null)
                kehuname = "";
            if (kehuphone == null)
                kehuphone = "";
            if (kehuxiaoqu == null)
                kehuxiaoqu = "";
            if (kehuaddress == null)
                kehuaddress = "";
            if (kehuremark == null)
                kehuremark = "";
            tvKehuName.setText(kehuname);
            tvKehuAddress.setText(kehuxiaoqu + kehuaddress);
            tvKehuPhone.setText(kehuphone);
            Glide.with(this)
                    .load(info.getString("activityShareImage"))
                    .placeholder(R.drawable.tubiao)
                    .error(R.drawable.tubiao)
                    .into(activityImg);
            activityTheme.setText(info.getString("activityName"));
            if (status == 0) {
                rlMoneyView.setVisibility(View.GONE);
            } else if (status == 1) {
                llMoneyView.setVisibility(View.GONE);
                btNextPage.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.iv_title_back, R.id.iv_title_right, R.id.img_call_kehu, R.id.bt_next_page})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.iv_title_right:
                popwindows.showAsDropDown(view);
                break;
            case R.id.img_call_kehu:
                String tellphone = tvKehuPhone.getText().toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + tellphone));
                startActivity(intent);
                break;
            case R.id.bt_next_page:
                JSONObject json = new JSONObject();
                String totalmoney = editTotalmoney.getText().toString();
                try {
                    json.put("staffUuid", staffUuid);
                    json.put("totalMoney", totalmoney);
                    json.put("uuid", uuid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                HttpUtil.VolleyHttpPost(this, ConstantValues.HTTP_CHECKORDER_NEXT + "?staffuuid=" + staffUuid + "&staffid=", staffid, json, handler, HTTP_SELECT_NEXT);
                break;
        }
    }

    private void initPop() {
        popView = LayoutInflater.from(this).inflate(R.layout.popwindows_menu, null);
        popwindows = new PopupWindowHelper(popView);
        LinearLayout popBtedit = (LinearLayout) popView.findViewById(R.id.pop_bt_deit);
        LinearLayout popBtshare = (LinearLayout) popView.findViewById(R.id.pop_bt_share);
        LinearLayout popBtdelete = (LinearLayout) popView.findViewById(R.id.pop_bt_delete);
        LinearLayout popBtmove = (LinearLayout) popView.findViewById(R.id.pop_bt_move);
        popBtedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("kehuname", kehuname);
                bundle.putSerializable("kehuphone", kehuphone);
                bundle.putSerializable("kehuxiaoqu", kehuxiaoqu);
                bundle.putSerializable("kehuaddress", kehuaddress);
                bundle.putSerializable("kehuremark", kehuremark);
                bundle.putSerializable("uuid", uuid);
                IntentUtil.intentToNull(OrderSaveActivity.this, UpdateInfoActivity.class, bundle);
                popwindows.dismiss();
            }
        });
        popBtshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popwindows.dismiss();
            }
        });
        popBtdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popwindows.dismiss();
            }
        });
        popBtmove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popwindows.dismiss();
            }
        });

    }
}

