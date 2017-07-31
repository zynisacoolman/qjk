package com.qijukeji.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.qijukeji.qiju_dg.R;

import java.util.List;

/**
 * Created by Administrator on 2017/4/29.
 */

public class MapListAdapter extends BaseAdapter {
    Context context;
    List<PoiItem> list;

    public MapListAdapter() {
    }

    public MapListAdapter(Context context, List<PoiItem> list) {
        this.context = context;
        this.list = list;
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
        return 0;
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
        HomeAdapter homeAdapter = new HomeAdapter();
        homeAdapter.tv_map_name = (TextView) view.findViewById(R.id.tv_map_name);
        homeAdapter.tv_map_address = (TextView) view.findViewById(R.id.tv_map_address);
        homeAdapter.tv_map_name.setText(list.get(position).getTitle());
        homeAdapter.tv_map_address.setText(list.get(position).getSnippet());
        return view;
    }

    class HomeAdapter {
        TextView tv_map_name;

        TextView tv_map_address;

        private HomeAdapter() {

        }

    }
}
