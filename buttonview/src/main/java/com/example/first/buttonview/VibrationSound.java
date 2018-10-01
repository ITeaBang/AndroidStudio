package com.example.first.buttonview;

import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class VibrationSound extends AppCompatActivity {

    Button btnvibrate, btnsystemsound, btnusersound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibration_sound);

        btnvibrate = (Button)findViewById(R.id.btnvibrate);
        btnsystemsound = (Button)findViewById(R.id.btnsystemsound);
        btnusersound = (Button)findViewById(R.id.btnusersound);

        btnvibrate.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Vibrator vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                vib.vibrate(3000);
            }
        });

        btnsystemsound.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Uri systemsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(),systemsound);
                ringtone.play();
            }
        });

        btnusersound.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(VibrationSound.this,R.raw.ttl);
                player.start();
            }
        });
    }
}