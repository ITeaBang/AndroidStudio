package com.example.first.webview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = (WebView) findViewById(R.id.webView);

        // 리다이렉트가 있는 경우, 크롬으로 가지 않고 현재 앱에서 이동하도록 설정
        webView.setWebViewClient(new WebViewClient());

        // 자바 스크립트 사용 설정
        WebSettings set = webView.getSettings();
        set.setJavaScriptEnabled(true);

        // 확대 축소 버튼을 추가
        set.setBuiltInZoomControls(true);

        webView.loadUrl(
                "http://192.168.0.229:8015/181012/"
        );
    }
}
