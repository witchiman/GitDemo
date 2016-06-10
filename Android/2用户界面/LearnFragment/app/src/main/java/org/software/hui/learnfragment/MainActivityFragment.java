package org.software.hui.learnfragment;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);
        rootView.findViewById(R.id.btnAnotherFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().
                        addToBackStack(null).
                        replace(R.id.fragment, new AnotherFragment()).
                        commit();
            }
        });

        rootView.findViewById(R.id.btnStartSliderActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SliderActivity.class));
            }
        });

        rootView.findViewById(R.id.btnTabsActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Tabs.class));
            }
        });
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("mainfragment onDestroy");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("mainfragment onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("mainfragment onDestroyView");
    }
}
