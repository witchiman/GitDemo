package org.software.hui.learncontext;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Created by wangj on 2015/10/9.
 */
/*全局上下文*/
public class App extends Application {
    private String str = "Default Value";

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("App create!");  //启动时执行，在Activity的onCreate()执行之前执行
    }

    @Override
    public void onTerminate() {
        super.onTerminate();   //一般情况不调用，在模拟环境下才调用。
        System.out.println("App terminate!");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.out.println("App lowMemory!");  //低内存时执行。;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);         //内存清理时执行
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);  //配置发生改变时执行
    }
}
