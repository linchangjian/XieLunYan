package com.aniu.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * TODO: document your custom view class.
 */
public class MyView extends View {
    private Paint paint;
    private int widthCenter;
    private  int heightCenter;
    public MyView(Context context) {
        super(context);
        init(null, 0);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);


    }

    private void init(AttributeSet attrs, int defStyle) {
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        widthCenter = width / 2;
        heightCenter = height / 2;
        paint = new Paint();
    }


    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(0.1f);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        //第三象限的四分之一圆
        RectF rect0 = new RectF(0, 0, width, height);

        canvas.drawArc(rect0,
                90,//开始角度
                90, //扫过的角度
                true, //是否使用中心
                paint);

        //第四象限的半圆
        RectF rect = new RectF(width / 2 - (width / 4), height/2, width-(width/4), height);

        canvas.drawArc(rect, //弧线所使用的矩形区域大小
                90,  //开始角度
                -180, //扫过的角度
                true, //是否使用中心
                paint);

        //第二象限的四分之一圆
        canvas.drawArc(rect0, //弧线所使用的矩形区域大小
                -90,  //开始角度
                -90, //扫过的角度
                true, //是否使用中心
                paint);

        //第二象限的小半圆
        paint.setColor(Color.RED);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
        RectF rect2 = new RectF(width/4, 0, width/2+width/4, height/2);
        paint.setStrokeWidth(1.5f);
        canvas.drawArc(rect2, //弧线所使用的矩形区域大小
                90,  //开始角度
                180, //扫过的角度
                true, //是否使用中心
                paint);
    }

    @Override
    public void addOnLayoutChangeListener(OnLayoutChangeListener listener) {
        super.addOnLayoutChangeListener(listener);
    }

    private float getAngle(float startX, float startY) {
        float x = startX - widthCenter;
        float y = startY - heightCenter;
        return (float) (Math.asin(y / Math.hypot(x,y))*180 / Math.PI);
    }


}
