package com.qijukeji.view;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.qijukeji.controller.RegisterController;
import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.IntentUtil;
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

public class RegisterActivity extends AppCompatActivity {
    @SuppressWarnings("ResourceType")
    @Bind(R.id.brand_spinner)
    Spinner brand_spinner;
    @Bind(R.id.storelist_spinner)
    Spinner storelistSpinner;
    @Bind(R.id.button2)
    Button button2;
    @Bind(R.id.reg_name)
    EditText etName;
    @Bind(R.id.et_register_phone)
    EditText etRegisterPhone;
    @Bind(R.id.et_register_password)
    EditText etRegisterPassword;
    @Bind(R.id.ll_brand_spinner)
    LinearLayout llBrandSpinner;
    @Bind(R.id.ll_storelist_spinner)
    LinearLayout llStorelistSpinner;
    @BindColor(R.color.text_gray)
    int text_gray;
    @BindColor(R.color.colorPrimary)
    int colorPrimary;

    @BindDrawable(R.drawable.spinner_register_gray)
    Drawable spinner_register_gray;
    @BindDrawable(R.drawable.spinner_register_green)
    Drawable spinner_register_green;

    private RegisterController controller;
    private Context context;
    JSONObject json = null;
    public static final int HTTP_PLTEXT = 1;
    public static final int HTTP_STORELIST = 2;
    private static final int HTTP_REGISTER = 3;
    private JSONArray jsonArray;
    private JSONArray jsonArray2;
    private String companyid;
    private String tenantUuid;
    private String gsmc;
    private String[] arrBrand;
    private String[] arrStore;
    private ArrayAdapter<String> adapterBrand;
    private ArrayAdapter<String> adapterStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        context = this;
        controller = new RegisterController(context, handler);
        toHttpPltext(null);
    }

    @SuppressWarnings("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnItemSelected(R.id.brand_spinner)
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
            llBrandSpinner.setBackground(spinner_register_gray);
            tv.setTextColor(text_gray);
            return;
        }
        JSONObject jsonA = null;

        try {
            String a = jsonArray.getString(position - 1);
            jsonA = new JSONObject(a);
            jsonA.getString("id");
            tenantUuid = jsonA.getString("id");
            json.put("tenantUuid", tenantUuid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tv.setTextColor(colorPrimary);
        llBrandSpinner.setBackground(spinner_register_green);
        toHttpStorelist(json, HTTP_STORELIST);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnItemSelected(R.id.storelist_spinner)
    public void onItemSelectedStor(AdapterView<?> parent, View view,
                                   int position, long id) {
        TextView tv = (TextView) view;
        if (position == 0) {
            gsmc = "";
            companyid = "";
            llStorelistSpinner.setBackground(spinner_register_gray);
            tv.setTextColor(text_gray);
            return;
        }
        Spinner spinner = (Spinner) parent;
        JSONObject jsonA = null;
        try {
            String a = jsonArray2.getString(position - 1);
            jsonA = new JSONObject(a);
            companyid = jsonA.getString("id");
            gsmc = jsonA.getString("text");
            json.put("companyid", companyid);
            json.put("gsmc", gsmc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tv.setTextColor(colorPrimary);
        llStorelistSpinner.setBackground(spinner_register_green);
    }


    private void toHttpPltext(JSONObject json) {
        controller.toHttpPltext(HTTP_PLTEXT,json);
    }

    private void toHttpStorelist(JSONObject json, int msgWhat) {
        controller.toHttpStorelist(json, msgWhat);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            switch (msg.what) {
                case HTTP_PLTEXT:
                    try {
                        toSetSpinner(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case HTTP_STORELIST:
                    try {
                        toSetStorelist(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case HTTP_REGISTER:
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
        String phone = etRegisterPhone.getText().toString();
        String password = etRegisterPassword.getText().toString();
        JSONObject json = new JSONObject(data);
        if (json.getString("jg").equals("0")) {
            SharedPreferences sp = getSharedPreferences("qiju", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("staffid", phone);
            editor.putString("password", password);
            editor.putString("tc", "1");
            editor.commit();
            Utils.setToast(context, "注册成功");
            IntentUtil.intentToNull(this, BlankPageActivity.class);
        } else if (json.getString("jg").equals("1")) {
            Utils.setToast(context, "注册失败");
        } else if (json.getString("jg").equals("2")) {
            Utils.setToast(context, "该账号已经注册");
        }
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
        brand_spinner.setAdapter(adapterBrand);
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

    //注册按钮
    @OnClick(R.id.button2)
    public void onViewClicked() {
        String name = etName.getText().toString();
        String phone = etRegisterPhone.getText().toString();
        String password = etRegisterPassword.getText().toString();
        if (name.equals("") || name == null) {
            Utils.setToast(context, "请输入姓名");
            return;
        }
        if (phone.equals("") || phone == null) {
            Utils.setToast(context, "请输入账号");
            return;
        }
        if (password.equals("") || password == null) {
            Utils.setToast(context, "请输入密码");
            return;
        }
        try {
            json.put("xm", name);
            json.put("password", password);
            json.put("name", phone);
            if (tenantUuid.equals("") || tenantUuid == null) {
                Utils.setToast(context, "请选择品牌");
                return;
            }
            if (gsmc.equals("") || gsmc == null || companyid.equals("") || companyid == null) {
                Utils.setToast(context, "请选择门店");
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("123", json.toString());
        controller.toHttpRegister(json, HTTP_REGISTER, phone);
    }
}
