package com.hui.mobileguard.domain;

import android.graphics.drawable.Drawable;

/**
 * Created by HUI on 2016/6/4.
 */
public class AppInfo {
    private String appName;
    private long appSize;
    private Drawable appLogo;
    private boolean isUserApp;
    private boolean isInRom;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    private String packageName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public long getAppSize() {
        return appSize;
    }

    public void setAppSize(long appSize) {
        this.appSize = appSize;
    }

    public Drawable getAppLogo() {
        return appLogo;
    }

    public void setAppLogo(Drawable appLogo) {
        this.appLogo = appLogo;
    }

    public boolean isUserApp() {
        return isUserApp;
    }

    public void setUserApp(boolean userApp) {
        isUserApp = userApp;
    }

    public boolean isInRom() {
        return isInRom;
    }

    public void setInRom(boolean inRom) {
        isInRom = inRom;
    }
}
