package com.hui.listview_fruits;

/**
 * Created by HUI on 2016/1/15.
 */
public class Fruit {
    private String fruitName;
    private int imgId;

    public Fruit(String fruitName, int imgId) {
        this.fruitName = fruitName;
        this.imgId = imgId;
    }

    public String getFruitName() {
        return fruitName;
    }

    public int getImgId() {
        return imgId;
    }
}
