package com.tianci.binder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyService extends Service implements IService {
    private static final String TAG = "MyService";
    private final IBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void func1() {
        Log.d(TAG,"func1");
    }

    @Override
    public void fun2() {
        Log.d(TAG,"func2");
    }

    public class MyBinder extends Binder {
        IService getService(){
            return MyService.this;
        }
    }
}
