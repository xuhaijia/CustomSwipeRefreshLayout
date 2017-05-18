package com.cmad.swipe.simple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Cmad on 2015/5/13.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        findViewById(R.id.default_btn).setOnClickListener(this);
        findViewById(R.id.code_add_btn).setOnClickListener(this);
        findViewById(R.id.xml_add_btn).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.default_btn:
                intent.setClass(this,DefaultHeadViewActivity.class);
                break;
            case R.id.code_add_btn:
                intent.setClass(this,CodeAddHeadViewActivity.class);
                break;
            case R.id.xml_add_btn:
                intent.setClass(this,XmlAddHeadViewActivity.class);
                break;
        }
        startActivity(intent);
    }
}
