package com.blue.view.balllayout;

import android.view.View;
import android.view.ViewGroup;

public abstract class Adapter<T> {

    private BallLayout ballLayout;

    void bindLayout(BallLayout ballLayout) {
        this.ballLayout = ballLayout;
    }

    protected abstract int getCount();

    protected abstract Item<T> getItem(int position);

    protected abstract View getItemView(int position, Item<T> item, ViewGroup parent);

    protected abstract int getMeasureWidth(int position);

    protected abstract int getMeasureHeight(int position);

    protected void onMeasured(BallLayout ballLayout, int measureWidth, int measureHeight) {
    }

    protected abstract int getBaseX(int position, int measureWidth);

    protected abstract int getBaseY(int position, int measureHeight);

    public void notifyDataSetChanged() {
        if (ballLayout != null) {
            ballLayout.notifyDataSetChanged();
        }
    }
}