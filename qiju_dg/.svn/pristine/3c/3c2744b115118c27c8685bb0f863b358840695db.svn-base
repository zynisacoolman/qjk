package com.qijukeji.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/10.
 */

public class HttpUtil {

    /**
     * volley 访问网络
     */
    private static RequestQueue mRequestQueue;
    private static JsonObjectRequest mJsonObjectRequest;
    private static Request request;

    public static void VolleyHttpPost(Context mContext, String url, String staffid, JSONObject json, Handler handler, int msgWhat) {
        url = url + staffid;
        Log.e("urls", url);
        mRequestQueue = Volley.newRequestQueue(mContext);
        mJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                json, createMyReqSuccessListener(handler, msgWhat),
                createMyReqErrorListener(handler));
        mRequestQueue.add(mJsonObjectRequest);
    }

    public static void VolleyHttpPost(Context mContext, String url, JSONObject json, final Handler handler, final int msgWhat) {
        Log.e("urls", url);
        mRequestQueue = Volley.newRequestQueue(mContext);
        request = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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
        mRequestQueue.add(request);
    }

    public static void VolleyHttpJsonArrayPost(Context mContext, String url, String staffid, JSONObject json, final Handler handler, final int msgWhat) {
        url = url + staffid;
        Log.e("urls", url);
        mRequestQueue = Volley.newRequestQueue(mContext);
        JsonRequest jr = new JsonRequest(Request.Method.POST, url, json, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
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
                } catch (Exception e) {
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
                }
            }
        };
    }

    public static Bitmap HttpClientPost(String url, String uuid, String staffUuid) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        //设置传入的数据
        String params = "{activityUuid:'" + uuid + "',staffUuid:'" + staffUuid + "'}";
        Log.e("params", params);
        Log.e("url", url);
        InputStream is = null;
        try {
            //设置传递的参数格式
            post.setEntity(new StringEntity(params, "UTF-8"));
            post.setHeader("Content-Type", "application/json");
            //执行get方法获得服务器返回的所有数据
            HttpResponse response = client.execute(post);
            //HttpClient获得服务器返回的表头。
            StatusLine statusLine = response.getStatusLine();
            //获得状态码
            int code = statusLine.getStatusCode();
            Log.e("code", code + "");
            if (code == HttpURLConnection.HTTP_OK) {
                //得到数据的实体。
                HttpEntity entity = response.getEntity();
                //得到数据的输入流。
                is = entity.getContent();
                Log.e("scanimg", is.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(is);
    }

}
