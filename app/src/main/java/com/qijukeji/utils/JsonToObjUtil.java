package com.qijukeji.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qijukeji.entityModel.UserGift;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */

public class JsonToObjUtil {

    private static Gson gson;

    private static GsonBuilder builder;

    public static List<Object> jsonArrayToListObj(String data, Class<?> text) {
        builder = new GsonBuilder();
        gson = builder.create();
        JSONObject json;
        List<Object> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                json = new JSONObject(jsonArray.get(i).toString());
                Object obj = gson.fromJson(json.toString(), text);
                list.add(obj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //礼品列表解析
    public static List<UserGift> jsonToListObj(String gift, Class<?> text, String fanxian) {
        builder = new GsonBuilder();
        gson = builder.create();
        JSONObject json;
        List<UserGift> list = new ArrayList<>();
        UserGift user = new UserGift(fanxian);
        list.add(user);
        try {
            JSONArray jsonArray = new JSONArray(gift);
            for (int i = 0; i < jsonArray.length(); i++) {
                json = new JSONObject(jsonArray.get(i).toString());
                UserGift userGift = (UserGift) gson.fromJson(json.toString(), text);
                list.add(userGift);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Object jsonArrayToObj(String data, Class<?> text) {
        builder = new GsonBuilder();
        gson = builder.create();
        JSONObject json;
        Object obj = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            json = new JSONObject(jsonArray.get(0).toString());
            obj = gson.fromJson(json.toString(), text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static Object jsonToObj(String data, Class<?> text) {
        builder = new GsonBuilder();
        gson = builder.create();
        JSONObject json;
        Object obj = null;
        try {
            JSONObject jsonone = new JSONObject(data);
            JSONArray jsonArray = jsonone.getJSONArray("list");
            json = new JSONObject(jsonArray.get(0).toString());
            obj = gson.fromJson(json.toString(), text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject objToJson(Object obj) {
        Gson gson2 = new Gson();
        String obj2 = gson2.toJson(obj);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(obj2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static String jaonAnalysis(String data, Context context) {

        try {
            JSONObject json = new JSONObject(data);
            if (json.getString("hasErrors").equals("false")) {
                return json.getString("list");
            } else {
                Utils.setToast(context, "请求失败");
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
