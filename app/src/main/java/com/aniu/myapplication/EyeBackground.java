package com.aniu.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class EyeBackground extends View {
    private final int EyeWidth = 6;
    private final int OutsideRound = 3;
    private Paint paint;
    public EyeBackground(Context context) {
        super(context);
        init(null, 0);
    }

    public EyeBackground(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public EyeBackground(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        paint = new Paint();
        paint.setColor(Color.RED);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = canvas.getHeight();
        int width = canvas.getWidth();
        int centerWidth = width/2;
        int centerHeight = height/2;
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        RectF rect = new RectF(0,0,width,height);
        canvas.drawArc(rect,
                0,//开始角度
                360, //扫过的角度
                true, //是否使用中心
                paint);
        paint.setColor(Color.BLACK);
        //取中间的位置
        RectF rect0 = new RectF(centerWidth - width/EyeWidth,centerHeight-height/EyeWidth,centerWidth+width/EyeWidth,centerHeight+height/EyeWidth);
        canvas.drawArc(rect0,
                0,//开始角度
                360, //扫过的角度
                true, //是否使用中心
                paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        RectF rect1 = new RectF(centerWidth - width/OutsideRound,centerHeight-height/OutsideRound,centerWidth+width/OutsideRound,centerHeight+height/OutsideRound);

        canvas.drawArc(rect1,
                0,//开始角度
                360, //扫过的角度
                true, //是否使用中心
                paint);

    }


}
