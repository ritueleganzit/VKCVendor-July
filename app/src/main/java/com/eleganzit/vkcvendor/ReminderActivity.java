package com.eleganzit.vkcvendor;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.eleganzit.vkcvendor.util.AlarmReceiver;
import com.eleganzit.vkcvendor.util.LocalData;
import com.eleganzit.vkcvendor.util.NotificationScheduler;

import java.io.IOException;
import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {
    private static final String TAG = "Reminderrr";
    private MediaPlayer mMediaPlayer;
    LocalData localData;
    ClipboardManager myClipboard;
    int hour, min;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button btncontinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        sharedPreferences = getSharedPreferences("myalarm", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        localData = new LocalData(getApplicationContext());

        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        hour = localData.get_hour();
        min = localData.get_min();
        btncontinue = findViewById(R.id.btncontinue);
        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("timer", false);
                editor.commit();
                Log.d(TAG, "onCheckedChanged: false");
                try {
                    if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                        mMediaPlayer.stop();
                    }
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                    mMediaPlayer = new MediaPlayer();
                } catch (Exception e)
                {

                }


                startActivity(new Intent(ReminderActivity.this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });


        playSound(ReminderActivity.this, getAlarmUri());


    }

    private Uri getAlarmUri() {
        Uri alert = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) {
                alert = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alert;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void playSound(Context context, Uri alert) {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(context, alert);
            final AudioManager audioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            }
        } catch (IOException e) {
            System.out.println("OOPS");
        }
    }
}
