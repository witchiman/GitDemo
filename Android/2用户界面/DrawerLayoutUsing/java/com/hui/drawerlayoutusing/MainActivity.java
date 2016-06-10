package com.hui.drawerlayoutusing;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private DrawerLayout drawerLayout ;
    private ListView drawerList;
    private ArrayList<String> menuList;
    private ArrayAdapter<String> adapter;

    private ActionBarDrawerToggle mDrawerTogglle;
    private String mTitle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTitle = (String) getTitle();
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        menuList = new ArrayList<String>();
        for(int i=0; i< 5; i++) {
            menuList.add("条目： " + i);
        }
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menuList);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(this);

        mDrawerTogglle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                toolbar.setTitle("请选择");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);                 ;
                toolbar.setTitle(mTitle);
            }
        };

        drawerLayout.setDrawerListener(mDrawerTogglle);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isDrawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_websearch).setVisible(!isDrawerOpen);
        System.out.println("the drawer is open");
        return super.onPrepareOptionsMenu(menu);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.action_websearch) {
                Intent i = new Intent();
                i.setAction("android.intent.action.VIEW");
                Uri uri = Uri.parse("http://www.baidu.com");
                i.setData(uri);
                startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //动态插入一个Fragment到FrameLayout中
        Fragment contentFragment  = new ContentFragment();
        Bundle bundle  = new Bundle();
        bundle.putString("text", menuList.get(position));
        contentFragment.setArguments(bundle);

        FragmentManager fm =  getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        drawerLayout.closeDrawer(drawerList);
    }
}
