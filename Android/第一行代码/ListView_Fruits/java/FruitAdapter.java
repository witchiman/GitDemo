package com.hui.listview_fruits;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.List;

/**
 * Created by HUI on 2016/1/15.
 */
public class FruitAdapter extends ArrayAdapter<Fruit> {
    private int resourceId;
    public FruitAdapter(Context context, int resource, List<Fruit> objects) {
        super(context, resource,objects);
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ImageHolder imageHolder;
        Fruit fruit = getItem(position);
        if(convertView == null) {  //判断convertView是否为空，提高运行效率
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            imageHolder = new ImageHolder();
            imageHolder.imageView = (ImageView) view.findViewById(R.id.img);
            imageHolder.textView = (TextView) view.findViewById(R.id.tv);
            view.setTag(imageHolder);

        }else {
            view = convertView;
            imageHolder = (ImageHolder) view.getTag();
        }

        imageHolder.imageView.setImageResource(fruit.getImgId());
        imageHolder.textView.setText(fruit.getFruitName());
        return view;
    }

    private class ImageHolder {
        ImageView imageView;
        TextView textView;
    }
}
