package com.hui.huiweather.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hui.huiweather.R;
import com.hui.huiweather.db.WeatherDB;
import com.hui.huiweather.model.City;
import com.hui.huiweather.model.County;
import com.hui.huiweather.model.Province;
import com.hui.huiweather.util.HttpCallbackListener;
import com.hui.huiweather.util.HttpUtil;
import com.hui.huiweather.util.Utility;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by HUI on 2016/1/22.
 */
public class ChooseAreaActivity extends AppCompatActivity {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;

    private  ArrayAdapter<String> adapter;
    private WeatherDB weatherDB;
    private List<String> dataList = new ArrayList<String>();
    private ProgressDialog progressDialog;
    private ListView listView;
    private TextView titleText;

    /*返回的省市列表*/
    private List<Province> provinces;
    private List<City> cities;
    private List<County> counties;

    /*选中的省市*/
    private Province selectedProvince;
    private City selectedCity;

    /*当前选中的级别*/
    private int currentLevel;

    /*是否从WeatherActiviy跳转过来*/
    private boolean isFromWeatherActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_area);
        isFromWeatherActivity = getIntent().getBooleanExtra("from_weather_activity",false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        /*如果已经选择了城市而且不是从WeatherActivity跳转过来才跳转到WeatherActivity*/
        if (prefs.getBoolean("city_selected",false) && !isFromWeatherActivity) {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        listView = (ListView) findViewById(R.id.list_view);
        titleText  = (TextView) findViewById(R.id.title_text);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        weatherDB = WeatherDB.getInstance(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinces.get(position);
                    queryCityes();
                }else if(currentLevel == LEVEL_CITY) {
                    selectedCity = cities.get(position);
                    queryCounties();
                }else if (currentLevel == LEVEL_COUNTY) {
                    Intent intent = new Intent(ChooseAreaActivity.this, WeatherActivity.class);
                    String countyCode = counties.get(position).getCountyCode();
                    intent.putExtra("county_code",countyCode);
                    startActivity(intent);
                    finish();
                }
            }
        });
        queryProvinces();
    }

    /*先从数据库查询数据，如果没有再从服务器下载*/
    public void queryProvinces() {
        provinces = weatherDB.loadProvinces();
        if (provinces.size() > 0) {
            dataList.clear();
            for (Province p : provinces) {
                dataList.add(p.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText("中国");
            currentLevel = LEVEL_PROVINCE;
        } else {
            queryFromServer(null, "province");
        }

    }

    public void queryCityes() {
        cities = weatherDB.loadCities(selectedProvince.getId());
        if (cities.size() > 0) {
            dataList.clear();
            for (City c : cities) {
                dataList.add(c.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedProvince.getProvinceName());
            currentLevel = LEVEL_CITY;
        }else {
            queryFromServer(selectedProvince.getProvinceCode(), "city");
        }
    }

    public void queryCounties() {
        counties = weatherDB.loadCounties(selectedCity.getId());
        if (counties.size()>0) {
            dataList.clear();
            for (County c : counties) {
                dataList.add(c.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedCity.getCityName());
            currentLevel = LEVEL_COUNTY;
         } else {
            queryFromServer(selectedCity.getCityCode(),"county");
        }
    }

    public void queryFromServer(String code , final String type) {
        String address = "";
       if ("province".equals(type)) {
           address = "http://192.168.215.168/province";
       }else if (type.equals("city")) {
           address = "http://192.168.215.168/city";
       }else if (type.equals("county")) {
               address = "http://192.168.215.168/county";
       }
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                if("province".equals(type)) {
                    result = Utility.handleProvinceResponse(weatherDB, response);
                }else if ("city".equals(type)) {
                   result =  Utility.handleCityResponse(weatherDB,response,selectedProvince.getId());
                }else if ("county".equals(type)) {
                   result =  Utility.handleCountyResponse(weatherDB, response, selectedCity.getId());
                }

                if (result) {
                    /*回到主线程*/
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();
                            }else if ("city".equals(type)) {
                                queryCityes();
                            }else if ("county".equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /*显示进度对话框*/
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    /*关闭进度对话框*/
    public void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /*捕获Back事件根据当前级别来判断是返回城市列表还是省列表或者直接退出*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (currentLevel == LEVEL_COUNTY) {
            queryCityes();
        } if (currentLevel == LEVEL_CITY) {
            queryProvinces();
        }else {
            if (isFromWeatherActivity) {
                Intent intent = new Intent(ChooseAreaActivity.this,WeatherActivity.class);
                startActivity(intent);
            }
            finish();
        }
    }
}
