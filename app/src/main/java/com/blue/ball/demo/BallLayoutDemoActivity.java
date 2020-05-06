package com.blue.ball.demo;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.blue.view.balllayout.BallLayout;
import com.blue.view.balllayout.BaseAdapter;
import com.blue.view.balllayout.Item;
import com.blue.view.balllayout.Node;
import com.blue.view.balllayout.coordinate.SphericalCoordinate;
import com.bumptech.glide.Glide;

import java.util.List;

public class BallLayoutDemoActivity extends Activity {

    private BallLayout ballLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ball_layout);

        ballLayout = findViewById(R.id.ball_layout);
        ballLayout.setAdapter(new PortraitAdapter((int) (getResources().getDisplayMetrics().density * 32)));
//        ballLayout.enableFixedSpeed(true, Math.PI / 32, Math.PI / 16);
        SphericalCoordinate from = new SphericalCoordinate(Math.PI * 98 / 180, Math.PI * 82 / 180);
        SphericalCoordinate to = new SphericalCoordinate(Math.PI / 2, Math.PI / 2);
        ballLayout.startupAutoRotation(from, to);

        ballLayout.setOnItemClickListener(new BallLayout.OnItemClickListener() {

            @Override
            public void onItemClick(BallLayout parent, View view, Object data, int position) {
                Dialog dialog = new Dialog(parent.getContext());
                Window window = dialog.getWindow();
                window.setBackgroundDrawable(null);
                window.requestFeature(Window.FEATURE_NO_TITLE);
                ImageView imageView = new ImageView(dialog.getContext());
                imageView.setBackground(null);
                Glide.with(parent.getContext()).load(data).into(imageView);
                dialog.setContentView(imageView);
                window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                int width = (int) (getResources().getDisplayMetrics().density * 120);
                layoutParams.width = width;
                layoutParams.height = width;
                imageView.setLayoutParams(layoutParams);
                dialog.show();
            }
        });

        final TextView textView = findViewById(R.id.ball_info);
        ballLayout.setOnItemPositionChangedListener(new BallLayout.OnItemPositionChangedListener() {

            int count = 0;
            long second = 0;

            @Override
            public void onItemPositionChanged(List<Node> nodes) {
                long sec = SystemClock.elapsedRealtime() / 1000;
                if (sec == second) {
                    count++;
                } else {
                    textView.setText("FPS: " + count);
                    second = sec;
                    count = 1;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ballLayout.resumeAutoRotation();
    }

    @Override
    protected void onPause() {
        ballLayout.pauseAutoRotation();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        ballLayout.shutdownAutoRotation();
        super.onDestroy();
    }

    private static class PortraitAdapter extends BaseAdapter<Integer> {

        private final int width;

        PortraitAdapter(int width) {
            this.width = width;
            int countInOneCircuit = 20;
            int count = Item.calcSphericalLocationItemCount(countInOneCircuit);
            List<Integer> resources = Resource.getResources(count);
            addItems(Item.newSphericalLocation(countInOneCircuit, resources));
        }

        @Override
        protected View bindItemView(final int position, Item<Integer> item, ViewGroup parent) {
            ImageView image = new ImageView(parent.getContext());
            Glide.with(parent.getContext()).load(item.getData()).circleCrop().into(image);
            return image;
        }

        @Override
        protected int getMeasureWidth(int position) {
            return width;
        }

        @Override
        protected int getMeasureHeight(int position) {
            return width;
        }

        @Override
        protected int getBaseX(int position, int measureWidth) {
            return measureWidth / 2;
        }

        @Override
        protected int getBaseY(int position, int measureHeight) {
            return measureHeight / 2;
        }
    }
}
