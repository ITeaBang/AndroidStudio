package com.example.first.boundservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

public class AlarmReceiver extends BroadcastReceiver {

    // Receiver가 호출되면 수행되는 메소드
    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer player = MediaPlayer.create(context.getApplicationContext(), R.raw.redwin);
        player.start();
    }
}
