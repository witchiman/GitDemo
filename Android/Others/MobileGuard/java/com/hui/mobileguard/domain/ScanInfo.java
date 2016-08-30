package com.hui.mobileguard.domain;

/**
 * Created by HUI on 2016/6/10.
 */
public class ScanInfo  {
    private String appName;
    private String packageName;
    private String desc;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isVirus() {
        return isVirus;
    }

    public void setVirus(boolean virus) {
        isVirus = virus;
    }

    private boolean isVirus;


}
