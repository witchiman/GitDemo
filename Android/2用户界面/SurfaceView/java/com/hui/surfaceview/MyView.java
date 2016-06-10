package com.hui.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MyView extends SurfaceView implements SurfaceHolder.Callback {

    private Paint paint =  null;
    public MyView(Context context) {
        super(context);
        getHolder().addCallback(this);
        paint = new Paint();
        paint.setColor(Color.GREEN);
    }


    public void draw() {
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(Color.WHITE);
        canvas.drawRect(0, 0, 200, 200,paint);
        canvas.save();   //保存状态
        canvas.rotate(90, getWidth()/2, getHeight()/2);
        paint.setColor(Color.BLACK);
        canvas.drawLine(0, getHeight()/2, getWidth(), getHeight(), paint);
        canvas.restore();
        canvas.drawLine(0, getHeight()/3, getWidth(), getHeight(), paint);
        getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        draw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
