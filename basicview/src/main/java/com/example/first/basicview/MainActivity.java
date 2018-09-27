package com.example.first.basicview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);

        // 레이아웃을 생성 - 다른 뷰들을 묶어주기 위한 View 그룹
        LinearLayout layout = new LinearLayout(this);

        // View를 생성해서 레이아웃에 추가
        Button btn1 = new Button(this);
        Button btn2 = new Button(this);

        btn1.setText("버튼1");
        btn2.setText("버튼2");

        layout.addView(btn1);
        layout.addView(btn2);

        // Activity의 View로 Code로 만든 View를 설정
        setContentView(layout);


    }
}