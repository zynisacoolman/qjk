package com.qijukeji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qijukeji.entityModel.UserGift;
import com.qijukeji.qiju_dg.R;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */

public class SendgiftAdapter extends BaseAdapter {

    private Context context;
    private List<UserGift> data;

    public SendgiftAdapter(List<UserGift> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(
                    R.layout.fanxian_senditem, null);
            holder.tv_zengpin = (TextView) view.findViewById(R.id.tv_zengpin);
            UserGift userGift = data.get(position);
            holder.tv_zengpin.setText(userGift.getTitle());
        } else {
            holder = (ViewHolder) view.getTag();
        }
        return view;
    }

    class ViewHolder {
        private TextView tv_zengpin;
    }
}
