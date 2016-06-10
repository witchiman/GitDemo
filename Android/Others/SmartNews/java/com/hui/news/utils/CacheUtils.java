package com.hui.news.utils;

import android.content.Context;

/**
 * Created by HUI on 2016/4/22.
 */
public class CacheUtils {
    public static void setCache(String key, String value, Context context) {
      PrefUtils.setString(context, key, value);
    }

    public static String getCache(String key, String value,Context context) {
     return PrefUtils.getString(context, key, value);
    }
}
