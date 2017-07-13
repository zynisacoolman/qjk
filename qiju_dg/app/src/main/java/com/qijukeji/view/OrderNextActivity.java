package com.qijukeji.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.qijukeji.adapter.SendgiftAdapter;
import com.qijukeji.entityModel.UserGift;
import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.JsonToObjUtil;
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
    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.tv_title_name)
    TextView tvTitleName;
    @Bind(R.id.iv_title_right)
    ImageView ivTitleRight;
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
    private List<UserGift> listGift;
    private SendgiftAdapter sendgiftAdapter;
    private String staffid, staffUuid;

    private static final int HTTP_SELECT_ORDERINFO = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            switch (msg.what) {
                case HTTP_SELECT_ORDERINFO:
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
        titleNewstyle.setBackgroundResource(R.drawable.bg_statusbar);
        SharedPreferences preferences = getSharedPreferences("qiju", Context.MODE_PRIVATE);
        staffid = preferences.getString("staffid", "");
        staffUuid = preferences.getString("staffUuid", "");
    }

    private void toSetOrderInfo() {
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        try {
            JSONObject jsonnext = new JSONObject(data);
            JSONObject info = jsonnext.getJSONObject("data");
            String gift = info.getString("giftList");
            tvNextTotalmoney.setText(info.getString("totalAmount"));
            tvFanxian.setText(info.getString("discountAmount") + "元");
            listGift = JsonToObjUtil.jsonToListObj(gift, UserGift.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sendgiftAdapter = new SendgiftAdapter(listGift, this);
        lvZengping.setAdapter(sendgiftAdapter);
    }

    @OnClick({R.id.bt_backstep, R.id.bt_overorder})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.bt_backstep:
                finish();
                break;
            case R.id.bt_overorder:

                break;
            default:
                break;
        }
    }
}
