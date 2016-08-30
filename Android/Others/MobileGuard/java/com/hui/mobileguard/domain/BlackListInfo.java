package com.hui.mobileguard.domain;

/**
 * Created by HUI on 2016/5/21.
 */
public class BlackListInfo {
    String number;
    int mode;

    public String getNumber() {
        return number;
    }

    public int getMode() {
        return mode;
    }

    public void setNumber(String number) {

        this.number = number;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
