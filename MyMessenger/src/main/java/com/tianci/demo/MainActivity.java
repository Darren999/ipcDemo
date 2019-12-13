package com.tianci.demo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private static Button btnSend;
    private Messenger mMessenger;
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            Log.d(TAG,"onServiceConnected");
            mMessenger = new Messenger(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"onServiceDisconnected");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Intent intent = new Intent(this,MyService.class);
        bindService(intent,mConn, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:
                Message message = Message.obtain(null,0,"hello messenger");
                try {
                    mMessenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
