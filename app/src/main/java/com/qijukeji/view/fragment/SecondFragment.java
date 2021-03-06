package com.qijukeji.view.fragment;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qijukeji.adapter.GalleryAdapter;
import com.qijukeji.controller.StatementController;
import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.IntentUtil;
import com.qijukeji.utils.StaticField;
import com.qijukeji.utils.TimeUtil;
import com.qijukeji.utils.Utils;
import com.qijukeji.view.StatementActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/11.
 */

public class SecondFragment extends Fragment {

    private List<View> listviews;
    private ViewPager viewPager;
    private RelativeLayout ll_page;
    public static final int SHARE_TIMES = 2;
    private StatementController controller;
    private Activity context;
    private JSONObject json;
    private String staffid, staffUuid, brandid;
    private TextView tv_sharetimes0, tv_lookers0, tv_overorders0, pager_time0, lookers0, overorders0;
    private TextView tv_sharetimes1, tv_lookers1, tv_overorders1, pager_time1, pager_times1, lookers1, overorders1;
    private TextView tv_sharetimes2, tv_lookers2, tv_overorders2, pager_time2, pager_times2, lookers2, overorders2;
    private int mark;
    Handler sharehandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String share_times = msg.obj.toString();
            Log.e("share", share_times);
            switch (msg.what) {
                case SHARE_TIMES:
                    analysisJsonArraySharetimes(share_times);
                    break;
                case StaticField.MSGOGJ:
                    Utils.setToast(context, share_times);
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        ll_page = (RelativeLayout) view.findViewById(R.id.ll_page);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {
            staffid = bundle.getString("staffid");
            staffUuid = bundle.getString("staffUuid");
            brandid = bundle.getString("brandid");
        }
        return view;
    }

    private boolean isGetData = false;

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            mark = 0;
            initView();
            initData();
        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
        initData();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    private void initView() {
        Log.e("today", TimeUtil.getDate());
        Log.e("week", TimeUtil.getWeek());
        Log.e("moon", TimeUtil.getMoon());
        controller = new StatementController(context, sharehandler, staffid);
        toHttpJson(TimeUtil.getDate(), TimeUtil.getDate());
    }

