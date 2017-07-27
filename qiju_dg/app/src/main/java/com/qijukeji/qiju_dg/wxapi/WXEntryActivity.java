package com.qijukeji.qiju_dg.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.HttpUtil;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.SendAuth;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * WXEntryActivity 是微信固定的Activiy、 不要改名字、并且放到你对应的项目报名下面、
 * 例如： ....(package报名).wxapi.WXEntryActivity
 * 不然无法回调、切记...
 * Wx  回调接口 IWXAPIEventHandler
 * <p/>
 * 关于WXEntryActivity layout。 我们没给页面、而是把Activity  主题 android:theme="@android:style/Theme.Translucent" 透明、
 * <p/>
 * User: MoMo - Nen
 * Date: 2015-10-24
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    public static final String APP_ID = "wx8974a8e5df87e1ff";
    public static final String APP_SECRET = "69d8cd550259f86fd18629ce26fad7ac";

    private IWXAPI mApi;
    public static final int HTTP_GETACCESS_TOKEN = 0;
    public static final int HTTP_GETUSERMESG = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String data = msg.obj.toString();
            switch (msg.what) {
                case HTTP_GETACCESS_TOKEN:
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        String access_token = jsonObject.getString("access_token");
                        String openid = jsonObject.getString("openid");
                        getUserMesg(access_token, openid);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case HTTP_GETUSERMESG:
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        String openid = jsonObject.getString("openid");
                        String unionid = jsonObject.getString("unionid");
                        SharedPreferences preferences = getSharedPreferences("qiju", Context.MODE_PRIVATE);
                        String staffid = preferences.getString("staffid", "");
                        String staffUuid = preferences.getString("staffUuid", "");
                        JSONObject json = new JSONObject();
                        try {
                            json.put("appwxopenid", openid);
                            json.put("unionid", unionid);
                            json.put("staffUuid", staffUuid);
                            HttpUtil.VolleyHttpPost(WXEntryActivity.this, ConstantValues.HTTP_WECHATINSERT, staffid, json, null, 0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = WXAPIFactory.createWXAPI(this, APP_ID, true);
        mApi.handleIntent(this.getIntent(), this);
    }

    //微信发送的请求将回调到onReq方法
    @Override
    public void onReq(BaseReq baseReq) {
    }


    //发送到微信请求的响应结果
    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                //发送成功
                SharedPreferences preferences = getSharedPreferences("qiju", Context.MODE_PRIVATE);
                String hasErrors = preferences.getString("hasErrors", "");
                if (hasErrors.equals("true")) {
                    SendAuth.Resp sendResp = (SendAuth.Resp) resp;
                    if (sendResp != null) {
                        String code = sendResp.token;
                        getAccess_token(code);
                    }
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //发送取消
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //发送被拒绝
                break;
            default:
                //发送返回
                break;
        }
        finish();

    }


    /**
     * 获取openid accessToken值用于后期操作
     *
     * @param code 请求码
     */
    private void getAccess_token(final String code) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                        + APP_ID
                        + "&secret="
                        + APP_SECRET
                        + "&code="
                        + code
                        + "&grant_type=authorization_code";
                HttpUtil.VolleyHttpGet(WXEntryActivity.this, path, handler, HTTP_GETACCESS_TOKEN);
            }
        }).start();

    }


    /**
     * 获取微信的个人信息
     *
     * @param access_token
     * @param openid
     */
    private void getUserMesg(final String access_token, final String openid) {
        String path = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid="
                + openid;
        HttpUtil.VolleyHttpGet(WXEntryActivity.this, path, handler, HTTP_GETUSERMESG);
    }


}
