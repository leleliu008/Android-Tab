package com.fpliu.newton.ui.tab;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fpliu.newton.ui.base.BaseActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ScrollBar;
import com.shizhefei.view.viewpager.SViewPager;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public abstract class TabViewActivity<T> extends BaseActivity implements ITab<T>, IndicatorViewPager.OnIndicatorPageChangeListener {

    private ITab<T> tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tab = new TabImpl<>();
        View contentView = tab.init(this, getRelationShipAndPosition(), heightWrapContent());
        CoordinatorLayout.LayoutParams lp = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT);
        lp.setBehavior(new AppBarLayout.ScrollingViewBehavior());
        addContentView(contentView, lp);
        setIndicator(new FixedIndicatorView(this));
    }

    @Override
    public View init(Context context, RelationShipAndPosition relationShipAndPosition, boolean heightWrapContent) {
        throw new RuntimeException("Not Support");
    }

    @Override
    public void setIndicator(Indicator indicator) {
        tab.setIndicator(indicator);
        tab.setPagerAdapter(new IndicatorViewPager.IndicatorViewPagerAdapter() {
            @Override
            public int getCount() {
                return TabViewActivity.this.getTabCount();
            }

            @Override
            public View getViewForTab(int position, View convertView, ViewGroup container) {
                return TabViewActivity.this.getViewForTab(position, convertView, container, get(position));
            }

            @Override
            public View getViewForPage(int position, View convertView, ViewGroup container) {
                return TabViewActivity.this.getViewForPage(position, convertView, container, get(position));
            }
        });
        tab.setOnIndicatorPageChangeListener(this);
    }

    @Override
    public Indicator getIndicator() {
        return tab.getIndicator();
    }

    @Override
    public SViewPager getViewPager() {
        return tab.getViewPager();
    }

    @Override
    public void setPagerAdapter(IndicatorViewPager.IndicatorPagerAdapter adapter) {
        tab.setPagerAdapter(adapter);
    }

    @Override
    public IndicatorViewPager.IndicatorViewPagerAdapter getPagerAdapter() {
        return (IndicatorViewPager.IndicatorViewPagerAdapter) tab.getPagerAdapter();
    }

    @Override
    public void setScrollBar(ScrollBar scrollBar) {
        tab.setScrollBar(scrollBar);
    }

    @Override
    public void setColorScrollBar(@ColorInt int color, int height) {
        tab.setColorScrollBar(color, height);
    }

    @Override
    public void setColorScrollBar(@ColorInt int color, int height, ScrollBar.Gravity gravity) {
        tab.setColorScrollBar(color, height, gravity);
    }

    @Override
    public void setColorResScrollBar(@ColorRes int colorRes, int height) {
        tab.setColorResScrollBar(colorRes, height);
    }

    @Override
    public void setColorResScrollBar(@ColorRes int colorRes, int height, ScrollBar.Gravity gravity) {
        tab.setColorResScrollBar(colorRes, height, gravity);
    }

    @Override
    public void setOnTransitionListener(Indicator.OnTransitionListener onTransitionListener) {
        tab.setOnTransitionListener(onTransitionListener);
    }

    @Override
    public void setOnTransitionTextViewSizeAndColor(float selectSize, float unSelectSize, @ColorInt int selectColor, @ColorInt int unSelectColor) {
        tab.setOnTransitionTextViewSizeAndColor(selectSize, unSelectSize, selectColor, unSelectColor);
    }

    @Override
    public void setOnTransitionTextViewSizeAndColorRes(float selectSize, float unSelectSize, @ColorRes int selectColorRes, @ColorRes int unSelectColorRes) {
        tab.setOnTransitionTextViewSizeAndColorRes(selectSize, unSelectSize, selectColorRes, unSelectColorRes);
    }

    @Override
    public void setOnTransitionBackgroundColorChange(@ColorInt int selectColor, @ColorInt int unSelectColor) {
        tab.setOnTransitionBackgroundColorChange(selectColor, unSelectColor);
    }

    @Override
    public void setOnTransitionBackgroundColorResChange(@ColorRes int selectColorRes, @ColorRes int unSelectColorRes) {
        tab.setOnTransitionBackgroundColorResChange(selectColorRes, unSelectColorRes);
    }

    @Override
    public void setCurrentItem(int position) {
        tab.setCurrentItem(position);
    }

    @Override
    public void setCurrentItem(int position, boolean needAnimation) {
        tab.setCurrentItem(position, needAnimation);
    }

    @Override
    public void setTabItemClickable(boolean clickable) {
        tab.setTabItemClickable(clickable);
    }

    @Override
    public void setOnIndicatorItemClickListener(Indicator.OnIndicatorItemClickListener listener) {
        tab.setOnIndicatorItemClickListener(listener);
    }

    @Override
    public void setOnIndicatorPageChangeListener(IndicatorViewPager.OnIndicatorPageChangeListener listener) {
        tab.setOnIndicatorPageChangeListener(listener);
    }

    @Override
    public void setOnItemSelectListener(Indicator.OnItemSelectedListener listener) {
        tab.setOnItemSelectListener(listener);
    }

    @Override
    public void setCanScroll(boolean canScroll) {
        tab.setCanScroll(canScroll);
    }

    @Override
    public void setIndicatorWrapAndInCenter(@ColorInt int indicatorBarBackgroundColor) {
        tab.setIndicatorWrapAndInCenter(indicatorBarBackgroundColor);
    }

    @Override
    public void setIndicatorWrapAndAlignLeft(@ColorInt int indicatorBarBackgroundColor) {
        tab.setIndicatorWrapAndAlignLeft(indicatorBarBackgroundColor);
    }

    @Override
    public void setIndicatorWrapAndAlignRight(@ColorInt int indicatorBarBackgroundColor) {
        tab.setIndicatorWrapAndAlignRight(indicatorBarBackgroundColor);
    }

    @Override
    public void setLeftViewInIndicatorBar(View view) {
        tab.setLeftViewInIndicatorBar(view);
    }

    @Override
    public void setLeftViewInIndicatorBar(View view, RelativeLayout.LayoutParams lp) {
        tab.setLeftViewInIndicatorBar(view, lp);
    }

    @Override
    public void setRightViewInIndicatorBar(View view) {
        tab.setRightViewInIndicatorBar(view);
    }

    @Override
    public void setRightViewInIndicatorBar(View view, RelativeLayout.LayoutParams lp) {
        tab.setRightViewInIndicatorBar(view, lp);
    }

    @Override
    public void setViewBeforeTab(View view) {
        tab.setViewBeforeTab(view);
    }

    @Override
    public void setViewBeforeTab(View view, LinearLayout.LayoutParams lp) {
        tab.setViewBeforeTab(view, lp);
    }

    @Override
    public int getCurrentItemPosition() {
        return tab.getCurrentItemPosition();
    }

    @Override
    public View getCurrentTabView() {
        return tab.getCurrentTabView();
    }

    @Override
    public int getTabCount() {
        return tab.getTabCount();
    }

    @Override
    public void setItems(List<T> items) {
        tab.setItems(items);
    }

    @Override
    public List<T> getItems() {
        return tab.getItems();
    }

    @Override
    public int size() {
        return tab.size();
    }

    @Override
    public boolean isEmpty() {
        return tab.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return tab.contains(o);
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return tab.iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return tab.toArray();
    }

    @NonNull
    @Override
    public <T1> T1[] toArray(@NonNull T1[] a) {
        return tab.toArray(a);
    }

    @Override
    public boolean add(T element) {
        return tab.add(element);
    }

    @Override
    public boolean remove(Object element) {
        return tab.remove(element);
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> collection) {
        return tab.containsAll(collection);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends T> collection) {
        return tab.addAll(collection);
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends T> collection) {
        return tab.addAll(index, collection);
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> collection) {
        return tab.removeAll(collection);
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> collection) {
        return tab.retainAll(collection);
    }

    @Override
    public void clear() {
        tab.clear();
    }

    @Override
    public T get(int index) {
        return tab.get(index);
    }

    @Override
    public T set(int index, T element) {
        return tab.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        tab.add(index, element);
    }

    @Override
    public T remove(int index) {
        return tab.remove(index);
    }

    @Override
    public int indexOf(Object element) {
        return tab.indexOf(element);
    }

    @Override
    public int lastIndexOf(Object element) {
        return tab.lastIndexOf(element);
    }

    @Override
    public ListIterator<T> listIterator() {
        return tab.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator(int index) {
        return tab.listIterator(index);
    }

    @NonNull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return tab.subList(fromIndex, toIndex);
    }

    public RelationShipAndPosition getRelationShipAndPosition() {
        return RelationShipAndPosition.LINEAR_TOP;
    }

    public boolean heightWrapContent() {
        return false;
    }

    @Override
    public void onIndicatorPageChange(int preItem, int currentItem) {

    }

    public abstract View getViewForTab(int position, View convertView, ViewGroup container, T item);

    public abstract View getViewForPage(int position, View convertView, ViewGroup container, T item);
}
