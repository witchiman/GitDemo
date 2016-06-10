package com.hui.news.utils;

import android.content.Context;

/**
 * 用以dp和px之间的转换
 * Created by HUI on 2016/4/25.
 */
public class DensityUtils {
    public static int dpToPx(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp*density + 0.5f);        //加上0.5f来四舍五入
    }

    public static float pxToDp(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return px/density;
    }
}
