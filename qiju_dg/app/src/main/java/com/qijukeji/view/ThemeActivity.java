package com.qijukeji.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qijukeji.qiju_dg.R;
import com.qijukeji.receiver.JPushService;
import com.qijukeji.receiver.UpdateService;
import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.HttpUtil;
import com.qijukeji.utils.IntentUtil;
import com.qijukeji.utils.SignCalendar;
import com.qijukeji.utils.StaticField;
import com.qijukeji.utils.TimeUtil;
import com.qijukeji.utils.Utils;
import com.qijukeji.view.fragment.FirstFragment;
import com.qijukeji.view.fragment.SecondFragment;
import com.qijukeji.view.fragment.ThirdFragment;
import com.zxing.android.CaptureActivity;
import com.zxing.android.PenreadActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Administrator on 2017/5/11.
 */


public class ThemeActivity extends FragmentActivity implements FirstFragment.FirstFragmentClickListener {
    private FragmentManager fragmentManager;// Fragment管理
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    @Bind(R.id.iv_title_right)
    ImageView iv_title_right;
    @Bind(R.id.tv_title_name)
    TextView title;
    @Bind(R.id.iv_title_back)
    ImageView iv_title_back;
    @Bind(R.id.iv_title_select)
    ImageView iv_title_select;
    @Bind(R.id.id_news_iv)
    ImageView idNewsIv;
    @Bind(R.id.id_news_tv)
    TextView idNewsTv;
    @Bind(R.id.id_news_ll)
    LinearLayout idNewsLl;
    @Bind(R.id.id_read_iv)
    ImageView idReadIv;
    @Bind(R.id.id_read_tv)
    TextView idReadTv;
    @Bind(R.id.id_read_ll)
    LinearLayout idReadLl;
    @Bind(R.id.id_va_iv)
    ImageView idVaIv;
    @Bind(R.id.id_va_tv)
    TextView idVaTv;
    @Bind(R.id.id_va_ll)
    LinearLayout idVaLl;
    @Bind(R.id.ll_title)
    LinearLayout ll_title;
    @Bind(R.id.select_title_show)
    RelativeLayout select_title_show;
    @Bind(R.id.iv_title_write)
    ImageView iv_title_write;
    @BindColor(R.color.colorPrimary)
    int colorPrimary;
    @BindColor(R.color.color_aaaaaa)
    int color_aaaaaa;
    private String tag = "0";
    boolean Clockable = false;
    private String staffid;
    private String staffUuid;
    private String brandid;
    public static final int HTTP_VERSIONCODE = 0;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String version = msg.obj.toString();
            switch (msg.what) {
                case HTTP_VERSIONCODE:
                    compareVersion(version);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        staffid = intent.getStringExtra("staffid");
        staffUuid = intent.getStringExtra("staffUuid");
        brandid = intent.getStringExtra("brandid");
        Log.e("theme", "-------staffid----------" + staffid + "-------staffUuid----------" + staffUuid);
        loadData();
    }

    private void loadData() {
        fragmentManager = getSupportFragmentManager();
        iv_title_back.setImageResource(R.drawable.quit_icon);
        setTabSelection(0);
        Intent intent = new Intent(ThemeActivity.this, JPushService.class);
        startService(intent);
        toUpVersion();
    }


    @OnClick({R.id.id_news_ll, R.id.id_read_ll, R.id.id_va_ll, R.id.tv_title_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_news_ll:
                iv_title_back.setVisibility(View.VISIBLE);
                iv_title_back.setImageResource(R.drawable.quit_icon);
                select_title_show.setVisibility(View.VISIBLE);
                title.setVisibility(View.GONE);
                setTabSelection(0);
                title.setText("订单");
                Clockable = false;
                break;
            case R.id.id_read_ll:
                iv_title_right.setVisibility(View.GONE);
                iv_title_back.setVisibility(View.GONE);
                select_title_show.setVisibility(View.GONE);
                title.setVisibility(View.VISIBLE);
                setTabSelection(1);
                title.setText("报表");
                Clockable = true;
                break;
            case R.id.id_va_ll:
                iv_title_right.setVisibility(View.GONE);
                iv_title_back.setVisibility(View.GONE);
                select_title_show.setVisibility(View.GONE);
                title.setVisibility(View.VISIBLE);
                setTabSelection(2);
                title.setText("发现");
                Clockable = false;
                break;
        }
    }

