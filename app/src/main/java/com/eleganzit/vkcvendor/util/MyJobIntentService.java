package com.eleganzit.vkcvendor.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

public class MyJobIntentService extends JobIntentService {
    private static final int JOB_ID = 1000;
    public static void enqueueWork(Context ctx, Intent intent) {
        enqueueWork(ctx, MyJobIntentService.class, JOB_ID, intent);
    }
    @Override
    protected void onHandleWork(@NonNull Intent intent) {

    }
}
