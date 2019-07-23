package com.eleganzit.vkcvendor;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.vkcvendor.api.RetrofitAPI;
import com.eleganzit.vkcvendor.api.RetrofitInterface;
import com.eleganzit.vkcvendor.model.poline.ArtLine;
import com.eleganzit.vkcvendor.model.poline.ArtLineResponse;
import com.eleganzit.vkcvendor.model.poline.POLineResponse;
import com.eleganzit.vkcvendor.util.UserLoggedInSession;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HourWiseEntry extends AppCompatActivity {
    List<String> stateArrayList=new ArrayList();
    List<String> stateArrayListnum=new ArrayList();
  List<String> articlelist;

    LinearLayout next;
    String pur_doc_num,lineid;
    TextInputEditText ed_line_number,arted;
    TextView pnumber;
    UserLoggedInSession userLoggedInSession;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hour_wise_entry);
        next=findViewById(R.id.next);
        arted=findViewById(R.id.arted);
        userLoggedInSession=new UserLoggedInSession(HourWiseEntry.this);
        progressDialog=new ProgressDialog(HourWiseEntry.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        pnumber=findViewById(R.id.pnumber);
        ed_line_number=findViewById(R.id.ed_line_number);
        pur_doc_num=getIntent().getStringExtra("pur_doc_num");
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_line_number.getText().toString().equals("")) {

                    YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(ed_line_number);

                }
                else if (arted.getText().toString().equals("")) {

                    YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(arted);

                }
                else
                {
                    String art[]=arted.getText().toString().split("-");

                    startActivity(new Intent(HourWiseEntry.this,HourWiseActivity.class)
                            .putExtra("pur_doc_num",pur_doc_num)
                            .putExtra("lineid",lineid)
                            .putExtra("linename",ed_line_number.getText().toString())
                            .putExtra("article",art[0])
                            .putExtra("item",art[1])

                    );
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                }

            }
        });
        pnumber.setText(pur_doc_num);
        arted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (articlelist.size()>0)
                {
                    final ListAdapter adapter = new ArrayAdapter(HourWiseEntry.this, android.R.layout.simple_list_item_1, android.R.id.text1, articlelist);

                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(new ContextThemeWrapper(HourWiseEntry.this, R.style.AlertDialogCustom));

                    builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();


                            arted.setText(articlelist.get(i));

                        }
                    });
                    builder.show();
                }
                else
                {
                    Toast.makeText(HourWiseEntry.this, "No Articles", Toast.LENGTH_SHORT).show();
                }


            }


        });
        ed_line_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stateArrayList.size()>0) {
                    final ListAdapter adapter = new ArrayAdapter(HourWiseEntry.this, android.R.layout.simple_list_item_1, android.R.id.text1, stateArrayList);

                    final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(new ContextThemeWrapper(HourWiseEntry.this, R.style.AlertDialogCustom));

                    builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();


                            ed_line_number.setText(stateArrayList.get(i));
                            lineid = "" + stateArrayListnum.get(i);


                            assignArticle(lineid);


                        }
                    });
                    builder.show();
                }
                else {

                }

            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        getPOLine();
    }

    private void getPOLine() {
        progressDialog.show();
        Log.d("sdfs",""+userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID));
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<POLineResponse> call=myInterface.assignLine(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID),pur_doc_num);
        call.enqueue(new Callback<POLineResponse>() {
            @Override
            public void onResponse(Call<POLineResponse> call, Response<POLineResponse> response) {
                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    Log.d("stattelist","--"+response.body().getMessage()    );
                if (response.body().getData()!=null)
                {
                    if (response.body().getData().size()>0) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            stateArrayList.add(response.body().getData().get(i).getLineNumber());
                            stateArrayListnum.add(response.body().getData().get(i).getLineId());


                        }
                        ed_line_number.setText(stateArrayList.get(0));
                        lineid = "" + stateArrayListnum.get(0);
                        assignArticle("" + stateArrayListnum.get(0));
                    }
                }


                }
            }

            @Override
            public void onFailure(Call<POLineResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }private void assignArticle(String lineid) {
        articlelist=new ArrayList();
        progressDialog.show();
        Log.d(lineid+"sdfs",""+userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID));
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<ArtLineResponse> call=myInterface.assignArticle(lineid,pur_doc_num);
        call.enqueue(new Callback<ArtLineResponse>() {
            @Override
            public void onResponse(Call<ArtLineResponse> call, Response<ArtLineResponse> response) {
                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    Log.d("asfsdf",""+response.body().getData());

                    if(response.body().getData()!=null)
                    {
                        if (response.body().getData().size()>0)
                        {
                            for (int i=0;i<response.body().getData().size();i++)
                            {
                                articlelist.add(response.body().getData().get(i).getArticle()+"-"+response.body().getData().get(i).getItem());
                                Log.d("asfsdf",""+response.body().getData().get(i).getArticle());
                            }
                            arted.setText(articlelist.get(0));
                        }
                        }




                }
            }

            @Override
            public void onFailure(Call<ArtLineResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,HomeActivity.class));
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        finish();

    }
}
