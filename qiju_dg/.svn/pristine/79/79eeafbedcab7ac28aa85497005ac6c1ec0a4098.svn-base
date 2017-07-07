package com.qijukeji.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qijukeji.qiju_dg.R;

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
    }

    @OnClick(R.id.iv_title_back)
    public void CardClick(View view){
        switch (view.getId()){
            case R.id.iv_title_back:
                finish();
                break;
            default:
                break;
        }
    }

}
