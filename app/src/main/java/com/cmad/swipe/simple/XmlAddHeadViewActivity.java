package com.cmad.swipe.simple;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cmad.swipe.OnPullListener;
import com.cmad.swipe.SwipeRefreshLayoutNew;


public class XmlAddHeadViewActivity extends Activity implements  OnPullListener {
    private SwipeRefreshLayoutNew mRefreshLayout;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private RotateAnimation mRotateAnimation;
    private RotateAnimation mRotateAnimationDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xml_add_activity);

        initView();
    }

    private void initView() {
        mRefreshLayout = (SwipeRefreshLayoutNew) findViewById(R.id.refresh_layout);
        mListView = (ListView) findViewById(R.id.listview);


        View headview = findViewById(R.id.headview);
        initHeadView(headview);
//        mRefreshLayout.setColorSchemeResources(R.color.swiperefresh_color1, R.color.swiperefresh_color2, R.color.swiperefresh_color3, R.color.swiperefresh_color4);
        mRefreshLayout.setOnPullListener(this);
        mAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                DataUtils.getData());
        mListView.setAdapter(mAdapter);
    }

    private void initHeadView(View headview) {
        mRotateAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateAnimation.setDuration(150);
        mRotateAnimation.setFillAfter(true);

        mRotateAnimationDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateAnimationDown.setDuration(150);
        mRotateAnimationDown.setFillAfter(true);
        HeadHolder holder = new HeadHolder();
        holder.mRefreshImageview = (ImageView) headview.findViewById(R.id.pull_to_refresh_image);
        holder.mRefreshProgress = (ProgressBar) headview.findViewById(R.id.pull_to_refresh_progress);
        holder.mRefreshTitle = (TextView) headview.findViewById(R.id.pull_to_refresh_text);
        headview.setTag(holder);

    }

    @Override
    public void onPulling(View headview) {
        HeadHolder holder = (HeadHolder) headview.getTag();
        holder.mRefreshImageview.clearAnimation();
        holder.mRefreshImageview.setVisibility(View.VISIBLE);
        holder.mRefreshProgress.setVisibility(View.GONE);
        holder.mRefreshTitle.setText(getString(R.string.pullToRefresh)+"...");
        holder.mRefreshImageview.startAnimation(mRotateAnimationDown);

    }

    @Override
    public void onCanRefreshing(View headview) {
        HeadHolder holder = (HeadHolder) headview.getTag();
        holder.mRefreshImageview.startAnimation(mRotateAnimation);
        holder.mRefreshTitle.setText(getString(R.string.loosenToRefresh)+"...");
    }

    @Override
    public void onRefreshing(View headview) {
        HeadHolder holder = (HeadHolder) headview.getTag();
        holder.mRefreshImageview.clearAnimation();
        holder.mRefreshImageview.setVisibility(View.GONE);
        holder.mRefreshProgress.setVisibility(View.VISIBLE);
        holder.mRefreshTitle.setText(getString(R.string.refreshing) + "...");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    class HeadHolder {
        ImageView mRefreshImageview;
        TextView mRefreshTitle;
        ProgressBar mRefreshProgress;
    }

}
