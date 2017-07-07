package com.qijukeji.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.qijukeji.qiju_dg.R;
import com.qijukeji.view.ShareCardActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 友盟分享工具类
 *
 * @author tangxian
 */
public class UmengShareUtils {
    private static Context context;
    private static Activity activity;
    private static String staffid;
    private static String activityid;
    private static String staffUuid;
    private static UMWeb web;
    private static Bundle b;

    /**
     * 分享
     *
     * @param context
     * @param activity
     */
    public static void shareActionOpen(Context context, Activity activity, String staffid, String activityid, String title, String description, String imgurl, String imgcard, String staffUuid) {
        UmengShareUtils.context = context;
        UmengShareUtils.activity = activity;
        UmengShareUtils.staffid = staffid;
        UmengShareUtils.activityid = activityid;
        UmengShareUtils.staffUuid = staffUuid;
        b = new Bundle();
        b.putSerializable("title", title);
        b.putSerializable("description", description);
        b.putSerializable("imgcard", imgcard);
        ShareAction shareAction = new ShareAction(activity);
        UMImage image;
        if (imgurl == null) {
            image = new UMImage(context, R.drawable.logo);//资源文件
        } else {
            image = new UMImage(context, imgurl);
        }
        web = new UMWeb(ConstantValues.HOST_URL + "wechat/wechatpublic/activitydetails?uuid=" + activityid + "&upperuserid=" + staffUuid + "&source=4");
        web.setTitle(title);//标题
        web.setThumb(image);  //缩略图
        web.setDescription(description);//描述
        shareAction
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
                .addButton("umeng_sharebutton_card", "umeng_sharebutton_card", "tubiao", "tubiao")
                .setShareboardclickCallback(shareBoardlistener);
        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        config.setCancelButtonVisibility(true);
        shareAction.open(config);
    }

    /**
     * 自定义
     */
    static ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            if (share_media == null) {
                if (snsPlatform.mKeyword.equals("umeng_sharebutton_card")) {
                    IntentUtil.intentToNull(context, ShareCardActivity.class, b);
                }
            } else {
                new ShareAction(activity).setPlatform(share_media)
                        .withMedia(web)
                        .setCallback(umShareListener).share();
            }
        }
    };

    /**
     * 接受返回值
     */
    static UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            toAddsharetimes();
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Utils.setToast(context, " 分享成功啦");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Log.e("throwable", share_media + throwable.toString());
            Utils.setToast(context, share_media + throwable.toString() + " 分享失败啦");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Utils.setToast(context, " 分享取消了");
        }
    };

    public static void toAddsharetimes() {
        JSONObject json = new JSONObject();
        try {
            json.put("staffid", staffid);
            json.put("staffUuid", staffUuid);
            json.put("activityid", activityid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtil.VolleyHttpPost(context, ConstantValues.HTTP_SHARE_ADDTIMES, staffid, json, null, -1);
    }

    public static void shareCard(Bitmap bitmap, Activity activity) {
        UMImage image;
        if (bitmap == null) {
            image = new UMImage(context, R.drawable.logo);//资源文件
        } else {
            image = new UMImage(context, bitmap);
        }
        ShareAction shareAction = new ShareAction(activity);
        shareAction
                .withMedia(image)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
                .setCallback(umShareListener);
        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        config.setCancelButtonVisibility(true);
        shareAction.open(config);
    }
}
