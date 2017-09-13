package com.fpliu.newton.ui.tab;

import android.view.View;
import android.widget.ImageView;

import com.shizhefei.view.indicator.Indicator;

public class OnTransitionImageListener implements Indicator.OnTransitionListener {

    private int selectedResId;
    private int unselectedResId;

    public OnTransitionImageListener(int unselectedResId, int selectedResId) {
        this.selectedResId = selectedResId;
        this.unselectedResId = unselectedResId;
    }

    public ImageView getImageView(View convertView, int position) {
        return (ImageView) convertView;
    }

    @Override
    public void onTransition(View convertView, int position, float selectPercent) {
        ImageView imageView = getImageView(convertView, position);
        if (selectPercent == 1) {
            imageView.setImageResource(selectedResId);
        } else if (selectPercent == 0) {
            imageView.setImageResource(unselectedResId);
        }
    }
}
