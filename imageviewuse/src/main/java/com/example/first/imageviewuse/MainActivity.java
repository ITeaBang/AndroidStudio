package com.example.first.imageviewuse;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TextView 찾아오기
        TextView textView = (TextView)findViewById(R.id.textView);

        // 스크롤이 가능하도록 설정
        textView.setMovementMethod(new ScrollingMovementMethod());

        String data = "안녕하세요\n airport \n 텍스트 중간에 이미지를 출력하고, 제목 부분만 서식 변경";

        // img 부분에 이미지를 삽입
        SpannableStringBuilder builder = new SpannableStringBuilder(data);

        // 삽입할 시작 위치 찾기
        int start = data.indexOf("airport");

        // 삽입할 종료 위치 찾기
        int end = start + "airport".length();

        // 출력할 이미지 찾기
        Drawable dr = ResourcesCompat.getDrawable(getResources(), R.drawable.airport, null);

        // 이미지 추출하기
        dr.setBounds(0,0,
                dr.getIntrinsicWidth(),
                dr.getIntrinsicHeight());

        // 이미지를 출력하기 위한 Span 만들기
        ImageSpan imageSpan = new ImageSpan(dr);

        // SpannableBuilder에 적용
        builder.setSpan(imageSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 안녕하세요 부분의 서식을 변경
        start = data.indexOf("안녕하세요");
        end = start + "안녕하세요".length();
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD_ITALIC);
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(2.0f);
        builder.setSpan(styleSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(sizeSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        
        // 텍스트 뷰에 적용
        textView.setText(builder);
    }
}