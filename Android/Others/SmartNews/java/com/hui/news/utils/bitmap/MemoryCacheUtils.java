package com.hui.news.utils.bitmap;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * Created by HUI on 2016/4/24.
 */
public class MemoryCacheUtils {
    private static final String TAG = "MemoryCacheUtils";
    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCacheUtils() {
        long maxSize = Runtime.getRuntime().maxMemory();   //获取最大内存
        //Log.d(TAG, "最大内存是 : " + maxSize);
        mMemoryCache = new LruCache<String,Bitmap>((int) (maxSize/8)) {
            @Override
            protected int sizeOf(String key, Bitmap value) {  //实现此方法返回Bitmap对象的大小
                return value.getByteCount(); 
            }
        };
    }

    public Bitmap getBitmapFromMemory(String url) {
        Log.d(TAG, "getBitmapFromMemory: 从内存获取图片");
        return mMemoryCache.get(url);
    }

    public void setBitmapToMemory(String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);
    }
}
