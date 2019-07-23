package com.eleganzit.vkcvendor;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.vkcvendor.adapter.ArticleAdapter;
import com.eleganzit.vkcvendor.adapter.PlanAdapter;
import com.eleganzit.vkcvendor.api.RetrofitAPI;
import com.eleganzit.vkcvendor.api.RetrofitInterface;
import com.eleganzit.vkcvendor.model.ArticleSelection;
import com.eleganzit.vkcvendor.model.article.ArticleResponse;
import com.eleganzit.vkcvendor.model.articlemap.MapArticleResponse;
import com.eleganzit.vkcvendor.model.line.LineResponse;
import com.eleganzit.vkcvendor.util.UserLoggedInSession;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignToLineActivity extends AppCompatActivity {

    String stateId="",pur_doc_num="",no_of_stitcher="",no_of_helper="",articles;
int articlecounts=0;
    List<String> stateArrayList=new ArrayList();
    List<String> stateArrayListnum=new ArrayList();

    List<String> articlelist=new ArrayList();
    List<String> itemlist=new ArrayList();
    List<String> articlelistnum=new ArrayList();
    MyArticleAdapter myArticleAdapter;

    ImageView addarticle,closearticle;
    TextInputEditText ed_line_number,arted;
    LinearLayout lineararticle2,save,cancel;
    RecyclerView rc_article_list;
    UserLoggedInSession userLoggedInSession;
    ProgressDialog progressDialog;
    private String stateid="0";
    ImageView addmanpower;
    boolean isblank=false;
    int total=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_to_line);
        //closearticle=findViewById(R.id.closearticle);
        userLoggedInSession=new UserLoggedInSession(AssignToLineActivity.this);
        pur_doc_num=getIntent().getStringExtra("pur_doc_num");
myArticleAdapter=new  MyArticleAdapter(articlelistnum,AssignToLineActivity.this);
        progressDialog=new ProgressDialog(AssignToLineActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        cancel=findViewById(R.id.cancel);
        addarticle=findViewById(R.id.addarticle);
        rc_article_list=findViewById(R.id.rc_article_list);
        addmanpower=findViewById(R.id.addmanpower);
        arted=findViewById(R.id.arted);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(AssignToLineActivity.this,LinearLayoutManager.VERTICAL,false);
        rc_article_list.setLayoutManager(layoutManager);
        rc_article_list.setAdapter(myArticleAdapter);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ed_line_number=findViewById(R.id.ed_line_number);
        lineararticle2=findViewById(R.id.lineararticle2);
        save=findViewById(R.id.save);
/*
        ed_line_number.setText(""+animals[0]);
*/
        addmanpower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d=new Dialog(AssignToLineActivity.this,
                        R.style.Theme_Dialog);
                d.setContentView(R.layout.manpower_dialog);

                TextView ok=d.findViewById(R.id.ok);
                TextView cancel=d.findViewById(R.id.cancel);
                final EditText ed_stitcher=d.findViewById(R.id.ed_stitcher);
                final EditText ed_helper=d.findViewById(R.id.ed_helper);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ed_stitcher.getText().toString().equals("")) {
                            ed_stitcher.setError(""+getResources().getString(R.string.Please_enter_stitcher));

                            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(ed_stitcher);

                            ed_stitcher.requestFocus();
                        }
                        else  if (ed_helper.getText().toString().equals("")) {
                            ed_helper.setError(""+getResources().getString(R.string.Please_enter_helper));

                            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(ed_helper);

                            ed_helper.requestFocus();
                        }
                        else
                        {
                            no_of_helper=""+ed_helper.getText().toString();
                            no_of_stitcher=""+ed_stitcher.getText().toString();
                            Toast.makeText(AssignToLineActivity.this, "Data Save", Toast.LENGTH_SHORT).show();
                            d.dismiss();


                        }

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();

                    }
                });

                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                if(!isFinishing())
                {
                    d.show();
                }
            }
        });
        arted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ListAdapter adapter = new ArrayAdapter(AssignToLineActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, articlelist);

                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(new ContextThemeWrapper(AssignToLineActivity.this, R.style.AlertDialogCustom));

                builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

