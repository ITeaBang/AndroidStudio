package com.example.first.outputview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class ButtonShow extends AppCompatActivity {

    // 스레드를 anonymous class를 이용해서 생성, anonymous class는 지역 변수를 사용할 수 없으므로 현 위치에서 변수 선언
    ImageView imageView;
    Bitmap bitmap;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_show);

        imageView = (ImageView) findViewById(R.id.imgview);

        // 리소스에 포함된 이미지 가져오기
        // imgView.setImageResource(R.drawable.snow);

        // 웹의 이미지 출력하기
        url = "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2F20150627_286%2Fabcde408xo_1435376690874ANDhh_JPEG%2F234D3241558809392CA3D4.jpeg&type=b400";

        // 이미지를 다운로드 받기 위한 스레드 생성
        Thread th = new Thread() {
            public void run(){
                try{

                // 웹에서 데이터를 가져올 수 있는 스트림을 생성
                InputStream is = new URL(url).openStream();

                // 스트림의 데이터를 이미지로 변경
                bitmap = BitmapFactory.decodeStream(is);
            }catch(Exception e){
                    Log.e("에러", e.getMessage());
                }
        }
    };
        // 스레드 시작
        th.start();

        // 스레드 수행이 종료되면 이미지를 설정 { (스레드)다운로드 작업 이후에 설정 }
        try{

            // 스레드의 수행이 종료될 때까지 대기
            th.join();
            imageView.setImageBitmap(bitmap);
        }catch(Exception e){
            Log.e("에러", e.getMessage());
        }
    }
}
