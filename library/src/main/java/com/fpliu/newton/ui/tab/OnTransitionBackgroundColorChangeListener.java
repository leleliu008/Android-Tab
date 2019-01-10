package com.fpliu.newton.ui.tab;

import android.content.Context;
import android.view.View;

import com.shizhefei.view.indicator.Indicator.OnTransitionListener;
import com.shizhefei.view.utils.ColorGradient;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

public class OnTransitionBackgroundColorChangeListener implements OnTransitionListener {

    private ColorGradient gradient;

    public OnTransitionBackgroundColorChangeListener() {
        super();
    }

    public OnTransitionBackgroundColorChangeListener(@ColorInt int selectColor, @ColorInt int unSelectColor) {
        super();
        setColor(selectColor, unSelectColor);
    }

    public OnTransitionBackgroundColorChangeListener(Context context, @ColorRes int selectColorRes, @ColorRes int unSelectColorRes) {
        super();
        setColorRes(context, selectColorRes, unSelectColorRes);
    }

    public final OnTransitionBackgroundColorChangeListener setColor(@ColorInt int selectColor, @ColorInt int unSelectColor) {
        gradient = new ColorGradient(unSelectColor, selectColor, 100);
        return this;
    }

    public final OnTransitionBackgroundColorChangeListener setColorRes(Context context, @ColorRes int selectColorRes, @ColorRes int unSelectColorRes) {
        setColor(ContextCompat.getColor(context, selectColorRes), ContextCompat.getColor(context, unSelectColorRes));
        return this;
    }

    @Override
    public void onTransition(View view, int position, float selectPercent) {
        if (gradient != null) {
            view.setBackgroundColor(gradient.getColor((int) (selectPercent * 100)));
        }
    }
}
