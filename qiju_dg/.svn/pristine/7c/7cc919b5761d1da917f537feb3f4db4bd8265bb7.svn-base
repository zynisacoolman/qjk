package com.qijukeji.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qijukeji.view.BlankPageActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/4/11.
 */

public class Utils {
    private static Toast toast;

    /**
     * 优化吐司
     *
     * @param context
     * @param content
     */
    public static void setToast(Context context,
                                String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }


    public static Double countMoney(Context context, String type, Double sum_money, Double lowerLimitPrice, Double upperLimitPrice, Double discount, String isCircle) {
        if (upperLimitPrice == null) {
            upperLimitPrice = 0.0;
        }
        //1直减，2满减，3打折
        Double deal_money = sum_money;
        if (type.equals("1")) {
            if (lowerLimitPrice <= sum_money) {
                deal_money = sum_money - discount;
            }
        } else if (type.equals("2")) {
            if (lowerLimitPrice <= sum_money) {
                if (isCircle.equals("true")) {

                    if (upperLimitPrice == 0.0) {
                        Double a = ((int) (sum_money / lowerLimitPrice)) * discount;
                        deal_money = sum_money - a;
                    } else {
                        if (upperLimitPrice <= sum_money) {
                            Double a = ((int) (upperLimitPrice / lowerLimitPrice)) * discount;
                            deal_money = sum_money - a;
                        } else {
                            Double a = ((int) (sum_money / lowerLimitPrice)) * discount;
                            deal_money = sum_money - a;
                        }
                    }
                } else {
                    deal_money = sum_money - discount;
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("温馨提示").setMessage("交易金额不符，优惠券不能用");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).show();
            }
        } else if (type.equals("3")) {
            if (lowerLimitPrice <= sum_money) {
                deal_money = sum_money * discount;
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("温馨提示").setMessage("交易金额不符，优惠券不能用");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).show();
            }
        }
        return deal_money;
    }

    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    public static Boolean isExit = false;

    public static void exitBy2Click(Context context) {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            setToast(context, "再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            SharedPreferences sp = context.getSharedPreferences("qiju", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("tc", "0");
            editor.commit();
            IntentUtil.intentToNull(context, BlankPageActivity.class);
        }
    }

    public static String version(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = info.versionName;
        return version;
    }

    //Url转bitmap
    public static Bitmap returnBitmap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
