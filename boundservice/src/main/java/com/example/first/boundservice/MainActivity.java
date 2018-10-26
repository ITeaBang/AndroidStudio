package com.example.first.boundservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 서비스 객체 생성
    MyService myService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Intent 생성
        Intent intent = new Intent(this, MyService.class);

        // 서비스와 바인드된 경우와 바인드가 해제된 경우에 호출되는 메소드를 소유한 ServiceConnection 객체 생성
        ServiceConnection myConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyLocalBinder binder = (MyService.MyLocalBinder)service;
            myService = binder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        // 바운드 서비스로 등록
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);

        final TextView textView = (TextView)findViewById(R.id.result);
        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = myService.remoteMethod();
                textView.setText(result);
            }
        });
    }
}
