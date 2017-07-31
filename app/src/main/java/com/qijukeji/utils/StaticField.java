package com.qijukeji.utils;

/**
 * Created by Administrator on 2017/4/10.
 */

public class StaticField {
    public static final String KEY = "key";
    /**
     * IntentionDetailsActivity   用户详细信息页面
     * <p>
     * key 1 扫描后竟然
     * 2和3是修改后  按钮是保存
     * <p>
     * DealDetailsActivity       交易信息页面
     * key 1  按钮显示完成
     * 2  按钮显示评价
     * 4   按钮显示评价  直接弹出评价
     * 3  没有按钮，显示评价信息
     * <p>
     * CaptureActivity     扫一扫页面
     * key 1  跳转到详细信息页面
     * 2 返回原界面
     *
     *
     * UserDetailsActivity  用户信息页面
     *
     * key1可以编辑的
     * key2不可编辑的
     * key3评价的
     */
    public static final int key1 = 1;
    public static final int key2 = 2;
    public static final int key3 = 3;
    //没有网络
    public static final int MSGOGJ = -1;

    //    导购Id
    public static final int gdId = 68;

}
