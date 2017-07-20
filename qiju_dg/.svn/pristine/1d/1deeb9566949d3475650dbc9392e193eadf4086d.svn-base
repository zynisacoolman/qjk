package com.qijukeji.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.ConstantValues;
import com.qijukeji.utils.HttpUtil;
import com.qijukeji.utils.UmengShareUtils;
import com.qijukeji.utils.Utils;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/7/5.
 */

public class ShareCardActivity extends AppCompatActivity {
    @Bind(R.id.bt_save_card)
    Button btSaveCard;
    @Bind(R.id.ll_card_img)
    LinearLayout llCardImg;
    @Bind(R.id.img_card_bg)
    ImageView imgCardBg;
    @Bind(R.id.share_card_title)
    TextView shareCardTitle;
    @Bind(R.id.share_card_description)
    TextView shareCardDescription;
    @Bind(R.id.img_share_scan)
    ImageView imgShareScan;
    @Bind(R.id.ll_card_wechat)
    ImageButton llCardWechat;
    @Bind(R.id.ll_card_moments)
    ImageButton llCardMoments;
    private String title, description, imgcard, time;
    private String staffid, staffUuid, uuid;
    private static final int HTTP_SHARE_CARD = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HTTP_SHARE_CARD:
                    imgShareScan.setImageBitmap((Bitmap) msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharecard);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        uuid = intent.getStringExtra("uuid");
        description = intent.getStringExtra("description");
        time = intent.getStringExtra("time");
        imgcard = intent.getStringExtra("imgcard");
        shareCardTitle.setText(title);
        shareCardDescription.setText(time);
        Glide.with(this)
                .load(imgcard)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(imgCardBg);
        SharedPreferences preferences = getSharedPreferences("qiju", Context.MODE_PRIVATE);
        staffid = preferences.getString("staffid", "");
        staffUuid = preferences.getString("staffUuid", "");
        JSONObject json = new JSONObject();
        try {
            json.put("activityUuid", uuid);
            json.put("staffUuid", staffUuid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("card", json.toString());
        HttpUtil.VolleyHttpPost(this, ConstantValues.HTTP_CARD_SHARE, json, handler, HTTP_SHARE_CARD);
    }

    @OnClick({R.id.bt_save_card, R.id.ll_card_wechat, R.id.ll_card_moments})
    public void CardClick(View view) {
        switch (view.getId()) {
            case R.id.bt_save_card:
                try {
                    saveBitmap(getViewBitmap());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ll_card_wechat:
                UmengShareUtils.shareCard(getViewBitmap(), ShareCardActivity.this, SHARE_MEDIA.WEIXIN);
                break;
            case R.id.ll_card_moments:
                UmengShareUtils.shareCard(getViewBitmap(), ShareCardActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            default:
                break;
        }
    }

    private Bitmap getViewBitmap() {
        int me = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        llCardImg.measure(me, me);
        llCardImg.buildDrawingCache();
        Bitmap bitmap = llCardImg.getDrawingCache();
        return bitmap;
    }

    private void saveBitmap(Bitmap bitmap) throws IOException {
        if (bitmap == null) {
            Utils.setToast(this, "图片生成失败");
            return;
        }
        File file = new File("/sdcard/Pictures/", System.currentTimeMillis() + ".jpg");
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            Utils.setToast(this, "图片保存成功");
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void getHttpBitmap() {
//        // 创建默认的httpClient实例.
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        // 创建httppost
//        HttpPost httppost = new HttpPost("http://localhost:8080/myDemo/Ajax/serivceJ.action");
//        // 创建参数队列
//        List<namevaluepair> formparams = new ArrayList<namevaluepair>();
//        formparams.add(new BasicNameValuePair("username", "admin"));
//        formparams.add(new BasicNameValuePair("password", "123456"));
//        UrlEncodedFormEntity uefEntity;
//        try {
//            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
//            httppost.setEntity(uefEntity);
//            System.out.println("executing request " + httppost.getURI());
//            CloseableHttpResponse response = httpclient.execute(httppost);
//            try {
//                HttpEntity entity = response.getEntity();
//                if (entity != null) {
//                    System.out.println("--------------------------------------");
//                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));
//                    System.out.println("--------------------------------------");
//                }
//            } finally {
//                response.close();
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e1) {
//            e1.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            // 关闭连接,释放资源
//            try {
//                httpclient.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