String olddata=arted.getText().toString();
String newdata=articlelist.get(i);

                        arted.setText(newdata);
                        articlelist.remove(i);
                        articlelist.add(olddata);



                    }
                });

                if (articlelist.size()>0)
                    builder.show();
                else
                    Toast.makeText(AssignToLineActivity.this, "No articles left", Toast.LENGTH_SHORT).show();
            }


        });
        ed_line_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ListAdapter adapter = new ArrayAdapter(AssignToLineActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, stateArrayList);

                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(new ContextThemeWrapper(AssignToLineActivity.this, R.style.AlertDialogCustom));

                builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();


                        ed_line_number.setText(stateArrayList.get(i));
                        stateid=""+stateArrayListnum.get(i);
                       // Toast.makeText(AssignToLineActivity.this, "---"+stateid, Toast.LENGTH_SHORT).show();

                        no_of_helper="";
                        no_of_stitcher="";
                        if (articlelist.size()>0)
                        {
                            articlelist.clear();
                            getArticleList();

                        }
                        if (articlelistnum.size()>0)
                        {
                            articlelistnum.clear();
                            myArticleAdapter.campaigns.clear();
                            myArticleAdapter=new  MyArticleAdapter(articlelistnum,AssignToLineActivity.this);
rc_article_list.setAdapter(myArticleAdapter);
                        }


                    }
                });
                if (stateArrayList!=null)
                {
                    if (stateArrayList.size()>0)
                    {
                        builder.show();

                    }
                    else
                    {
                        Toast.makeText(AssignToLineActivity.this, "No Lines available", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        addarticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("ffffff",""+articlelist.size()+"  "+total);
                if((articlelist.size()>0) && (articlecounts!=total))
                {


                    articlelistnum.add("");
                    articlecounts++;
                    myArticleAdapter.notifyDataSetChanged();
                    Log.d("unyugh", articlecounts+"   "+"   "+articlelist+"   "+ articlelistnum);
                }
                else
                {
                    Toast.makeText(AssignToLineActivity.this, "No articles", Toast.LENGTH_SHORT).show();
                }

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
String art[]=arted.getText().toString().split("-");
                if (no_of_helper.equalsIgnoreCase("")) {
                    Toast.makeText(AssignToLineActivity.this, "Please Enter Manpower", Toast.LENGTH_SHORT).show();
                }
else if (no_of_stitcher.equalsIgnoreCase(""))
                {
                    Toast.makeText(AssignToLineActivity.this, "Please Enter Manpower", Toast.LENGTH_SHORT).show();

                }
                else
                {

                    for (String data:myArticleAdapter.campaigns)
                    {
                        if (data.equals(""))
                        {
                            isblank=true;
                            break;
                        }
                        else
                        {
                            isblank=false;
                        }


                    }

                    if (isblank)
                    {
                        Toast.makeText(AssignToLineActivity.this, "Please select article", Toast.LENGTH_SHORT).show();
                    }
                    else if (myArticleAdapter.campaigns==null)
                    {
                        Toast.makeText(AssignToLineActivity.this, "No ", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(AssignToLineActivity.this, "Save Data", Toast.LENGTH_SHORT).show();

                        StringBuilder sb = new StringBuilder();
                        StringBuilder sb2 = new StringBuilder();
                        if (myArticleAdapter.campaigns.size()>0)
                        {
                            sb.append(art[0]).append(",");
                            sb2.append(art[1]).append(",");

                        }
                        else
                        {
                            sb.append(art[0]);
                            sb2.append(art[1]);

                        }
                        for(int i=0;i<myArticleAdapter.campaigns.size();i++)
                        {
                            Log.d("productsssssssss",myArticleAdapter.campaigns.get(i)+"");
                            String arts[]=myArticleAdapter.campaigns.get(i).split("-");

                            if (i==myArticleAdapter.campaigns.size()-1)
                            {

                                sb.append(arts[0]).append("");
                                sb2.append(arts[1]).append("");
                            }
                            else {
                                sb.append(arts[0]).append(",");
                                sb2.append(arts[1]).append(",");

                            }
                        }
                        articles=""+sb;
                        String items=""+sb2;
                      //  Log.d("svcg",""+articles);
                        Log.d("svcg",""+articles);
                        Log.d("svcg",""+items);
                        Log.d("svcg","helper "+no_of_helper);
                        Log.d("svcg","stitcher "+no_of_stitcher);
                         addAssignLine(articles,items);

                    }

                    //finish();

                }




            }
        });
        /*closearticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineararticle2.setVisibility(View.GONE);

            }
        });*/

        getVendorLine();
        getArticleList();
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            onBackPressed();
            }
        });
    }

    private void getVendorLine() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);

        Call<LineResponse> call=myInterface.getVendorLine(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID));
        call.enqueue(new Callback<LineResponse>() {
            @Override
            public void onResponse(Call<LineResponse> call, Response<LineResponse> response) {


                if (response.isSuccessful())
                {
                    Log.d("stattelist","--"+response.body().getMessage()    );
                    if (response.body().getData()!=null){
if (response.body().getData().size()>0) {
    for (int i = 0; i < response.body().getData().size(); i++) {
        stateArrayList.add(response.body().getData().get(i).getLineNumber());
        stateArrayListnum.add(response.body().getData().get(i).getLineId());
        ed_line_number.setText(stateArrayList.get(0));
        stateid = "" + stateArrayListnum.get(0);

    }
}
}


                }
            }

            @Override
            public void onFailure(Call<LineResponse> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(AssignToLineActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getArticleList() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);

        Call<ArticleResponse> call=myInterface.getArticleList(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID),pur_doc_num);
        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {


                if (response.isSuccessful())
                {
                    Log.d("stattelist","--"+response.body().getMessage()    );

                    if (response.body().getData()!=null)
                    {
                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            articlelist.add(response.body().getData().get(i).getArticle()+"-"+response.body().getData().get(i).getItem());


                        }
                        arted.setText(articlelist.get(0));

                        articlelist.remove(0);
                        total=articlelist.size();
                        Log.d("stattelist","--"+articlelist    );
                    }


                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AssignToLineActivity.this, "Server errror", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AssignToLineActivity.this, HomeActivity.class));
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        finish();

    }

    public  class MyArticleAdapter extends RecyclerView.Adapter<MyArticleAdapter.MyViewHolder> {

     List<String> campaigns;
        Context context;
        Activity activity;

    public MyArticleAdapter(List<String> campaigns, Context context) {
            this.campaigns = campaigns;
            this.context = context;
            activity = (Activity) context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_addarticles,viewGroup,false);
            MyViewHolder myViewHolder=new MyViewHolder(v);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.tvarticle.setHint("Select Article "+(position+2));
            holder.tvarticle.setText(
                   campaigns.get(position));

            holder.tvarticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String olddata= holder.tvarticle.getText().toString();
                final ListAdapter adapter = new ArrayAdapter(AssignToLineActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, articlelist);

                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(new ContextThemeWrapper(AssignToLineActivity.this, R.style.AlertDialogCustom));

                builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
if (olddata!=null && !(olddata.isEmpty())) {
    articlelist.add(olddata);
}
                        String newdata=articlelist.get(i);

                        holder.tvarticle.setText(newdata);
                        campaigns.set(position,newdata);
                        articlelist.remove(i);


                    }
                });
                if (articlelist.size()>0)
                builder.show();
                else
                    Toast.makeText(context, "No articles left", Toast.LENGTH_SHORT).show();
            }
        });

