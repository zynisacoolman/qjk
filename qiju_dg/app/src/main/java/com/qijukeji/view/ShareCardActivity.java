package com.qijukeji.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.UmengShareUtils;
import com.qijukeji.utils.Utils;

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
    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.select_title_show)
    RelativeLayout selectTitleShow;
    @Bind(R.id.tv_title_name)
    TextView tvTitleName;
    @Bind(R.id.iv_title_right)
    ImageView ivTitleRight;
    @Bind(R.id.bt_save_card)
    Button btSaveCard;
    @Bind(R.id.bt_share_card)
    Button btShareCard;
    @Bind(R.id.ll_card_img)
    LinearLayout llCardImg;
    @Bind(R.id.img_card_bg)
    ImageView imgCardBg;
    @Bind(R.id.share_card_title)
    TextView shareCardTitle;
    @Bind(R.id.share_card_description)
    TextView shareCardDescription;
    private String title, description, imgcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharecard);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitleName.setText("卡片分享");
        tvTitleName.setVisibility(View.VISIBLE);
        selectTitleShow.setVisibility(View.GONE);
        ivTitleRight.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");
        imgcard = intent.getStringExtra("imgcard");
        shareCardTitle.setText(title);
        shareCardDescription.setText(description);
        Glide.with(this)
                .load(imgcard)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(imgCardBg);
    }

    @OnClick({R.id.iv_title_back, R.id.bt_save_card, R.id.bt_share_card})
    public void CardClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.bt_save_card:
                try {
                    saveBitmap(getViewBitmap());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.bt_share_card:
                UmengShareUtils.shareCard(getViewBitmap(), ShareCardActivity.this);
                break;
            default:
                break;
        }
    }

    private Bitmap getViewBitmap() {
        int me = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        llCardImg.measure(me, me);
        llCardImg.layout(0, 0, llCardImg.getMeasuredWidth(), llCardImg.getMeasuredHeight());
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

}
