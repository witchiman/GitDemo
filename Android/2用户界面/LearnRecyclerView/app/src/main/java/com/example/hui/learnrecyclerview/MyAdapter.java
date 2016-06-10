package com.example.hui.learnrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Hui on 2015/11/23.
 */
class MyAdapter extends RecyclerView.Adapter {
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvContent;
        private View root;
        public ViewHolder(View root) {
            super(root);
            this.root = root;
            tvTitle = (TextView) root.findViewById(R.id.tvTitle);
            tvContent = (TextView) root.findViewById(R.id.tvContent);
        }

        public TextView getTvContent() {
            return tvContent;
        }

        public TextView getTvTitle() {
            return tvTitle;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_cell, null ));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder vh = (ViewHolder) viewHolder;
       /* TextView tv = vh.getTextView();
        tv.setText(data[i] + i);*/
        vh.getTvTitle().setText(data[i].title);
        vh.getTvContent().setText(data[i].content);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

     private  CellData[] data = new CellData[]{new CellData("Jim", "A fucking guy!"), new CellData("Tom", "He may be funny!"),
     new CellData("Lucy", "Certainly,she is not a whore!"), new CellData("Tom", "He may be funny!"),
             new CellData("Lucy", "Certainly,she is not a whore!"), new CellData("Tom", "He may be funny!"),
             new CellData("Lucy", "Certainly,she is not a whore!"), new CellData("Tom", "He may be funny!"),
             new CellData("Lucy", "Certainly,she is not a whore!"), new CellData("Tom", "He may be funny!"),
             new CellData("Lucy", "Certainly,she is not a whore!"), new CellData("Tom", "He may be funny!"),
             new CellData("Lucy", "Certainly,she is not a whore!"), new CellData("Tom", "He may be funny!"),
             new CellData("Lucy", "Certainly,she is not a whore!"), new CellData("Tom", "He may be funny!"),
             new CellData("Lucy", "Certainly,she is not a whore!"), new CellData("Tom", "He may be funny!"),
             new CellData("Lucy", "Certainly,she is not a whore!"), new CellData("Tom", "He may be funny!"),
             new CellData("Lucy", "Certainly,she is not a whore!"), new CellData("Tom", "He may be funny!"),
             new CellData("Lucy", "Certainly,she is not a whore!"), new CellData("Tom", "He may be funny!"),
             new CellData("Lucy", "Certainly,she is not a whore!"), new CellData("Tom", "He may be funny!"),
             new CellData("Lucy", "Certainly,she is not a whore!"), new CellData("Tom", "He may be funny!"),
             new CellData("Lucy", "Certainly,she is not a whore!")};
    //private String[] data = new String[]{"Jim", "Tom", "Green", "Lucy", "Damn"};
}
