package com.qijukeji.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qijukeji.adapter.GvEvaluateAdapter;
import com.qijukeji.controller.UserDetailsController;
import com.qijukeji.entityModel.Checkorder;
import com.qijukeji.entityModel.CheckorderXx;
import com.qijukeji.entityModel.Checkorderactivity;
import com.qijukeji.entityModel.Comment;
import com.qijukeji.entityModel.Pllist;
import com.qijukeji.entityModel.SelectCoupon;
import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.BuilderShow;
import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.EvaluateUtils;
import com.qijukeji.utils.HttpUtil;
import com.qijukeji.utils.JsonToObjUtil;
import com.qijukeji.utils.StaticField;
import com.qijukeji.utils.TimeUtil;
import com.qijukeji.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;


/**
 * Created by Administrator on 2017/5/15.
 */

public class UserDetailsActivity extends AppCompatActivity {
    private static final String TAG = UserDetailsActivity.class.getSimpleName();
    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.tv_title_name)
    TextView tvTitleName;
    @Bind(R.id.iv_title_right)
    ImageView ivTitleRight;
    @Bind(R.id.select_title_show)
    RelativeLayout select_title_show;
    @Bind(R.id.et_detail_address_xq)
    EditText etDetailAddressXq;
    @Bind(R.id.bt_toMap)
    Button btToMap;
    @Bind(R.id.tv_detail_coupon)
    TextView tvDetailCoupon;
    @Bind(R.id.tv_detail_activity)
    TextView tvDetailActivity;
    @Bind(R.id.tv_detail_activity_title)
    TextView tvDetailActivityTitle;
    @Bind(R.id.et_detail_name)
    EditText etDetailName;
    @Bind(R.id.tv_from)
    TextView tv_from;
    @Bind(R.id.tell_phone)
    ImageView tellPhone;
    @Bind(R.id.et_detail_phone)
    EditText etDetailPhone;
    @Bind(R.id.et_detail_address)
    EditText etDetailAddress;
    @Bind(R.id.et_detail_remarks)
    EditText etDetailRemarks;
    @Bind(R.id.et_detail_amount)
    EditText etDetailAmount;
    @Bind(R.id.et_detail_pay_money)
    EditText etDetailPayMoney;
    @Bind(R.id.ll_paymoney)
    LinearLayout ll_paymoney;
    @Bind(R.id.bt_detail_conserve)
    Button btDetailConserve;
    @BindColor(R.color.gary)
    int gary;
    @BindColor(R.color.black_text)
    int black_text;
    @Bind(R.id.tv_detail_evaluate)
    TextView tvDetailEvaluate;
    @Bind(R.id.gv_detail_list)
    GridView gvDetailList;
    @Bind(R.id.ll_detail_evaluate)
    LinearLayout llDetailEvaluate;
    private JSONObject json;
    private int key;
    private String holldays;
    private String staffid, staffUuid, brandid, companyid, tenantUuid, uuid, source, activityid, data, editString;
    private String type, isCircle, couponid, status, userid, typeEvaluate, content, ordernumber, autocashback;
    private static final int HTTP_SELECT = 1;
    private static final int HTTP_MONEY = 2;
    private static final int HTTP_INSENT = 3;
    private static final int HTTP_UPDATE = 4;
    private static final int HTTP_FINISH = 5;
    private static final int HTTP_SEL_EVALUATE = 6;
    private static final int HTTP_INSENT_EVALUATE = 7;
    private static final int INTENT_KEY = 8;
    private static final int HTTP_HOLIDAYS_OK = 9;
    private Context context;
    private UserDetailsController controller;
    private Double lowerLimitPrice, upperLimitPrice, discount;
    boolean workday;
    private Checkorder checkorder;
    private List<Object> listEvaluate;
    private EvaluateUtils e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_detail);
        Intent intent = getIntent();
        key = intent.getIntExtra(StaticField.KEY, 0);
        SharedPreferences preferences = getSharedPreferences("qiju", Context.MODE_PRIVATE);
        staffid = preferences.getString("staffid", "");
        staffUuid = preferences.getString("staffUuid", "");
        brandid = preferences.getString("brandid", "");
        companyid = preferences.getString("companyid", "");
        tenantUuid = preferences.getString("tenantUuid", "");
        ButterKnife.bind(this);
        tvTitleName.setText("详细信息");
        tvTitleName.setVisibility(View.VISIBLE);
        ivTitleRight.setVisibility(View.VISIBLE);
        select_title_show.setVisibility(View.GONE);
        initView();
        httpHolldays();
        if (key == StaticField.key1) {
            //设置可编辑的
            //扫描跳转页面
            savedata = false;
            Glide.with(context).load(R.drawable.save_icon).into(ivTitleRight);
            data = intent.getStringExtra("data");
            try {
                toSetEt(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            editableEditText();
        } else if (key == StaticField.key2) {
            int resource = R.drawable.edit_icon;
            Glide.with(context).load(resource).into(ivTitleRight);
            //点击意向或者成单跳转的页面
            uuid = intent.getStringExtra("uuid");
            source = intent.getStringExtra("source");
            controller.toHttpSelect(HTTP_SELECT, uuid, source);
        }
    }


    //扫描信息显示
    private void toSetEt(String data) throws JSONException {
        json = new JSONObject(data);
        JSONObject list = json.getJSONObject("list");
        userid = list.getString("userid");
        etDetailPhone.setText(list.getString("phone"));
        etDetailName.setText(list.getString("name"));
        etDetailAddressXq.setText(list.getString("addressvillage"));
        etDetailAddress.setText(list.getString("addressunit"));
        tvDetailActivity.setText(list.getString("descriptionhd"));
        activityid = list.getString("activityid");
        ordernumber = list.getString("ordernumber");
        controller.toMoneyHttp(HTTP_MONEY, activityid, brandid);
        status = "0";
        btDetailConserve.setVisibility(View.GONE);
    }

    //接受网络返回数据
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            switch (msg.what) {
                case HTTP_SELECT:
//                    解析
                    try {
                        setViewText(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case HTTP_MONEY:
                    toSetMoney(data);
                    break;
                case HTTP_INSENT:
                    try {
                        toKey2(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case HTTP_UPDATE:
                    try {
                        toKey2(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case HTTP_FINISH:
                    toStatus(data);
                    break;
                case HTTP_SEL_EVALUATE:
//                    toStatus(data);
                    e.totEvaluate(data, btDetailConserve);
                    break;
                case HTTP_INSENT_EVALUATE:
                    setEvaluate(data);
                    break;
                case HTTP_HOLIDAYS_OK:
                    holldays = holldays(data);
                    break;
                default:
                    break;
            }
        }


    };

    private void httpHolldays() {
        String url = ConstantValues.HTTP_HOLIDAYS + TimeUtil.getDate() + "&apikey=123456";
        HttpUtil.VolleyHttpPost(context, url, "", null, handler, HTTP_HOLIDAYS_OK);
    }

    private String holldays(String data) {
        String isrelax = "";
        try {
            JSONObject json = new JSONObject(data);
            isrelax = json.getString("data");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return isrelax;
    }

    public void setEvaluate(String data) {
        String jg = null;
        try {
            json = new JSONObject(data);
            jg = json.getString("jg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jg.equals("true")) {
            status = "2";
            Utils.setToast(context, "评论成功");
            controller.toHttpSelect(HTTP_SELECT, uuid, source);
            noEditableEditText();
        } else {
            Utils.setToast(context, "评论失败");
        }
    }

    private void toStatus(String data) {
        JSONObject json;
        String jg = null;
        String errorMessage = "";
        String errorCode = "";
        try {
            json = new JSONObject(data);
            jg = json.getString("hasErrors");
            errorMessage = json.getString("errorMessage");
            errorCode = json.getString("errorCode");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jg.equals("false")) {
            status = "1";
            Utils.setToast(context, "结单成功");
            noEditableEditText();
            SharedPreferences sp = getSharedPreferences("qiju", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("kehutype", "2");
            editor.commit();
            finish();
        } else {
            if (errorCode.equals("200"))
            Utils.setToast(context, errorMessage);
        }
    }

    private void toKey2(String data) throws JSONException {
        JSONObject json = new JSONObject(data);
        if (json.getString("jg").equals("true")) {
            uuid = json.getString("uuid");
            key = 2;
            status = "0";
            noEditableEditText();
        }
    }

    //显示优惠信息和标题
    private void toSetMoney(String data) {
        Log.e("coupontype", data + "测试区域");
        SelectCoupon sel = (SelectCoupon) JsonToObjUtil.jsonToObj(data, SelectCoupon.class);
        if (sel == null) {
            autocashback = "true";
            tvDetailActivityTitle.setText("优惠：无优惠券信息");
            tvDetailCoupon.setText("");
            return;
        }
        autocashback = sel.getAutocashback();
        tvDetailCoupon.setText(sel.getSubtitle());
        tvDetailActivityTitle.setText("优惠：" + sel.getTitle());
        lowerLimitPrice = Double.parseDouble(sel.getLowerLimitPrice());
        upperLimitPrice = Double.parseDouble(sel.getUpperLimitPrice());
        discount = Double.parseDouble(sel.getDiscount());
        workday = sel.getWorkday();
        type = sel.getType();
        isCircle = sel.getIsCircle();
        couponid = sel.getCouponid();
        if (autocashback != null) {
            if (autocashback.equals("true")) {
                ll_paymoney.setVisibility(View.GONE);
            } else if (autocashback.equals("false")) {
                ll_paymoney.setVisibility(View.VISIBLE);
            }
        } else if (autocashback == null) {
            ll_paymoney.setVisibility(View.VISIBLE);
        }
    }

    //点击意向和成单进入  显示用户信息
    private void setViewText(String data) throws JSONException {
        JSONObject json = new JSONObject(data);
        Log.e("info", "测试区域" + data);
        String nr = json.getString("nr");
        String zyly = json.getString("zyly");
        CheckorderXx c = (CheckorderXx) JsonToObjUtil.jsonArrayToObj(nr, CheckorderXx.class);
        if (c == null) {
            autocashback = "true";
            Utils.setToast(this, "无客户信息");
            return;
        }
        autocashback = c.getAutocashback();
        if (autocashback != null) {
            if (autocashback.equals("true")) {
                ll_paymoney.setVisibility(View.GONE);
            } else if (autocashback.equals("false")) {
                ll_paymoney.setVisibility(View.VISIBLE);
            }
        } else if (autocashback == null) {
            ll_paymoney.setVisibility(View.VISIBLE);
        }
        editString = Double.parseDouble(c.getTotalmoney()) + "";
        etDetailAmount.setText(editString);
        etDetailPayMoney.setText(Double.parseDouble(c.getPaymoney()) + "");
        if (zyly.equals("")) {
            etDetailName.setText(c.getUsername());
        } else {
            etDetailName.setText(c.getUsername());
            tv_from.setText("(" + zyly + ")");
        }
        etDetailPhone.setText(c.getUserphone());
        etDetailAddressXq.setText(c.getUseraddressvillage());
        etDetailAddress.setText(c.getUseraddressunit());
        tvDetailCoupon.setText(c.getDescriptionconpant());
        tvDetailActivity.setText(c.getDescriptionhd());
        etDetailRemarks.setText(c.getRemark());
        tvDetailActivityTitle.setText("优惠：" + c.getTitle());
        lowerLimitPrice = c.getLowerlimitprice();
        upperLimitPrice = c.getUpperlimitprice();
        status = c.getStatus();
        userid = c.getUserid();
        discount = c.getDiscount();
        type = c.getType();
        isCircle = c.getIscircle();
        activityid = c.getActivityid();
        couponid = c.getCouponid();
        ordernumber = c.getOrdernumber();

        if (status.equals("2")) {
            String pl = json.getString("pl");
            List<Object> listPl = JsonToObjUtil.jsonArrayToListObj(pl, Pllist.class);

            if (listPl.size() > 0) {
                typeEvaluate = ((Pllist) listPl.get(0)).getType();
                if (typeEvaluate.equals("3")) {
                    content = ((Pllist) listPl.get(0)).getContent1();
                } else {
                    this.listEvaluate = new ArrayList<>();
                    for (Object p : listPl) {
                        Comment comment = new Comment();
                        comment.setContent(((Pllist) p).getContent2());
                        this.listEvaluate.add(comment);
                    }

                }
            } else {
            }
            noEditableEditText();
        } else {
            noEditableEditText();
        }
    }

    private void initView() {
        context = this;
        upperLimitPrice = 0.0;
        controller = new UserDetailsController(context, handler, staffid);
        //输入金额停止1秒时间计算
        etDetailAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (delayRun != null) {
                    //每次editText有变化的时候，则移除上次发出的延迟线程
                    handler1.removeCallbacks(delayRun);
                }
                String editString1 = s.toString();
                if (editString1 == editString) {
                    return;
                }
                if (editString1.equals("") || editString1 == null) {
                    return;
                }
                if (key == 2) {
                    return;
                }
                editString = editString1;
                //延迟1000ms，如果不再输入字符，则执行该线程的run方法
                handler1.postDelayed(delayRun, 1500);
            }
        });

    }

    private Handler handler1 = new Handler();

    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
            //在这里调用服务器的接口，获取数据
            if (autocashback != null) {
                if (autocashback.equals("false")) {
                    countMoney();
                }
            } else if (autocashback == null) {
                countMoney();
            }

        }
    };

    //计算金额
    private void countMoney() {
        Double d = Utils.countMoney(context, type, Double.parseDouble(editString), lowerLimitPrice, upperLimitPrice, discount, isCircle);
        etDetailPayMoney.setText(d + "");
    }

    /**
     * 可编辑
     */

    private void editableEditText() {
        savedata = false;
        Glide.with(context).load(R.drawable.save_icon).into(ivTitleRight);
        etDetailName.setEnabled(true);
        etDetailAddress.setEnabled(true);
        etDetailAddressXq.setEnabled(true);
        etDetailPhone.setEnabled(true);
        etDetailRemarks.setEnabled(true);
        etDetailAmount.setEnabled(true);
        etDetailPayMoney.setEnabled(true);
        etDetailName.setHint("请输入名字");
        etDetailPhone.setHint("请输入电话");
        etDetailAddress.setHint("详细地址");
        etDetailAddressXq.setHint("小区信息");
        etDetailRemarks.setHint("请输入备注");
        etDetailAmount.setHint("请输入总金额");
        etDetailPayMoney.setHint("请输入支付金额");
        tv_from.setTextColor(gary);
        etDetailName.setTextColor(gary);
        etDetailPhone.setTextColor(gary);
        etDetailAddress.setTextColor(gary);
        etDetailAddressXq.setTextColor(gary);
        etDetailRemarks.setTextColor(gary);
        etDetailAmount.setTextColor(gary);
        etDetailPayMoney.setTextColor(gary);
        llDetailEvaluate.setVisibility(View.GONE);
    }

    /**
     * 不可编辑
     */
    private void noEditableEditText() {
        e = new EvaluateUtils(uuid, staffid, userid, handler, HTTP_INSENT_EVALUATE, context);
        ivTitleRight.setVisibility(View.VISIBLE);
        etDetailName.setEnabled(false);
        etDetailAddress.setEnabled(false);
        etDetailPhone.setEnabled(false);
        etDetailRemarks.setEnabled(false);
        etDetailAmount.setEnabled(false);
        etDetailPayMoney.setEnabled(false);
        tv_from.setTextColor(black_text);
        etDetailName.setTextColor(black_text);
        etDetailPhone.setTextColor(black_text);
        etDetailAddress.setTextColor(black_text);
        etDetailAddressXq.setTextColor(black_text);
        etDetailRemarks.setTextColor(black_text);
        etDetailAmount.setTextColor(black_text);
        etDetailPayMoney.setTextColor(black_text);
        if (status == null) {
            return;
        }
        if (status.equals("0")) {
            ivTitleRight.setVisibility(View.VISIBLE);

            btDetailConserve.setText("成单");
            llDetailEvaluate.setVisibility(View.GONE);
        } else if (status.equals("1")) {
            ivTitleRight.setVisibility(View.INVISIBLE);
            btDetailConserve.setText("评价");
            llDetailEvaluate.setVisibility(View.GONE);
        } else {
            btDetailConserve.setVisibility(View.GONE);
            ivTitleRight.setVisibility(View.INVISIBLE);
            if (typeEvaluate == null) {
                tvDetailEvaluate.setText("无评价");
                return;
            }
            llDetailEvaluate.setVisibility(View.VISIBLE);
            if (typeEvaluate.equals("3")) {
                tvDetailEvaluate.setText(content);
                gvDetailList.setVisibility(View.GONE);
                tvDetailEvaluate.setVisibility(View.VISIBLE);
            } else {
                tvDetailEvaluate.setVisibility(View.GONE);
                gvDetailList.setVisibility(View.VISIBLE);
                int[] clickedList = new int[listEvaluate.size()];
                for (int i = 0; i < listEvaluate.size(); i++) {
                    clickedList[i] = 1;
                }
                GvEvaluateAdapter adapter = new GvEvaluateAdapter(listEvaluate, context, clickedList, handler);
                gvDetailList.setAdapter(adapter);
            }
        }

    }

    @OnClick({R.id.iv_title_back, R.id.iv_title_right, R.id.bt_detail_conserve, R.id.et_detail_address_xq, R.id.tell_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                Log.e("123", "------iv_title_back--退出------");
                finish();
                break;
            case R.id.iv_title_right:
                Log.e("123", "------iv_title_right---保存/编辑-----");
                if (savedata) {
                    key = 3;
                    editableEditText();
                    btDetailConserve.setVisibility(View.GONE);
                } else {
                    if (key == 1) {
                        try {
                            toInsert();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (key == 3) {
                        try {
                            toHttpUpdate();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Glide.with(context).load(R.drawable.edit_icon).into(ivTitleRight);
                    savedata = true;
                    btDetailConserve.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.bt_detail_conserve:
                Log.e("123", "------bt_detail_conserve---成单/评价-----");
                toBuilderShou();
                break;
            case R.id.et_detail_address_xq:
                Log.e("123", "------bt_detail_conserve-----地图---");
                if (key == 1 || key == 3) {
                    Intent it = new Intent(UserDetailsActivity.this, MapActivity.class);
                    startActivityForResult(it, INTENT_KEY);
                }
                break;
            case R.id.tell_phone:
                if (savedata) {
                    String tellphone = etDetailPhone.getText().toString();
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + tellphone));
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * 结束扫描页面返回信息
     *
     * @param requestCode 传入标记值
     * @param resultCode  返回标记值
     * @param data        返回数据
     */
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

    boolean savedata = true;

    private void toBuilderShou() {
        String bttext = btDetailConserve.getText().toString();
        if (bttext.equals("评价")) {
            toConserve();
        } else if (bttext.equals("成单")) {
            BuilderShow.LeaveMyDialogListener DialogonClick = new BuilderShow.LeaveMyDialogListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String total = etDetailAmount.getText().toString();
//                    String paymoney = etDetailPayMoney.getText().toString();
                    if (total.equals("") || Double.parseDouble(total) <= 0) {
                        Utils.setToast(context, "总金额不可为空或小于0");
                    } else {
                        toConserve();
                    }
                }
            };
            BuilderShow.builderShou(context, "确认", "是否确定？", "确认", "取消", DialogonClick);
        }
    }

    private void toConserve() {
        if (status.equals("0")) {
            //成单
            String total = etDetailAmount.getText().toString();
            String paymoney = etDetailPayMoney.getText().toString();
            controller.toHttpFinish(uuid, userid, activityid, ordernumber, staffUuid, couponid, staffid, brandid, tenantUuid, companyid, paymoney, total, HTTP_FINISH);
        } else if (status.equals("1")) {
            //查询评价标签
            e.toHttpSelEvaluate(1, HTTP_SEL_EVALUATE);
        }
    }

    /**
     * 修改之后调取保存接口
     */
    private void toHttpUpdate() throws JSONException {
        Checkorder jsonCheckorder = getCheckorder();
        controller.toHttpUpdate(jsonCheckorder, HTTP_UPDATE);
    }

    //添加用户信息
    private void toInsert() throws JSONException {
        JSONObject list = json.getJSONObject("list");
        Checkorder jsonCheckorder = getCheckorder(list.getString("userid").toString());
        jsonCheckorder.setOrdernumber(list.getString("ordernumber").toString());
        Checkorderactivity chec = new Checkorderactivity();
        chec.setCheckorderid(list.getString("ordernumber").toString());
        chec.setCouponid(couponid);
        chec.setActivityid(list.getString("activityid").toString());
        controller.toHttpInsert(jsonCheckorder, chec, HTTP_INSENT);
    }

    /**
     * 修改完成点击保存获取页面显示内容
     *
     * @return
     */
    public Checkorder getCheckorder() throws JSONException {
        Checkorder c = getCheckorder("");
        c.setActivityid(activityid);
        c.setUuid(uuid);
        c.setCouponid(couponid);
        return c;
    }

    public Checkorder getCheckorder(String userid) throws JSONException {
        String memberPhone = etDetailPhone.getText().toString();
        String memberName = etDetailName.getText().toString();
        String memberAddress_xq = etDetailAddressXq.getText().toString();
        String memberAddress = etDetailAddress.getText().toString();
        String remark = etDetailRemarks.getText().toString();
        checkorder = new Checkorder();
        checkorder.setStaffid(staffid);
        checkorder.setUserid(userid);
        checkorder.setUserphone(memberPhone);
        checkorder.setUsername(memberName);
        checkorder.setUseraddressvillage(memberAddress_xq);
        checkorder.setUseraddressunit(memberAddress);
        checkorder.setRemark(remark);
        String total = etDetailAmount.getText().toString();
        String paymoney = etDetailPayMoney.getText().toString();
        if (total.equals("")) {
            total = "0";
        }
        if (paymoney.equals("")) {
            paymoney = "0";
        }
        checkorder.setTotalmoney(total);
        checkorder.setPaymoney(paymoney);
        return checkorder;
    }

    @OnFocusChange(R.id.et_detail_amount)
    public void focusevent(boolean hasFocus) {
        if (hasFocus) {
            etDetailAmount.setText("");
        }
    }

}
