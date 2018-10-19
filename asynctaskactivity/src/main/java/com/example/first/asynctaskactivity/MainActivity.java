package com.example.first.asynctaskactivity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

class AsyncTaskEx extends AsyncTask<Integer, Integer, Integer> {

    // MainActivity의 데이터를 저장하기 위한 변수
    ProgressBar progressBar;
    TextView textView;
    int value;

    // 생성자 - MainActivity에서 데이터를 넘겨받기 위해 생성
    public AsyncTaskEx(ProgressBar progressBar, TextView textView, int value) {
        this.progressBar = progressBar;
        this.textView = textView;
        this.value = value;
    }

    @Override
    // 인스턴스가 생성되면 가장 먼저 호출되는 메소드, MainThread에서 수행 : 화면을 갱신하는 코드를 작성해도된다.
    protected void onPreExecute() {
        super.onPreExecute();
        // UI 초기화
        value = 0;
        progressBar.setProgress(value);
    }

    @Override
    // 비동기적인 작업을 처리하는 메소드, 매개변수는 클래스를 생성할 때 적용한 제너릭의 두번째 자료형과 일치해야 합니다.
    // 리턴 타입은 세번째 자료형과 일치해야 합니다.
    // MainThread에서는 동작 X, UI를 갱신하는 코드는 작성할 수 없다.
    protected Integer doInBackground(Integer... integers) {
        while (isCancelled() == false){
            value = value + 1;
            if(value >= 100){
                break;
            }else{
                // onProgressUpdate 호출
                publishProgress(value);
            }try{
                Thread.sleep(500);
            }catch (Exception e) {
                Log.e("문제 발생", e.getMessage());
            }
        }
        return value;
    }

    // doInBackground에서 publishProgress를 호출하면 자동으로 호출되는 메소드, MainThread에서 수행
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(value);
        textView.setText(
                String.format("현재 값 : %d", value));
    }

    // 인스턴스가 cancelled를 호출했을 때 호출되는 메소드, Main Thread에서 수행
    @Override
    protected void onCancelled(Integer integer) {
        super.onCancelled(integer);
        textView.setText("Thread 중지");
    }

    // doInBackground가 작업을 종료했을 때 호출되는 메소드
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        value = 0;
        progressBar.setProgress(value);
        textView.setText("Thread 종료");
    }
}

public class MainActivity extends AppCompatActivity {

    // 프로그래스 바와 텍스트 뷰 변수 선언
    ProgressBar progressBar;
    TextView textView;

    // 프로그래스 바의 값을 표시할 정수 변수 선언
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start = (Button)findViewById(R.id.start);
        Button end = (Button)findViewById(R.id.end);
        progressBar = (ProgressBar)findViewById(R.id.progress);
        textView = (TextView)findViewById(R.id.textView);

        View.OnClickListener clickListener = new View.OnClickListener(){
            AsyncTaskEx task = null;
            @Override
            public void onClick(View v){
                switch (v.getId()){
                    case R.id.start:
                        task = new AsyncTaskEx(progressBar, textView, value);
                        task.execute(100);
                        break;
                    case R.id.end:
                        task.cancel(true);
                        break;
                }
            }
        };
        start.setOnClickListener(clickListener);
        end.setOnClickListener(clickListener);
    }
}
