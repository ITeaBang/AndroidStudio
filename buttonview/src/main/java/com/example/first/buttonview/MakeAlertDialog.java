package com.example.first.buttonview;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MakeAlertDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_alert_dialog);

        Button btnbasicalert = (Button) findViewById(R.id.btnbasicalert);
        btnbasicalert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 메소드 체이닝을 이용한 생성과 출력
                new AlertDialog.Builder(MakeAlertDialog.this)
                        .setMessage("기본 대화상자")
                        .setTitle("대화상자")
                        .setIcon(R.drawable.applebar)
                        .setPositiveButton("긍정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MakeAlertDialog.this, "긍정을 눌렀습니다.", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("부정",null)
                        .setNeutralButton("중립",null)
                        .setCancelable(false)
                        .show();
            }
        });

        Button btnaync = (Button)findViewById(R.id.btnasync);
        btnaync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 대화상자 출력
                new AlertDialog.Builder(MakeAlertDialog.this)
                        .setMessage("액티비티 종료")
                        .setTitle("대화상자")
                        .setIcon(R.drawable.applebar)
                        .setPositiveButton("프로그램 종료", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .show();

                // 토스트 출력
                Toast.makeText(MakeAlertDialog.this, "토스트 출력", Toast.LENGTH_LONG).show();

                // 액티비티 종료
                // finish();
            }
        });

    }
}