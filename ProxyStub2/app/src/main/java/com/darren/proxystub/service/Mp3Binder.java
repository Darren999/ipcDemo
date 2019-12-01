package com.darren.proxystub.service;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;

public class Mp3Binder extends Binder {
    private Context mContext;
    private MediaPlayer mPlayer = null;


    public Mp3Binder(Context context){
        this.mContext = context;

    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {

        switch (code){
            case 1:{
                String cmd = "play";
                Message msg = Mp3RemoteService.handler.obtainMessage(1,1,1,cmd);
                Mp3RemoteService.handler.sendMessage(msg);
                break;
            }
            case 2:
                String cmd = "stop";
                Message msg = Mp3RemoteService.handler.obtainMessage(2,2,2,cmd);
                Mp3RemoteService.handler.sendMessage(msg);
                break;
        }

        return true;
    }
}
