package com.example.first.imageviewuse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        final ProgressBar lect = (ProgressBar)findViewById(R.id.progress1);
        final ProgressBar circle = (ProgressBar)findViewById(R.id.progress2);

        // 여러 개의 클릭 이벤트를 처리하기 위한 핸들러 만들기
        View.OnClickListener eventHandler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.start:
                        lect.setProgress(lect.getProgress() + 20);
                        circle.setVisibility(View.VISIBLE);
                        break;
                    case R.id.end:
                        lect.setProgress(lect.getProgress() - 20);
                        circle.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        };
        Button start = (Button)findViewById(R.id.start);
        Button end = (Button)findViewById(R.id.end);
        start.setOnClickListener(eventHandler);
        end.setOnClickListener(eventHandler);

        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        final TextView textView = (TextView)findViewById(R.id.value);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            textView.setText(String.format("%d", progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                Toast.makeText(ProgressActivity.this, "값 변경 시작", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                // textView.setText(String.format("%d", seekBar.getProgress()));
                Toast.makeText(ProgressActivity.this, "값 변경 종료", Toast.LENGTH_LONG).show();

            }
        });

        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        final TextView ratingText = (TextView)findViewById(R.id.ratingValue);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                ratingText.setText(String.format("%.1f", rating));

            }
        });















    }
}
