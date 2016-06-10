package com.hui.gps_geocoding;

/**
 * Created by HUI on 2016/1/20.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
