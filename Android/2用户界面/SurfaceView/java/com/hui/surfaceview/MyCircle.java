package com.hui.surfaceview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Hui on 2015/12/6.
 */
public class MyCircle extends Container {
    private Paint paint= null;
    public MyCircle() {
        paint = new Paint();
        paint.setColor(Color.YELLOW);
    }

    @Override
    public void drawView(Canvas canvas) {
        super.drawView(canvas);
        canvas.drawCircle(50, 50, 50, paint);
    }
}
