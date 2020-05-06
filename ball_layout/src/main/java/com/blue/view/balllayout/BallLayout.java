package com.blue.view.balllayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.blue.view.balllayout.coordinate.Rotation;
import com.blue.view.balllayout.coordinate.SphericalCoordinate;
import com.blue.view.balllayout.coordinate.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BallLayout extends ViewGroup {

    private Adapter<?> adapter;
    private List<Node> nodes = new ArrayList<>();
    private List<Node> unmodifiableNodes = Collections.unmodifiableList(nodes);
    private List<Vector> vectors = new ArrayList<>();

    private int radius;

    private boolean autoRotation;
    private SphericalCoordinate from = new SphericalCoordinate(0D, 0D);
    private SphericalCoordinate to = new SphericalCoordinate(0D, 0D);

    private boolean autoRotationByFinger;
    private boolean fixedSpeed;
    private double acceleration;
    private double endSpeed;

    private float frontAlpha;
    private float backAlpha;
    private float frontScale;
    private float backScale;

    private SphericalCoordinate touchPrev1Point;
    private SphericalCoordinate touchPrev2Point;
    private SphericalCoordinate touchPrev3Point;
    private Node clickNode = null;
    private Rect clickRect = new Rect();

    private static final int FPS = 60;
    private static final int FRAME_INTERVAL_TIME = 1000 / FPS;

    private static final int MSG_CLICK = 1;
    private static final int MSG_ROTATION = 2;

    private long startAutoRotationTime;

    private Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CLICK:
                    if (onItemClickListener != null) {
                        Node node = (Node) msg.obj;
                        onItemClickListener.onItemClick(BallLayout.this, node.view, node.item.getData(), node.position);
                    }
                    break;
                case MSG_ROTATION:
                    int fixed = 1;
                    int times = msg.arg2;
                    if (msg.arg1 == 0 && fixedSpeed) {
                        if (!calcRotationPoint(from, to, acceleration, endSpeed)) {
                            fixed = 0;
                        }
                    }

                    int max = times + FPS / 10;
                    long delay;
                    do {
                        Rotation.rotate(from, to, vectors);
                        times++;
                        delay = startAutoRotationTime + 1000 * times / FPS - SystemClock.elapsedRealtime();
                    } while (delay < -FRAME_INTERVAL_TIME && times <= max);
                    resetChildren();
                    layoutChanged(getWidth() / 2, getHeight() / 2);

                    Message message = handler.obtainMessage(MSG_ROTATION, fixed, times);
                    handler.sendMessageDelayed(message, delay);
                    break;
                default:
                    break;
            }
            return false;
        }
    };
    private Handler handler = new Handler(Looper.getMainLooper(), callback);
    private OnItemClickListener onItemClickListener;
    private OnItemPositionChangedListener onItemPositionChangedListener;

    public BallLayout(Context context) {
        super(context);
    }

    public BallLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttribute(attrs);
    }

    public BallLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(attrs);
    }

    private void initAttribute(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BallLayout);

        radius = (int) typedArray.getDimension(R.styleable.BallLayout_radius, 0F);
        if (radius <= 0) {
            radius = 1;
        }

        frontAlpha = typedArray.getFloat(R.styleable.BallLayout_frontAlpha, 1F);
        backAlpha = typedArray.getFloat(R.styleable.BallLayout_backAlpha, 1F);
        frontScale = typedArray.getFloat(R.styleable.BallLayout_frontScale, 1F);
        backScale = typedArray.getFloat(R.styleable.BallLayout_backScale, 1F);
        autoRotationByFinger = typedArray.getBoolean(R.styleable.BallLayout_autoRotationByFinger, false);

        enableFixedSpeed(
                typedArray.getBoolean(R.styleable.BallLayout_fixedSpeed, false),
                typedArray.getFloat(R.styleable.BallLayout_acceleration, 0F),
                typedArray.getFloat(R.styleable.BallLayout_endSpeed, 0F));

        typedArray.recycle();
    }

    public void setRadius(int radius) {
        if (this.radius != radius) {
            if (radius <= 0) {
                throw new IllegalArgumentException("Radius must be bigger than 0.");
            }
            this.radius = radius;
            for (Node node : nodes) {
                node.setRadius(radius);
            }
        }
    }

    public void setChildAlpha(float frontAlpha, float backAlpha) {
        this.frontAlpha = frontAlpha;
        this.backAlpha = backAlpha;
        requestLayout();
    }

    public void setChildScale(float frontScale, float backScale) {
        this.frontScale = frontScale;
        this.backScale = backScale;
        requestLayout();
    }

    public void enableAutoRotationByFinger(boolean enable) {
        this.autoRotationByFinger = enable;
    }

    public void enableFixedSpeed(boolean enable, double acceleration, double endSpeed) {
        if (acceleration < 0D) {
            throw new IllegalArgumentException("Acceleration must be bigger than 0.");
        }
        if (endSpeed < 0D) {
            throw new IllegalArgumentException("EndSpeed must be bigger than 0.");
        }
        this.fixedSpeed = enable;
        this.acceleration = acceleration;
        this.endSpeed = endSpeed;
    }

    public boolean startupAutoRotation(int x1, int y1, int x2, int y2) {
        return startupAutoRotation(uiToSphericalCoordinate(x1, y1), uiToSphericalCoordinate(x2, y2));
    }

    public boolean startupAutoRotation(SphericalCoordinate from, SphericalCoordinate to) {
        if (from == null || to == null) {
            return false;
        }
        if (!autoRotation) {
            autoRotation = true;
            startAutoRotationTime = SystemClock.elapsedRealtime();
            handler.sendEmptyMessage(MSG_ROTATION);
        }
        this.from.set(radius, from.getTheta(), from.getPhi());

        SphericalCoordinate to2 = new SphericalCoordinate(radius, to.getTheta(), to.getPhi() - from.getPhi());
        Vector toVector = to2.toVector();
        Rotation.rotateByY(-from.getTheta(), toVector);
        SphericalCoordinate to3 = toVector.toSphericalCoordinate();
        to3.setTheta(to3.getTheta() / FPS);
        to3.copyToVector(toVector);
        Rotation.rotateByY(from.getTheta(), toVector);
        SphericalCoordinate to4 = toVector.toSphericalCoordinate();
        to4.setPhi(to4.getPhi() + from.getPhi());
        this.to = to4;
        return true;
    }

    private boolean calcRotationPoint(SphericalCoordinate from, SphericalCoordinate to, double acceleration, double endSpeed) {
        boolean done;
        SphericalCoordinate to2 = new SphericalCoordinate(radius, to.getTheta(), to.getPhi() - from.getPhi());
        Vector toVector = to2.toVector();
        Rotation.rotateByY(-from.getTheta(), toVector);
        SphericalCoordinate to3 = toVector.toSphericalCoordinate();
        double end = endSpeed / FPS;
        double acc = acceleration / FPS;
        double old = to3.getTheta();
        double speed;
        if (old > end + acc) {
            speed = old - acc;
            done = false;
        } else if (old < end - acc) {
            speed = old + acc;
            done = false;
        } else {
            speed = end;
            done = true;
        }
        to3.setTheta(speed);
        to3.copyToVector(toVector);
        Rotation.rotateByY(from.getTheta(), toVector);
        SphericalCoordinate to4 = toVector.toSphericalCoordinate();
        to4.setPhi(to4.getPhi() + from.getPhi());
        to.set(to4);
        return done;
    }

    public void shutdownAutoRotation() {
        if (autoRotation) {
            autoRotation = false;
            handler.removeMessages(MSG_ROTATION);
        }
    }

    public void resumeAutoRotation() {
        if (autoRotation) {
            handler.removeMessages(MSG_ROTATION);
            startAutoRotationTime = SystemClock.elapsedRealtime();
            handler.sendEmptyMessage(MSG_ROTATION);
        }
    }

    private void resumeAutoRotation(SphericalCoordinate from, SphericalCoordinate to) {
        if (from != null && to != null) {
            this.from.set(from);
            this.to.set(to);
        }
        resumeAutoRotation();
    }

    public void pauseAutoRotation() {
        if (autoRotation) {
            handler.removeMessages(MSG_ROTATION);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        resumeAutoRotation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        pauseAutoRotation();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemPositionChangedListener(OnItemPositionChangedListener onItemPositionChangedListener) {
        this.onItemPositionChangedListener = onItemPositionChangedListener;
    }

    public void setAdapter(Adapter adapter) {
        adapter.bindLayout(this);
        this.adapter = adapter;
        addAllViews();
    }

    void notifyDataSetChanged() {
        removeAllViews();
        addAllViews();
    }

    private void addAllViews() {
        nodes.clear();
        vectors.clear();
        for (int i = 0; i < adapter.getCount(); i++) {
            Item item = adapter.getItem(i);
            Node node = new Node();
            node.position = i;
            node.item = item;
            node.view = adapter.getItemView(i, item, this);
            node.sphericalCoordinate = new SphericalCoordinate(radius, item.getTheta(), item.getPhi());
            node.vector = node.sphericalCoordinate.toVector();
            vectors.add(node.vector);

            nodes.add(node);
            if (node.view.getParent() != this) {
                addView(node.view);
            }
        }
        resetChildren();
    }

    private void resetChildren() {
        resetChildLocation();
        resetChildAlpha();
        resetChildScale();
    }

    private void resetChildLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (Node node : nodes) {
                node.view.setZ((float) node.vector.getUiZ());
            }
        }
    }

    private void resetChildAlpha() {
        if (frontAlpha == backAlpha) {
            for (Node node : nodes) {
                node.alpha = frontAlpha;
            }
        } else {
            double tmp = (frontAlpha - backAlpha) / (radius * 2);
            for (Node node : nodes) {
                double a = frontAlpha - (radius - node.vector.getUiZ()) * tmp;
                node.alpha = (float) a;
            }
        }
    }

    private void resetChildScale() {
        if (frontScale == backScale) {
            for (Node node : nodes) {
                node.scale = frontScale;
            }
        } else {
            double tmp = (frontAlpha - backAlpha) / (radius * 2);
            for (Node node : nodes) {
                double a = frontScale - (radius - node.vector.getUiZ()) * tmp;
                node.scale = (float) a;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));

        if (adapter == null) {
            measureChildren(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        } else {
            int width = getMeasuredWidth() - getPaddingRight() - getPaddingLeft();
            int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
            adapter.onMeasured(this, width, height);
            for (int i = 0, count = getChildCount(); i < count; i++) {
                View child = getChildAt(i);
                if (child != null && child.getVisibility() != GONE) {
                    int measureWidth = MeasureSpec.makeMeasureSpec(adapter.getMeasureWidth(i), MeasureSpec.EXACTLY);
                    int measureHeight = MeasureSpec.makeMeasureSpec(adapter.getMeasureHeight(i), MeasureSpec.EXACTLY);
                    child.measure(measureWidth, measureHeight);
                }
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            layoutChanged((r - l) / 2, (b - t) / 2);
        }
    }

    private void layoutChanged(int centerX, int centerY) {
        if (adapter == null) {
            return;
        }
        for (Node node : nodes) {
            View child = node.view;
            if (child == null || child.getVisibility() == GONE) {
                continue;
            }
            child.setScaleX(node.scale);
            child.setScaleY(node.scale);
            child.setAlpha(node.alpha);
            int measuredWidth = child.getMeasuredWidth();
            int measuredHeight = child.getMeasuredHeight();
            int position = node.position;
            int left = centerX + (int) node.vector.getUiX() - adapter.getBaseX(position, measuredWidth);
            int top = centerY + (int) node.vector.getUiY() - adapter.getBaseY(position, measuredHeight);
            child.layout(left, top, left + measuredWidth, top + measuredHeight);
        }
        if (onItemPositionChangedListener != null) {
            onItemPositionChangedListener.onItemPositionChanged(unmodifiableNodes);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int action = e.getAction();
        float eventX = e.getX();
        float eventY = e.getY();

        dispatchOnTouchForRotation(action, eventX, eventY);
        dispatchOnTouchForClick(action, eventX, eventY);
        return true;
    }

    private void dispatchOnTouchForRotation(int action, float eventX, float eventY) {
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            touchDone();
            return;
        }

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        float x = eventX - centerX;
        float y = eventY - centerY;

        SphericalCoordinate sc = uiToSphericalCoordinate(x, y);
        if (sc == null) {
            touchDone();
            return;
        }
        pauseAutoRotation();

        if (touchPrev1Point != null) {
            Rotation.rotate(touchPrev1Point, sc, vectors);
            resetChildren();
            layoutChanged(centerX, centerY);
        }
        touchPrev3Point = touchPrev2Point;
        touchPrev2Point = touchPrev1Point;
        touchPrev1Point = sc;
    }

    private void touchDone() {
        if (autoRotationByFinger) {
            resumeAutoRotation(touchPrev3Point, touchPrev1Point);
        } else {
            resumeAutoRotation();
        }
        touchPrev1Point = null;
        touchPrev2Point = null;
        touchPrev3Point = null;
    }

    private SphericalCoordinate uiToSphericalCoordinate(float x, float y) {
        double a = radius * radius - x * x - y * y;
        if (a <= 0) {
            return null;
        }
        return Vector.createFromUI(x, y, Math.sqrt(a)).toSphericalCoordinate(radius);
    }

    private void dispatchOnTouchForClick(int action, float eventX, float eventY) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (onItemClickListener != null) {
                    for (Node node : nodes) {
                        if (node.isInBound(eventX, eventY)) {
                            if (clickNode == null || clickNode.vector.getUiZ() < node.vector.getUiZ()) {
                                node.setRect(clickRect);
                                clickNode = node;
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (clickNode != null && !clickRect.contains((int) eventX, (int) eventY)) {
                    clickNode = null;
                    clickRect.setEmpty();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (clickNode != null) {
                    if (onItemClickListener != null && clickRect.contains((int) eventX, (int) eventY)) {
                        handler.obtainMessage(MSG_CLICK, clickNode).sendToTarget();
                    }
                    clickNode = null;
                    clickRect.setEmpty();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (clickNode != null) {
                    clickNode = null;
                    clickRect.setEmpty();
                }
                break;
        }
    }

    public interface OnItemClickListener<T> {

        void onItemClick(BallLayout parent, View view, T data, int position);
    }

    public interface OnItemPositionChangedListener {

        void onItemPositionChanged(List<Node> nodes);
    }
}
