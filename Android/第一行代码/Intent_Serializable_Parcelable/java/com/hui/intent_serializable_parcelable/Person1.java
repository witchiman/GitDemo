package com.hui.intent_serializable_parcelable;

import java.io.Serializable;

/**
 * Created by HUI on 2016/1/20.
 */
public class Person1 implements Serializable {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
