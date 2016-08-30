package com.hui.databingding_recycleview;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

/**
 * Created by HUI on 2016/8/30.
 */
public class People extends BaseObservable {
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> gender = new ObservableField<>();
    public final ObservableInt age = new ObservableInt();

}
