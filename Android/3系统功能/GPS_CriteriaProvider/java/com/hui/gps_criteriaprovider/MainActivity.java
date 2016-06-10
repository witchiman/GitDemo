package com.hui.gps_criteriaprovider;

import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        lv = (ListView) findViewById(R.id.lv);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //设置过滤条件
        Criteria cri = new Criteria();
        //不免费
        cri.setCostAllowed(false);
        cri.setAltitudeRequired(true);
        cri.setBearingRequired(true);

        List<String> providers = mLocationManager.getProviders(cri,true);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                providers);
        lv.setAdapter(adapter);

    }

    ListView lv;
    LocationManager mLocationManager;

}
