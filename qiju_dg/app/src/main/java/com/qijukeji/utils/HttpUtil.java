package com.qijukeji.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/10.
 */

public class HttpUtil {

    public HttpUtil() {
        // TODO Auto-generated constructor stub
    }

    /**
     * volley 访问网络
     */
    private static RequestQueue mRequestQueue;
    private static JsonObjectRequest mJsonObjectRequest;

    public static void VolleyHttpPost(Context mContext, String url, String staffid, JSONObject json, Handler handler, int msgWhat) {
        url = url + staffid;
        Log.e("urls", url);
        mRequestQueue = Volley.newRequestQueue(mContext);
        mJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                json, createMyReqSuccessListener(handler, msgWhat),
                createMyReqErrorListener(handler));
        mRequestQueue.add(mJsonObjectRequest);
    }

    public static void VolleyHttpJsonArrayPost(Context mContext, String url, String staffid, JSONObject json, final Handler handler, final int msgWhat) {
        mRequestQueue = Volley.newRequestQueue(mContext);
        JsonRequest jr = new JsonRequest(Request.Method.POST, url + staffid, json, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("returnback", response.toString());
                Message msg = new Message();
                msg.what = msgWhat;
                msg.obj = response;
                handler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Message msg = new Message();
                msg.what = StaticField.MSGOGJ;
                msg.obj = "网络请求失败";
                handler.sendMessage(msg);
            }
        });
        mRequestQueue.add(jr);

    }

    public static void VolleyHttpGet(Context mContext, String url, final Handler handler, final int msgWhat) {
        mRequestQueue = Volley.newRequestQueue(mContext);
        JsonObjectRequest
                mJsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("returnback", response.toString());
                Message msg = new Message();
                msg.what = msgWhat;
                msg.obj = response;
                handler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Message msg = new Message();
                msg.what = StaticField.MSGOGJ;
                msg.obj = "网络请求失败";
                handler.sendMessage(msg);
            }
        });
        mRequestQueue.add(mJsonObjectRequest1);
    }

    public static void VolleyHttpGet(Context mContext, String url, String staffid, final Handler handler, final int msgWhat) {
        mRequestQueue = Volley.newRequestQueue(mContext);
        url = url + "&staffid=" + staffid;
        Log.e("urls", url);
        JsonObjectRequest
                mJsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("returnback", response.toString());
                Message msg = new Message();
                msg.what = msgWhat;
                msg.obj = response;
                handler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Message msg = new Message();
                msg.what = StaticField.MSGOGJ;
                msg.obj = "网络请求错误";
                handler.sendMessage(msg);
            }
        });
        mRequestQueue.add(mJsonObjectRequest1);
    }

    public static void VolleyHttpGet(Context mContext, String url, String ordernumber, String staffid, final Handler handler, final int msgWhat) {
        if (ordernumber == "") {
            VolleyHttpGet(mContext, url, staffid, handler, msgWhat);
        } else {
            url = url + "&ordernumber=" + ordernumber;
            VolleyHttpGet(mContext, url, staffid, handler, msgWhat);
        }


    }

    /**
     * 返回成功值
     *
     * @param handler
     * @param msgWhat
     * @return
     */
    private static Response.Listener<JSONObject> createMyReqSuccessListener(
            final Handler handler, final int msgWhat) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i("Hanjh", "volley_post_JsonObjectRequest请求结果:"
                            + response.toString());
                    Message msg = new Message();
                    msg.what = msgWhat;
                    msg.obj = response;
                    handler.sendMessage(msg);
//                    text3.setText("volley_post_JsonObjectRequest请求结果:"
//                            + response.toString());
                } catch (Exception e) {
//                    text3.setText("Parse error");
                }
            }
        };
    }

    /**
     * 返回失败
     *
     * @param handler
     * @return
     */

    private static Response.ErrorListener createMyReqErrorListener(final Handler handler) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (null != error) {
                    Log.i("Hanjh",
                            "volley_post_JsonObjectRequest请求错误:" + error.toString());
                    Message msg = new Message();
                    msg.what = StaticField.MSGOGJ;
                    msg.obj = "网络请求失败";
                    handler.sendMessage(msg);
//                    text3.setText("volley_post_JsonObjectRequest请求错误:" + error.toString());
                }
            }
        };
    }

    public static void VolleyHttpGetRe(Context context, String url, final Handler handler, final int msgWhat) {

        mRequestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest
                mJsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("returnback", response.toString());
                Message msg = new Message();
                msg.what = msgWhat;
                msg.obj = response;

                handler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Message msg = new Message();
                msg.what = StaticField.MSGOGJ;
                msg.obj = "网络请求失败";
                handler.sendMessage(msg);
            }
        });
        mRequestQueue.add(mJsonObjectRequest1);

    }
}
