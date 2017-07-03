package com.qijukeji.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.qijukeji.adapter.InPuttipsAdapter;
import com.qijukeji.qiju_dg.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * 输入提示功能实现
 */
public class InputtipsActivity extends AppCompatActivity {

    private String city = "石家庄";
    @Bind(R.id.input_edittext)
    AutoCompleteTextView mKeywordText;
    @Bind(R.id.inputlist)
    ListView minputlist;
    List<HashMap<String, String>> listString;
    public static final int INTENT_RETURN = 20;
    private Context context;
    private InPuttipsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputtip);
        ButterKnife.bind(this);
        context = this;
        listString = new ArrayList<>();
        initview();
    }

    private void initview() {
        mKeywordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String newText = s.toString().trim();
                InputtipsQuery inputquery = new InputtipsQuery(newText, city);
                inputquery.setCityLimit(true);
                Inputtips inputTips = new Inputtips(InputtipsActivity.this, inputquery);
                inputTips.setInputtipsListener(new InputtipsListener() {
                    @Override
                    public void onGetInputtips(List<Tip> tipList, int rCode) {
                        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                            for (int i = 0; i < tipList.size(); i++) {
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("name", tipList.get(i).getName());
                                map.put("address", tipList.get(i).getDistrict());
                                listString.add(map);
                            }
                            adapter = new InPuttipsAdapter(listString, context);
                            minputlist.setAdapter(adapter);
                        }
                    }
                });
                listString = new ArrayList<HashMap<String, String>>();
                inputTips.requestInputtipsAsyn();
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", newText);
                map.put("address", "");
                listString.add(map);
            }
        });

    }

    @OnItemClick(R.id.inputlist)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("123", "----------" + position);
        Intent intent = new Intent();
        intent.putExtra("address", listString.get(position).get("name"));
        setResult(INTENT_RETURN, intent);
        finish();
    }

}