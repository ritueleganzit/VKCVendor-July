package com.eleganzit.vkcvendor;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.vkcvendor.api.RetrofitAPI;
import com.eleganzit.vkcvendor.api.RetrofitInterface;
import com.eleganzit.vkcvendor.model.LoginRespose;
import com.eleganzit.vkcvendor.model.Vendor;
import com.eleganzit.vkcvendor.util.DateUtils;
import com.eleganzit.vkcvendor.util.MyService;
import com.eleganzit.vkcvendor.util.UserLoggedInSession;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    LinearLayout login;
    TextView forgotpassword;
    ProgressDialog progressDialog;
    int hour[]={8,9,10,11,12,13,14,15,16,17,18,19,20};
    TextInputEditText ed_email, ed_pw;
    UserLoggedInSession userLoggedInSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login);
        ed_email = findViewById(R.id.ed_email);
        userLoggedInSession = new UserLoggedInSession(LoginActivity.this);

        ed_pw = findViewById(R.id.ed_pw);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        forgotpassword = findViewById(R.id.forgotpassword);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {

                    loginUser();
                }


            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    private void loginUser() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<LoginRespose> call = myInterface.loginUser(ed_email.getText().toString(), ed_pw.getText().toString());
        call.enqueue(new Callback<LoginRespose>() {
            @Override
            public void onResponse(Call<LoginRespose> call, Response<LoginRespose> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData() != null) {
                            String email, id, username, vendor_start_time = "", vendor_end_date = "";
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                email = response.body().getData().get(i).getVendorEmail();
                                id = response.body().getData().get(i).getVendorId();
                                username = response.body().getData().get(i).getVendorName();
                                vendor_start_time = response.body().getData().get(i).getVendorStartTime();
                                vendor_end_date = response.body().getData().get(i).getVendorEndTime();
                                userLoggedInSession.createLoginSession(email, id, username, vendor_start_time, vendor_end_date);

                            }
                         //   compareDate(vendor_start_time, vendor_end_date);
                            setAlarm();
                            //startService(new Intent(LoginActivity.this, MyService.class));
                           /* Calendar cal = Calendar.getInstance();

                            Intent intent = new Intent(LoginActivity.this, MyService.class);
                            PendingIntent pintent = PendingIntent
                                    .getBroadcast(LoginActivity.this, 0, intent, 0);

                            AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            // Start service every hour
                            alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                                    3540000, pintent);*/

                        }
                    } else {

                        Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<LoginRespose> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void compareDate(String start, String end) {
        Log.d("DateDate", "" + DateUtils.getCurrentHour());

        if (DateUtils.isHourInInterval(DateUtils.getCurrentHour(), start, end)) {
            Calendar wakeUpTime = Calendar.getInstance();
            wakeUpTime.add(Calendar.SECOND, 1000 * 60 * 60);

            //Create a new PendingIntent and add it to the AlarmManager
            Intent intent = new Intent(LoginActivity.this, ReminderActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(LoginActivity.this,
                    12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager am =
                    (AlarmManager) LoginActivity.this.getSystemService(Activity.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP,
                    wakeUpTime.getTimeInMillis(), pendingIntent);
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


    public boolean isValid() {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(ed_email.getText().toString());

        if (ed_email.getText().toString().equals("")) {
            ed_email.setError("" + getResources().getString(R.string.Please_enter_email));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(ed_email);

            ed_email.requestFocus();
            return false;
        } else if (!matcher.matches()) {
            ed_email.setError("" + getResources().getString(R.string.Please_Enter_Valid_Email));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(ed_email);

            ed_email.requestFocus();
            return false;
        } else if (ed_pw.getText().toString().equals("")) {

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(ed_pw);

            ed_pw.requestFocus();
            return false;
        }


        return true;
    }

    private void setAlarm() {

/*
      AlarmManager  alarmManager;

      for (int i=0;i<hour.length;i++)
      {
          alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);

          Intent intent = new Intent(getApplicationContext(), ReminderActivity.class);
          intent.setAction(Intent.ACTION_MAIN);
          intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

          PendingIntent  pIntent = PendingIntent.getService(this, hour[i], intent, PendingIntent.FLAG_UPDATE_CURRENT);
          Calendar calendar = Calendar.getInstance();
          calendar.set(Calendar.MILLISECOND, 0);
          calendar.set(Calendar.SECOND, 0);
          calendar.set(Calendar.MINUTE, 0);
          calendar.set(Calendar.HOUR_OF_DAY, hour[i]);

          Log.d("sdf","dsgd g"+calendar.getTimeInMillis());

          alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntent);



      }*/

        for (int i=0;i<hour.length;i++) {
            Intent intent = new Intent(getApplicationContext(), ReminderActivity.class);
            intent.setAction(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            // END_INCLUDE (intent_fired_by_alarm)

            // BEGIN_INCLUDE (pending_intent_for_alarm)
            // Because the intent must be fired by a system service from outside the application,
            // it's necessary to wrap it in a PendingIntent.  Providing a different process with
            // a PendingIntent gives that other process permission to fire the intent that this
            // application has created.
            // Also, this code creates a PendingIntent to start an Activity.  To create a
            // BroadcastIntent instead, simply call getBroadcast instead of getIntent.
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), hour[i],
                    intent, 0);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, hour[i]);

            // END_INCLUDE (pending_intent_for_alarm)

            // BEGIN_INCLUDE (configure_alarm_manager)
            // There are two clock types for alarms, ELAPSED_REALTIME and RTC.
            // ELAPSED_REALTIME uses time since system boot as a reference, and RTC uses UTC (wall
            // clock) time.  This means ELAPSED_REALTIME is suited to setting an alarm according to
            // passage of time (every 15 seconds, 15 minutes, etc), since it isn't affected by
            // timezone/locale.  RTC is better suited for alarms that should be dependant on current
            // locale.

            // Both types have a WAKEUP version, which says to wake up the device if the screen is
            // off.  This is useful for situations such as alarm clocks.  Abuse of this flag is an
            // efficient way to skyrocket the uninstall rate of an application, so use with care.
            // For most situations, ELAPSED_REALTIME will suffice.
            int alarmType = AlarmManager.ELAPSED_REALTIME;
            final int FIFTEEN_SEC_MILLIS = 15000;

            // The AlarmManager, like most system services, isn't created by application code, but
            // requested from the system.
            AlarmManager alarmManager = (AlarmManager)
                    getSystemService(ALARM_SERVICE);

            // setRepeating takes a start delay and period between alarms as arguments.
            // The below code fires after 15 seconds, and repeats every 15 seconds.  This is very
            // useful for demonstration purposes, but horrendous for production.  Don't be that dev.
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }


    }
}
