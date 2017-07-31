package com.qijukeji.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2017/6/9.
 */

public class DownlodePath {
    /**
     * 应用缓存目录
     */
    public static final String CachePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
            + "Android/data/";
}
