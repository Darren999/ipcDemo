package com.darren.proxystub.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.darren.proxystub.R;

public class Mp3RemoteService extends Service implements Runnable{

    private Binder mBinder;
    private Thread thread;
    private Context mContext;
    private MediaPlayer mMediaPlayer;

    public static EventHandler handler;

    private static final String MY_S_EVENT = "com.darren.proxystub.M_S_EVENT";
    @Override
    public void onCreate() {

        mContext = this;
        mBinder = new Mp3Binder(getApplicationContext());

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void run() {
        Looper.prepare();

        handler = new EventHandler(Looper.myLooper());
        Looper.loop();
    }

    class EventHandler extends Handler{
        public EventHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            String cmd = (String)msg.obj;

            if(cmd.equals("play")){
                if(mMediaPlayer == null){
                    mMediaPlayer = MediaPlayer.create(Mp3RemoteService.this, R.raw.eason);
                }

                Intent intent = new Intent();
                intent.setAction(MY_S_EVENT);
                intent.setPackage("com.darren.proxystub");
                intent.putExtra("cmd",1);
                mContext.sendBroadcast(intent);

                mMediaPlayer.start();
            }else if(cmd.equals("stop")){
                if(mMediaPlayer != null){

                    Intent intent = new Intent();
                    intent.setAction(MY_S_EVENT);
                    intent.setPackage("com.darren.proxystub");
                    intent.putExtra("cmd",2);
                    mContext.sendBroadcast(intent);

                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                }
            }
        }
    }
}
