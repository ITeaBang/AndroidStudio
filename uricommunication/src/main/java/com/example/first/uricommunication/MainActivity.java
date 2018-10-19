package com.example.first.uricommunication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText url;
    Button download;
    TextView html;

    // 진행 상황을 출력할 진행 대화상자
    ProgressDialog progressDialog;

    // 데이터를 출력할 Handler 만들기, 1개만 생성 후 message.what으로 구분
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            // Thread가 넘겨준 데이터를 TextView에 출력
            html.setText(message.obj.toString());

            // 종료시 대화 상자를 닫는 코드
            progressDialog.dismiss();
        }
    };

    /*// 단 1번 사용시의 Thread 생성 방법, 다수 사용시 부적합
    Thread th = new Thread(){

    };*/

    // 다수 사용시 Thread 생성 방법
    class ThreadEx extends Thread {
        @Override
        public void run() {
            try {
                // 다운로드 받을 주소 가져오기
                String addr = url.getText().toString();

                // 문자열 주소로 URL 객체 생성
                URL downloadUrl = new URL(addr);

                // 연결 객체 생성
                HttpURLConnection con = (HttpURLConnection) downloadUrl.openConnection();

                // 옵션 설정
                con.setConnectTimeout(20000);
                con.setUseCaches(false);

                // 문자열을 다운로드 받기 위한 스트림 생성
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                // 줄 단위로 문자열을 읽어서 sb에 추가
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = br.readLine();
                    if (line == null)
                        break;
                    sb.append(line + "\n");
                }

                // 전부 가져왔으면 닫기
                br.close();
                con.disconnect();

                // message에 저장해서 handler에게 메시지 전송
                Message message = new Message();
                message.obj = sb.toString();
                handler.sendMessage(message);

            } catch (Exception e) {
                Log.e("다운로드 에러", e.getMessage());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = (EditText) findViewById(R.id.url);
        download = (Button) findViewById(R.id.download);
        html = (TextView) findViewById(R.id.html);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다운로드 시작시 출력하는 대화 상자
                progressDialog = ProgressDialog.show(MainActivity.this, "", "다운로드 중입니다.");
                ThreadEx th = new ThreadEx();
                th.start();
            }
        });
    }
}