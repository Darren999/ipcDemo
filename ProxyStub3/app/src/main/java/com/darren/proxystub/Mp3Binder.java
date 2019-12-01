package com.darren.proxystub;

import android.content.Context;
import android.media.MediaPlayer;

public class Mp3Binder extends PlayerStub {
    private Context mContext;
    private MediaPlayer mPlayer = null;

    public Mp3Binder(Context context){
        this.mContext = context;
    }


    @Override
    public void play() {
        if(mPlayer == null){
            mPlayer = MediaPlayer.create(mContext,R.raw.eason);
        }

        mPlayer.start();
    }

    @Override
    public void stop() {
        if(mPlayer != null){
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    public String getStatus(){
        return null;
    }
}
