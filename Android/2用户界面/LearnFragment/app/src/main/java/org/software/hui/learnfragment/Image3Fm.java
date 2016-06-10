package org.software.hui.learnfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by wangj on 2015/10/24.
 */
public class Image3Fm extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageView iv  = new ImageView(getActivity());
        iv.setImageResource(R.drawable.img3);
        return iv;
    }
}
