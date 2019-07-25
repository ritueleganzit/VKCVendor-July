package com.eleganzit.vkcvendor.fragment;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eleganzit.vkcvendor.HomeActivity;
import com.eleganzit.vkcvendor.LoginActivity;
import com.eleganzit.vkcvendor.ReminderActivity;
import com.eleganzit.vkcvendor.adapter.PlanAdapter;
import com.eleganzit.vkcvendor.R;
import com.eleganzit.vkcvendor.api.RetrofitAPI;
import com.eleganzit.vkcvendor.api.RetrofitInterface;
import com.eleganzit.vkcvendor.model.plan.PNumber;
import com.eleganzit.vkcvendor.model.plan.PlanResponse;
import com.eleganzit.vkcvendor.util.UserLoggedInSession;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.ALARM_SERVICE;
import static com.eleganzit.vkcvendor.HomeActivity.tablayout;
import static com.eleganzit.vkcvendor.HomeActivity.textTitle;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanFragment extends Fragment {

    public PlanFragment() {
        // Required empty public constructor
    }
    List<PNumber> arrayList;

    RecyclerView rc_plan;
    int hour[]={8,9,10,11,12,13,14,15,16,17,18,19,20};
    LinearLayout lin_nodata;
    UserLoggedInSession userLoggedInSession;
    ProgressDialog progressDialog;
    ArrayList nocount=new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_plan, container, false);
        userLoggedInSession=new UserLoggedInSession(getActivity());
        tablayout.setVisibility(View.VISIBLE);
        textTitle.setText("HOME");

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        lin_nodata=v.findViewById(R.id.lin_nodata);
        rc_plan=v.findViewById(R.id.rc_plan);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rc_plan.setLayoutManager(layoutManager);

        getAllPoDetail();

        return v;
    }

    private void getAllPoDetail() {
        progressDialog.show();
        arrayList=new ArrayList<>();
        Log.d("sdfs",""+userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID));
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<PlanResponse> call=myInterface.getAllPoDetail(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID));
        call.enqueue(new Callback<PlanResponse>() {
            @Override
            public void onResponse(Call<PlanResponse> call, Response<PlanResponse> response) {
                if (response.isSuccessful())
                {


                    if (response.body().getStatus().toString().equalsIgnoreCase("1"))
                    {
                        if (response.body().getData()!=null)
                        {
                            lin_nodata.setVisibility(View.GONE);
                            rc_plan.setVisibility(View.VISIBLE);
                            if (response.body().getAssignCount().equalsIgnoreCase("1"))
                            {

                            }
                            else
                            {

                            }
                            for (int i=0;i<response.body().getData().size();i++)
                            {


                                if (response.body().getData().get(i).getMapping().equalsIgnoreCase("yes"))
                                {
                                    arrayList.add(0,response.body().getData().get(i));


                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                    if (!prefs.getBoolean("firstTime", false)) {
                                        // <---- run your one time code here
                                        setAlarm();

                                        Log.d("ddddd","caalled");
                                        // mark first time has ran.
                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.putBoolean("firstTime", true);
                                        editor.commit();
                                    }


                                }

                                else
                                {

                                    nocount.add(response.body().getData().get(i).getMapping());
                                    arrayList.add(response.body().getData().get(i));
                                }


                                Log.d("ccccc",""+response.body().getData().get(i).getArticledata().size());
                            }

                            Log.d("ccccc",""+arrayList.size());

                            rc_plan.setAdapter(new PlanAdapter(arrayList,getActivity(),nocount));
                            progressDialog.dismiss();

                        }
                        else
                        {
                            lin_nodata.setVisibility(View.VISIBLE);
                            rc_plan.setVisibility(View.GONE);
                        }

                    }
                    else
                    {
                        lin_nodata.setVisibility(View.VISIBLE);
                        rc_plan.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    }
                }
                else
                {

                    lin_nodata.setVisibility(View.VISIBLE);
                    rc_plan.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<PlanResponse> call, Throwable t) {
                progressDialog.dismiss();
                rc_plan.setVisibility(View.GONE);

                lin_nodata.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();

            }
        });

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
            Intent intent = new Intent(getActivity(), ReminderActivity.class);
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
            PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), hour[i],
                    intent, 0);
            Calendar now = Calendar.getInstance();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, hour[i]);
            long _alarm = 0;
            if(calendar.getTimeInMillis() <= now.getTimeInMillis())
                _alarm = calendar.getTimeInMillis() + (AlarmManager.INTERVAL_DAY+1);
            else
                _alarm = calendar.getTimeInMillis();

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
                    getActivity().getSystemService(ALARM_SERVICE);

            // setRepeating takes a start delay and period between alarms as arguments.
            // The below code fires after 15 seconds, and repeats every 15 seconds.  This is very
            // useful for demonstration purposes, but horrendous for production.  Don't be that dev.
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, _alarm, AlarmManager.INTERVAL_DAY, pendingIntent);
        }


    }
}