    private void analysisJsonArraySharetimes(String share_times) {
        try {
            JSONObject json = new JSONObject(share_times);
            JSONArray jsonArray = json.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (i == 0) {
                    if (mark == 0) tv_sharetimes0.setText(jsonObject.getInt("count") + "次");
                    if (mark == 1) tv_sharetimes1.setText(jsonObject.getInt("count") + "次");
                    if (mark == 2) tv_sharetimes2.setText(jsonObject.getInt("count") + "次");
                } else if (i == 1) {
                    if (mark == 0) tv_lookers0.setText(jsonObject.getInt("count") + "人");
                    if (mark == 1) tv_lookers1.setText(jsonObject.getInt("count") + "人");
                    if (mark == 2) tv_lookers2.setText(jsonObject.getInt("count") + "人");
                } else if (i == 2) {
                    if (mark == 0) tv_overorders0.setText(jsonObject.getInt("count") + "人");
                    if (mark == 1) tv_overorders1.setText(jsonObject.getInt("count") + "人");
                    if (mark == 2) tv_overorders2.setText(jsonObject.getInt("count") + "人");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void toHttpJson(String startTime, String endTime) {
        json = new JSONObject();
        try {
            json.put("staffid", staffid);
            json.put("staffUuid", staffUuid);
            json.put("begindateTime", startTime);
            json.put("enddateTime", endTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        controller.toHttpSharetimes(SHARE_TIMES, json);
    }

    private void initData() {
        listviews = new ArrayList<>();
        View view0 = LayoutInflater.from(context).inflate(
                R.layout.fragment_second_pager0, null);
        View view1 = LayoutInflater.from(context).inflate(
                R.layout.fragment_second_pager1, null);
        View view2 = LayoutInflater.from(context).inflate(
                R.layout.fragment_second_pager2, null);
        initpager0(view0);
        initpager1(view1);
        initpager2(view2);
        listviews.add(view0);
        listviews.add(view1);
        listviews.add(view2);
        viewPager.setPageMargin(10);
        ll_page.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Utils.bigSmall(listviews, position, positionOffset, context);
            }

            @Override
            public void onPageSelected(int position) {
                mark = position;
                if (position == 0)
                    toHttpJson(TimeUtil.getDate(), TimeUtil.getDate());
                if (position == 1)
                    toHttpJson(TimeUtil.getWeek(), TimeUtil.getDate());
                if (position == 2)
                    toHttpJson(TimeUtil.getMoon(), TimeUtil.getDate());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new GalleryAdapter(listviews));
    }

    private void initpager0(View view) {
        tv_sharetimes0 = (TextView) view.findViewById(R.id.tv_sharetimes0);
        tv_lookers0 = (TextView) view.findViewById(R.id.tv_lookers0);
        tv_overorders0 = (TextView) view.findViewById(R.id.tv_overorders0);
        pager_time0 = (TextView) view.findViewById(R.id.pager_time0);
        pager_time0.setText(TimeUtil.getDateshow());
        lookers0 = (TextView) view.findViewById(R.id.lookers0);
        overorders0 = (TextView) view.findViewById(R.id.overorders0);
        lookers0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("staffid", staffid);
                bundle.putSerializable("staffUuid", staffUuid);
                bundle.putSerializable("brandid", brandid);
                bundle.putSerializable("start_time", TimeUtil.getDate());
                bundle.putSerializable("end_time", TimeUtil.getDate());
                bundle.putSerializable("type", "2");
                IntentUtil.intentToNull(context, StatementActivity.class, bundle);
            }
        });
        overorders0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("staffid", staffid);
                bundle.putSerializable("staffUuid", staffUuid);
                bundle.putSerializable("brandid", brandid);
                bundle.putSerializable("start_time", TimeUtil.getDate());
                bundle.putSerializable("end_time", TimeUtil.getDate());
                bundle.putSerializable("type", "1");
                IntentUtil.intentToNull(context, StatementActivity.class, bundle);
            }
        });
    }

    private void initpager1(View view) {
        tv_sharetimes1 = (TextView) view.findViewById(R.id.tv_sharetimes1);
        tv_lookers1 = (TextView) view.findViewById(R.id.tv_lookers1);
        tv_overorders1 = (TextView) view.findViewById(R.id.tv_overorders1);
        pager_time1 = (TextView) view.findViewById(R.id.pager_time1);
        pager_times1 = (TextView) view.findViewById(R.id.pager_times1);
        pager_time1.setText(TimeUtil.getWeekshow());
        pager_times1.setText(TimeUtil.getDateshow());
        lookers1 = (TextView) view.findViewById(R.id.lookers1);
        overorders1 = (TextView) view.findViewById(R.id.overorders1);
        lookers1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("staffid", staffid);
                bundle.putSerializable("staffUuid", staffUuid);
                bundle.putSerializable("brandid", brandid);
                bundle.putSerializable("start_time", TimeUtil.getWeek());
                bundle.putSerializable("end_time", TimeUtil.getDate());
                bundle.putSerializable("type", "2");
                IntentUtil.intentToNull(context, StatementActivity.class, bundle);
            }
        });
        overorders1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("staffid", staffid);
                bundle.putSerializable("staffUuid", staffUuid);
                bundle.putSerializable("brandid", brandid);
                bundle.putSerializable("start_time", TimeUtil.getWeek());
                bundle.putSerializable("end_time", TimeUtil.getDate());
                bundle.putSerializable("type", "1");
                IntentUtil.intentToNull(context, StatementActivity.class, bundle);
            }
        });
    }

    private void initpager2(View view) {
        tv_sharetimes2 = (TextView) view.findViewById(R.id.tv_sharetimes2);
        tv_lookers2 = (TextView) view.findViewById(R.id.tv_lookers2);
        tv_overorders2 = (TextView) view.findViewById(R.id.tv_overorders2);
        pager_time2 = (TextView) view.findViewById(R.id.pager_time2);
        pager_times2 = (TextView) view.findViewById(R.id.pager_times2);
        pager_time2.setText(TimeUtil.getMoonshow());
        pager_times2.setText(TimeUtil.getDateshow());
        lookers2 = (TextView) view.findViewById(R.id.lookers2);
        overorders2 = (TextView) view.findViewById(R.id.overorders2);
        lookers2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("staffid", staffid);
                bundle.putSerializable("staffUuid", staffUuid);
                bundle.putSerializable("brandid", brandid);
                bundle.putSerializable("start_time", TimeUtil.getMoon());
                bundle.putSerializable("end_time", TimeUtil.getDate());
                bundle.putSerializable("type", "2");
                IntentUtil.intentToNull(context, StatementActivity.class, bundle);
            }
        });
        overorders2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("staffid", staffid);
                bundle.putSerializable("staffUuid", staffUuid);
                bundle.putSerializable("brandid", brandid);
                bundle.putSerializable("start_time", TimeUtil.getMoon());
                bundle.putSerializable("end_time", TimeUtil.getDate());
                bundle.putSerializable("type", "1");
                IntentUtil.intentToNull(context, StatementActivity.class, bundle);
            }
        });
    }
}
