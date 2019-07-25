package com.eleganzit.vkcvendor;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.vkcvendor.adapter.PlanAdapter;
import com.eleganzit.vkcvendor.api.RetrofitAPI;
import com.eleganzit.vkcvendor.api.RetrofitInterface;
import com.eleganzit.vkcvendor.fragment.CompletedPoFragment;
import com.eleganzit.vkcvendor.fragment.EntryFragment;
import com.eleganzit.vkcvendor.fragment.MarkPOFragment;
import com.eleganzit.vkcvendor.fragment.PlanFragment;
import com.eleganzit.vkcvendor.fragment.ViewDefectsFragment;
import com.eleganzit.vkcvendor.model.plan.PlanResponse;
import com.eleganzit.vkcvendor.util.UserLoggedInSession;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    int hour[]={8,9,10,11,12,13,14,15,16,17,18,19,20};

    UserLoggedInSession userLoggedInSession;
public static TextView textTitle;
    TextView plan, entry, tv_defects;
    public static LinearLayout tablayout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        textTitle = findViewById(R.id.textTitle);
        tv_defects = findViewById(R.id.tv_defects);
        plan = findViewById(R.id.plan);
        tablayout = findViewById(R.id.tablayout);
        entry = findViewById(R.id.entry);
        setSupportActionBar(toolbar);
        userLoggedInSession = new UserLoggedInSession(HomeActivity.this);

        getSupportActionBar().setElevation(0);
        toolbar.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView text = (TextView) header.findViewById(R.id.vendor_name);
        text.setText("Welcome, "+userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_NAME));
        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plan.setBackgroundResource(R.drawable.transparentdark_bg);
                entry.setBackgroundResource(R.drawable.transparent_bg);
                tv_defects.setBackgroundResource(R.drawable.transparent_bg);

                PlanFragment myPhotosFragment = new PlanFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, myPhotosFragment, "TAG")
                        .commit();
            }
        });
        entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plan.setBackgroundResource(R.drawable.transparent_bg);
                entry.setBackgroundResource(R.drawable.transparentdark_bg);
                tv_defects.setBackgroundResource(R.drawable.transparent_bg);
                EntryFragment myPhotosFragment = new EntryFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, myPhotosFragment, "TAG")
                        .commit();
            }
        });
        tv_defects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plan.setBackgroundResource(R.drawable.transparent_bg);
                entry.setBackgroundResource(R.drawable.transparent_bg);
                tv_defects.setBackgroundResource(R.drawable.transparentdark_bg);
                ViewDefectsFragment myPhotosFragment = new ViewDefectsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, myPhotosFragment, "TAG")
                        .commit();
            }
        });
        textTitle.setText("HOME");

        tablayout.setVisibility(View.VISIBLE);
        plan.setBackgroundResource(R.drawable.transparentdark_bg);
        entry.setBackgroundResource(R.drawable.transparent_bg);
        tv_defects.setBackgroundResource(R.drawable.transparent_bg);

        PlanFragment myPhotosFragment = new PlanFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, myPhotosFragment, "TAG")
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();



    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            tablayout.setVisibility(View.VISIBLE);
            textTitle.setText("HOME");

            PlanFragment myPhotosFragment = new PlanFragment();
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack("HomeActivity")
                    .replace(R.id.container, myPhotosFragment, "TAG")
                    .commit();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(HomeActivity.this, MessageActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        } /*else if (id == R.id.markpo) {
            tablayout.setVisibility(View.GONE);
            textTitle.setText("MARK PO COMPLETE");
            MarkPOFragment myPhotosFragment = new MarkPOFragment();
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack("HomeActivity")
                    .replace(R.id.container, myPhotosFragment, "TAG")
                    .commit();
        }*/ else if (id == R.id.nav_manage) {

            tablayout.setVisibility(View.GONE);
            textTitle.setText("COMPLETED PO");
            CompletedPoFragment myPhotosFragment = new CompletedPoFragment();
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack("HomeActivity")
                    .replace(R.id.container, myPhotosFragment, "TAG")
                    .commit();

        }  else if (id == R.id.nav_logout) {
            userLoggedInSession.logoutUser();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.commit();

            for (int i=0;i<hour.length;i++) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                Intent myIntent = new Intent(getApplicationContext(), HomeActivity.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), hour[i], myIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.cancel(pendingIntent);
            }

            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            finish();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;


    }


    private void getAllPoDetail() {
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
                        if (response.body().getAssignCount().equalsIgnoreCase("1"))
                        {
                            entry.setEnabled(true);
                        }
                        else
                        {
                            entry.setEnabled(false);
                        }

                    }
                    else
                    {
                    }
                }
                else
                {
                }

            }

            @Override
            public void onFailure(Call<PlanResponse> call, Throwable t) {


            }
        });

    }
}