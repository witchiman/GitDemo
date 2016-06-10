package com.hui.volley;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
        * Volley是Android平台网络通信库：更快。更简单。更健壮 volley提供的功能：
        * 1.JSON、图片（异步）
        * 2.网络请求的排序
        * 3.网络请求的优先级处理
        * 4.缓存
        * 5.多级别的取消请求
        * 6.与Activity生命周期联动
        * 获取Volley git clone
        * https://android.googlesource.com/platform/frameworks/volley
        *
        */

public class MainActivity extends AppCompatActivity {
    private String imgUrl = "http://img.woyaogexing.com/touxiang/nv/20140212/8f18e9ab749a157d!200x200.jpg";
    private ImageView iv1;
    private NetworkImageView iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (NetworkImageView) findViewById(R.id.iv2);

        //getJSONByVolley();
        loadImageByVolley();
        getNetworkImageView();
    }

    //获取JSON字符串
    public void getJSONByVolley() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String jsonDataUrl = "http://fanyi.youdao.com/openapi.do?keyfrom=witchiman&key=777555096&type=data&doctype=json&version=1.1&q=good";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, jsonDataUrl,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("MainActivity",jsonObject.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("MainActivity","出错啦！");
            }
        });
        queue.add(jsonObjectRequest);
    }

    //获取图片
    public void loadImageByVolley() {
        RequestQueue queue = Volley.newRequestQueue(this);
        final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(20);
        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                return lruCache.get(s);
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {
                lruCache.put(s,bitmap);
            }
        };

        ImageLoader imgLoader = new ImageLoader(queue, imageCache);
        ImageLoader.ImageListener listener = imgLoader.getImageListener(iv1, R.drawable.img,R.mipmap.ic_launcher);
        imgLoader.get(imgUrl, listener);

    }

    public void getNetworkImageView() {
        RequestQueue queue = Volley.newRequestQueue(this);
        final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(20);
        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                return lruCache.get(s);
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {
                lruCache.put(s, bitmap);
            }
        };

        ImageLoader imageLoader = new ImageLoader(queue, imageCache);
        iv2.setTag("url");
        iv2.setImageUrl(imgUrl, imageLoader);
    }
}
