package com.eleganzit.vkcvendor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.eleganzit.vkcvendor.util.DateChangeReceiver;
import com.eleganzit.vkcvendor.util.UserLoggedInSession;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {
    UserLoggedInSession userLoggedInSession;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_splash);
        userLoggedInSession=new UserLoggedInSession(SplashActivity.this);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (userLoggedInSession.isLoggedIn())
                {
                    startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();
                }
                else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }



            }
        },3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
