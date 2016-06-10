package com.hui.drawerlayoutusing;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ContentFragment extends Fragment {
    private  TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_content_fragment, container, false);
        tv = (TextView) view.findViewById(R.id.tv);
        String text = getArguments().getString("text");
        tv.setText(text);
        return view;
    }
}
