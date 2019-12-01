package com.darren.proxystub.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.darren.proxystub.Mp3Binder;

public class Mp3RemoteService extends Service{

    private Binder mBinder;

    @Override
    public void onCreate() {
        mBinder = new Mp3Binder(getApplicationContext());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
