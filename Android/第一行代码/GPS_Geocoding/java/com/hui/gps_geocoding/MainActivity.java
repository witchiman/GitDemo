package com.hui.gps_geocoding;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/*不知道为什么部署到手机无法获取数据*/
public class MainActivity extends AppCompatActivity {
    private LocationManager locationManager;
    private TextView tv;
    private String provider;

    public static final int SHOW_LOCATION = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_LOCATION:
                    tv.setText(msg.obj.toString());
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {

            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(MainActivity.this, "No location providers to use", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            //showLocation(location);
            showLocationByGeoCoding(location);
        }

        /*添加一个监听器，时间间隔是3秒，时间间隔是1米*/
        locationManager.requestLocationUpdates(provider, 3000, 1, listener);


    }

    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //showLocation(location);
            showLocationByGeoCoding(location);
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
    };


    private void showLocationByGeoCoding(Location location) {
        /*拼接URL,谷歌服务无法正常连接*/
        /*StringBuilder url = new StringBuilder();
        url.append("http://maps.googleapis.com/maps/api/geocode/json?latlng=");
        url.append(location.getLatitude()).append(",");
        url.append(location.getLongitude()).append("&sensor=false");*/

         String address = "http://192.168.215.168/location.json";
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    //取出resluts节点下的位置信息
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    Log.d("MainActivity","jsonArray's length is " + jsonArray.length());
                    if (jsonArray.length() > 0) {
                        JSONObject subObject = jsonArray.getJSONObject(0);
                        //取出格式化后的信息
                        String address = subObject.getString("formatted_address");
                        Log.d("MainActivity",address);
                        Message message = new Message();
                        message.what = SHOW_LOCATION;
                        message.obj = address;
                        handler.sendMessage(message);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void showLocation(Location location) {
        tv.setText("纬度信息：" + location.getLatitude() + "\n" +
                "经度信息" + location.getLongitude());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.removeUpdates(listener);
        }
    }
}
