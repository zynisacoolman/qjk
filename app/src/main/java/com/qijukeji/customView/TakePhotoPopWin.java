package com.qijukeji.customView;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qijukeji.adapter.GvEvaluateAdapter;
import com.qijukeji.entityModel.Comment;
import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.HttpUtil;
import com.qijukeji.utils.JsonToObjUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class TakePhotoPopWin extends PopupWindow implements View.OnClickListener, View.OnTouchListener {

    private EditText etInputPj;
    private Context mContext;

    private View view;
    private View view1;
    private LinearLayout ll_gv_content, ll_text_content, pop_layout;

    private Button bt_submit;

    private TextView tv_praise, tv_bad_review, tv_custom;

    private GvEvaluateAdapter adapter;
    private GridView gv_list;
    private List<Object> listComment;
    private String type;//1 好评 2 差评  3有话说
    private String staffid;

    private int[] clickedList;//这个数组用来存放item的点击状态
    HashMap hm = new HashMap();

    public TakePhotoPopWin(Context mContext, View.OnClickListener onClickListener, List<Object> listComment, String staffid) {
        type = "1";
        this.mContext = mContext;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.evaluate_list, null);
        this.listComment = listComment;
        this.staffid = staffid;
/**
 * 获取屏幕高度
 */
        WindowManager manager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        //设置是屏幕高度的三分之二
        pop_layout = (LinearLayout) view.findViewById(R.id.pop_layout);
        ViewGroup.LayoutParams lp;
        lp = pop_layout.getLayoutParams();
        lp.height = (display.getHeight() / 5) * 2;
        pop_layout.setLayoutParams(lp);
        //结束
        tv_praise = (TextView) view.findViewById(R.id.tv_praise);
        tv_bad_review = (TextView) view.findViewById(R.id.tv_bad_review);
        tv_custom = (TextView) view.findViewById(R.id.tv_custom);
        bt_submit = (Button) view.findViewById(R.id.bt_submit);
        etInputPj = (EditText) view.findViewById(R.id.et_input_pj);
        bt_submit.setOnClickListener(onClickListener);
        tv_praise.setOnClickListener(this);
        tv_bad_review.setOnClickListener(this);
        tv_custom.setOnClickListener(this);
        ll_gv_content = (LinearLayout) view.findViewById(R.id.ll_gv_content);
        ll_text_content = (LinearLayout) view.findViewById(R.id.ll_text_content);
        gv_list = (GridView) view.findViewById(R.id.gv_list);
        //显示好评
        tv_praise();
        setBackgroundColor();
        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(this);


        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

//         实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);

        gv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSeclection(position);
                adapter.notifyDataSetChanged();
            }
        });

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    int a = (int) msg.obj;
                    hm.put(a + "", a);
                    notifyDataSetChanged();
                    break;
                case 11:
                    int a1 = (int) msg.obj;
                    hm.remove(a1 + "");
                    notifyDataSetChanged();
                    break;
                case 2:
                    String data = msg.obj.toString();
                    Log.e("评价区分标志", data);
                    setComment(data);
                    break;
                default:
                    break;
            }
        }
    };

    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    private void setBackgroundColor() {
        tv_praise.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        tv_bad_review.setTextColor(mContext.getResources().getColor(R.color.text_gray_2));
        tv_custom.setTextColor(mContext.getResources().getColor(R.color.text_gray_2));
    }

    private void setBackgroundColor1() {
        tv_praise.setTextColor(mContext.getResources().getColor(R.color.text_gray_2));
        tv_bad_review.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        tv_custom.setTextColor(mContext.getResources().getColor(R.color.text_gray_2));
    }

    private void setVisibility() {
        ll_text_content.setVisibility(View.GONE);
        ll_gv_content.setVisibility(View.VISIBLE);
    }


    /**
     * 点击好评
     *
     * @param v
     */
    private void tv_praise(View v) {
        type = "1";
        toHttp(type);
        setVisibility();
        setBackgroundColor();
    }

    private void toHttp(String type) {
        JSONObject json = new JSONObject();
        try {
            json.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtil.VolleyHttpJsonArrayPost(mContext, ConstantValues.HTTP_SEL_EVALUATE, staffid, json, handler, 2);

    }

    private void tv_praise() {
        setVisibility();
        clickedList = new int[listComment.size()];
        for (int i = 0; i < listComment.size(); i++) {
//            list.add(0);
            clickedList[i] = 0;
        }
        adapter = new GvEvaluateAdapter(listComment, mContext, clickedList, handler);
        gv_list.setAdapter(adapter);
    }

    /**
     * 点击差评
     */
    private void tv_bad_review(View v) {
        type = "2";
        toHttp(type);
        setVisibility();
        setBackgroundColor1();
    }

    /**
     * 点击有话说
     */
    private void tv_custom(View v) {
        type = "3";
        ll_gv_content.setVisibility(View.GONE);
        ll_text_content.setVisibility(View.VISIBLE);
        tv_praise.setTextColor(mContext.getResources().getColor(R.color.text_gray_2));
        tv_bad_review.setTextColor(mContext.getResources().getColor(R.color.text_gray_2));
        tv_custom.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
    }

    /**
     * 提交按钮
     */
    private void bt_submit() {
        Iterator iter = hm.keySet().iterator();
        while (iter.hasNext()) {
            Object key = iter.next();
            int val = (int) hm.get(key);
            Comment c = (Comment) listComment.get(val);
            dismiss();
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_praise:
                tv_praise(v);
                break;
            case R.id.tv_bad_review:
                tv_bad_review(v);
                break;
            case R.id.tv_custom:
                tv_custom(v);
                break;
//
            default:
                break;
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {


        int height = view.findViewById(R.id.pop_layout).getTop();

        int y = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (y < height) {
                dismiss();
            }
        }
        return true;

    }

    public HashMap gethm() {
        return hm;
    }

    public List getList() {
        return listComment;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return etInputPj.getText().toString();
    }

    public void setComment(String data) {
        listComment
                = JsonToObjUtil.jsonArrayToListObj(data, Comment.class);
        tv_praise();
        if (type.equals("1")) {
            setBackgroundColor();
        } else if (type.equals("2")) {
            setBackgroundColor1();
        }
    }
}
