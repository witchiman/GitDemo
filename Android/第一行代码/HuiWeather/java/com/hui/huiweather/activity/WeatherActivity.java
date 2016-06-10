package com.hui.huiweather.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.hui.huiweather.R;
import com.hui.huiweather.service.AutoUpdateService;
import com.hui.huiweather.util.HttpCallbackListener;
import com.hui.huiweather.util.HttpUtil;
import com.hui.huiweather.util.Utility;

/**
 * Created by HUI on 2016/1/22.
 */
public class WeatherActivity extends AppCompatActivity {
    private LinearLayout weatherInfoLayout;
    private TextView cityNameText;        //用于显示城市名
    private TextView publishText;       //用于显示发布时间
    private TextView weatherDespText;   //用于显示天气描述信息
    private TextView temp1Text;         //用于显示气温1
    private TextView temp2Text;         //用于显示气温2
    private TextView currentDateText;   //用于显示当前日期

    private Button switchButton;   //切换城市
    private Button refreshButton;   //刷新天气

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameText = (TextView) findViewById(R.id.city_name);
        publishText = (TextView) findViewById(R.id.publish_text);
        weatherDespText = (TextView) findViewById(R.id.weather_desp);
        temp1Text = (TextView) findViewById(R.id.temp1);
        temp2Text = (TextView) findViewById(R.id.temp2);
        currentDateText = (TextView) findViewById(R.id.current_date);
        String countyCode = getIntent().getStringExtra("county_code");
        if (!TextUtils.isEmpty(countyCode)) {
            //有县级代号就去查询天气
            publishText.setText("同步中...");
            weatherInfoLayout.setVisibility(View.VISIBLE);
            cityNameText.setVisibility(View.VISIBLE);
            queryWeatherCode(countyCode);
        }else {
            //没有县级代码显示本地天气
            showWeather();
        }

        switchButton = (Button) findViewById(R.id.switch_city);
        refreshButton = (Button) findViewById(R.id.refresh_weather);
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity",true);
                startActivity(intent);
                finish();
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishText.setText("同步中...");
                 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(
                         WeatherActivity.this);
                String weatherCode = prefs.getString("weather_code","");
                if (!TextUtils.isEmpty(weatherCode)) {
                    queryWeatherCode(weatherCode);
                }
            }
        });

        /*加入自动更新*/
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
    }

    private void queryWeatherCode(String countyCode) {
        String address = "http://192.168.215.168/weathercode";
        queryFromServer(address,"countyCode");
    }

    private void queryWeatherInfo(String weatherCode) {
        String address  = "http://192.168.215.168/weatherinfo";
        queryFromServer(address, "weatherCode");
    }

    private void queryFromServer(String address, final String type) {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if ("countyCode".equals(type)) {
                    if (!TextUtils.isEmpty(response)) {
                        String[] array = response.split("\\|");
                        if (array != null && array.length == 2) {
                            queryWeatherInfo(array[1]);
                        }
                    }
                } else if ("weatherCode".equals(type)) {
                    Utility.handleWeatherResponse(WeatherActivity.this, response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishText.setText("同步失败");
                    }
                });
            }
        });
    }
    /*从SharedPreferences中读取数据并显示到界面上*/
    private void showWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText(prefs.getString("city_name",""));
        temp1Text.setText(prefs.getString("temp1",""));
        temp2Text.setText(prefs.getString("temp2",""));
        weatherDespText.setText(prefs.getString("weather_desp",""));
        publishText.setText(prefs.getString("publish_time",""));
        currentDateText.setText(prefs.getString("current_date",""));
        weatherInfoLayout.setVisibility(View.VISIBLE);
        cityNameText.setVisibility(View.VISIBLE);
    }

}
