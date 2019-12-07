package com.tianci.binder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements IService,View.OnClickListener{

    private static final String TAG = "MainActivity";
    private Button btnFunc1;
    private Button btnFunc2;
    private IService iService;
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            Log.d(TAG,"onServiceConnected");
            MyService.MyBinder myBinder = (MyService.MyBinder)iBinder;
            iService = myBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"onServiceConnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this,MyService.class);
        bindService(intent,mConn,Context.BIND_AUTO_CREATE);
        initView();
    }

    private void initView() {
        btnFunc1 = findViewById(R.id.btn_func1);
        btnFunc2 = findViewById(R.id.btn_func2);
        btnFunc1.setOnClickListener(this);
        btnFunc2.setOnClickListener(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_func1:
                func1();
                break;
            case R.id.btn_func2:
                fun2();
                break;
        }
    }

    @Override
    public void func1() {
        Log.d(TAG,"func1");
        iService.func1();
    }

    @Override
    public void fun2() {
        Log.d(TAG,"func2");
        iService.fun2();
    }



}
