package com.example.first.outputview;

import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;

public class TabUse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_use);

        // 전체 탭을 관리하기 위한 객체를 찾아오기
        TabHost host = (TabHost) findViewById(R.id.host);

        // 탭 설정을 위한 메소드 호출
        host.setup();

        // 탭을 생성
        TabHost.TabSpec spec = host.newTabSpec("tab1");

        // 탭의 아이콘 설정
        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.clock, null));

        // 탭의 내용을 설정
        spec.setContent(R.id.tab_content1);

        // 탭을 추가
        host.addTab(spec);

        // 탭을 생성
        spec = host.newTabSpec("tab2");

        // 탭의 아이콘 설정
        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.database, null));

        // 탭의 내용을 설정
        spec.setContent(R.id.tab_content2);

        // 탭을 추가
        host.addTab(spec);

        // 탭을 생성
        spec = host.newTabSpec("tab3");

        // 탭의 아이콘 설정
        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(), R.drawable.delivery, null));

        // 탭의 내용을 설정
        spec.setContent(R.id.tab_content3);

        // 탭을 추가
        host.addTab(spec);

    }
}
