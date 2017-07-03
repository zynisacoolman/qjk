package com.qijukeji.view.fragment;

import android.app.Activity;
import android.content.Context;
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
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qijukeji.adapter.MainAdapter;
import com.qijukeji.controller.MainController;
import com.qijukeji.customView.XListView;
import com.qijukeji.entityModel.Checkorder;
import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.EvaluateUtils;
import com.qijukeji.utils.IntentUtil;
import com.qijukeji.utils.JsonToObjUtil;
import com.qijukeji.utils.StaticField;
import com.qijukeji.utils.Utils;
import com.qijukeji.view.UserDetailsActivity;
import com.zxing.android.CaptureActivity;
import com.zxing.android.PenreadActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

import static com.qijukeji.utils.StaticField.key2;


/**
 * 首页订单
 * Created by Administrator on 2017/5/11.
 */

public class FirstFragment extends Fragment {
    private static final String TAG = FirstFragment.class.getSimpleName();
    private static final int HTTP = 1;
    private static final int HTTP_1 = 2;
    private static final int HTTP_INTENTION_ONE = 3;//意向返回值
    private static final int HTTP_INTENTION = 4;//意向返回值
    private static final int HTTP_FINISH_ONE = 5;//成单返回值
    private static final int HTTP_FINISH = 6;//成单返回值
    private static final int HTTP_INSENT_EVALUATE = 7;//快捷评价
    public static final int HTTP_SEL_EVALUATE = 8;//快捷查询
    private XListView homeList;
    private TextView tv_bargain;
    private TextView tv_intention;
    private ImageView img_nodata;
    private LinearLayout order_ll;
    private RelativeLayout scan_edit;
    private ImageView scan_circle, scan_line, img_scan;
    private LinearLayout img_write;
    private MainAdapter mainAdapter;
    private MainController controller;
    private int page1 = 0;
    private int page2 = 0;
    private String staffid;
    private String staffUuid;
    private String brandid;
    private EvaluateUtils e;
    /**
     * type = 1 是意向顾客
     * type = 2 是成单顾客
     */
    private int type = 1;
    List<Object> list;
    private Context context;
    private String getkehumark;

    public interface FirstFragmentClickListener {
        void FirstFragmentBtnClick(boolean upDown);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        homeList = (XListView) view.findViewById(R.id.lv_main);
        homeList.setPullRefreshEnable(true);
        homeList.setPullLoadEnable(true);
        tv_bargain = (TextView) view.findViewById(R.id.tv_bargain);
        tv_intention = (TextView) view.findViewById(R.id.tv_intention);
        img_nodata = (ImageView) view.findViewById(R.id.img_nodata);
        order_ll = (LinearLayout) view.findViewById(R.id.order_ll);
        scan_edit = (RelativeLayout) view.findViewById(R.id.scan_edit);
        scan_circle = (ImageView) view.findViewById(R.id.scan_circle);
        scan_line = (ImageView) view.findViewById(R.id.scan_line);
        img_scan = (ImageView) view.findViewById(R.id.img_scan);
        img_write = (LinearLayout) view.findViewById(R.id.img_write);
        circleAnimation(scan_circle);
        lineAnimation(scan_line);
        onClick();
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {
            staffid = bundle.getString("staffid");
            staffUuid = bundle.getString("staffUuid");
            brandid = bundle.getString("brandid");
            Log.e("first", "-------staffid----------" + staffid + "-------brandid----------" + staffUuid);
        }
        e = new EvaluateUtils(staffid, handler, HTTP_INSENT_EVALUATE, context);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    private boolean isGetData = false;

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            page1 = 0;
            page2 = 0;
            initview();
        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onResume() {
        super.onResume();
        page1 = 0;
        page2 = 0;
        initview();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String data = msg.obj.toString();
            switch (msg.what) {
                case HTTP:
                    break;
                case HTTP_1:
                    break;
                case HTTP_INTENTION_ONE:
                    if (!data.equals("[]")) {
                        img_nodata.setVisibility(View.GONE);
                        order_ll.setBackgroundResource(R.color.white);
                    } else {
                        img_nodata.setVisibility(View.VISIBLE);
                        order_ll.setBackgroundResource(R.color.gary_button);
                    }
                    analysisJsonArrayYx(data, Checkorder.class);
                    break;
                case HTTP_INTENTION:
                    analysisJsonArrayYxs(data, Checkorder.class);
                    break;
                case HTTP_FINISH_ONE:
                    if (!data.equals("[]")) {
                        img_nodata.setVisibility(View.GONE);
                        order_ll.setBackgroundResource(R.color.white);
                    } else {
                        img_nodata.setVisibility(View.VISIBLE);
                        order_ll.setBackgroundResource(R.color.gary_button);
                    }
                    analysisJsonArrayCd(data, Checkorder.class);
                    break;
                case HTTP_FINISH:
                    analysisJsonArrayCds(data, Checkorder.class);
                    break;

                case StaticField.MSGOGJ:
                    Utils.setToast(context, data);
                    break;
                case HTTP_INSENT_EVALUATE:
                    toEvaluate(data);
                    break;
                case HTTP_SEL_EVALUATE:
//                    toStatus(data);
                    mainAdapter.setData(data);
                    break;
                default:
                    break;
            }
        }
    };

    private void toEvaluate(String data) {
        String jg = null;
        try {
            JSONObject json = new JSONObject(data);
            jg = json.getString("jg");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jg.equals("true")) {
            mainAdapter.notifyDataSetChanged();
            Utils.setToast(context, "评论成功");
        } else {
            Utils.setToast(context, "评论失败");
        }
    }

