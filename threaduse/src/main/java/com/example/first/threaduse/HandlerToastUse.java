package com.example.first.threaduse;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HandlerToastUse extends AppCompatActivity {

    int value;
    ProgressDialog progressDialog;

    Handler handler = new Handler() {
        public void handleMessage(Message message) {
            value = value + 1;
            try{
                Thread.sleep(500);
                if(value <= 13){
                    progressDialog.setProgress(value);
                    handler.sendEmptyMessage(0);
                }
                else{
                    progressDialog.dismiss();
                }
            }catch (Exception e){}
        }
    };

    public void download() {
        try {
            Thread.sleep(10000);
            Toast.makeText(this, "다운로드 완료", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_toast_use);

        Button btn = (Button) findViewById(R.id.download);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(HandlerToastUse.this)
                        .setTitle("다운로드")
                        .setMessage("다운로드 받으시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                value = 0;
                                progressDialog = new ProgressDialog(HandlerToastUse.this);
                                progressDialog.setMax(13);
                                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                progressDialog.setTitle("다운로드");
                                progressDialog.setMessage("기다려주세요.");

                                // 뒤로 버튼으로 대화 상자를 닫을 수 없게 설정
                                progressDialog.setCancelable(false);
                                progressDialog.show();

                                handler.sendEmptyMessage(0);
                            }
                        })
                        .setNegativeButton("아니요", null)
                        .show();
            }
        });

    }
}
