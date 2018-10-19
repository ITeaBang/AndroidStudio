package com.example.first.asynctaskactivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

public class LooperActivity extends AppCompatActivity {

    ArrayList<String> oddDatas;
    ArrayList<String> evenDatas;

    ArrayAdapter<String> oddAdapter;
    ArrayAdapter<String> evenAdapter;

    OneThread oneThread;
    TwoThread twoThread;

    Handler handler;

    class OneThread extends Thread {
        // Thread 내부에 Handler 만들기
        Handler oneHandler;

        public void run() {
            // Thread 내부에서 Handler를 만들어서 사용할 때는 Looper를 이용
            Looper.prepare();
            oneHandler = new Handler() {
                @Override
                // Looper 사용시 Handler 안에 반드시 handleMessage가 필요
                public void handleMessage(Message message) {
                    // Thread.sleep(1000);
                    // 1초 대기,  Thread.sleep과는 다르게 try, catch가 불필요
                    SystemClock.sleep(1000);
                    // 메시지의 전송 내용 가져오기
                    final int data = message.arg1;
                    // 메시지를 구분
                    if (message.what == 0) {
                        // 다른 작업이 없을 경우 처리 요청
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                // 데이터를 추가하고 리스트 뷰 다시 출력
                                evenDatas.add("even : " + data);
                                evenAdapter.notifyDataSetChanged();
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                oddDatas.add("odd : " + data);
                                oddAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                }
            };
            // Looper를 반복[
            Looper.loop();
        }
    }

    // 랜덤한 정수를 생성해서 oneThread에게 메시지를 전송하는 Thread 클래스
    class TwoThread extends Thread {
        @Override
        public void run() {
            Random r = new Random();
            for (int i = 0; i < 10; i = i + 1) {
                SystemClock.sleep(100);
                int data = r.nextInt(10);
                Message message = new Message();
                if (data % 2 == 0) {
                    message.what = 0;
                } else {
                    message.what = 1;
                }
                message.arg1 = data;
                oneThread.oneHandler.sendMessage(message);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 생성한 Looper 종료
        oneThread.oneHandler.getLooper().quit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper);

        oddDatas = new ArrayList<>();
        evenDatas = new ArrayList<>();

        oddAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, oddDatas);
        evenAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, evenDatas);

        ListView oddListView = (ListView) findViewById(R.id.oddListView);
        ListView evenListView = (ListView) findViewById(R.id.evenListView);

        oddListView.setAdapter(oddAdapter);
        evenListView.setAdapter(evenAdapter);

        handler = new Handler();

        oneThread = new OneThread();
        oneThread.start();

        twoThread = new TwoThread();
        twoThread.start();

    }

}
