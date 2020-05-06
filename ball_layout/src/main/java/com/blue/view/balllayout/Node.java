package com.blue.view.balllayout;

import android.graphics.Rect;
import android.view.View;

import com.blue.view.balllayout.coordinate.SphericalCoordinate;
import com.blue.view.balllayout.coordinate.Vector;

public final class Node {

    int position;
    Item item;
    View view;
    Vector vector;
    SphericalCoordinate sphericalCoordinate;
    float scale = 1f;
    float alpha = 1f;

    void setRadius(float radius) {
        sphericalCoordinate.setRadius(radius);
        sphericalCoordinate.copyToVector(vector);
    }

    boolean isInBound(float x, float y) {
        return view.getLeft() < x && view.getRight() > x && view.getTop() < y && view.getBottom() > y;
    }

    void setRect(Rect rect) {
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    public int getPosition() {
        return position;
    }

    public View getView() {
        return view;
    }

    public float getScale() {
        return scale;
    }

    public float getAlpha() {
        return alpha;
    }

    public double getX() {
        return vector.getUiX();
    }

    public double getY() {
        return vector.getUiY();
    }

    public double getZ() {
        return vector.getUiZ();
    }

    public double getRadius() {
        return sphericalCoordinate.getRadius();
    }

    public double getTheta() {
        return sphericalCoordinate.getTheta();
    }

    public double getPhi() {
        return sphericalCoordinate.getPhi();
    }
}
