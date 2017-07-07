package com.qijukeji.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
            holder.findProgressBar = (FindProgressBar) convertView.findViewById(R.id.find_pro);
            holder.find_img = (ImageView) convertView
                    .findViewById(R.id.find_img);
            holder.find_rl = (RelativeLayout) convertView
                    .findViewById(R.id.find_rl);
            holder.find_theme = (TextView) convertView
                    .findViewById(R.id.find_theme);
            holder.find_time = (TextView) convertView
                    .findViewById(R.id.find_time);
            holder.find_money = (TextView) convertView
                    .findViewById(R.id.find_money);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        find_info = (ActivityEM) data.get(position);
//        int numnow = find_info.getGetCount();
//        int pernum = numnow / find_info.getUpperLimit();
        holder.findProgressBar.setProgress(36, 36);
        String startshow = TimeUtil.getFormatedDateTime(find_info.getStartShowTime());
        String endshow = TimeUtil.getFormatedDateTime(find_info.getEndShowTime());
        System.out.println(startshow + endshow);
        holder.find_theme.setText(find_info.getTitle());
        holder.find_time.setText(startshow + "至" + endshow);
        String price = "";
        if (find_info.getPrice() > 0) {
            price = find_info.getPrice() + "元";
        } else {
            price = "免费";
        }
        holder.find_money.setText(price + " " + find_info.getAddress());
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
                    UmengShareUtils.shareActionOpen(context, activity, staffid, find_info.getUuid(), find_info.getTitle(), find_info.getDescription(), find_info.getShareImage(), find_info.getCoverImage(), staffUuid);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        private ImageView find_img;
        private TextView find_theme, find_time, find_money;
        private FindProgressBar findProgressBar;
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
