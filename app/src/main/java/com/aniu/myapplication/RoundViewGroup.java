package com.aniu.myapplication;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class RoundViewGroup extends ViewGroup {
    private int Cx;
    private int Cy;
    private float angOffset;
    public final String TAG = RoundViewGroup.class.getSimpleName();

    public RoundViewGroup(Context context) {
        super(context);
        init(context);
    }

    public RoundViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public RoundViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        Cx = sizeWidth / 2;
        Cy = sizeHeight / 2;

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int cCount = getChildCount();

        int cWidth = 0;
        int cHeight = 0;
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
        }

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth : cWidth,
                            (heightMode == MeasureSpec.EXACTLY) ? sizeHeight : cHeight);
    }
    float startX = 0;
    float startY = 0;
    float endX;
    float endY;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                Log.e(TAG, "startX : " + startX+" startY : " + startY);
                break;
            case MotionEvent.ACTION_MOVE:
                endX = ev.getX();
                endY = ev.getY();
                float startAng = getAngle(startX, startY);
                float endAng = getAngle(endX, endY);
                float tempAngOffset;
                if(Cx > endX){
                    tempAngOffset = startAng - endAng;
                }else{
                    tempAngOffset = endAng - startAng;

                }
                angOffset += tempAngOffset;
                Log.e(TAG, "startAng : " + startAng + " endAng : " + endAng);
                requestLayout();
                startX = endX;
                startY = endY;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    private float getAngle(float startX, float startY) {
        float x = startX - Cx;
        float y = startY - Cy;
        return (float) (Math.asin(y / Math.hypot(x,y))*180 / Math.PI);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            double d = Math.toRadians((360 / cCount) * i);
            double a = Math.toRadians(angOffset%360);
            double c = Math.toRadians(225);

            int Y = (int) (Cy + 150 * Math.sin(a-d));
            int X = (int) (Cx + 150 * Math.cos(a-d));
            Y = Y - cHeight / 2;
            X = X - cWidth / 2;
            Log.e(TAG, "Y : " + Y+" X : " + X);

            childView.layout(X, Y, X + cWidth, Y + cHeight);
            ObjectAnimator.ofFloat(childView, "rotation",(float)Math.toDegrees(a-d-c)).setDuration(0).start();

        }
    }
}
