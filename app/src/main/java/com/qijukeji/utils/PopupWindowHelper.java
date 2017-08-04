package com.qijukeji.utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.qijukeji.qiju_dg.R;

/**
 * Created by Administrator on 2015/8/3.
 */
public class PopupWindowHelper {

    private View popupView;
    private PopupWindow mPopupWindow;
    private static final int TYPE_WRAP_CONTENT = 0, TYPE_MATCH_PARENT = 1;

    public PopupWindowHelper(View view) {
        popupView = view;
    }

    public void dismiss() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public void showAsPopUp(View anchor) {
        initPopupWindow(TYPE_WRAP_CONTENT);
        mPopupWindow.setAnimationStyle(R.style.AnimationUpPopup);
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            showAsPopUp(anchor, 0, 0);
        }
    }

    public void showAsPopUp(View anchor, int xoff, int yoff) {
        popupView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int height = popupView.getMeasuredHeight();
        mPopupWindow.showAsDropDown(anchor, xoff, yoff - height);
    }

    public void showAsDropDown(View anchor) {
        initPopupWindow(TYPE_WRAP_CONTENT);
        mPopupWindow.setAnimationStyle(R.style.AnimationDownPopup);
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            showAsDropDown(anchor, -10, -30);
        }
    }

    public void showAsDropDown(View anchor, int xoff, int yoff) {
        popupView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int height = popupView.getMeasuredHeight();
        mPopupWindow.showAsDropDown(anchor, xoff, yoff);
    }

    /**
     * touch outside dismiss the popupwindow, default is ture
     *
     * @param isCancelable
     */
    public void setCancelable(boolean isCancelable) {
        if (isCancelable) {
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setFocusable(true);
        } else {
            mPopupWindow.setOutsideTouchable(false);
            mPopupWindow.setFocusable(false);
        }
    }

    public void initPopupWindow(int type) {
        if (type == TYPE_WRAP_CONTENT) {
            mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else if (type == TYPE_MATCH_PARENT) {
            mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(true);
    }
}
