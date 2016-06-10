package com.hui.news.utils.bitmap;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.hui.news.R;

/**
 * 自定义三级缓存
 * Created by HUI on 2016/4/24.
 */
public class MyBitmapUtils {
    LocalCacheUtils mLocalCacheUtils;
    MemoryCacheUtils mMemoryCacheUtils;
    NetCacheUtils mNetCacheUtils;

    public MyBitmapUtils() {
        mMemoryCacheUtils = new MemoryCacheUtils();
        mLocalCacheUtils = new LocalCacheUtils();
        mNetCacheUtils = new NetCacheUtils(mLocalCacheUtils, mMemoryCacheUtils);
    }

    public void display(ImageView iv, String url) {
        iv.setTag(url);   // 将url和ImageView绑定，必须在UI线程里调用
        iv.setImageResource(R.drawable.news_pic_default);  //设置默认图片
        Bitmap bitmap = null;

        //内存
        bitmap = mMemoryCacheUtils.getBitmapFromMemory(url);
        if (bitmap != null) {
            iv.setImageBitmap(bitmap);
            return;
        }
        //本地
        bitmap = mLocalCacheUtils.getBitmapFromLocal(url);
        if (bitmap != null) {
            iv.setImageBitmap(bitmap);
            mMemoryCacheUtils.setBitmapToMemory(url, bitmap);
            return;
        }
        //网络
        mNetCacheUtils.getBitmapFromNet(iv, url);
    }
}
