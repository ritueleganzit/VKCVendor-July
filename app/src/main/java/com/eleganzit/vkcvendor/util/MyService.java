package com.eleganzit.vkcvendor.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.eleganzit.vkcvendor.HomeActivity;
import com.eleganzit.vkcvendor.LoginActivity;
import com.eleganzit.vkcvendor.R;
import com.eleganzit.vkcvendor.ReminderActivity;

import java.util.Calendar;

import static com.eleganzit.vkcvendor.util.UserLoggedInSession.USER_END_TIME;
import static com.eleganzit.vkcvendor.util.UserLoggedInSession.USER_START_TIME;

public class MyService extends Service {


    SharedPreferences pref;
    private static final String PREF_NAME = "VKC";
    int PRIVATE_MODE = 0;
    int hour[]={8,9,10,11,12,13,14,15,16,17,18,19,20};

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground(){
        String NOTIFICATION_CHANNEL_ID = "com.eleganzit.vkcvendor";
        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.logo_white)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);


    }
    private void setAlarm() {


        AlarmManager  alarmManager;

        for (int i=0;i<hour.length;i++)
        {
            alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);

            Intent intent = new Intent(this, MyService.class);



            PendingIntent  pIntent = PendingIntent.getService(this, hour[i], intent, PendingIntent.FLAG_CANCEL_CURRENT);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, hour[i]);

            Log.d("sdf","dsgd g"+calendar.getTimeInMillis());


            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntent);


        }


    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        setAlarm();
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onCreate() {

        pref = getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
       // Toast.makeText(this, " MyService Created ", Toast.LENGTH_LONG).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());
        Log.d("afdfsdfsdfsd",pref.getString(USER_START_TIME, null)+"   <----->    "+pref.getString(USER_END_TIME, null));
        //compareDate(pref.getString(USER_START_TIME, null),pref.getString(USER_END_TIME, null));

    }

    @Override
    public void onStart(Intent intent, int startId) {
       // Toast.makeText(this, " MyService Started", Toast.LENGTH_LONG).show();
        Log.d("afdfsdfsdfsd",pref.getString(USER_START_TIME, null)+"   <----->    "+pref.getString(USER_END_TIME, null));
        compareDate(pref.getString(USER_START_TIME, null),pref.getString(USER_END_TIME, null));

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Log.d("asdasd","Service Stopped");
       // Toast.makeText(this, "Servics Stopped", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }


    private void compareDate(String start,String end) {
        /*Intent intent1 = new Intent(MyService.this, ReminderActivity.class);
        startActivity(intent1);*/
        //if(DateUtils.isHourInInterval(DateUtils.getCurrentHour()+":00",start,end)){
        if (start!=null && !start.isEmpty())
        {
            if(DateUtils.isHourInInterval(DateUtils.getCurrentHour()+":00",start,end)){
                Log.d("eeeeeeeeeeee",""+DateUtils.getCurrentHour()+":00"+" ");
                Log.d("eeeeeeeeeeee",""+start);
                Log.d("eeeeeeeeeeee",""+end);
            /*Calendar wakeUpTime = Calendar.getInstance();
            wakeUpTime.add(Calendar.SECOND, 1000 * 60 * 60);
*/
                //Create a new PendingIntent and add it to the AlarmManager
                Intent intent = new Intent(MyService.this, ReminderActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Log.d("DateeeeeDate","ifffff     "+DateUtils.getCurrentHour()+":00  ---  "+start+"  ---   "+end);
/*PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this,
                    12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager am =
                    (AlarmManager)MyService.this.getSystemService(Activity.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP,
                    wakeUpTime.getTimeInMillis(),                pendingIntent);*/
            }
            else
            {
                Log.d("DateeeeeDate","elseeee      "+DateUtils.getCurrentHour()+":00  ---  "+start+"  ---   "+end);


            }
        }

     /*   Calendar wakeUpTime = Calendar.getInstance();
        wakeUpTime.add(Calendar.SECOND, 30 * 60);

        //Create a new PendingIntent and add it to the AlarmManager
        Intent intent = new Intent(getActivity(), ReminderActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),
                12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am =
                (AlarmManager)getActivity().getSystemService(Activity.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP,
                wakeUpTime.getTimeInMillis(),                pendingIntent);*/
    }


}