package com.qijukeji.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import com.qijukeji.adapter.SendgiftAdapter;
import com.qijukeji.entityModel.UserGift;
import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.HttpUtil;
import com.qijukeji.utils.IntentUtil;
import com.qijukeji.utils.JsonToObjUtil;
import com.qijukeji.utils.PopupWindowHelper;
import com.qijukeji.utils.StaticField;
import com.qijukeji.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/10.
 */

public class OrderSaveActivity extends AppCompatActivity {
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
    @Bind(R.id.new_title_back)
    ImageView newTitleBack;
    @Bind(R.id.new_title_name)
    TextView newTitleName;
    @Bind(R.id.new_title_right)
    ImageView newTitleRight;
    @Bind(R.id.title_newstyle)
    LinearLayout titleNewstyle;
    @Bind(R.id.order_fanxian)
    TextView orderFanxian;
    @Bind(R.id.order_zengping)
    ListView orderZengping;
    @Bind(R.id.ll_order_show)
    LinearLayout llOrderShow;
    @Bind(R.id.ll_fanxian_show)
    LinearLayout llFanxianShow;
    @Bind(R.id.new_title_edit)
    ImageView newTitleEdit;
    @Bind(R.id.new_title_share)
    ImageView newTitleShare;
    @Bind(R.id.ll_activity_show)
    LinearLayout llActivityShow;
    private SendgiftAdapter sendgiftAdapter;
    private List<UserGift> listGift;
    private PopupWindowHelper popwindows;
    private View popView;
    private String bitmapstr;
    private String staffid, staffUuid, uuid, isGift;
    private String kehuname, kehuphone, kehuxiaoqu, kehuaddress, kehuremark;
    private Bitmap bitmap;
    private static final int HTTP_SELECT_NEXT = 1;
    private static final int HTTP_SELECT_YXCD = 2;
    Handler handlers = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    weixinTouxiang.setImageBitmap(bitmap);
                    break;
            }
        }
    };
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
                            bundle.putSerializable("uuid", uuid);
                            bundle.putSerializable("data", data);
                            bundle.putSerializable("bitmap", bitmapstr);
                            bundle.putSerializable("kehuname", kehuname);
                            IntentUtil.intentToNull(OrderSaveActivity.this, OrderNextActivity.class, bundle);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case HTTP_SELECT_YXCD:
                    getOrderinfo(data);
                    break;
                default:
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
            final JSONObject info = json.getJSONObject("data");
            uuid = info.getString("checkOrderUuid");
            bitmapstr = info.getString("userHeadImageUrl");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bitmap = Utils.returnBitmap(bitmapstr);
                    handlers.sendEmptyMessage(0);
                }
            }).start();
            kehuname = info.getString("userName");
            kehuxiaoqu = info.getString("userAddressVillage");
            kehuaddress = info.getString("userAddressUnit");
            kehuphone = info.getString("userPhone");
            if (kehuname == null || kehuname.equals("null"))
                kehuname = "";
            if (kehuphone == null || kehuphone.equals("null"))
                kehuphone = "";
            if (kehuxiaoqu == null || kehuxiaoqu.equals("null"))
                kehuxiaoqu = "";
            if (kehuaddress == null || kehuaddress.equals("null"))
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
        isGift = intent.getStringExtra("isGift");
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
            final JSONObject info = json.getJSONObject("data");
            uuid = info.getString("checkOrderUuid");
            int status = info.getInt("checkOrderStatus");
            bitmapstr = info.getString("userHeadImageUrl");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bitmap = Utils.returnBitmap(bitmapstr);
                    handlers.sendEmptyMessage(0);
                }
            }).start();
            kehuname = info.getString("userName");
            kehuphone = info.getString("userPhone");
            kehuxiaoqu = info.getString("userAddressVillage");
            kehuaddress = info.getString("userAddressUnit");
            kehuremark = info.getString("remark");
            if (kehuname == null || kehuname.equals("null"))
                kehuname = "";
            if (kehuphone == null || kehuphone.equals("null"))
                kehuphone = "";
            if (kehuxiaoqu == null || kehuxiaoqu.equals("null"))
                kehuxiaoqu = "";
            if (kehuaddress == null || kehuaddress.equals("null"))
                kehuaddress = "";
            if (kehuremark == null || kehuremark.equals("null"))
                kehuremark = "";
            tvKehuName.setText(kehuname);
            tvKehuAddress.setText(kehuxiaoqu + kehuaddress);
            tvKehuPhone.setText(kehuphone);
            if (isGift.equals("false")) {
                Glide.with(this)
                        .load(info.getString("activityShareImage"))
                        .placeholder(R.drawable.tubiao)
                        .error(R.drawable.tubiao)
                        .into(activityImg);
                activityTheme.setText(info.getString("activityName"));
            }
            if (status == 0) {
                rlMoneyView.setVisibility(View.GONE);
                llOrderShow.setVisibility(View.GONE);
            } else if (status == 1) {
                String gift = info.getString("giftList");
                if (isGift.equals("false")) {
                    newTitleShare.setVisibility(View.VISIBLE);
                    tvFirstTotalmoney.setText(info.getString("totalAmount"));
                    orderFanxian.setText(info.getString("discountAmount") + "元");
                } else {
                    llFanxianShow.setVisibility(View.GONE);
                    newTitleShare.setVisibility(View.GONE);
                    llActivityShow.setVisibility(View.GONE);
                }
                listGift = JsonToObjUtil.jsonToListObj(gift, UserGift.class);
                llMoneyView.setVisibility(View.GONE);
                newTitleEdit.setVisibility(View.VISIBLE);
                newTitleRight.setVisibility(View.GONE);
                llOrderShow.setVisibility(View.VISIBLE);
                btNextPage.setVisibility(View.GONE);
                rlMoneyView.setVisibility(View.VISIBLE);
                sendgiftAdapter = new SendgiftAdapter(listGift, this);
                orderZengping.setAdapter(sendgiftAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.new_title_back, R.id.new_title_right, R.id.img_call_kehu, R.id.bt_next_page, R.id.new_title_edit, R.id.new_title_share})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.new_title_back:
                finish();
                break;
            case R.id.new_title_right:
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
            case R.id.new_title_edit:
                Bundle bundle = new Bundle();
                bundle.putSerializable("kehuname", kehuname);
                bundle.putSerializable("kehuphone", kehuphone);
                bundle.putSerializable("kehuxiaoqu", kehuxiaoqu);
                bundle.putSerializable("kehuaddress", kehuaddress);
                bundle.putSerializable("kehuremark", kehuremark);
                bundle.putSerializable("uuid", uuid);
                IntentUtil.intentToNull(OrderSaveActivity.this, UpdateInfoActivity.class, bundle);
                break;
            case R.id.new_title_share:
                Bundle bundles = new Bundle();
                bundles.putSerializable("uuid", uuid);
                bundles.putSerializable("type", 0);
                bundles.putSerializable("staffUuid", staffUuid);
                IntentUtil.intentToNull(OrderSaveActivity.this, ShareOrderActivity.class, bundles);
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
                Bundle bundle = new Bundle();
                bundle.putSerializable("uuid", uuid);
                bundle.putSerializable("type", 0);
                bundle.putSerializable("staffUuid", staffUuid);
                IntentUtil.intentToNull(OrderSaveActivity.this, ShareOrderActivity.class, bundle);
                popwindows.dismiss();
            }
        });
        popBtdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderSaveActivity.this);
                builder.setTitle("删除订单").setMessage("确定删除该客户信息吗？");
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JSONObject json = new JSONObject();
                        try {
                            json.put("uuid", uuid);
                            json.put("staffUuid", staffUuid);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        HttpUtil.VolleyHttpPost(OrderSaveActivity.this, ConstantValues.HTTP_HIDDENORDER + "?staffuuid=" + staffUuid + "&staffid=", staffid, json, null, 1);
                        finish();
                    }
                }).setCancelable(false).show();
                popwindows.dismiss();
            }
        });
        popBtmove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("uuid", uuid);
                bundle.putSerializable("type", 1);
                bundle.putSerializable("staffUuid", staffUuid);
                IntentUtil.intentToNull(OrderSaveActivity.this, ShareOrderActivity.class, bundle);
                popwindows.dismiss();
            }
        });

    }
}

