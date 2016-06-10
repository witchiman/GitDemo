package com.hui.surfaceview;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hui on 2015/12/6.
 */
public class Container {
    private List<Container> children;
    private float x=0;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    private float y = 0;
    public Container( ) {
        this.children = new ArrayList<Container>();
    }

    public void draw(Canvas canvas) {
        //canvas.save();
        canvas.translate(getX(),getY());
        drawView(canvas);
        for (Container child : children) {
            child.draw(canvas);
        }
        //canvas.restore();
    }

    public void drawView(Canvas canvas) {

    }
    public void addChildren(Container container) {
        children.add(container);
    }

    public void removeChildren(Container container) {
        children.remove(container);
    }
}
