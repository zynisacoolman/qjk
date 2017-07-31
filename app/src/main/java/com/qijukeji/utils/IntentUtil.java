package com.qijukeji.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Administrator on 2017/4/7.
 * 跳转页面工具类
 */

public class IntentUtil {
    /**
     * 不带参数跳转
     *
     * @param context
     * @param activity
     */
    public static void intentToNull(Context context, Class<?> activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }

    /**
     * 带参数跳转
     *
     * @param context
     * @param activity
     * @param mBundle
     */
    public static void intentToNull(Context context, Class<?> activity, Bundle mBundle) {
        Intent intent = new Intent(context, activity);
        intent.putExtras(mBundle);
        context.startActivity(intent);
    }


}
