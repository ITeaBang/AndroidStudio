package com.example.first.imageviewuse;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;

    // 커스텀 뷰 생성
    class MyView extends View {
        // View 클래스에 default constructor 가 없어서 생성
        public MyView(Context context){
            super(context);
        }

        // 화면의 크기를 설정하는 메소드
        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
            setMeasuredDimension(2000, 4000);
            // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        // 뷰를 화면에 출력할 때 호출하는 메소드
        // 뷰를 직접 생성할 때 중요한 메소드
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint pnt = new Paint();
            // 5픽셀 단위로 사각형을 그리기
            for(int i=0; i<4000; i=i+5){
                pnt.setARGB(255, 255-i/5, 255-i/5, 255);
                canvas.drawRect(0,i,2000, i+5, pnt);
            }
        }
    }

    public class ScrollUse extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_scroll_use);
            // setContentView(new MyView(this));

            // 스크롤 뷰 위에 배치하면 화면보다 큰 경우 스크롤이 가능합니다.
            ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView);
            scrollView.addView(new MyView(this));
        }
    }
