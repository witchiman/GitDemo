package com.hui.huiweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.hui.huiweather.db.WeatherDB;
import com.hui.huiweather.model.City;
import com.hui.huiweather.model.County;
import com.hui.huiweather.model.Province;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HUI on 2016/1/22.
 */
public class Utility {
    public synchronized static boolean handleProvinceResponse(WeatherDB weatherDB,String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length>0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceName(array[1]);
                    province.setProvinceCode(array[0]);
                   /*将解析出来的内容存储到数据库*/
                    weatherDB.saveProvince(province);
                }
            }
            return true;
        }
        return false;
    }

    public  static boolean handleCityResponse(WeatherDB weatherDB, String response,
            int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length>0) {
                for (String c : allCities) {
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityName(array[1]);
                    city.setCityCode(array[0]);
                    city.setProvinceId(provinceId);
                    weatherDB.saveCity(city);
                }
            }
            return true;
        }

        return false;
    }

    public  static boolean handleCountyResponse(WeatherDB weatherDB, String response,
                int cityId ) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCounties = response.split(",");
            if (allCounties!=null && allCounties.length>0) {
                for (String c : allCounties) {
                    String[] array  = c.split("\\|");
                    County county = new County();
                    county.setCountyName(array[1]);
                    county.setCountyCode(array[0]);
                    county.setCityId(cityId);
                    weatherDB.saveCounty(county);
                }
            }
            return true;
        }
        return false;
    }
    /*解析服务器返回的JSON数据*/
    public static void handleWeatherResponse(Context context, String response) {
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDesp = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("ptime");
            saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishTime);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //解析完后将服务器返回的数据保存到SharedPreferces中
    public static void saveWeatherInfo(Context context, String cityName, String weatherCode, String temp1,
                                       String temp2, String weatherDesp, String publishTime) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather_desp", weatherDesp);
        editor.putString("publish_time", publishTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年M月d日");
        editor.putString("current_date",format.format(new Date()));
        editor.commit();
    }
}
