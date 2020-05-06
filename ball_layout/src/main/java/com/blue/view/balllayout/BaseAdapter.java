package com.blue.view.balllayout;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseAdapter<T> extends Adapter<T> {

    private final List<Item<T>> datas;
    private final List<View> cache;

    public BaseAdapter() {
        this.datas = new ArrayList<>();
        this.cache = new ArrayList<>();
    }

    public BaseAdapter(List<Item<T>> datas) {
        this.datas = new ArrayList<>(datas);
        this.cache = new ArrayList<>(datas.size());
    }

    public void addItems(Item<T>... items) {
        addItems(Arrays.asList(items));
    }

    public void addItem(Item<T> item) {
        datas.add(item);
        notifyDataSetChanged();
    }

    public void updateItems(Item<T>... items) {
        updateItems(Arrays.asList(items));
    }

    public void updateItems(List<Item<T>> items) {
        datas.clear();
        datas.addAll(items);
        notifyDataSetChanged();
    }

    public void addItems(List<Item<T>> items) {
        datas.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    protected int getCount() {
        return datas.size();
    }

    @Override
    protected Item<T> getItem(int position) {
        return datas.get(position);
    }

    @Override
    protected final View getItemView(int position, Item<T> item, ViewGroup parent) {
        if (cache.size() > position) {
            View view = cache.get(position);
            updateItemView(position, view);
            return view;
        }
        View view = bindItemView(position, item, parent);
        updateItemView(position, view);
        return view;
    }

    protected abstract View bindItemView(int position, Item<T> item, ViewGroup parent);

    protected void updateItemView(int position, View view) {
    }
}