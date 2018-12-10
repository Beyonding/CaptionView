package com.bec.captionview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.view.caption.CaptionView;
import com.view.caption.bean.CaptionBean;

public class MainActivity extends AppCompatActivity {

    private CaptionView mCaptionView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mCaptionView=(CaptionView)findViewById(R.id.captionView);

        CaptionBean data=new CaptionBean();
        data.setFontPath("fangzheng_cuyuan.ttf");
        data.setContent("我是字幕");
        data.setTextColor(R.color.white);
        data.setTextSize(18);
        data.setBorderColor(R.color.colorPrimary);
        mCaptionView.setData(data);
        mCaptionView.start();
    }
}
