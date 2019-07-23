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


      AlarmManager  alarmManager;

      for (int i=0;i<hour.length;i++)
      {
          alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);

          Intent intent = new Intent("com.eleganzit.vkcvendor");
          // create an explicit intent by defining a class
          intent.setClass(getApplicationContext(), MyService.class);

          intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);


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
}
