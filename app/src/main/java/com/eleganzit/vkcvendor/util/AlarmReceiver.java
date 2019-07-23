package com.eleganzit.vkcvendor.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Jaison on 17/06/17.
 */

public class AlarmReceiver extends BroadcastReceiver {

    String TAG = "AlarmReceiver";
    String intent_action;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
            {
                context.startForegroundService(new Intent(context, MyService.class));
            }
            else
            {
                context.startService(new Intent(context, MyService.class));
            }
        }

}


