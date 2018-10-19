package com.example.first.threaduse;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HandlerUse extends AppCompatActivity {

    // Handler 생성, 수정
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String str = (String)msg.obj;
            Toast.makeText(HandlerUse.this, str, Toast.LENGTH_SHORT).show();
        }
    };

    // Thread 생성
    Thread th1 = new Thread() {
        @Override
        public void run() {
            try {
                Thread.sleep(10000);
                handler.sendEmptyMessage(0);
            } catch (Exception e) {}
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_use);

        class ThreadEx extends Thread{
            String url;
            public ThreadEx(String url){
                this.url = url;
            }

            @Override
            public void run() {
                try{
                    // Handler에게 데이터를 전달하면서 호출
                    Message message = new Message();
                    message.obj = url;
                    handler.sendMessageDelayed(message, 10000);
                }catch(Exception e){}
            }
        }

        // Button을 눌렀을 때 Thread 시작
        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadEx th1 = new ThreadEx("영화 정보");
                ThreadEx th2 = new ThreadEx("극장 정보");
                th1.start();
                th2.start();
            }
        });
    }
}
