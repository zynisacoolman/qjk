package com.qijukeji.utils;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/5/2.
 */

public class JsonRequest extends com.android.volley.toolbox.JsonRequest<JSONArray> {


    public JsonRequest(int method, String url, JSONObject jsonRequest,
                       Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener,
                errorListener);
    }

    public JsonRequest(int method, String url,
                       Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(method, url, null, listener,
                errorListener);
    }

//    public JsonRequest(int method, String url, Response.Listener<JSONObject> listener,
//                         Response.ErrorListener errorListener) {
//        super(method, url, errorListener);
//        mListener = listener;
//    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(new JSONArray(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}
