package com.qijukeji.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qijukeji.entityModel.Comment;
import com.qijukeji.qiju_dg.R;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */

public class GvEvaluateAdapter extends BaseAdapter {
    private List<Object> list;
    private Context context;
    private int clickTemp = -1;//标识被选择的item
    private int[] clickedList;//这个数组用来存放item的点击状态

    private Handler handler;

    public GvEvaluateAdapter(List<Object> list, Context context, int[] clickedList, Handler handler) {
        this.list = list;
        this.clickedList = clickedList;
        this.context = context;
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
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            view = View.inflate(context, R.layout.gv_adapter, null);
        } else {
            // 否则表示可以复用convertView
            view = convertView;
        }
        TextView bu_text = (TextView) view.findViewById(R.id.bu_text);
        Comment c = (Comment) list.get(position);
        bu_text.setText(c.getContent());
        bu_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSeclection(position);

//                context.notifyDataSetChanged();
            }
        });
        if (clickedList[position] == 0) {
            bu_text.setBackgroundResource(R.drawable.button_green_background);
            bu_text.setTextColor(context.getResources().getColor(R.color.text_gray_2));
        } else {
            bu_text.setBackgroundResource(R.drawable.button_green_background1);
            bu_text.setTextColor(context.getResources().getColor(R.color.white));
        }
        return view;
    }

    public void setSeclection(int seclection) {
        if (clickedList[seclection] == 0) {
            clickedList[seclection] = 1;
            Message msg = new Message();
            msg.what = 10;
            msg.obj = seclection;
            handler.sendMessage(msg);
        } else {
            clickedList[seclection] = 0;
            Message msg = new Message();
            msg.what = 11;
            msg.obj = seclection;
            handler.sendMessage(msg);
        }

    }
}
