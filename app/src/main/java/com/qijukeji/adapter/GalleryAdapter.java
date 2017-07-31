package com.qijukeji.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */

public class GalleryAdapter extends PagerAdapter {
    private List<View> listviews;

    public GalleryAdapter(List<View> listviews) {
        this.listviews = listviews;
    }

    /**
     * PagerAdapter管理数据大小
     */
    @Override
    public int getCount() {
        return listviews.size();
    }

    /**
     * 关联key 与 obj是否相等，即是否为同一个对象
     */
    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    /**
     * 销毁当前page的相隔2个及2个以上的item时调用
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 当前的page的前一页和后一页也会被调用，如果还没有调用或者已经调用了destroyItem
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.e("position", "" + position);
        container.addView(listviews.get(position));
        return listviews.get(position);
    }
}
