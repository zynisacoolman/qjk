package com.qijukeji.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qijukeji.qiju_dg.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/19.
 */

public class InPuttipsAdapter extends BaseAdapter {

    private List<HashMap<String, String>> list;
    private Context context;

    public InPuttipsAdapter(List<HashMap<String, String>> list, Context context) {
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
            view = View.inflate(context, R.layout.adapter_map_list, null);
        } else {
            // 否则表示可以复用convertView
            view = convertView;
        }
        Map<String, String> map = list.get(position);
        InPuttipsAdapter.HomeAdapter homeAdapter = new InPuttipsAdapter.HomeAdapter();
        homeAdapter.tv_map_name = (TextView) view.findViewById(R.id.tv_map_name);
        homeAdapter.tv_map_address = (TextView) view.findViewById(R.id.tv_map_address);
        homeAdapter.tv_map_name.setText(map.get("name"));
        homeAdapter.tv_map_address.setText(map.get("address"));
        return view;
    }

    class HomeAdapter {
        TextView tv_map_name;

        TextView tv_map_address;

        private HomeAdapter() {

        }

    }
}
