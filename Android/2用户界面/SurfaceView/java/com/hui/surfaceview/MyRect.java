package com.hui.surfaceview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Hui on 2015/12/6.
 */
public class MyRect extends Container {
    private Paint paint = null;

    public MyRect() {
        paint = new Paint();
        paint.setColor(Color.RED);
    }

    @Override
    public void drawView(Canvas canvas) {
        super.drawView(canvas);
        canvas.drawRect(0, 0, 100, 100, paint);
        if(getY() <= canvas.getHeight()) {
            setY(getY() +20);
        }else {
            setY(0);
        }
    }
}
