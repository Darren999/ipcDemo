package com.darren.proxystub.view;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.darren.proxystub.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private TextView tvStatus;
    private Button btnStart;
    private Button btnStop;

    private IBinder mBinder;
    private static final String MY_S_EVENT = "com.darren.proxystub.M_S_EVENT";
    private static final String MY_S_ACTION = "com.darren.proxystub.REMOTE_SERVICE";
    private BroadcastReceiver receiver=new MyIntentReceiver();
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG,"onServiceConnected");
            mBinder = iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG,"onServiceDisconnected");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MY_S_EVENT);
        registerReceiver(receiver,intentFilter);

        Intent intent = new Intent();
        intent.setPackage("com.darren.proxystub");
        intent.setAction(MY_S_ACTION);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }

    private void initView() {

        tvStatus = findViewById(R.id.tv_status);
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);

        tvStatus.setText("stop");
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:{
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                data.writeString("playing");

                try {
                    mBinder.transact(1,data,reply,0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            }
            case R.id.btn_stop: {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                data.writeString("stop");

                try {
                    mBinder.transact(2, data, reply, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class MyIntentReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            int cmd = intent.getIntExtra("cmd",-1);
            switch (cmd){
                case 1:
                    tvStatus.setText("playing");
                    break;
                case 2:
                    tvStatus.setText("stop");
                    break;
            }
        }
    }
}

