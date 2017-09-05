package com.fpliu.newton.ui.tab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fpliu.newton.ui.base.BaseActivity;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.viewpager.SViewPager;

public abstract class TabActivity extends BaseActivity implements IndicatorViewPager.OnIndicatorPageChangeListener {

    private Indicator indicatorView;
    private SViewPager viewPager;
    private IndicatorViewPager.IndicatorFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);

        Context context = this;

        LinearLayout contentView = new LinearLayout(context);
        contentView.setOrientation(LinearLayout.VERTICAL);

        LinearLayout headerView = new LinearLayout(context);
        headerView.setOrientation(LinearLayout.VERTICAL);

        indicatorView = new FixedIndicatorView(context);

        viewPager = new SViewPager(context);

        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(indicatorView, viewPager);
        indicatorViewPager.setAdapter(adapter = new IndicatorViewPager.IndicatorFragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return TabActivity.this.getTabCount();
            }

            @Override
            public View getViewForTab(int position, View convertView, ViewGroup container) {
                return TabActivity.this.getContentView();
            }

            @Override
            public Fragment getFragmentForPage(int position) {
                return TabActivity.this.getFragmentForPage(position);
            }
        });
        indicatorViewPager.setOnIndicatorPageChangeListener(this);

        contentView.addView(headerView, lp1);

        if (isTabOnTop()) {
            contentView.addView((View) indicatorView, lp1);
            contentView.addView(viewPager, lp2);
        } else {
            contentView.addView(viewPager, lp2);
            contentView.addView((View) indicatorView, lp1);
        }

        setContentView(contentView);
    }

    public Indicator getIndicatorView() {
        return indicatorView;
    }

    public SViewPager getViewPager() {
        return viewPager;
    }

    public IndicatorViewPager.IndicatorFragmentPagerAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onIndicatorPageChange(int preItem, int currentItem) {

    }

    public boolean isTabOnTop() {
        return true;
    }

    public abstract int getTabCount();

    public abstract View getViewForTab(int position, View convertView, ViewGroup container);

    public abstract Fragment getFragmentForPage(int position);
}
