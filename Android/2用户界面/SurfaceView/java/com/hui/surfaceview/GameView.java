package com.hui.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Hui on 2015/12/6.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private Container container;
    private MyRect myRect;
    private MyCircle myCircle;
    private Timer timer;
    private TimerTask task;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        container = new Container();
        myRect = new MyRect();
        myCircle = new MyCircle();
        myRect.addChildren(myCircle);
        container.addChildren(myRect);
    }

    public void startTimer() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                draw();
            }
        };
        timer.schedule(task, 100, 100);
    }

    public void stopTimer() {
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void draw() {
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(Color.WHITE);
        container.draw(canvas);
        getHolder().unlockCanvasAndPost(canvas);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startTimer();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopTimer();
    }
}
