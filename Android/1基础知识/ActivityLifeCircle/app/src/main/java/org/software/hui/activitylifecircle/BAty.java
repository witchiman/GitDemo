package org.software.hui.activitylifecircle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class BAty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baty);
        System.out.println("B onCreate ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("B onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("B onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("B onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("B onStop");;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("B onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("B onRestart");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_baty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
