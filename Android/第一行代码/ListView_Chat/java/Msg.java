package com.hui.listview_chat;

/**
 * Created by HUI on 2016/1/16.
 */
public class Msg {
    public static final int TYPE_SEND = 0;
    public static final int TYPE_RECIEVE = 1;
    private String content;
    private int type;

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
