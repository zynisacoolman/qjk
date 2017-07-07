package com.qijukeji.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.qijukeji.qiju_dg.R;
import com.qijukeji.utils.IntentUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/24.
 */

public class DialogActivity extends Activity {

    @Bind(R.id.exit_true)
    Button exitTrue;
    @Bind(R.id.exit_false)
    Button exitFalse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_img);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.exit_true)
    public void exitTure() {
        SharedPreferences sp = this.getSharedPreferences("qiju", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("staffid", "");
        editor.putString("password", "");
        editor.putString("appwxopenid", "");
        editor.commit();
        IntentUtil.intentToNull(this, LonInActivity.class);
    }

    @OnClick(R.id.exit_false)
    public void exitFalse() {
        finish();
    }


}
