package com.example.first.buttonview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class LayoutPractice extends AppCompatActivity {

    private Button btn1, btn2, btn3;
    private LinearLayout page1, page2, page3;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_practice);

        // layout에 만든 뷰 찾아오기
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        page1 = (LinearLayout)findViewById(R.id.page1);
        page2 = (LinearLayout)findViewById(R.id.page2);
        page3 = (LinearLayout)findViewById(R.id.page3);

        // btn1을 클릭했을 때 처리
        btn1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                page1.setVisibility(View.VISIBLE);
                page2.setVisibility(View.INVISIBLE);
                page3.setVisibility(View.INVISIBLE);
            }
        });

        // btn2을 클릭했을 때 처리
        btn2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                page1.setVisibility(View.INVISIBLE);
                page2.setVisibility(View.VISIBLE);
                page3.setVisibility(View.INVISIBLE);
            }
        });

        // btn3을 클릭했을 때 처리
        btn3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                page1.setVisibility(View.INVISIBLE);
                page2.setVisibility(View.INVISIBLE);
                page3.setVisibility(View.VISIBLE);
            }
        });




    }
}