holder.closearticle.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        removeAt(position,holder.tvarticle);
    }
});

        }

        private void removeAt(int position, TextInputEditText tvarticle) {

            campaigns.remove(position);
            articlecounts--;
            String olddata= tvarticle.getText().toString();
            if (olddata!=null && !(olddata.isEmpty())) {
                articlelist.add(olddata);

            }

            notifyItemRemoved(position);
            notifyItemRangeChanged(position, campaigns.size());
            notifyItemChanged(position);
        }

        @Override
        public int getItemCount() {
            return campaigns.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView closearticle;
            TextInputEditText tvarticle;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                closearticle=itemView.findViewById(R.id.closearticle);
                tvarticle=itemView.findViewById(R.id.tvarticle);

            }
        }
    }

    public  void addAssignLine(String art,String item)
    {
        Log.d("sdfsf",""+articles);
        Log.d("sdfsf",""+stateid);
        Log.d("sdfsf",""+pur_doc_num);
        Log.d("sdfsf",""+no_of_stitcher);
        Log.d("sdfsf",""+no_of_helper);

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
Call<MapArticleResponse> mapArticleResponseCall=myInterface.addAssignLine(art,item,stateid,pur_doc_num,no_of_stitcher,no_of_helper);
mapArticleResponseCall.enqueue(new Callback<MapArticleResponse>() {
    @Override
    public void onResponse(Call<MapArticleResponse> call, Response<MapArticleResponse> response) {
        progressDialog.dismiss();
        if (response.isSuccessful())
        {
            Toast.makeText(AssignToLineActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<MapArticleResponse> call, Throwable t) {
        progressDialog.dismiss();
        Toast.makeText(AssignToLineActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
    }
});


    }
}
