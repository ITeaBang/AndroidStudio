package com.example.first.buttonview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MakeToast extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_toast);

        Button btntoast = (Button) findViewById(R.id.btntoast);
        btntoast.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MakeToast.this, "안녕하세요. 토스트입니다.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
