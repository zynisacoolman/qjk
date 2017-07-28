package com.qijukeji.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qijukeji.customView.CircleImageView;
import com.qijukeji.entityModel.CheckOrder;
import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.IntentUtil;
import com.qijukeji.view.ShareOrderActivity;

import java.util.List;

/**
 * 首页显示adapter
 * Created by Administrator on 2017/4/7.
 */

public class MainAdapter extends BaseAdapter {
    private List<CheckOrder> list;
    private Context context;
    private int type;
    private String staffUuid;
    HomeAdapter homeAdapter;

    public MainAdapter(List<CheckOrder> list, Context context, int type, String staffUuid) {
        this.list = list;
        this.context = context;
        this.type = type;
        this.staffUuid = staffUuid;
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
        final CheckOrder checkorder = list.get(position);
        if (convertView == null) {
            view = View.inflate(context, R.layout.adapter_main_list, null);
        } else {
            // 否则表示可以复用convertView
            view = convertView;
        }
        homeAdapter = new HomeAdapter();
        homeAdapter.img_list_mark = (ImageView) view.findViewById(R.id.img_list_mark);
        homeAdapter.img_list_wxpicture = (CircleImageView) view.findViewById(R.id.img_list_wxpicture);
        homeAdapter.tv_order_name = (TextView) view.findViewById(R.id.tv_order_name);
        homeAdapter.tv_order_address = (TextView) view.findViewById(R.id.tv_order_address);
        homeAdapter.tv_time_label = (TextView) view.findViewById(R.id.tv_time_label);
        homeAdapter.tv_order_time = (TextView) view.findViewById(R.id.tv_order_time);
        homeAdapter.img_call_phone = (ImageButton) view.findViewById(R.id.img_call_phone);
        homeAdapter.order_bt_share = (LinearLayout) view.findViewById(R.id.order_bt_share);
        homeAdapter.order_bt_move = (LinearLayout) view.findViewById(R.id.order_bt_move);
        homeAdapter.tv_order_name.setText(checkorder.getUserName());
        if (checkorder.getJustGift().equals("true")) {
            homeAdapter.order_bt_share.setVisibility(View.GONE);
            homeAdapter.img_list_mark.setVisibility(View.VISIBLE);
            homeAdapter.img_list_mark.setBackgroundResource(R.drawable.gift_label);
        } else {
            homeAdapter.order_bt_share.setVisibility(View.VISIBLE);
            if (checkorder.getSource() == null || checkorder.getSource().equals("1")) {
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
        homeAdapter.img_list_wxpicture.setImageBitmap(checkorder.getWxHeadpic());
        String addressUnit = checkorder.getUserAddressUnit();
        if (addressUnit.equals("null")) {
            addressUnit = "";
        }
        homeAdapter.tv_order_address.setText(checkorder.getUserAddressVillage() + addressUnit);
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
        private ImageView img_list_mark;
        private CircleImageView img_list_wxpicture;
        private ImageButton img_call_phone;
        private LinearLayout order_bt_share, order_bt_move;
    }
}