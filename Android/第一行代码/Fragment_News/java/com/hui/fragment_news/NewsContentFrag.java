package com.hui.fragment_news;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class NewsContentFrag extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.news_content_frag,container);
        return view;
    }

    public void refresh(String title, String content) {
        View visibilityLayout = view.findViewById(R.id.visibilityLayout);
        visibilityLayout.setVisibility(View.VISIBLE);
        TextView tvTitle = (TextView) view.findViewById(R.id.news_content_title);
        TextView tvContent = (TextView) view.findViewById(R.id.news_content_content);
        tvTitle.setText(title);
        tvContent.setText(content);
    }
}
