package com.darren.proxystub;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class PlayerProxy implements IPlayer {
    private static final String TAG = "PlayerProxy";
    private IBinder mIBinder;

    public PlayerProxy(IBinder iBinder){
        this.mIBinder = iBinder;
    }

    @Override
    public void play() {
        Log.d(TAG,"play");
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();

        data.writeString("playing");

        try {
            mIBinder.transact(1,data,reply,0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        Log.d(TAG,"stop");
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();

        data.writeString("stop ");

        try {
            mIBinder.transact(2,data,reply,0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
