package com.hui.drawapi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Hui on 2015/11/25.
 */
public class RotateRect extends View{

    public RotateRect(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    public RotateRect(Context context) {
        super(context);
        initPaint();
    }

    public RotateRect(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.save();

        canvas.translate(200, 200);
        canvas.rotate(degree++, 50, 50);
        canvas.drawRect(0, 0, 100, 100, p);

        canvas.restore();

        invalidate();

    }

    public void initPaint() {
        p = new Paint();
        p.setColor(Color.GREEN);
    }

    private Paint p;
    private float degree = 0;
}
