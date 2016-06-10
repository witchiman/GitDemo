package com.hui.sensor_orientation;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private SensorManager manager;
    private ImageView imgCopass;
    private float[] accelerometerValues = new float[3];
    private float[] magneticValues = new float[3];
    private float lastRotateDegree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgCopass = (ImageView) findViewById(R.id.compass);
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor accelerometerSensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magneticSensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        manager.registerListener(listener, accelerometerSensor,SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(listener, magneticSensor,SensorManager.SENSOR_DELAY_GAME);

    }

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accelerometerValues = event.values.clone();
            }else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                magneticValues = event.values.clone();
            }

            float[] values = new float[3];
            float[] R = new float[9];
            SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticValues);
            SensorManager.getOrientation(R, values);

            //将计算出的旋转角度取反，用于旋转指南针背景图片
            float rotateDegree = -(float) Math.toDegrees(values[0]);
            if(Math.abs(rotateDegree - lastRotateDegree) >1 ) {
                RotateAnimation animation = new RotateAnimation(lastRotateDegree,rotateDegree,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setFillAfter(true); //动画执行后停留在最后一帧
                imgCopass.startAnimation(animation);
                lastRotateDegree = rotateDegree;
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(manager != null) {
            manager.unregisterListener(listener);
        }
    }
}
