package com.eleganzit.vkcvendor;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eleganzit.vkcvendor.adapter.HourlyPOAdapter;
import com.eleganzit.vkcvendor.api.RetrofitAPI;
import com.eleganzit.vkcvendor.api.RetrofitInterface;
import com.eleganzit.vkcvendor.model.griddata.POGridResponse;
import com.eleganzit.vkcvendor.util.UserLoggedInSession;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HourlyProductionActivity extends AppCompatActivity {
RecyclerView rc_hourly;
    ArrayList<String> arrayList=new ArrayList<>();
    String txt_pur_doc_num, article, doc_date,item;
    UserLoggedInSession userLoggedInSession;
    LinearLayout lin_nodata;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_production);
        txt_pur_doc_num = getIntent().getStringExtra("pur_doc_num");
        article = getIntent().getStringExtra("article");
        item = getIntent().getStringExtra("item");
        lin_nodata = findViewById(R.id.lin_nodata);

       // doc_date = getIntent().getStringExtra("doc_date");
        userLoggedInSession = new UserLoggedInSession(HourlyProductionActivity.this);
        progressDialog = new ProgressDialog(HourlyProductionActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        rc_hourly=findViewById(R.id.rc_hourly);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(HourlyProductionActivity.this, LinearLayoutManager.VERTICAL,false);
        rc_hourly.setLayoutManager(layoutManager);


   findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           onBackPressed();
       }
   });
        pendingGrid();
    }

    private void pendingGrid() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<POGridResponse> call=myInterface.completedHourlyGrid(txt_pur_doc_num,item,article);
        call.enqueue(new Callback<POGridResponse>() {
            @Override
            public void onResponse(Call<POGridResponse> call, Response<POGridResponse> response) {
                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    if (response.body().getData()!=null)
                    {
                        lin_nodata.setVisibility(View.GONE);
                        rc_hourly.setVisibility(View.VISIBLE);
                        rc_hourly.setAdapter(new HourlyPOAdapter(response.body().getData(),HourlyProductionActivity.this));

                    }
                    else {
                        lin_nodata.setVisibility(View.VISIBLE);
                        rc_hourly.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<POGridResponse> call, Throwable t) {

                lin_nodata.setVisibility(View.VISIBLE);
                rc_hourly.setVisibility(View.GONE);
                Toast.makeText(HourlyProductionActivity.this, "Server or Internet Error" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
}
