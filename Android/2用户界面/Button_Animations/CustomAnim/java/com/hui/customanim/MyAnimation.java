package com.hui.customanim;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Hui on 2015/11/27.
 */
public class MyAnimation extends Animation{
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        t.getMatrix().setTranslate((float) (Math.sin(interpolatedTime*10)*80), 0);
    }
}
