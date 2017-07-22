package com.qijukeji.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qijukeji.customView.FindProgressBar;
import com.qijukeji.entityModel.ActivityEM;
import com.qijukeji.qiju_dg.R;
import com.qijukeji.qiju_dg.wxapi.WXEntryActivity;
import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.HttpUtil;
import com.qijukeji.utils.TimeUtil;
import com.qijukeji.utils.UmengShareUtils;
import com.qijukeji.utils.Utils;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendAuth;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 */

public class FindAdapter extends BaseAdapter {
    private IWXAPI mApi;
    private Context context;
    private List<Object> data;
    private ActivityEM find_info;
    private Activity activity;
    private String staffid;
    private String staffUuid;

    public FindAdapter(Context context, List<Object> data, Activity activity, String staffid, String staffUuid) {
        this.data = data;
        this.context = context;
        this.activity = activity;
        this.staffid = staffid;
        this.staffUuid = staffUuid;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.fragment_third_item, null);
            holder.find_img = (ImageView) convertView
                    .findViewById(R.id.find_img);
            holder.find_rl = (RelativeLayout) convertView
                    .findViewById(R.id.find_rl);
            holder.find_theme = (TextView) convertView
                    .findViewById(R.id.find_theme);
            holder.find_address = (TextView) convertView
                    .findViewById(R.id.find_store);
            holder.find_time = (TextView) convertView
                    .findViewById(R.id.find_time);
            holder.find_money = (TextView) convertView
                    .findViewById(R.id.find_money);
            holder.already_mark = (TextView) convertView
                    .findViewById(R.id.already_mark);
            holder.tv_share_price = (TextView) convertView
                    .findViewById(R.id.tv_share_price);
            holder.over_yhq = (TextView) convertView
                    .findViewById(R.id.over_yhq);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        find_info = (ActivityEM) data.get(position);
        int numnow = find_info.getGetCount();
        int numcheck = find_info.getCheckCount();
        holder.already_mark.setText("报名：" + numnow + "人");
        holder.over_yhq.setText("核券：" + numcheck + "人");
//        int pernum = numnow / find_info.getUpperLimit();
        final String startshow = TimeUtil.getFormatedDateTime(find_info.getStartShowTime());
        final String endshow = TimeUtil.getFormatedDateTime(find_info.getEndShowTime());
        holder.find_theme.setText(find_info.getTitle());
        holder.find_time.setText(startshow + "-" + endshow);
        String price = "";
        if (find_info.getPrice() > 0) {
            price = find_info.getPrice() + "元";
        } else {
            price = "免费";
        }
        holder.find_money.setText(price);
        holder.tv_share_price.setText(find_info.getShareReward());
        holder.find_address.setText("适用" + find_info.getAddress());
        Glide.with(context)
                .load(find_info.getShareImage())
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(holder.find_img);

        holder.find_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = context.getSharedPreferences("qiju", Context.MODE_PRIVATE);
                String hasErrors = preferences.getString("hasErrors", "");
                if (hasErrors.equals("true")) {
                    mApi = WXAPIFactory.createWXAPI(context, WXEntryActivity.APP_ID, true);
                    mApi.registerApp(WXEntryActivity.APP_ID);
                    if (mApi != null && mApi.isWXAppInstalled()) {
                        SendAuth.Req req = new SendAuth.Req();
                        req.scope = "snsapi_userinfo";
                        req.state = "wechat_sdk_demo_test_neng";
                        mApi.sendReq(req);
                    } else {
                        Utils.setToast(context, "用户未安装微信");
                    }
                } else {
                    int id = position;
                    find_info = (ActivityEM) data.get(id);
                    UmengShareUtils.shareActionOpen(context, activity, staffid, find_info.getUuid(), find_info.getTitle(), find_info.getDescription(), find_info.getShareImage(), find_info.getCoverImage(), staffUuid, find_info.getUuid(), find_info.getShareReward(), startshow + "-" + endshow);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        private ImageView find_img;
        private TextView find_theme, find_address, find_time, find_money;
        private TextView already_mark, over_yhq, tv_share_price;
        private RelativeLayout find_rl;
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
}
