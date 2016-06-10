package com.hui.multouch;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        root = (RelativeLayout) findViewById(R.id.container);
        iv = (ImageView) findViewById(R.id.iv);
        root.setOnTouchListener(new View.OnTouchListener() {

            float currentDistance = 0;
            float lastDistance = -1;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    System.out.println("down");
                    break;
                case MotionEvent.ACTION_MOVE:
                    System.out.println("move");

                   // System.out.println(String.format("x: %f,y: %f",event.getX(),event.getY()));

                   /* 图片移动*/
                  /*  RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) iv.getLayoutParams();
                    lp.leftMargin = (int) event.getX() ;
                    lp.topMargin = (int) event.getY();
                    iv.setLayoutParams(lp)*/;

                   /* 多点触控*/
                    // System.out.println("多点触屏的点数:"+ event.getPointerCount());
                    //获取多个触摸点的坐标
                    /*System.out.println(String.format("x1: %f,y1: %f, x2: %f, y2： %f", event.getX(0),event.getY(0),
                            event.getX(1),event.getY(1));*/

                   /* 图片缩放*/
                   if(event.getPointerCount() >=2) { //判断是否两个以上的点触控
                       float offsetX = event.getX(0) - event.getX(1);
                       float offsetY = event.getY(0) - event.getY(1);
                       currentDistance = (float) Math.sqrt(offsetX*offsetX + offsetY*offsetY);
                       RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) iv.getLayoutParams();
                       if(lastDistance <=0 ){
                           lastDistance = currentDistance;
                       }else {
                           if(currentDistance-lastDistance > 5) {
                               lp.width = (int) (1.1f*iv.getWidth());
                               lp.height = (int) (1.1f*iv.getHeight());
                           }else if(currentDistance - lastDistance < -5) {
                               lp.width = (int) (0.9f*iv.getWidth());
                               lp.height = (int) (0.9f*iv.getHeight());
                           }
                           iv.setLayoutParams(lp);
                           lastDistance = currentDistance;
                       }
                   }

                    break;
                case MotionEvent.ACTION_UP:
                    System.out.println("up");
                    break;
                }
                return true;  //返回值为ture时down的后续事件才能触发
            }
        });

    }

    private ImageView iv;
    private RelativeLayout root;
}
