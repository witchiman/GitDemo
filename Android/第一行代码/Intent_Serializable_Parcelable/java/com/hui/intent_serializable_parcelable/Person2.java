package com.hui.intent_serializable_parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HUI on 2016/1/20.
 */
public class Person2 implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }
    public static final Creator<Person2> CREATOR = new Creator<Person2>() {
        @Override
        public Person2 createFromParcel(Parcel source) {
            Person2 person2 = new Person2();
            person2.name = source.readString();   //读取name
            person2.age = source.readInt(); //读取age
            return person2;
        }

        @Override
        public Person2[] newArray(int size) {
            return new Person2[size];
        }
    };
}
