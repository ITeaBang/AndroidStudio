package com.example.first.threaduse;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HandlerActivity extends AppCompatActivity {

    TextView textView;
    Handler handler = new Handler() {
        int i = 0;
        public void handleMessage(Message message) {
            textView.setText(i + "");
            i = i + 1;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        textView = (TextView) findViewById(R.id.textView);
/*
      class ThreadEx extends Thread {
            public void run() {
                for (int i = 0; i < 10; i = i + 1) {
                    try {
                        Thread.sleep(1000);
                        handler.sendEmptyMessage(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        new ThreadEx().start();
        // textView.setText("끝");
*/
        new Thread() {
            public void run(){
                for( int i = 0; i < 10; i = i + 1){
                    try{
                        Thread.sleep(1000);
                        handler.sendEmptyMessage(0);
                    }catch (Exception e){}
                }
            }
        }.start();
        // textView.setText("끝");
    }
}