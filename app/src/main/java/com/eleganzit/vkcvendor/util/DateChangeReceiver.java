package com.eleganzit.vkcvendor.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class DateChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Date Change", Toast.LENGTH_SHORT).show();
        Log.i("DATE RECEIVER", "OK");
    }
}
