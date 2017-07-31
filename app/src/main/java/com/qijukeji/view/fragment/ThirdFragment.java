package com.qijukeji.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;

import com.qijukeji.adapter.FindAdapter;
import com.qijukeji.controller.FindController;
import com.qijukeji.customView.XListView;
import com.qijukeji.entityModel.ActivityEM;
import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.HttpUtil;
import com.qijukeji.utils.JsonToObjUtil;
import com.qijukeji.utils.StaticField;
import com.qijukeji.utils.Utils;
import com.umeng.socialize.UMShareAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;


/**
 * 首页活动
 * Created by Administrator on 2017/5/11.
 */

public class ThirdFragment extends Fragment {
    public static final int FIND_ACTIVITY = 0;
    public static final int FIND_LOOD_ACTIVITY = 1;
    public static final int FIND_WEXINCHECK = 2;
    private FindController findController;
    private FindAdapter findAdapter;
    private int page = 0;
    private List<Object> data = new ArrayList<>();
    @Bind(R.id.lv_find)
    XListView lv_find;
    private Activity context;
    private String staffid, staffUuid, brandid;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String data = msg.obj.toString();
            switch (msg.what) {
                case FIND_ACTIVITY:
                    analysisJsonArrayFind(data, ActivityEM.class);
                    break;
                case FIND_LOOD_ACTIVITY:
                    analysisJsonArrayLood(data, ActivityEM.class);
                    break;
                case StaticField.MSGOGJ:
                    Utils.setToast(context, data);
                    break;
                case FIND_WEXINCHECK:
                    weixinCheck(data);
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {
            staffid = bundle.getString("staffid");
            staffUuid = bundle.getString("staffUuid");
            brandid = bundle.getString("brandid");
            Log.e("123", "-------staffid----------" + staffid + "-------brandid----------" + brandid);
        }
        initView();
        return view;
    }

    private boolean isGetData = false;

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            HttpUtil.VolleyHttpGet(context, ConstantValues.HTTP_WECHATCHECK + staffid + "&staffUuid=" + staffUuid, handler, FIND_WEXINCHECK);
        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onResume() {
        super.onResume();
        HttpUtil.VolleyHttpGet(context, ConstantValues.HTTP_WECHATCHECK + staffid + "&staffUuid=" + staffUuid, handler, FIND_WEXINCHECK);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    private void initView() {
        findController = new FindController(context, handler, staffid);
        findController.toFindHttp(FIND_ACTIVITY, page);
        lv_find.setPullRefreshEnable(true);
        lv_find.setPullLoadEnable(true);
        lv_find.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 0;
                findController.toFindHttp(FIND_ACTIVITY, page);
            }

            @Override
            public void onLoadMore() {
                page++;
                findController.toFindHttp(FIND_LOOD_ACTIVITY, page);
            }
        });
    }

    private void analysisJsonArrayFind(String datas, Class<ActivityEM> text) {
        lv_find.stopRefresh();
        lv_find.stopLoadMore();
        if (datas.equals("[]")) {
            Utils.setToast(context, "已经没有数据了");
        } else {
            String list = JsonToObjUtil.jaonAnalysis(datas, context);
            List<Object> listObj = JsonToObjUtil.jsonArrayToListObj(list, text);
            this.data = listObj;
            findAdapter = new FindAdapter(context, this.data, context, staffid, staffUuid);
            lv_find.setAdapter(findAdapter);
        }
    }

    private void analysisJsonArrayLood(String datas, Class<ActivityEM> text) {
        lv_find.stopRefresh();
        lv_find.stopLoadMore();
        if (datas.equals("[]")) {
            page--;
            Utils.setToast(context, "已经没有数据了");
        } else {
            String list = JsonToObjUtil.jaonAnalysis(datas, context);
            List<Object> listObj = JsonToObjUtil.jsonArrayToListObj(list, text);
            this.data = listObj;
        }
        findAdapter.notifyDataSetChanged();
    }

    @OnItemClick(R.id.lv_find)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Bundle bundle = new Bundle();
//        IntentUtil.intentToNull(context, ActivityDetailActivity.class, bundle);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data);
    }

    private void weixinCheck(String data) {
        try {
            JSONObject json = new JSONObject(data);
            String hasErrors = json.getString("hasErrors");
            SharedPreferences sp = context.getSharedPreferences("qiju", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("hasErrors", hasErrors);
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
