package com.tianci.demo;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;


public class MyService extends Service {

    private static final String TAG = "MyService";
    private static final Messenger mMessenger = new Messenger(new MyHandler());
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    private static class MyHandler extends Handler  {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG,"msg = " + msg);
        }
    }
}
