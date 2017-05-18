package com.cmad.swipe.simple;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cmad.swipe.OnPullListener;
import com.cmad.swipe.SwipeRefreshLayoutNew;

/**
 * Created by Cmad on 2015/5/13.
 */
public class CodeAddHeadViewActivity extends Activity implements OnPullListener {
    protected SwipeRefreshLayoutNew mRefreshLayout;
    protected ListView mListView;
    protected ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_activity);

        initView();

    }

    private void addHeadView() {
        TextView view = new TextView(this);
        view.setPadding(100,100,100,100);
        mRefreshLayout.setHeadView(view);
    }

    protected void initView() {
        mRefreshLayout = (SwipeRefreshLayoutNew) findViewById(R.id.refresh_layout);
        mListView = (ListView) findViewById(R.id.listview);

        addHeadView();

        mRefreshLayout.setOnPullListener(this);
        mRefreshLayout.setColorSchemeResources(R.color.swiperefresh_color1, R.color.swiperefresh_color2, R.color.swiperefresh_color3, R.color.swiperefresh_color4);
        mAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                DataUtils.getData());
        mListView.setAdapter(mAdapter);

    }



    @Override
    public void onPulling(View headview) {
        TextView view = (TextView) headview;
        view.setText("下拉刷新");
    }

    @Override
    public void onCanRefreshing(View headview) {
        TextView view = (TextView) headview;
        view.setText("松开开始刷新");
    }

    @Override
    public void onRefreshing(View headview) {
        TextView view = (TextView) headview;
        view.setText("正在刷新...");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }
}
