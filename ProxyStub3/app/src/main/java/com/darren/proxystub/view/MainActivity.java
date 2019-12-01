package com.darren.proxystub.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.darren.proxystub.PlayerProxy;
import com.darren.proxystub.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private TextView tvStatus;
    private Button btnStart;
    private Button btnStop;
    private PlayerProxy mPlayerProxy;
    private static final String ACTION_REMOTE_SERVICE = "com.darren.proxystub.REMOTE_SERVICE";

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG,"onServiceConnected");
            mPlayerProxy = new PlayerProxy(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG,"onServiceDisconnected");
            mPlayerProxy = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        Intent intent = new Intent();
        intent.setAction(ACTION_REMOTE_SERVICE);
        intent.setPackage("com.darren.proxystub");
        startService(intent);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }

    private void initView(){
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
            case R.id.btn_start:
                if(mPlayerProxy != null){
                    mPlayerProxy.play();
                    tvStatus.setText(mPlayerProxy.getStatus());
                }
                break;
            case R.id.btn_stop:
                if(mPlayerProxy != null){
                    mPlayerProxy.stop();
                    tvStatus.setText(mPlayerProxy.getStatus());
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent();
        intent.setAction(ACTION_REMOTE_SERVICE);
        intent.setPackage("com.darren.proxystub");
        unbindService(connection);
        stopService(intent);

        super.onDestroy();
    }
}
