package com.fpliu.newton.ui.tab;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fpliu.newton.ui.base.UIUtil;
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

public final class TabImpl<T> implements ITab<T> {

    private List<T> items;
    private Context context;
    private LinearLayout headerView;
    private RelativeLayout indicatorPanel;
    private RelativeLayout leftPanel;
    private RelativeLayout rightPanel;
    private Indicator indicatorView;
    private WrapContentHeightViewPager viewPager;
    private IndicatorViewPager indicatorViewPager;
    private IndicatorViewPager.IndicatorPagerAdapter adapter;

    @Override
    public View init(Context context, RelationShipAndPosition relationShipAndPosition, boolean heightWrapContent) {
        this.context = context;

        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams lp4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);

        RelativeLayout contentView = new RelativeLayout(context);

        headerView = new LinearLayout(context);
        headerView.setId(R.id.tab_view_header);
        headerView.setOrientation(LinearLayout.VERTICAL);
        contentView.addView(headerView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));

        LinearLayout container = new LinearLayout(context);
        container.setId(R.id.tab_view_indicator_container);
        container.setOrientation(LinearLayout.HORIZONTAL);
        container.addView(leftPanel = new RelativeLayout(context), lp3);
        container.addView(indicatorPanel = new RelativeLayout(context), lp4);
        container.addView(rightPanel = new RelativeLayout(context), lp3);

        viewPager = new WrapContentHeightViewPager(context).setHeightWrapContent(heightWrapContent);
        viewPager.setId(R.id.tab_view_pager);
        viewPager.setCanScroll(true);

        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        switch (relationShipAndPosition) {
            case LINEAR_TOP:
                lp1.addRule(RelativeLayout.BELOW, R.id.tab_view_header);
                contentView.addView(container, lp1);

                lp2.addRule(RelativeLayout.BELOW, R.id.tab_view_indicator_container);
                lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                contentView.addView(viewPager, lp2);
                break;
            case LINEAR_BOTTOM:
                lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                contentView.addView(container, lp1);

                lp2.addRule(RelativeLayout.BELOW, R.id.tab_view_header);
                lp2.addRule(RelativeLayout.ABOVE, R.id.tab_view_indicator_container);
                contentView.addView(viewPager, lp2);
                break;
            case FRAME_BOTTOM:
                lp1.addRule(RelativeLayout.BELOW, R.id.tab_view_header);
                lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                contentView.addView(viewPager, lp1);

                lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                lp2.bottomMargin = UIUtil.dip2px(context, 20);
                contentView.addView(container, lp2);
                break;
        }

        return contentView;
    }

    @Override
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

        indicatorViewPager = new IndicatorViewPager(indicatorView, viewPager);
    }

    @Override
    public Indicator getIndicator() {
        return indicatorView;
    }

    @Override
    public SViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public void setPagerAdapter(IndicatorViewPager.IndicatorPagerAdapter adapter) {
        this.adapter = adapter;
        indicatorViewPager.setAdapter(adapter);
    }

    @Override
    public IndicatorViewPager.IndicatorPagerAdapter getPagerAdapter() {
        return adapter;
    }

    @Override
    public void setScrollBar(ScrollBar scrollBar) {
        indicatorView.setScrollBar(scrollBar);
    }

    @Override
    public void setColorScrollBar(int color, int height) {
        indicatorView.setScrollBar((new ColorBar(context, color, height)));
    }

    @Override
    public void setColorScrollBar(int color, int height, ScrollBar.Gravity gravity) {
        indicatorView.setScrollBar((new ColorBar(context, color, height, gravity)));
    }

    @Override
    public void setColorResScrollBar(int colorId, int height) {
        indicatorView.setScrollBar((new ColorBar(context, context.getResources().getColor(colorId), height)));
    }

    @Override
    public void setColorResScrollBar(int colorId, int height, ScrollBar.Gravity gravity) {
        indicatorView.setScrollBar((new ColorBar(context, context.getResources().getColor(colorId), height, gravity)));
    }

    @Override
    public void setOnTransitionListener(Indicator.OnTransitionListener onTransitionListener) {
        indicatorView.setOnTransitionListener(onTransitionListener);
    }

    @Override
    public void setOnTransitionTextViewSizeAndColor(float selectSize, float unSelectSize, int selectColor, int unSelectColor) {
        indicatorView.setOnTransitionListener(new OnTransitionTextListener(selectSize, unSelectSize, selectColor, unSelectColor));
    }

    @Override
    public void setOnTransitionTextViewSizeAndColorRes(float selectSize, float unSelectSize, int selectColorId, int unSelectColorId) {
        indicatorView.setOnTransitionListener(new OnTransitionTextListener(selectSize, unSelectSize, context.getResources().getColor(selectColorId), context.getResources().getColor(unSelectColorId)));
    }

    @Override
    public void setCurrentItem(int position) {
        setCurrentItem(position, false);
    }

    @Override
    public void setCurrentItem(int position, boolean needAnimation) {
        indicatorViewPager.setCurrentItem(position, needAnimation);
    }

    @Override
    public void setTabItemClickable(boolean clickable) {
        indicatorView.setItemClickable(clickable);
    }

    @Override
    public void setOnIndicatorItemClickListener(Indicator.OnIndicatorItemClickListener listener) {
        indicatorView.setOnIndicatorItemClickListener(listener);
    }

    @Override
    public void setOnIndicatorPageChangeListener(IndicatorViewPager.OnIndicatorPageChangeListener listener) {
        indicatorViewPager.setOnIndicatorPageChangeListener(listener);
    }

    @Override
    public void setOnItemSelectListener(Indicator.OnItemSelectedListener listener) {
        indicatorView.setOnItemSelectListener(listener);
    }

    @Override
    public void setCanScroll(boolean canScroll) {
        viewPager.setCanScroll(canScroll);
    }

    @Override
    public void setIndicatorWrapAndInCenter(int indicatorBarBackgroundColor) {
        View view = (View) indicatorView;
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
        lp.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        view.setLayoutParams(lp);
        ((ViewGroup) indicatorPanel.getParent()).setBackgroundColor(indicatorBarBackgroundColor);
    }

    @Override
    public void setLeftViewInIndicatorBar(View view) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        lp.leftMargin = UIUtil.dip2px(view.getContext(), 15);
        lp.rightMargin = UIUtil.dip2px(view.getContext(), 15);
        leftPanel.addView(view, lp);
    }

    @Override
    public void setLeftViewInIndicatorBar(View view, RelativeLayout.LayoutParams lp) {
        leftPanel.addView(view, lp);
    }

    @Override
    public void setRightViewInIndicatorBar(View view) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        lp.leftMargin = UIUtil.dip2px(view.getContext(), 15);
        lp.rightMargin = UIUtil.dip2px(view.getContext(), 15);
        rightPanel.addView(view, lp);
    }

    @Override
    public void setRightViewInIndicatorBar(View view, RelativeLayout.LayoutParams lp) {
        rightPanel.addView(view, lp);
    }

    @Override
    public void setViewBeforeTab(View view) {
        headerView.addView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void setViewBeforeTab(View view, LinearLayout.LayoutParams lp) {
        headerView.addView(view, lp);
    }

    @Override
    public int getCurrentItemPosition() {
        return indicatorView.getCurrentItem();
    }

    @Override
    public View getCurrentTabView() {
        return indicatorView.getItemView(getCurrentItemPosition());
    }

    @Override
    public int getTabCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public void setItems(List<T> items) {
        this.items = items;
        adapter.notifyDataSetChanged();
    }

    @Override
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

    @Override
    public Iterator<T> iterator() {
        return items == null ? null : items.iterator();
    }

    @Override
    public T[] toArray() {
        return items == null ? null : (T[]) items.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
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
    public boolean containsAll(Collection<?> collection) {
        if (items == null) {
            return false;
        }
        if (items.isEmpty()) {
            return false;
        }
        return items.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
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
    public boolean addAll(int index, Collection<? extends T> collection) {
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
    public boolean removeAll(Collection<?> collection) {
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
    public boolean retainAll(Collection<?> collection) {
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

    @Override
    public ListIterator<T> listIterator(int index) {
        return items == null ? null : items.listIterator(index);
    }

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
}
