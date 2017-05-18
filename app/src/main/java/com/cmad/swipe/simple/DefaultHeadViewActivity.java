package com.cmad.swipe.simple;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cmad.swipe.SwipeRefreshLayoutNew;

/**
 * Created by Cmad on 2015/5/13.
 */
public class DefaultHeadViewActivity extends Activity implements SwipeRefreshLayoutNew.OnRefreshListener {
    protected SwipeRefreshLayoutNew mRefreshLayout;
    protected ListView mListview;
    protected ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_activity);

        initView();
    }

    protected void initView() {
        mRefreshLayout = (SwipeRefreshLayoutNew) findViewById(R.id.refresh_layout);
        mListview = (ListView) findViewById(R.id.listview);

        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeResources(R.color.swiperefresh_color1, R.color.swiperefresh_color2, R.color.swiperefresh_color3, R.color.swiperefresh_color4);
        mRefreshLayout.hideColorProgressBar();
        mAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                DataUtils.getData());
        mListview.setAdapter(mAdapter);

    }



    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }
}
