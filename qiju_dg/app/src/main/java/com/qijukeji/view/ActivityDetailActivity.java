package com.qijukeji.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.DownloadeManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityDetailActivity extends AppCompatActivity {

    @Bind(R.id.iv_title_right)
    ImageView iv_title_right;
    @Bind(R.id.tv_title_name)
    TextView title;
    @Bind(R.id.iv_title_back)
    ImageView iv_title_back;
    @Bind(R.id.activity_web)
    WebView activity_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        iv_title_right.setVisibility(View.INVISIBLE);
        title.setText("启居导购端");
        //创建下载任务,downloadUrl就是下载链接
        DownloadeManager dm = new DownloadeManager(this,
                "qijukeji", "http://www.qijukeji.cn:8080/test/qjkj.apk");
        dm.showDownloadDialog();
    }

    @OnClick(R.id.iv_title_back)
    public void backPage() {
        finish();
    }
}
