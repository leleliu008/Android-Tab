package com.fpliu.newton.ui.tab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fpliu.newton.ui.base.BaseView;
import com.fpliu.newton.ui.base.LazyFragment;
import com.fpliu.newton.ui.base.UIUtil;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.shizhefei.view.viewpager.SViewPager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class TabFragment<T> extends LazyFragment implements IndicatorViewPager.OnIndicatorPageChangeListener, List<T> {

    private List<T> items;
    private LinearLayout headerView;
    private RelativeLayout indicatorPanel;
    private RelativeLayout leftPanel;
    private RelativeLayout rightPanel;
    private Indicator indicatorView;
    private SViewPager viewPager;
    private IndicatorViewPager.IndicatorFragmentPagerAdapter adapter;

    @Override
    protected void onCreateViewLazy(BaseView baseView, Bundle savedInstanceState) {
        super.onCreateViewLazy(baseView, savedInstanceState);

        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);

        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);

        Context context = getActivity();

        LinearLayout contentView = new LinearLayout(context);
        contentView.setOrientation(LinearLayout.VERTICAL);
        addContentView(contentView);

        headerView = new LinearLayout(context);
        headerView.setOrientation(LinearLayout.VERTICAL);
        contentView.addView(headerView, lp1);

        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.HORIZONTAL);

        leftPanel = new RelativeLayout(context);
        container.addView(leftPanel, lp3);

        indicatorPanel = new RelativeLayout(context);
        container.addView(indicatorPanel, lp4);

        rightPanel = new RelativeLayout(context);
        container.addView(rightPanel, lp3);

        viewPager = new SViewPager(context);
        viewPager.setId(R.id.tab_view_pager);
        viewPager.setCanScroll(true);

        if (isTabOnTop()) {
            contentView.addView(container, lp1);
            contentView.addView(viewPager, lp2);
        } else {
            contentView.addView(viewPager, lp2);
            contentView.addView(container, lp1);
        }

        setIndicator(new FixedIndicatorView(context));
    }

    public void setIndicator(Indicator indicator) {
        ((View) indicator).setId(R.id.tab_view_indicator);

        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp3.addRule(RelativeLayout.CENTER_IN_PARENT);

        if (indicatorView == null) {
            indicatorPanel.addView((View) indicator, lp3);
            indicatorView = indicator;
        } else {
            int index = indicatorPanel.indexOfChild((View) indicatorView);
            indicatorPanel.removeViewAt(index);
            indicatorPanel.addView((View) indicator, index, lp3);
            indicatorView = indicator;
        }

        //TODO 暂时先把原来的都清空吧
        viewPager.clearOnPageChangeListeners();

        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(indicatorView, viewPager);
        indicatorViewPager.setAdapter(adapter = new IndicatorViewPager.IndicatorFragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return TabFragment.this.getTabCount();
            }

            @Override
            public View getViewForTab(int position, View convertView, ViewGroup container) {
                return TabFragment.this.getViewForTab(position, convertView, container, get(position));
            }

            @Override
            public Fragment getFragmentForPage(int position) {
                return TabFragment.this.getFragmentForPage(position);
            }
        });
        indicatorViewPager.setOnIndicatorPageChangeListener(this);
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

    public void setScrollBar(ScrollBar scrollBar) {
        indicatorView.setScrollBar(scrollBar);
    }

    public void setColorScrollBar(int color, int height) {
        indicatorView.setScrollBar((new ColorBar(getActivity(), color, height)));
    }

    public void setColorScrollBar(int color, int height, ScrollBar.Gravity gravity) {
        indicatorView.setScrollBar((new ColorBar(getActivity(), color, height, gravity)));
    }

    public void setColorResScrollBar(int colorId, int height) {
        indicatorView.setScrollBar((new ColorBar(getActivity(), getResources().getColor(colorId), height)));
    }

    public void setColorResScrollBar(int colorId, int height, ScrollBar.Gravity gravity) {
        indicatorView.setScrollBar((new ColorBar(getActivity(), getResources().getColor(colorId), height, gravity)));
    }

    public void setOnTransitionListener(Indicator.OnTransitionListener onTransitionListener) {
        indicatorView.setOnTransitionListener(onTransitionListener);
    }

    public void setOnTransitionTextViewSizeAndColor(float selectSize, float unSelectSize, int selectColor, int unSelectColor) {
        indicatorView.setOnTransitionListener(new OnTransitionTextListener(selectSize, unSelectSize, selectColor, unSelectColor));
    }

    public void setOnTransitionTextViewSizeAndColorRes(float selectSize, float unSelectSize, int selectColorId, int unSelectColorId) {
        indicatorView.setOnTransitionListener(new OnTransitionTextListener(selectSize, unSelectSize, getResources().getColor(selectColorId), getResources().getColor(unSelectColorId)));
    }

    public void setCurrentItem(int position) {
        indicatorView.setCurrentItem(position);
    }

    public void setCurrentItem(int position, boolean anim) {
        indicatorView.setCurrentItem(position, anim);
    }

    public void setTabItemClickable(boolean clickable) {
        indicatorView.setItemClickable(clickable);
    }

    public void setOnIndicatorItemClickListener(Indicator.OnIndicatorItemClickListener listener) {
        indicatorView.setOnIndicatorItemClickListener(listener);
    }

    public void setOnItemSelectListener(Indicator.OnItemSelectedListener listener) {
        indicatorView.setOnItemSelectListener(listener);
    }

    public void setCanScroll(boolean canScroll) {
        viewPager.setCanScroll(canScroll);
    }

    /**
     * 设置Tab条为包裹内容，这时候，后面的背景需要进行设置，否则会很难看
     *
     * @param indicatorBarBackgroundColor
     */
    public void setIndicatorWrapAndInCenter(int indicatorBarBackgroundColor) {
        View view = (View) indicatorView;
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
        lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        view.setLayoutParams(lp);
        ((ViewGroup) indicatorPanel.getParent()).setBackgroundColor(indicatorBarBackgroundColor);
    }

    public void setLeftViewInIndicatorBar(View view) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        lp.leftMargin = UIUtil.dip2px(view.getContext(), 15);
        leftPanel.addView(view, lp);
    }

    public void setLeftViewInIndicatorBar(View view, RelativeLayout.LayoutParams lp) {
        leftPanel.addView(view, lp);
    }

    public void setRightViewInIndicatorBar(View view) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp.rightMargin = UIUtil.dip2px(view.getContext(), 15);
        rightPanel.addView(view, lp);
    }

    public void setRightViewInIndicatorBar(View view, RelativeLayout.LayoutParams lp) {
        rightPanel.addView(view, lp);
    }

    public void setViewBeforeTab(View view) {
        headerView.addView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    public void setViewBeforeTab(View view, LinearLayout.LayoutParams lp) {
        headerView.addView(view, lp);
    }

    @Override
    public void onIndicatorPageChange(int preItem, int currentItem) {

    }

    public boolean isTabOnTop() {
        return true;
    }

    public void setItems(List<T> items) {
        this.items = items;
        adapter.notifyDataSetChanged();
    }

    public List<T> getItems() {
        return items;
    }

    @Override
    public int size() {
        return items == null ? 0 : items.size();
    }

    @Override
    public boolean isEmpty() {
        return items == null ? true : items.isEmpty();
    }

    @Override
    public boolean contains(Object object) {
        return items == null ? false : items.contains(object);
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return items == null ? null : items.iterator();
    }

    @NonNull
    @Override
    public T[] toArray() {
        return items == null ? null : (T[]) items.toArray();
    }

    @NonNull
    @Override
    public <T1> T1[] toArray(@NonNull T1[] a) {
        return items == null ? null : (T1[]) items.toArray();
    }

    @Override
    public boolean add(T item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        boolean isSuccess = items.add(item);
        if (isSuccess) {
            adapter.notifyDataSetChanged();
        }
        return isSuccess;
    }

    @Override
    public boolean remove(Object object) {
        if (items == null) {
            return false;
        }
        if (items.isEmpty()) {
            return false;
        }
        boolean isSuccess = items.remove(object);
        if (isSuccess) {
            adapter.notifyDataSetChanged();
        }
        return isSuccess;
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> collection) {
        if (items == null) {
            return false;
        }
        if (items.isEmpty()) {
            return false;
        }
        return items.containsAll(collection);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends T> collection) {
        if (items == null) {
            items = new ArrayList<>();
        }
        boolean isSuccess = items.addAll(collection);
        if (isSuccess) {
            adapter.notifyDataSetChanged();
        }
        return isSuccess;
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends T> collection) {
        if (items == null) {
            items = new ArrayList<>();
        }
        boolean isSuccess = items.addAll(index, collection);
        if (isSuccess) {
            adapter.notifyDataSetChanged();
        }
        return isSuccess;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> collection) {
        if (items == null) {
            return false;
        }
        if (items.isEmpty()) {
            return false;
        }
        boolean isSuccess = items.removeAll(collection);
        if (isSuccess) {
            adapter.notifyDataSetChanged();
        }
        return isSuccess;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> collection) {
        if (items == null) {
            return false;
        }
        if (items.isEmpty()) {
            return false;
        }
        boolean isSuccess = items.retainAll(collection);
        if (isSuccess) {
            adapter.notifyDataSetChanged();
        }
        return isSuccess;
    }

    @Override
    public void clear() {
        if (items == null) {
            return;
        }
        if (items.isEmpty()) {
            return;
        }
        items.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public T get(int index) {
        if (items == null || items.isEmpty()) {
            return null;
        }
        if (index >= items.size()) {
            return null;
        }
        return items.get(index);
    }

    @Override
    public T set(int index, T element) {
        if (items == null || items.isEmpty()) {
            return null;
        }
        if (index >= items.size()) {
            return null;
        }
        return items.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(index, element);
    }

    @Override
    public T remove(int index) {
        if (items == null || items.isEmpty()) {
            return null;
        }
        if (index >= items.size()) {
            return null;
        }
        return items.remove(index);
    }

    @Override
    public int indexOf(Object object) {
        return items == null ? -1 : items.indexOf(object);
    }

    @Override
    public int lastIndexOf(Object object) {
        return items == null ? -1 : items.lastIndexOf(object);
    }

    @Override
    public ListIterator<T> listIterator() {
        return items == null ? null : items.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator(int index) {
        return items == null ? null : items.listIterator(index);
    }

    @NonNull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (items == null || items.isEmpty()) {
            return null;
        }
        int size = items.size();
        if (fromIndex >= size || toIndex >= size) {
            return null;
        }
        return items.subList(fromIndex, toIndex);
    }

    public int getCurrentItemPosition() {
        return indicatorView.getCurrentItem();
    }

    public View getCurrentTabView() {
        return indicatorView.getItemView(getCurrentItemPosition());
    }

    public Fragment getCurrentFragment() {
        return adapter.getCurrentFragment();
    }

    public int getTabCount() {
        return items == null ? 0 : items.size();
    }

    public abstract View getViewForTab(int position, View convertView, ViewGroup container, T item);

    public abstract Fragment getFragmentForPage(int position);
}