    /**
     * 解析第一次加载jsonArray数据
     *
     * @param data
     */
    private void analysisJsonArrayYx(String data, Class<?> text) {
        List<Object> listObj = JsonToObjUtil.jsonArrayToListObj(data, text);
        setAdapter(listObj);
    }

    private void analysisJsonArrayYxs(String data, Class<?> text) {
        onLoad();
        if (data.equals("[]")) {
            page1--;
        } else {
            list = JsonToObjUtil.jsonArrayToListObj(data, text);
        }
        mainAdapter.notifyDataSetChanged();
    }

    private void analysisJsonArrayCd(String data, Class<?> text) {
        List<Object> listObj = JsonToObjUtil.jsonArrayToListObj(data, text);
        setAdapter(listObj);
    }

    private void analysisJsonArrayCds(String data, Class<?> text) {
        onLoad();
        if (data.equals("[]")) {
            page2--;
        } else {
            list = JsonToObjUtil.jsonArrayToListObj(data, text);
        }
        mainAdapter.notifyDataSetChanged();
    }


    /**
     * list显示
     *
     * @param listObj
     */
    private void setAdapter(List<Object> listObj) {
        list = new ArrayList<>();
        list = listObj;
        mainAdapter = new MainAdapter(list, context, type, staffid, staffUuid, brandid, handler, e);
        homeList.setAdapter(mainAdapter);
    }

    /**
     * 设置下拉和上拉
     */
    private void initview() {
        controller = new MainController(context, handler, staffid);
        SharedPreferences preferences = context.getSharedPreferences("qiju", Context.MODE_PRIVATE);
        getkehumark = preferences.getString("kehutype", "");
        if (getkehumark.equals("2")) {
            type = 2;
            tv_bargain.setTextColor(getResources().getColor(R.color.colorPrimary));
            tv_intention.setTextColor(getResources().getColor(R.color.black_text));
            page2 = 0;
            toFinishHttp(HTTP_FINISH_ONE, page2);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("kehutype", "1");
            editor.commit();
        } else {
            if (type == 1) {
                toIntentionHttp(HTTP_INTENTION_ONE, page1);
            } else if (type == 2) {
                toFinishHttp(HTTP_FINISH_ONE, page2);
            }
        }
        homeList.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                if (getActivity() instanceof FirstFragmentClickListener) {
                    ((FirstFragmentClickListener) getActivity()).FirstFragmentBtnClick(true);
                }
                scan_edit.setVisibility(View.VISIBLE);
                // 设置下拉刷新
                if (type == 1) {
                    page1 = 0;
                    toIntentionHttp(HTTP_INTENTION, page1);
                } else if (type == 2) {
                    page2 = 0;
                    toFinishHttp(HTTP_FINISH, page2);
                }
            }

            @Override
            public void onLoadMore() {
                if (getActivity() instanceof FirstFragmentClickListener) {
                    ((FirstFragmentClickListener) getActivity()).FirstFragmentBtnClick(false);
                }
                scan_edit.setVisibility(View.GONE);
                // 设置上拉加载更多
                if (type == 1) {
                    page1++;
                    toIntentionHttp(HTTP_INTENTION, page1);
                } else if (type == 2) {
                    page2++;
                    toFinishHttp(HTTP_FINISH, page2);
                }
            }
        });
    }

    /**
     * 查询意向顾客
     */
    private void toIntentionHttp(int httpIntention, int page) {
        controller.toIntentionHttp(httpIntention, page);
    }

    /**
     * 查询成单顾客
     */
    private void toFinishHttp(int httpFinish, int page) {
        controller.toFinishHttp(httpFinish, page);
    }

    private void onClick() {
        homeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                Checkorder c = (Checkorder) list.get(position - 1);
                bundle.putSerializable(StaticField.KEY, key2);
                bundle.putSerializable("uuid", c.getUuid());
                bundle.putSerializable("source", c.getSource());
                IntentUtil.intentToNull(context, UserDetailsActivity.class, bundle);
            }
        });
        tv_intention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                tv_intention.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_bargain.setTextColor(getResources().getColor(R.color.black_text));
                page1 = 0;
                toIntentionHttp(HTTP_INTENTION_ONE, page1);
            }
        });
        tv_bargain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                tv_bargain.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_intention.setTextColor(getResources().getColor(R.color.black_text));
                page2 = 0;
                toFinishHttp(HTTP_FINISH_ONE, page2);
            }
        });
        img_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(StaticField.KEY, StaticField.key1);
                bundle.putSerializable("staffid", staffid);
                bundle.putSerializable("staffUuid", staffUuid);
                bundle.putSerializable("brandid", brandid);
                IntentUtil.intentToNull(context, CaptureActivity.class, bundle);
            }
        });
        img_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("staffid", staffid);
                bundle.putSerializable("staffUuid", staffUuid);
                bundle.putSerializable("brandid", brandid);
                IntentUtil.intentToNull(context, PenreadActivity.class, bundle);
            }
        });
    }

    private void onLoad() {
        homeList.stopRefresh();
        homeList.stopLoadMore();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void circleAnimation(ImageView img_view) {
        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);//设置动画持续时间
        animation.setRepeatMode(Animation.RESTART);//重复模式
        animation.setRepeatCount(Animation.INFINITE);//设置次数
        img_view.setAnimation(animation);
        animation.start();
    }

    private void lineAnimation(ImageView img_view) {
        Animation animation = new TranslateAnimation(0, 0, -35, 18);
        animation.setDuration(2000);//设置动画持续时间
        animation.setRepeatMode(Animation.RESTART);//重复模式
        animation.setRepeatCount(Animation.INFINITE);//设置次数
        img_view.setAnimation(animation);
        animation.start();
    }
}
