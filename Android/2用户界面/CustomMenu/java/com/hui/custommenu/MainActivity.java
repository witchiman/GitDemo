package com.hui.custommenu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class MainActivity extends FragmentActivity {
    private  LeftMenu leftMenu;
    private  MainUI mainUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        mainUI = new MainUI(this);
        leftMenu = new LeftMenu();
        setContentView(mainUI);
        getSupportFragmentManager().beginTransaction().add(MainUI.LEFT_ID, leftMenu).commit();
    }

}
