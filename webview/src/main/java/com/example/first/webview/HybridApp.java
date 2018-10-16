package com.example.first.webview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HybridApp extends AppCompatActivity {

    private WebView webView;

    // WebViewClient 클래스 생성, 리다이렉트하는 URL이 왔을 때, 처리를 위한 클래스
    class WebCustomClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }
    }

    class AndroidJavascriptInterface {
        // 토스트 출력을 위해서 생성
        private Context context;

        // 핸들러 생성
        private Handler handler = new Handler();

        // 생성자
        public AndroidJavascriptInterface(Context context) {
            this.context = context;
        }

        // 자바 스크립트가 호출할 수 있는 메소드를 생성하는 어노테이션
        @JavascriptInterface
        public void showToastMessage(final String message) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hybrid_app);

        webView = (WebView) findViewById(R.id.webView);
        // 리다이렉트를 웹 뷰에서 처리하기 위한 설정
        webView.setWebViewClient(new WebCustomClient());

        // 웹 뷰 안에서 자바스크립트 함수를 사용할 수 있도록 하는 설정
        webView.getSettings().setJavaScriptEnabled(true);

        // 자바스크립트에서 네이티브 메소드를 호출할 수 있도록 설정
        // 첫번째 매개변수는 메소드의 위치를 알려주는 것
        // 두번째 매개변수는 html에서 호출할 때 사용할 인스턴스 이름 설정
        webView.addJavascriptInterface(new AndroidJavascriptInterface(this),"MYAPP");

        // url 로딩
        webView.loadUrl(
                "http://192.168.0.229:8015/181012/hybridapp"
        );

        // 버튼의 클릭 이벤트 처리
        Button sendmsg = (Button) findViewById(R.id.sendmsg);
        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText message = (EditText) findViewById(R.id.message);

                // 자바 스크립트 함수를 호출
                webView.loadUrl("javascript:showDisplayMessage('" + message.getText().toString() + "')");}
        });
    }
}
