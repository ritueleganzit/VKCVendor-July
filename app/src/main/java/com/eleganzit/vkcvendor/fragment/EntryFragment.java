package com.eleganzit.vkcvendor.fragment;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eleganzit.vkcvendor.ReminderActivity;
import com.eleganzit.vkcvendor.adapter.EntryAdapter;
import com.eleganzit.vkcvendor.adapter.PlanAdapter;
import com.eleganzit.vkcvendor.R;
import com.eleganzit.vkcvendor.api.RetrofitAPI;
import com.eleganzit.vkcvendor.api.RetrofitInterface;
import com.eleganzit.vkcvendor.model.plan.PNumber;
import com.eleganzit.vkcvendor.model.plan.PlanResponse;
import com.eleganzit.vkcvendor.util.UserLoggedInSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eleganzit.vkcvendor.HomeActivity.tablayout;
import static com.eleganzit.vkcvendor.HomeActivity.textTitle;

/**
 * A simple {@link Fragment} subclass.
 */
public class EntryFragment extends Fragment {
    RecyclerView rc_entry;

    ProgressDialog progressDialog;
    LinearLayout lin_nodata;
    UserLoggedInSession userLoggedInSession;
    ArrayList<PNumber> arrayList=new ArrayList<>();
    public EntryFragment() {
        // Required empty public constructor
    }

    int hour,min;
    String AM_PM;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tablayout.setVisibility(View.VISIBLE);
        textTitle.setText("HOME");
        View v=inflater.inflate(R.layout.fragment_entry, container, false);
        userLoggedInSession = new UserLoggedInSession(getActivity());
        lin_nodata=v.findViewById(R.id.lin_nodata);
        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        rc_entry=v.findViewById(R.id.rc_entry);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rc_entry.setLayoutManager(layoutManager);

        getAllPoDetail();
compareDates();
        return v;
    }

    private void compareDates(){


    }


    private void getAllPoDetail() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<PlanResponse> call = myInterface.getAllPoDetail(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID));
        call.enqueue(new Callback<PlanResponse>() {
            @Override
            public void onResponse(Call<PlanResponse> call, Response<PlanResponse> response) {


                List<PNumber> arrayList=new ArrayList<>();

                if (response.isSuccessful()) {
                    Log.d("EntryTab","if success");

                    progressDialog.dismiss();
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        if (response.body().getData()!=null)
                        {
                            Log.d("EntryTab","hasdata");
                            lin_nodata.setVisibility(View.GONE);
                            rc_entry.setVisibility(View.VISIBLE);


                            for(int i=0;i<response.body().getData().size();i++)
                            {

                                if (response.body().getData().get(i).getMapping().contains("yes"))
                                {
                                    Log.d("responseee","-"+response.body().getData().get(i).getMapping());
                                }
                                else
                                {
                                    Log.d("responseee","--"+response.body().getData().get(i).getMapping());

                                }
                                if(response.body().getData().get(i).getMapping().equalsIgnoreCase("yes"))
                                {
                                    arrayList.add(response.body().getData().get(i));
                                }

                            }
                            rc_entry.setAdapter(new EntryAdapter(arrayList, getActivity(),userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_NAME)));

                        }
                        else
                        {
                            Log.d("EntryTab","data");

                            progressDialog.dismiss();
                            lin_nodata.setVisibility(View.VISIBLE);
                            rc_entry.setVisibility(View.GONE);
                        }


                    }
                    else
                    {
                        Log.d("EntryTab","status");

                        progressDialog.dismiss();
                        lin_nodata.setVisibility(View.VISIBLE);
                        rc_entry.setVisibility(View.GONE);
                    }
                }
                else
                {
                    Log.d("EntryTab","else");
                    lin_nodata.setVisibility(View.VISIBLE);
                    rc_entry.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<PlanResponse> call, Throwable t) {
                progressDialog.dismiss();
                lin_nodata.setVisibility(View.VISIBLE);
                rc_entry.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
