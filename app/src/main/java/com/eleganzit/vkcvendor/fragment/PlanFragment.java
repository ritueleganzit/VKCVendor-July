package com.eleganzit.vkcvendor.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eleganzit.vkcvendor.LoginActivity;
import com.eleganzit.vkcvendor.adapter.PlanAdapter;
import com.eleganzit.vkcvendor.R;
import com.eleganzit.vkcvendor.api.RetrofitAPI;
import com.eleganzit.vkcvendor.api.RetrofitInterface;
import com.eleganzit.vkcvendor.model.plan.PNumber;
import com.eleganzit.vkcvendor.model.plan.PlanResponse;
import com.eleganzit.vkcvendor.util.UserLoggedInSession;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                            for (int i=0;i<response.body().getData().size();i++)
                            {


                                if (response.body().getData().get(i).getMapping().equalsIgnoreCase("yes"))
                                {
                                    arrayList.add(0,response.body().getData().get(i));
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

}
