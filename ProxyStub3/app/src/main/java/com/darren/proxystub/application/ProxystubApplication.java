package com.darren.proxystub.application;

import android.app.Application;
import android.content.Context;

public class ProxystubApplication extends Application {

    public static final String TAG = "Proxystub";
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
