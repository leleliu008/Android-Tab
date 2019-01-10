package com.fpliu.newton.ui.tab;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ScrollBar;
import com.shizhefei.view.viewpager.SViewPager;

import java.util.List;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;

public interface ITab<T> extends List<T> {

    View init(Context context, RelationShipAndPosition relationShipAndPosition, boolean heightWrapContent);

    void setIndicator(Indicator indicator);

    Indicator getIndicator();

    SViewPager getViewPager();

    void setPagerAdapter(IndicatorViewPager.IndicatorPagerAdapter adapter);

    IndicatorViewPager.IndicatorPagerAdapter getPagerAdapter();

    void setScrollBar(ScrollBar scrollBar);

    void setColorScrollBar(@ColorInt int color, int height);

    void setColorScrollBar(@ColorInt int color, int height, ScrollBar.Gravity gravity);

    void setColorResScrollBar(@ColorRes int colorRes, int height);

    void setColorResScrollBar(@ColorRes int colorRes, int height, ScrollBar.Gravity gravity);

    void setOnTransitionListener(Indicator.OnTransitionListener onTransitionListener);

    void setOnTransitionTextViewSizeAndColor(float selectSize, float unSelectSize, @ColorInt int selectColor, @ColorInt int unSelectColor);

    void setOnTransitionTextViewSizeAndColorRes(float selectSize, float unSelectSize, @ColorRes int selectColorRes, @ColorRes int unSelectColorRes);

    void setOnTransitionBackgroundColorChange(@ColorInt int selectColor, @ColorInt int unSelectColor);

    void setOnTransitionBackgroundColorResChange(@ColorRes int selectColorRes, @ColorRes int unSelectColorRes);

    void setCurrentItem(int position);

    void setCurrentItem(int position, boolean needAnimation);

    void setTabItemClickable(boolean clickable);

    void setOnIndicatorItemClickListener(Indicator.OnIndicatorItemClickListener listener);

    void setOnIndicatorPageChangeListener(IndicatorViewPager.OnIndicatorPageChangeListener listener);

    void setOnItemSelectListener(Indicator.OnItemSelectedListener listener);

    void setCanScroll(boolean canScroll);

    /**
     * 设置Tab条为包裹内容，这时候，后面的背景需要进行设置，否则会很难看
     *
     * @param indicatorBarBackgroundColor
     */
    void setIndicatorWrapAndInCenter(@ColorInt int indicatorBarBackgroundColor);

    /**
     * 设置Tab条为包裹内容，这时候，后面的背景需要进行设置，否则会很难看
     *
     * @param indicatorBarBackgroundColor
     */
    void setIndicatorWrapAndAlignLeft(@ColorInt int indicatorBarBackgroundColor);

    /**
     * 设置Tab条为包裹内容，这时候，后面的背景需要进行设置，否则会很难看
     *
     * @param indicatorBarBackgroundColor
     */
    void setIndicatorWrapAndAlignRight(@ColorInt int indicatorBarBackgroundColor);

    void setLeftViewInIndicatorBar(View view);

    void setLeftViewInIndicatorBar(View view, RelativeLayout.LayoutParams lp);

    void setRightViewInIndicatorBar(View view);

    void setRightViewInIndicatorBar(View view, RelativeLayout.LayoutParams lp);

    void setViewBeforeTab(View view);

    void setViewBeforeTab(View view, LinearLayout.LayoutParams lp);

    int getCurrentItemPosition();

    View getCurrentTabView();

    int getTabCount();

    void setItems(List<T> items);

    List<T> getItems();
}