    /**
     * @param index
     */
    private void setTabSelection(int index) {
        // 每次选中之前先清除掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("staffid", staffid);
        bundle.putString("staffUuid", staffUuid);
        bundle.putString("brandid", brandid);
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                tag = "0";
                idNewsIv.setBackgroundResource(R.drawable.order_icon_orange);
                idNewsTv.setTextColor(colorPrimary);
                if (firstFragment == null) {
                    // 如果BrandSaleFragment为空，则创建一个并添加到界面上
                    firstFragment = new FirstFragment();
                    firstFragment.setArguments(bundle);
                    transaction.add(R.id.center_layout, firstFragment);

                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
//                    firstFragment.setArguments(bundle);
                    transaction.show(firstFragment);
                }
//                transaction.commit();
                break;
            case 1:
                tag = "1";
                idReadIv.setBackgroundResource(R.drawable.statistics_icon_orange);
                idReadTv.setTextColor(colorPrimary);
                if (secondFragment == null) {
                    // 如果BrandSaleFragment为空，则创建一个并添加到界面上
                    secondFragment = new SecondFragment();
                    secondFragment.setArguments(bundle);
                    transaction.add(R.id.center_layout, secondFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(secondFragment);
                }
                break;
            case 2:
                tag = "2";
                idVaIv.setBackgroundResource(R.drawable.more_icon_orange);
                idVaTv.setTextColor(colorPrimary);
                if (thirdFragment == null) {
                    // 如果BrandSaleFragment为空，则创建一个并添加到界面上
                    thirdFragment = new ThirdFragment();
                    thirdFragment.setArguments(bundle);
                    transaction.add(R.id.center_layout, thirdFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(thirdFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */

    private void clearSelection() {
        idNewsIv.setBackgroundResource(R.drawable.order_icon_gray);
        idReadIv.setBackgroundResource(R.drawable.statistics_icon_gray);
        idVaIv.setBackgroundResource(R.drawable.more_icon_gray);
        idNewsTv.setTextColor(color_aaaaaa);
        idReadTv.setTextColor(color_aaaaaa);
        idVaTv.setTextColor(color_aaaaaa);
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (firstFragment != null) {
            transaction.hide(firstFragment);
        }
        if (secondFragment != null) {
            transaction.hide(secondFragment);
        }
        if (thirdFragment != null) {
            transaction.hide(thirdFragment);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Utils.exitBy2Click(this); // 调用双击退出函数
        }
        return false;
    }

    private void toUpVersion() {
        HttpUtil.VolleyHttpGet(this, ConstantValues.HTTP_VERSION, handler, HTTP_VERSIONCODE);
    }

    private void compareVersion(String data) {
        try {
            JSONObject json = new JSONObject(data);
            JSONObject jsonObject = json.getJSONObject("list");
            String versionup = jsonObject.getString("versionid");
            String descriptionbb = jsonObject.getString("descriptionbb");
            String versiondown = Utils.version(this);
            Log.e("versionup", versionup);
            Log.e("versiondown", versiondown);
            if (!versionup.equals(versiondown)) {
                Log.e("版本更新", versionup + "本地" + versiondown);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("版本更新").setMessage(descriptionbb);
                builder.setPositiveButton("以后再说", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setNegativeButton("立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        IntentUtil.intentToNull(ThemeActivity.this, ActivityDetailActivity.class);
                        Intent intent = new Intent(ThemeActivity.this, UpdateService.class);
                        startService(intent);
                    }
                }).setCancelable(false).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.iv_title_back)
    public void onViewClicked() {
        IntentUtil.intentToNull(this, DialogActivity.class);
    }

    @OnClick({R.id.iv_title_write, R.id.iv_title_right})
    public void toCapture(View v) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("staffid", staffid);
        bundle.putSerializable("staffUuid", staffUuid);
        bundle.putSerializable("brandid", brandid);
        switch (v.getId()) {
            case R.id.iv_title_write:
                IntentUtil.intentToNull(this, PenreadActivity.class, bundle);
                break;
            case R.id.iv_title_right:
                bundle.putSerializable(StaticField.KEY, StaticField.key1);
                IntentUtil.intentToNull(this, CaptureActivity.class, bundle);
                break;
        }
    }

    @Override
    public void FirstFragmentBtnClick(boolean upDown) {
        if (upDown) {
            iv_title_right.setVisibility(View.INVISIBLE);
            iv_title_select.setVisibility(View.GONE);
            title.setVisibility(View.VISIBLE);
            iv_title_write.setVisibility(View.GONE);
        } else {
            iv_title_right.setVisibility(View.VISIBLE);
            iv_title_select.setVisibility(View.VISIBLE);
            title.setVisibility(View.GONE);
            iv_title_write.setVisibility(View.VISIBLE);
        }
    }
}
