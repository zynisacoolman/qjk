package com.qijukeji.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.qijukeji.controller.RegisterController;
import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.HttpUtil;
import com.qijukeji.utils.TimeUtil;
import com.qijukeji.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * Created by Administrator on 2017/6/23.
 */

public class ShareOrderActivity extends AppCompatActivity {
    @Bind(R.id.tv_title_name)
    TextView title;
    @Bind(R.id.select_title_show)
    RelativeLayout select_title_show;
    @Bind(R.id.iv_title_right)
    ImageView iv_title_right;
    @Bind(R.id.move_brand_spinner)
    Spinner brandSpinner;
    @Bind(R.id.move_storelist_spinner)
    Spinner storelistSpinner;
    @Bind(R.id.share_btn)
    Button shareBtn;
    @Bind(R.id.share_brand_spinner)
    LinearLayout ShareBrandSpinner;
    @Bind(R.id.share_storelist_spinner)
    LinearLayout ShareStorelistSpinner;
    @BindColor(R.color.text_gray)
    int text_gray;
    @BindColor(R.color.colorPrimary)
    int colorPrimary;
    @BindDrawable(R.drawable.spinner_register_gray)
    Drawable spinner_register_gray;
    @BindDrawable(R.drawable.spinner_register_green)
    Drawable spinner_register_green;
    private RegisterController controller;
    JSONObject json = null;
    private JSONArray jsonArray;
    private JSONArray jsonArray2;
    private String companyid;
    private String tenantUuid;
    private String gsmc;
    private String[] arrBrand;
    private String[] arrStore;
    private ArrayAdapter<String> adapterBrand;
    private ArrayAdapter<String> adapterStore;
    public static final int SHARE_BRAND = 0;
    private static final int SHARE_STORE = 1;
    private static final int SHARE_MOVE = 2;
    private String staffid, uuid, staffUuid;
    private int type;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String data = msg.obj.toString();
            Log.e("datashare", data);
            switch (msg.what) {
                case SHARE_BRAND:
                    try {
                        toSetSpinner(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case SHARE_STORE:
                    try {
                        toSetStorelist(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case SHARE_MOVE:
                    toback(data);
                    break;
                default:
                    break;
            }
        }
    };

    private void toback(String data) {
        try {
            TimeUtil.waitoneover();
            JSONObject jsonObject = new JSONObject(data);
            String message = jsonObject.getString("errorMessage");
            String hasErrors = jsonObject.getString("hasErrors");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            if (hasErrors.equals("false")) {
                builder.setTitle("温馨提示").setMessage("提交成功");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        finish();
                    }
                }).show();
            } else {
                Utils.setToast(this, message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shareorder);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        uuid = intent.getStringExtra("uuid");
        staffUuid = intent.getStringExtra("staffUuid");
        SharedPreferences preferences = getSharedPreferences("qiju", Context.MODE_PRIVATE);
        staffid = preferences.getString("staffid", "");
        type = intent.getIntExtra("type", 0);
        title.setText("客户共享转移");
        title.setVisibility(View.VISIBLE);
        select_title_show.setVisibility(View.GONE);
        iv_title_right.setVisibility(View.INVISIBLE);
        controller = new RegisterController(this, handler);
        JSONObject json = new JSONObject();
        try {
            json.put("uuid", uuid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        controller.toHttpPltext(SHARE_BRAND, json);
    }

    private void toSetSpinner(String data) throws JSONException {
        jsonArray = jxJson(data);
        int x = jsonArray.length() + 1;
        arrBrand = new String[x];
        arrBrand[0] = "请选择品牌";
        for (int i = 1; i < x; i++) {
            String listName = jsonArray.getString(i - 1);
            JSONObject jsonlist = new JSONObject(listName);
            arrBrand[i] = jsonlist.getString("text");
        }
        adapterBrand = new ArrayAdapter<String>(this, R.layout.adapter_spinner, arrBrand);
        adapterBrand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandSpinner.setAdapter(adapterBrand);
    }

    private void toSetStorelist(String data) throws JSONException {
        jsonArray2 = new JSONArray();
        jsonArray2 = jxJson(data);
        int x = jsonArray2.length() + 1;
        arrStore = new String[x];
        arrStore[0] = "请选择门店";
        for (int i = 1; i < x; i++) {
            String listName = jsonArray2.getString(i - 1);
            JSONObject jsonlist = new JSONObject(listName);
            arrStore[i] = jsonlist.getString("text");
        }
        adapterStore = new ArrayAdapter<String>(this, R.layout.adapter_spinner, arrStore);
        adapterStore.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storelistSpinner.setAdapter(adapterStore);
    }

    private JSONArray jxJson(String data) throws JSONException {
        JSONArray jsonArray1 = null;
        JSONObject json = new JSONObject(data);
        String hasErrors = json.getString("hasErrors");
        if (hasErrors.equals("false")) {
            String strList = json.getString("list");
            jsonArray1 = new JSONArray(strList);
        }
        return jsonArray1;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnItemSelected(R.id.move_brand_spinner)
    public void onItemSelected(AdapterView<?> parent, View view,
                               int position, long id) {
        jsonArray2 = new JSONArray();
        TextView tv = (TextView) view;
        json = new JSONObject();
        if (position == 0) {
            arrStore = new String[1];
            tenantUuid = "";
            gsmc = "";
            companyid = "";
            arrStore[0] = "请选择门店";
            adapterStore = new ArrayAdapter<String>(this, R.layout.adapter_spinner, arrStore);
            storelistSpinner.setAdapter(adapterStore);
            ShareBrandSpinner.setBackground(spinner_register_gray);
            tv.setTextColor(text_gray);
            return;
        }
        JSONObject jsonA = null;

        try {
            String a = jsonArray.getString(position - 1);
            jsonA = new JSONObject(a);
            tenantUuid = jsonA.getString("uuid");
            json.put("uuid", uuid);
            json.put("tenantUuid", tenantUuid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tv.setTextColor(colorPrimary);
        ShareBrandSpinner.setBackground(spinner_register_green);
        controller.toHttpStorelist(json, SHARE_STORE);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnItemSelected(R.id.move_storelist_spinner)
    public void onItemSelectedStor(AdapterView<?> parent, View view,
                                   int position, long id) {
        TextView tv = (TextView) view;
        if (position == 0) {
            gsmc = "";
            companyid = "";
            ShareStorelistSpinner.setBackground(spinner_register_gray);
            tv.setTextColor(text_gray);
            return;
        }
        Spinner spinner = (Spinner) parent;
        JSONObject jsonA = null;
        try {
            String a = jsonArray2.getString(position - 1);
            jsonA = new JSONObject(a);
            companyid = jsonA.getString("uuid");
            gsmc = jsonA.getString("text");
            json.put("companyid", companyid);
            json.put("gsmc", gsmc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tv.setTextColor(colorPrimary);
        ShareStorelistSpinner.setBackground(spinner_register_green);
    }

    @OnClick(R.id.share_btn)
    public void ShareBtn() {
        if (tenantUuid.equals("") || tenantUuid == null) {
            Utils.setToast(this, "请选择品牌");
            return;
        }
        if (gsmc.equals("") || gsmc == null || companyid.equals("") || companyid == null) {
            Utils.setToast(this, "请选择门店");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uuid", uuid);
            jsonObject.put("staffUuid", staffUuid);
            jsonObject.put("tenantUuid", tenantUuid);
            jsonObject.put("companyUuid", companyid);
            if (type == 0) jsonObject.put("delete", false);
            else jsonObject.put("delete", true);
            Log.e("jsonObject", jsonObject.toString());
            TimeUtil.waitwhile(this);
            HttpUtil.VolleyHttpPost(this, ConstantValues.HTTP_SHIFTSTAFF + "?staffuuid=" + staffUuid + "&staffid=", staffid, jsonObject, handler, SHARE_MOVE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.iv_title_back)
    public void backPage() {
        finish();
    }
}
