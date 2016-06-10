package com.hui.gson_test;

/**
 * Created by HUI on 2016/1/19.
 */
public interface HttpCallbackListener
{
    void onFinish(String response);
    void onError(Exception e);
}
