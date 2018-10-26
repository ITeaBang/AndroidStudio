package com.example.first.jsonlogin;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText id, passwd;
    LinearLayout linearLayout;
    Button button;
    ProgressDialog progressDialog;

    // Thread 작업 후 화면 갱신을 위한 객체, 1개만 있으면 Message의 what을 구분해서 사용할 수 있으므로 바로 인스턴스 생성
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
            if(msg.what == 1){
                linearLayout.setBackgroundColor(Color.RED);
            }else if(msg.what == 2){
                linearLayout.setBackgroundColor(Color.GREEN);
            }
        }
    };

    // 비동기적으로 작업을 수행하기 위한 Thread 클래스, Thread는 재사용이 불가능하므로 필요시마다
    // 인스턴스를 생성해야하므로 클래스를 생성해서 사용
    class ThreadEx extends Thread {
        @Override
        public void run() {
            try {
                String addr = "http://192.168.0.229:8015/androidphone/login?id=";
                String logid = id.getText().toString();
                String logpw = passwd.getText().toString();
                addr = addr + logid + "&pw=" + logpw;

                // 문자열 주소를 URL로 변경
                URL url = new URL(addr);

                // 연결 객체 생성
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                // 옵션 설정
                con.setUseCaches(false); // 캐시(로컬에 저장해두고 사용) 사용 여부
                con.setConnectTimeout(30000); // 접속을 시도하는 최대 시간(30초 동안 접속이 안되면 예외 발생)

                // 문자열을 다운로드 받을 스트림 생성
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                // 문자열 다운로드
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    sb.append(line + "\n");
                }
                br.close();
                con.disconnect();
                Log.e("다운로드 받은 데이터", sb.toString());

                // Json Parsing
                JSONObject result = new JSONObject(sb.toString());
                String x = result.getString("id");
                // Parsing 결과를 가지고 Message의 what을 통해 구분하여 Handler에게 전송
                Message msg = new Message();
                if (x.equals("null")) {
                    // Log.e("로그인 여부", "실패");
                    msg.what = 1;
                } else {
                    // Log.e("로그인 여부", "성공");
                    msg.what = 2;
                }


            } catch (Exception e) {
                Log.e("다운로드 실패", e.getMessage());
            }

        }
    }

    // Activity가 생성시 호출되는 메소드, Activity 실행시마다 호출되는 메소드는 onResume
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // layout 파일을 읽어서 메모리에 로드한 후 화면 출력을 준비하는 메소드를 호출
        setContentView(R.layout.activity_main);

        id = (EditText) findViewById(R.id.id);
        passwd = (EditText) findViewById(R.id.passwd);
        linearLayout = (LinearLayout) findViewById(R.id.layout01);
        button = (Button) findViewById(R.id.loginButton);

        // Button을 누르면 수행할 내용
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 진행 대화상자를 출력
                progressDialog = ProgressDialog.show(MainActivity.this, "로그인", "로그인 처리 중");

                // Thread를 만들어서 실행
                ThreadEx th = new ThreadEx();
                th.start();

            }
        });
    }
}
