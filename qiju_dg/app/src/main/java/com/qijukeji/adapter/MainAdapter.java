package com.qijukeji.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qijukeji.entityModel.CheckOrder;
import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.EvaluateUtils;
import com.qijukeji.utils.HttpUtil;
import com.qijukeji.utils.IntentUtil;
import com.qijukeji.utils.PopupWindowHelper;
import com.qijukeji.view.ShareOrderActivity;
import com.qijukeji.view.ThemeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
        final CheckOrder checkorder = (CheckOrder) list.get(position);
        if (convertView == null) {
            view = View.inflate(context, R.layout.adapter_main_list, null);
        } else {
            // 否则表示可以复用convertView
            view = convertView;
        }
        homeAdapter = new HomeAdapter();
        homeAdapter.img_list_mark = (ImageView) view.findViewById(R.id.img_list_mark);
        homeAdapter.img_list_wxpicture = (ImageView) view.findViewById(R.id.img_list_wxpicture);
        homeAdapter.tv_order_name = (TextView) view.findViewById(R.id.tv_order_name);
        homeAdapter.tv_order_address = (TextView) view.findViewById(R.id.tv_order_address);
        homeAdapter.tv_time_label = (TextView) view.findViewById(R.id.tv_time_label);
        homeAdapter.tv_order_time = (TextView) view.findViewById(R.id.tv_order_time);
        homeAdapter.img_call_phone = (ImageButton) view.findViewById(R.id.img_call_phone);
        homeAdapter.order_bt_share = (LinearLayout) view.findViewById(R.id.order_bt_share);
        homeAdapter.order_bt_move = (LinearLayout) view.findViewById(R.id.order_bt_move);
        homeAdapter.tv_order_name.setText(checkorder.getUserName());
        if (checkorder.getJustGift().equals("true")) {
            homeAdapter.img_list_mark.setVisibility(View.VISIBLE);
            homeAdapter.img_list_mark.setBackgroundResource(R.drawable.gift_label);
        } else {
            if (checkorder.getSource() == null || checkorder.getSource().equals("")) {
                homeAdapter.img_list_mark.setVisibility(View.GONE);
            } else {
                homeAdapter.img_list_mark.setVisibility(View.VISIBLE);
                homeAdapter.img_list_mark.setBackgroundResource(R.drawable.transfer_label);
            }
        }
        if (checkorder.getStatus() == 0) {
            homeAdapter.order_bt_move.setVisibility(View.VISIBLE);
        } else {
            homeAdapter.order_bt_move.setVisibility(View.GONE);
        }
        Glide.with(context)
                .load(checkorder.getUserHeadImageUrl())
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(homeAdapter.img_list_wxpicture);
        homeAdapter.tv_order_address.setText(checkorder.getUserAddressVillage() + checkorder.getUserAddressUnit());
        homeAdapter.tv_time_label.setText("获取时间");
        homeAdapter.tv_order_time.setText(checkorder.getUpdateTime());
        homeAdapter.img_call_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + checkorder.getUserPhone()));
                context.startActivity(intent);
            }
        });
        homeAdapter.order_bt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("uuid", checkorder.getUuid());
                bundle.putSerializable("type", 0);
                bundle.putSerializable("staffUuid", staffUuid);
                IntentUtil.intentToNull(context, ShareOrderActivity.class, bundle);
            }
        });
        homeAdapter.order_bt_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("uuid", checkorder.getUuid());
                bundle.putSerializable("type", 1);
                bundle.putSerializable("staffUuid", staffUuid);
                IntentUtil.intentToNull(context, ShareOrderActivity.class, bundle);
            }
        });
        return view;
    }

    class HomeAdapter {
        private TextView tv_order_name, tv_order_address, tv_time_label, tv_order_time;
        private ImageView img_list_mark, img_list_wxpicture;
        private ImageButton img_call_phone;
        private LinearLayout order_bt_share, order_bt_move;
//        private PopupWindowHelper popwindows;
//        private View popView;
//        private TextView tv_share_kehu, tv_delete_kehu, tv_move_kehu;

        private HomeAdapter() {
        }

//        private void initPop() {
//            popView = LayoutInflater.from(context).inflate(R.layout.popwindows, null);
//            popwindows = new PopupWindowHelper(popView);
//            tv_share_kehu = (TextView) popView.findViewById(R.id.tv_share_kehu);
//            tv_delete_kehu = (TextView) popView.findViewById(R.id.tv_delete_kehu);
//            tv_move_kehu = (TextView) popView.findViewById(R.id.tv_move_kehu);
//        }
//
//        public void tvOnClick(final String uuid) {
//            tv_share_kehu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("uuid", uuid);
//                    bundle.putSerializable("type", 0);
//                    bundle.putSerializable("staffUuid", staffUuid);
//                    IntentUtil.intentToNull(context, ShareOrderActivity.class, bundle);
//                    popwindows.dismiss();
//                }
//            });
//            tv_delete_kehu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setTitle("删除订单").setMessage("确定删除该客户信息吗？");
//                    builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
//                    }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            JSONObject json = new JSONObject();
//                            try {
//                                json.put("uuid", uuid);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_HIDDENORDER + "?staffuuid=" + staffUuid + "&staffid=", staffid, json, null, 1);
//                            Bundle b = new Bundle();
//                            b.putSerializable("staffid", staffid);
//                            b.putSerializable("staffUuid", staffUuid);
//                            b.putSerializable("brandid", brandid);
//                            IntentUtil.intentToNull(context, ThemeActivity.class, b);
//                        }
//                    }).setCancelable(false).show();
//                    popwindows.dismiss();
//                }
//            });
//            tv_move_kehu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("uuid", uuid);
//                    bundle.putSerializable("type", 1);
//                    bundle.putSerializable("staffUuid", staffUuid);
//                    IntentUtil.intentToNull(context, ShareOrderActivity.class, bundle);
//                    popwindows.dismiss();
//                }
//            });
//        }

    }
}
