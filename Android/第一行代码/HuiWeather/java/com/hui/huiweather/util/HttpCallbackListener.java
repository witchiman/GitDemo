package com.hui.huiweather.util;

public interface HttpCallbackListener {

	void onFinish(String response);

	void onError(Exception e);

}
