package com.eleganzit.vkcvendor.fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.vkcvendor.HourWiseActivity;
import com.eleganzit.vkcvendor.R;
import com.eleganzit.vkcvendor.adapter.CompletedPOAdapter;
import com.eleganzit.vkcvendor.api.RetrofitAPI;
import com.eleganzit.vkcvendor.api.RetrofitInterface;
import com.eleganzit.vkcvendor.model.searchCompletedPO.SearchCompletedPOResponse;
import com.eleganzit.vkcvendor.util.UserLoggedInSession;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eleganzit.vkcvendor.HomeActivity.tablayout;
import static com.eleganzit.vkcvendor.HomeActivity.textTitle;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedPoFragment extends Fragment {

    RecyclerView rc_completed_list;
    ArrayList<String> arrayList=new ArrayList<>();
    public CompletedPoFragment() {
        // Required empty public constructor
    }
    TextInputEditText edstartDate, edenddate;
    UserLoggedInSession userLoggedInSession;
    LinearLayout save;
    String vendor_id;
    int mYear, mMonth, mDay;
    ProgressBar progressBar;
    TextView txt_found;
    CardView card_found;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tablayout.setVisibility(View.GONE);
        textTitle.setText("COMPLETED PO");

        View v = inflater.inflate(R.layout.fragment_completed_po, container, false);
        userLoggedInSession=new UserLoggedInSession(getActivity());
        vendor_id=userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID);
        rc_completed_list = v.findViewById(R.id.rc_completed_list);
        edstartDate = v.findViewById(R.id.edstartdate);
        edenddate = v.findViewById(R.id.edenddate);
        txt_found = v.findViewById(R.id.txt_found);
        progressBar = v.findViewById(R.id.progressBar);
        save = v.findViewById(R.id.save);
        card_found = v.findViewById(R.id.card_found);
        card_found.setVisibility(View.GONE);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rc_completed_list.setLayoutManager(layoutManager);

        edstartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                edstartDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }

        });
        edenddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                edenddate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();

            }

        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edstartDate.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please select start date", Toast.LENGTH_SHORT).show();

                    YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(edstartDate);

                    edstartDate.requestFocus();
                }
                else if (edenddate.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please select end date", Toast.LENGTH_SHORT).show();

                    YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(edenddate);

                    edenddate.requestFocus();

                }
                else
                {
                    searchcompletedPO();
                }
            }
        });

        return v;
    }
    private void searchcompletedPO() {
        progressBar.setVisibility(View.VISIBLE);
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SearchCompletedPOResponse> call = myInterface.searchcompletedPO(edstartDate.getText().toString(), edenddate.getText().toString(),vendor_id);
        call.enqueue(new Callback<SearchCompletedPOResponse>() {
            @Override
            public void onResponse(Call<SearchCompletedPOResponse> call, Response<SearchCompletedPOResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {

                        if (response.body().getData()!=null) {
                            card_found.setVisibility(View.VISIBLE);
                            txt_found.setText("Found " + response.body().getData().get(0).getCountData() + " Records");

                            rc_completed_list.setAdapter(new CompletedPOAdapter(response.body().getData(), getActivity()));
                        }

                    } else {
                        card_found.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<SearchCompletedPOResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Server or Internet Error" , Toast.LENGTH_SHORT).show();
            }
        });

    }

}
