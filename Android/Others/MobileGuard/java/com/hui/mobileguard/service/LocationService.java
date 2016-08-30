package com.hui.mobileguard.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by HUI on 2016/5/9.
 */
public class LocationService extends Service {

    private static final String TAG = "LocationService";
    private LocationManager lm;
    private SharedPreferences config;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        config = getSharedPreferences("CONFIG", MODE_PRIVATE);
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        /*请求最佳provider*/
        Criteria criteria = new Criteria();
        criteria.setCostAllowed(true);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = lm.getBestProvider(criteria, true);

        /*自动添加的权限检查*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        lm.requestLocationUpdates(provider, 0, 0, new MyLocationListener());
    }

    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            config.edit().putString("location","longitude" + location.getLongitude()
                + ",latitude" + location.getLatitude()).commit();
            Log.d(TAG, "onLocationChanged: " + location.getLatitude()+"|"+ location.getLongitude());
            stopSelf();   //收到位置信息停止服务
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
