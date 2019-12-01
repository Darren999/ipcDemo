package com.darren.proxystub;

import android.os.Binder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public abstract class PlayerStub extends Binder implements IPlayer {
    private static final String TAG = "PlayerStub";
    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {

        Log.d(TAG,"onTransact code = " + code);
        reply.writeString(data.readString()+ " mp3");
        if(code == 1){
            this.play();
        }else if (code == 2 ){
            this.stop();
        }
        return true;
    }

    public abstract void play();
    public abstract void stop();
    public abstract String getStatus();
}
