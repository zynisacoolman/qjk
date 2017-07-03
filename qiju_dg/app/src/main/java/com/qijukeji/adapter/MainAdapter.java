package com.qijukeji.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qijukeji.entityModel.Checkorder;
import com.qijukeji.qiju_dg.R;
import com.qijukeji.receiver.UpdateService;
import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.EvaluateUtils;
import com.qijukeji.utils.HttpUtil;
import com.qijukeji.utils.IntentUtil;
import com.qijukeji.utils.PopupWindowHelper;
import com.qijukeji.utils.StaticField;
import com.qijukeji.view.ShareOrderActivity;
import com.qijukeji.view.ThemeActivity;
import com.qijukeji.view.UserDetailsActivity;
import com.qijukeji.view.fragment.FirstFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.qijukeji.utils.StaticField.key2;

/**
 * 首页显示adapter
 * Created by Administrator on 2017/4/7.
 */

public class MainAdapter extends BaseAdapter {
    private List<Object> list;
    private Context context;
    private int type;
    private String staffid, staffUuid, brandid;
    private Handler handler;
    private EvaluateUtils e;
    HomeAdapter homeAdapter;

    public MainAdapter(List<Object> list, Context context, int type, String staffid, String staffUuid, String brandid, Handler handler, EvaluateUtils e) {
        this.list = list;
        this.context = context;
        this.type = type;
        this.staffid = staffid;
        this.staffUuid = staffUuid;
        this.brandid = brandid;
        this.e = e;
        this.handler = handler;

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        final Checkorder checkorder = (Checkorder) list.get(position);
        if (convertView == null) {
            view = View.inflate(context, R.layout.adapter_main_list, null);
        } else {
            // 否则表示可以复用convertView
            view = convertView;
        }
        homeAdapter = new HomeAdapter();
        homeAdapter.bt_my_list_transaction = (Button) view.findViewById(R.id.bt_my_list_transaction);
        homeAdapter.kehu_seting = (Button) view.findViewById(R.id.kehu_seting);
        homeAdapter.img_gift = (ImageView) view.findViewById(R.id.img_gift);
        homeAdapter.tv_main_name = (TextView) view.findViewById(R.id.tv_main_name);
        homeAdapter.tv_main_phone = (TextView) view.findViewById(R.id.tv_main_phone);
        homeAdapter.tv_main_address = (TextView) view.findViewById(R.id.tv_main_address);
        homeAdapter.tv_main_time = (TextView) view.findViewById(R.id.tv_main_time);
        homeAdapter.tv_main_name.setText(checkorder.getUsername());
        homeAdapter.tv_main_phone.setText(checkorder.getUserphone());
        homeAdapter.tv_main_address.setText(checkorder.getUseraddressvillage() + checkorder.getUseraddressunit());
        if (checkorder.getOrderaction() == null) {
            homeAdapter.tv_main_time.setText(checkorder.getCreatedtime());
        } else {
            homeAdapter.tv_main_time.setText(checkorder.getCreatedtime() + checkorder.getOrderaction());
        }
        if (type == 1) {
        } else if (type == 2) {
            homeAdapter.tv_move_kehu.setVisibility(View.GONE);
            String a = ((Checkorder) list.get(position)).getStatus();
            String order = ((Checkorder) list.get(position)).getOrdernumber();
            if (a.equals("1")) {
                homeAdapter.bt_my_list_transaction.setVisibility(View.VISIBLE);
                homeAdapter.img_gift.setVisibility(View.GONE);
                homeAdapter.bt_my_list_transaction.setText("评价");
            } else if (a.equals("2")) {
                if (order.equals("")) {
                    homeAdapter.img_gift.setVisibility(View.VISIBLE);
                }
                homeAdapter.bt_my_list_transaction.setVisibility(View.GONE);
            }
        }
        //type = 1  是 意向信息
        //type = 2 是交易信息
        homeAdapter.bt_my_list_transaction.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("123", "---------");
                        if (type == 1) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(StaticField.KEY, key2);
                            bundle.putSerializable("uuid", checkorder.getUuid());
                            bundle.putSerializable("staffid", checkorder.getSource());
                            IntentUtil.intentToNull(context, UserDetailsActivity.class, bundle);
                        } else if (type == 2) {
                            e.setUuid(checkorder.getUuid());
                            e.setUserid(checkorder.getUserid());
                            e.toHttpSelEvaluate(1, FirstFragment.HTTP_SEL_EVALUATE);
                            checkorder.setStatus("2");
                        }
                    }
                }
        );
        homeAdapter.kehu_seting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeAdapter.popwindows.showAsPopUp(view);
            }
        });
        homeAdapter.tvOnClick(checkorder.getUuid());
        return view;
    }

    public void setData(String data) {
        e.totEvaluate(data, homeAdapter.bt_my_list_transaction);
    }

    class HomeAdapter {
        private Button bt_my_list_transaction, kehu_seting;
        private TextView tv_main_name, tv_main_time, tv_main_phone, tv_main_address;
        private ImageView img_gift;
        private PopupWindowHelper popwindows;
        private View popView;
        private TextView tv_share_kehu, tv_delete_kehu, tv_move_kehu;

        private HomeAdapter() {
            initPop();
        }

        private void initPop() {
            popView = LayoutInflater.from(context).inflate(R.layout.popwindows, null);
            popwindows = new PopupWindowHelper(popView);
            tv_share_kehu = (TextView) popView.findViewById(R.id.tv_share_kehu);
            tv_delete_kehu = (TextView) popView.findViewById(R.id.tv_delete_kehu);
            tv_move_kehu = (TextView) popView.findViewById(R.id.tv_move_kehu);
        }

        public void tvOnClick(final String uuid) {
            tv_share_kehu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("staffid", staffid);
                    bundle.putSerializable("uuid", uuid);
                    bundle.putSerializable("type", 2);
                    bundle.putSerializable("staffUuid", staffUuid);
                    bundle.putSerializable("brandid", brandid);
                    IntentUtil.intentToNull(context, ShareOrderActivity.class, bundle);
                    popwindows.dismiss();
                }
            });
            tv_delete_kehu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_HIDDENORDER, staffid, json, null, 1);
                            Bundle b = new Bundle();
                            b.putSerializable("staffid", staffid);
                            b.putSerializable("staffUuid", staffUuid);
                            b.putSerializable("brandid", brandid);
                            IntentUtil.intentToNull(context, ThemeActivity.class, b);
                        }
                    }).setCancelable(false).show();
                    popwindows.dismiss();
                }
            });
            tv_move_kehu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("staffid", staffid);
                    bundle.putSerializable("uuid", uuid);
                    bundle.putSerializable("type", 1);
                    bundle.putSerializable("staffUuid", staffUuid);
                    bundle.putSerializable("brandid", brandid);
                    IntentUtil.intentToNull(context, ShareOrderActivity.class, bundle);
                    popwindows.dismiss();
                }
            });
        }

    }
}
