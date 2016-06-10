package com.hui.news.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by HUI on 2016/4/24.
 */
public class NetCacheUtils {

    private static final String TAG = "NetCacheUtils";
    private  ImageView imageView;
    private  String url;
    private MemoryCacheUtils mMemoryCacheUtils;
    private LocalCacheUtils mLocalCacheUtils;

    public NetCacheUtils(LocalCacheUtils localCacheUtils, MemoryCacheUtils memoryCacheUtils) {
        this.mMemoryCacheUtils = memoryCacheUtils;
        this.mLocalCacheUtils = localCacheUtils;
    }

    public  void getBitmapFromNet(ImageView iv, String url) {
        new BitmapTask().execute(iv, url);
    }

    /**
     * 封装了Handler和线程池
     * 第一个参数是传入的参数
     * 第二个参数是进度
     * 第三个参数是返回类型
     */
    class BitmapTask extends AsyncTask<Object, String, Bitmap> {

        /*耗时方法在此执行*/
        @Override
        protected Bitmap doInBackground(Object... params) {
            imageView = (ImageView) params[0];
            url = (String) params[1];

            Bitmap bitmap = downloadBitmap(url);
            return bitmap;
        }

        /*更新进度，主线程*/
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        /*耗时方法结束后执行此方法，主线程*/
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                String bindUrl = (String) imageView.getTag();
                if (bindUrl.equals(url)) { // 确保图片设置给了正确的ImageView
                    imageView.setImageBitmap(bitmap);
                }
                mMemoryCacheUtils.setBitmapToMemory(url,bitmap);    //从网络获取图片时缓存到本地和内存
                mLocalCacheUtils.setBitmapToLocal(url, bitmap);
                Log.d(TAG, "onPostExecute:从网络获取图片");
            }
        }

        private Bitmap downloadBitmap(String url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                if (connection.getResponseCode() == 200) {
                    /*图片压缩*/
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;  //图片长宽高各缩小2倍
                    options.inPreferredConfig = Bitmap.Config.ARGB_4444; //设置图片格式
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null,options);
                    return bitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }

            return null;
        }
    }
}
