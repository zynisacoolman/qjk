package com.qijukeji.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qijukeji.qiju_dg.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/4/8.
 */

public class ListDetailsAdapter extends BaseAdapter {

    private List<Object> list;
    private Context context;

    public ListDetailsAdapter() {
    }

    public ListDetailsAdapter(List<Object> list, Context context) {
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
            view = View.inflate(context, R.layout.adapter_list_details, null);
        } else {
            // 否则表示可以复用convertView
            view = convertView;
        }
        HomeAdapter homeAdapter = new HomeAdapter();
        homeAdapter.tv_activity_content = (TextView) view.findViewById(R.id.tv_activity_content);
        homeAdapter.tv_activity_title = (TextView) view.findViewById(R.id.tv_activity_title);
        HashMap map = (HashMap) list.get(position);
        homeAdapter.tv_activity_content.setText(map.get("content").toString());
        homeAdapter.tv_activity_title.setText(map.get("title").toString());
        return view;
    }

    class HomeAdapter {
        private TextView tv_activity_content;
        private TextView tv_activity_title;

        public HomeAdapter() {
        }
    }
}
