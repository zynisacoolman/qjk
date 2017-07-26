package com.qijukeji.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.qijukeji.adapter.SendgiftAdapter;
import com.qijukeji.customView.CircleImageView;
import com.qijukeji.entityModel.UserGift;
import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.HttpUtil;
import com.qijukeji.utils.JsonToObjUtil;
import com.qijukeji.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/11.
 */

public class OrderNextActivity extends AppCompatActivity {

    @Bind(R.id.lv_zengping)
    ListView lvZengping;
    @Bind(R.id.tv_fanxian)
    TextView tvFanxian;
    @Bind(R.id.bt_backstep)
    Button btBackstep;
    @Bind(R.id.bt_overorder)
    Button btOverorder;
    @Bind(R.id.title_newstyle)
    LinearLayout titleNewstyle;
    @Bind(R.id.tv_next_totalmoney)
    TextView tvNextTotalmoney;
    @Bind(R.id.news_title_back)
    ImageView newsTitleBack;
    @Bind(R.id.news_title_name)
    TextView newsTitleName;
    @Bind(R.id.news_title_right)
    ImageView newsTitleRight;
    @Bind(R.id._next_wxheadimg)
    CircleImageView NextWxheadimg;
    @Bind(R.id.title_kehu_name)
    TextView titleKehuName;
    private List<UserGift> listGift;
    private JSONArray giftlist;
    private SendgiftAdapter sendgiftAdapter;
    private String staffid, staffUuid, uuid;
    private String discountAmount;
    private Bitmap bitmap;
    private static final int HTTP_OVER_ORDERINFO = 1;
    Handler handlers = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    NextWxheadimg.setImageBitmap(bitmap);
                    break;
            }
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            switch (msg.what) {
                case HTTP_OVER_ORDERINFO:
                    overOrderback(data);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetailss);
        ButterKnife.bind(this);
        toSetOrderInfo();
        initView();
    }

    private void initView() {
        newsTitleRight.setVisibility(View.INVISIBLE);
        SharedPreferences preferences = getSharedPreferences("qiju", Context.MODE_PRIVATE);
        staffid = preferences.getString("staffid", "");
        staffUuid = preferences.getString("staffUuid", "");
    }

    private void toSetOrderInfo() {
        Intent intent = getIntent();
        uuid = intent.getStringExtra("uuid");
        String data = intent.getStringExtra("data");
        String kehuname = intent.getStringExtra("kehuname");
        final String bitmapstr = intent.getStringExtra("bitmap");
        try {
            JSONObject jsonnext = new JSONObject(data);
            JSONObject info = jsonnext.getJSONObject("data");
            giftlist = info.getJSONArray("giftList");
            String gift = info.getString("giftList");
            tvNextTotalmoney.setText(info.getString("totalAmount"));
            discountAmount = info.getString("discountAmount");
            tvFanxian.setText(discountAmount + "元");
            titleKehuName.setText(kehuname);
            listGift = JsonToObjUtil.jsonToListObj(gift, UserGift.class);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bitmap = Utils.returnBitmap(bitmapstr);
                    handlers.sendEmptyMessage(0);
                }
            }).start();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sendgiftAdapter = new SendgiftAdapter(listGift, this);
        lvZengping.setAdapter(sendgiftAdapter);
    }

    @OnClick({R.id.news_title_back, R.id.bt_overorder, R.id.bt_backstep})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.news_title_back:
            case R.id.bt_backstep:
                finish();
                break;
            case R.id.bt_overorder:
                JSONObject json = new JSONObject();
                String totalAmount = tvNextTotalmoney.getText().toString();
                try {
                    json.put("uuid", uuid);
                    json.put("staffUuid", staffUuid);
                    json.put("totalMoney", totalAmount);
                    json.put("discountAmount", discountAmount);
                    json.put("giftList", giftlist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("order_json", json.toString());
                HttpUtil.VolleyHttpPost(this, ConstantValues.HTTP_ORDER_FINISH + "?staffuuid=" + staffUuid + "&staffid=", staffid, json, handler, HTTP_OVER_ORDERINFO);
                break;
            default:
                break;
        }
    }

    private void overOrderback(String data) {
        JSONObject jsonnext = null;
        try {
            jsonnext = new JSONObject(data);
            boolean hasErrors = jsonnext.getBoolean("hasErrors");
            String errorMessage = jsonnext.getString("errorMessage");
            if (hasErrors) {
                Utils.setToast(this, errorMessage);
            } else {
                Utils.setToast(this, "结单成功");
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
