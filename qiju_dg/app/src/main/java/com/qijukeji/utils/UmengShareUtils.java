package com.qijukeji.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;

import com.qijukeji.qiju_dg.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 友盟分享工具类
 *
 * @author tangxian
 */
public class UmengShareUtils {
    private static Context context;
    private static String staffid;
    private static String activityid;
    private static String staffUuid;

    /**
     * 分享
     *
     * @param context
     * @param activity
     */
    public static void shareActionOpen(Context context, Activity activity, String staffid, String activityid, String title, String description, String imgurl, String staffUuid) {
        UmengShareUtils.context = context;
        UmengShareUtils.staffid = staffid;
        UmengShareUtils.activityid = activityid;
        UmengShareUtils.staffUuid = staffUuid;

        ShareAction shareAction = new ShareAction(activity);
        UMImage image = new UMImage(context, imgurl);
//        UMImage image = new UMImage(context, R.drawable.logo);//资源文件
        UMWeb web = new UMWeb(ConstantValues.HOST_URL + "wechat/wechatpublic/activitydetails?uuid=" + activityid + "&upperuserid=" + staffUuid + "&source=4");
        web.setTitle(title);//标题
        web.setThumb(image);  //缩略图
        web.setDescription(description);//描述
        shareAction
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
                .setCallback(umShareListener);
        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        config.setCancelButtonVisibility(true);
        shareAction.open(config);
    }

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
            Utils.setToast(context, share_media + " 分享失败啦");
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
}
