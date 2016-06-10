package com.hui.news.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 通过SharedPreferences设置属性
 * Created by HUI on 2016/4/7.
 */
public class PrefUtils {
    private static String PREF_NAME = "config";

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        boolean result = preferences.getBoolean(key, defaultValue);
        return result;
    }

    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(key, value).commit();
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        String result = preferences.getString(key, defaultValue);
        return result;
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).commit();
    }
}
