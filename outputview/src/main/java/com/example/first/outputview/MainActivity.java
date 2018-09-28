package com.example.first.outputview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

        private EditText edit;
        private TextView text;
        InputMethodManager imm;
        Button show;
        Button hide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // xml에 디자인한 뷰 찾아오기
        edit = (EditText)findViewById(R.id.edit);
        text = (TextView)findViewById(R.id.text);

        // 조작
        text.setText("xml에 디자인 뷰 조작하기");
        text.setTextSize(38);

        // 콘솔에 출력 - 하단의 Logcat에서 확인 가능
        Log.e("태그", "내용");

        // 키보드 관리 객체 가져오기
        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        // edit에 포커스 설정
        imm.showSoftInput(edit,0);

        show = (Button)findViewById(R.id.show);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.showSoftInput(edit,0);
            }
        });

        hide = (Button)findViewById(R.id.hide);
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(edit.getWindowToken(),0);
            }
        });
    }
}
