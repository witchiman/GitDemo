package com.hui.broadcast_forceoffline;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUI on 2016/1/16.
 */
public class ActivityColletion {
    public static List<AppCompatActivity> activities = new ArrayList<AppCompatActivity>();

    public static void addActiviy(AppCompatActivity activity) {
        activities.add(activity);
    }

    public static void removeActivity(AppCompatActivity activity) {
        activities.remove(activity);
    }

    public static void removeAll() {
        for(AppCompatActivity activity: activities) {
            activity.finish();
        }
    }
}
