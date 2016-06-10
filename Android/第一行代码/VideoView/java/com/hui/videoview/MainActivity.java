package com.hui.videoview;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.VideoView;

import java.io.File;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private VideoView vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vv = (VideoView) findViewById(R.id.video_view);

        findViewById(R.id.button_play).setOnClickListener(this);
        findViewById(R.id.button_pause).setOnClickListener(this);
        findViewById(R.id.button_replay).setOnClickListener(this);

        initViedeoPath();

    }

    public void initViedeoPath() {
        File file = new File(Environment.getExternalStorageDirectory(),"1.mp4"); //VideoView只支持3GP、AVI和MP4
        System.out.println(file.getPath());
        vv.setVideoPath(file.getPath());  //指定视频文件的目录
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_play:
                if(!vv.isPlaying()) {
                    vv.start();
                }
                break;
            case R.id.button_pause:
                if (vv.isPlaying()) {
                    vv.pause();
                }
                break;
            case R.id.button_replay:
                if (vv.isPlaying()) {
                    vv.resume();   //重新播放
                }
                break;
            default:
                break;
        }
    }
}
