package com.example.first.uricommunication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;

    Handler displayHandler = new Handler() {

        @Override
        public void handleMessage(Message message) {
            Bitmap bitmap = (Bitmap) message.obj;
            imageView.setImageBitmap(bitmap);

        }
    };

    Handler downloadHandler = new Handler() {

        @Override
        public void handleMessage(Message message) {
            // 파일이 존재하는 경우
            if(message.obj != null){

                // 안드로이드의 Data 디렉토리 경로를 가져오기
                String path = Environment.getDataDirectory().getAbsolutePath();

                // 현재 앱 내의 파일 경로 만들기
                path = path + "/data/com.example.first.uricommunication/" + (String)message.obj;

                // 이미지 파일을 imageView에 출력
                imageView.setImageBitmap(BitmapFactory.decodeFile(path));
            }

            // 파일이 존재하지 않는 경우
            else{
                Toast.makeText(ImageActivity.this, "파일이 존재하지 않습니다.", Toast.LENGTH_LONG).show();
            }
        }
    };

    class DisplayThread extends Thread {
        @Override
        public void run() {
            try {
                String addr = "https://img.sbs.co.kr/newsnet/etv/upload/2018/02/14/30000593935_1280.jpg";
                URL url = new URL(addr);

                // url에 연결해서 비트맵 만들기
                Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());

                // Bitmap을 Handler에게 전송
                Message message = new Message();
                message.obj = bitmap;
                displayHandler.sendMessage(message);
            } catch (Exception e) {
                Log.e("이미지 가져오기", e.getMessage());
            }
        }
    }

    // 이미지를 다운로드 받아서 파일로 저장하는 Thread
    class DownloadThread extends Thread {
        String addr;
        String filepath;
        public DownloadThread(String addr, String filepath){
            this.addr = addr;
            this.filepath = filepath;
        }

        @Override
        public void run() {
            try{
                URL url = new URL(addr);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setUseCaches(false);
                con.setConnectTimeout(20000);

                // 내용을 읽을 스트림을 생성
                InputStream is = con.getInputStream();

                // 기록할 스트림을 생성
                PrintStream pw = new PrintStream(openFileOutput(filepath, 0));

                // is에서 읽어서 pw에 기록
                while(true){

                    byte []  b = new byte[1024];
                    int read = is.read(b);
                    if(read <= 0)break;
                    pw.write(b, 0, read);
                }
                is.close();
                pw.close();
                con.disconnect();
                Message message =  new Message();
                message.obj = filepath;
                downloadHandler.sendMessage(message);

            }catch (Exception e){
                Log.e("이미지 다운로드 실패", e.getMessage());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = (ImageView) findViewById(R.id.imageview);
        Button display = (Button) findViewById(R.id.display);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayThread th = new DisplayThread();
                th.start();
            }
        });

        Button download = (Button)findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 이미지를 다운로드 받을 주소
                String imageUrl = "https://img.sbs.co.kr/newsnet/etv/upload/2018/02/14/30000593935_1280.jpg";

                // 파일명 만들기
                int idx = imageUrl.lastIndexOf("/");
                String filename = imageUrl.substring(idx + 1);
                Log.e("파일 이름",filename);

                // 파일 경로 만들기
                String data = Environment.getDataDirectory().getAbsolutePath();
                String path = data + "/data/com.example.first.uricommunication/files/" + filename;
                Log.e("파일 경로", path);

                // 파일의 존재 여부 확인
                if(new File(path).exists()){
                    Toast.makeText(ImageActivity.this, "파일이 존재합니다.", Toast.LENGTH_LONG).show();
                    imageView.setImageBitmap(BitmapFactory.decodeFile(path));
                }else{
                    Toast.makeText(ImageActivity.this, "파일이 존재하지 않습니다.", Toast.LENGTH_LONG).show();

                    // 넘겨준 이미지 파일의 경로와 저장할 파일의 경로 확인
                    // Log.e("이미지 파일 경로", imageUrl);
                    // Log.e("저장할 파일 경로", path);

                    DownloadThread th = new DownloadThread(imageUrl, filename);
                    th.start();

                }
            }
        });
    }
}