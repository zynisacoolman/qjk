package com.qijukeji.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qijukeji.qiju_dg.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/12.
 */

public class DealDetailsAdapter extends BaseAdapter {
    private List<Object> list;
    private Context context;

    public DealDetailsAdapter() {
    }

    public DealDetailsAdapter(List<Object> list, Context context) {
        this.list = list;
        this.context = context;
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
        if (convertView == null) {
            view = View.inflate(context, R.layout.adapter_deal_details, null);
        } else {
            // 否则表示可以复用convertView
            view = convertView;
        }
        HomeAdapter homeAdapter = new HomeAdapter();
        Map<String, String> map = (Map<String, String>) list.get(position);
        homeAdapter.tv_deal_id = (TextView) view.findViewById(R.id.tv_deal_id);
        homeAdapter.tv_deal_name = (TextView) view.findViewById(R.id.tv_deal_name);
        homeAdapter.tv_deal_money = (TextView) view.findViewById(R.id.tv_deal_money);
        homeAdapter.tv_deal_content = (TextView) view.findViewById(R.id.tv_deal_content);
        homeAdapter.tv_deal_id.setText("活动" + (position + 1)+":");
        homeAdapter.tv_deal_name.setText(map.get("content"));
        homeAdapter.tv_deal_money.setText(map.get("money"));
        homeAdapter.tv_deal_content.setText(map.get("title"));
        return view;
    }

    class HomeAdapter {
        private TextView tv_deal_id;
        private TextView tv_deal_name;
        private TextView tv_deal_money;
        private TextView tv_deal_content;

        public HomeAdapter() {

        }
    }
}
