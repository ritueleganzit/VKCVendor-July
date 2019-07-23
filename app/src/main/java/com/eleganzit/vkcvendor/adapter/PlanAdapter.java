package com.eleganzit.vkcvendor.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.vkcvendor.AssignToLineActivity;
import com.eleganzit.vkcvendor.R;
import com.eleganzit.vkcvendor.api.RetrofitAPI;
import com.eleganzit.vkcvendor.api.RetrofitInterface;
import com.eleganzit.vkcvendor.fragment.MarkPOFragment;
import com.eleganzit.vkcvendor.model.CheckCompletePo;
import com.eleganzit.vkcvendor.model.plan.PNumber;
import com.eleganzit.vkcvendor.model.searchCompletedPO.CompletedPOData;
import com.eleganzit.vkcvendor.model.searcharticle.SerchArticleListResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eleganzit.vkcvendor.HomeActivity.tablayout;
import static com.eleganzit.vkcvendor.HomeActivity.textTitle;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.MyViewHolder> {
int row_index=-1;
   List<PNumber> campaigns;
   ArrayList<String> nocount;
    Context context;
    Activity activity;

    public PlanAdapter(List<PNumber> campaigns, Context context,ArrayList<String> nocount) {
        this.campaigns = campaigns;
        this.context = context;
        this.nocount = nocount;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_plan,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        final PNumber pNumber=campaigns.get(i);
        Log.d(campaigns.size()+"bbbbbbb",""+nocount.size());


        if (pNumber.getMapping().equalsIgnoreCase("yes")) {
            row_index=i;
            Log.d("fggfgdg",""+pNumber.getPurDocNum());

            holder.frame.setBackgroundColor(Color.TRANSPARENT);

        }
        else {

            if (nocount.size()==campaigns.size()) {
                holder.frame.setBackgroundColor(Color.TRANSPARENT);

            }
            else
            {
                holder.frame.setBackgroundColor(context.getResources().getColor(R.color.transparent));

            }
            /*for (int j = 0; j < campaigns.size(); j++) {


                if (campaigns.get(j).getMapping().equalsIgnoreCase("yes")) {

                    Log.d(campaigns.get(j).getMapping()+"sfasf",""+campaigns.get(j).getPurDocNum());
                    holder.frame.setBackgroundColor(context.getResources().getColor(R.color.transparent));


                } else {
                    Log.d(campaigns.get(j).getMapping()+"fggfgwerfwdg",""+campaigns.get(j).getPurDocNum());

                    holder.frame.setBackgroundColor(Color.TRANSPARENT);

                }
            }*/

        }


        holder.mark_as_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkCompletePo(pNumber);


            }
        });

        holder.ptxtnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean data=true;
                if (pNumber.getMapping().equalsIgnoreCase("yes")) {
                    context.startActivity(new Intent(context, AssignToLineActivity.class).putExtra("pur_doc_num",""+pNumber.getPurDocNum()));
                    activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                }
                else
                {

                    if (nocount.size()==campaigns.size()) {
                        context.startActivity(new Intent(context, AssignToLineActivity.class).putExtra("pur_doc_num",""+pNumber.getPurDocNum()));
                        activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                    }
                       /* for (int i=0;i<campaigns.size();i++) {
                        if (campaigns.get(i).getMapping().equalsIgnoreCase("yes")) {
data=true;
break;
                        } else {
                            data=false;

                        }
                    }
                    if (data)
                    {
                        context.startActivity(new Intent(context, AssignToLineActivity.class).putExtra("pur_doc_num",""+pNumber.getPurDocNum()));
                        activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                    }*/
                }

            }
        });

        holder.cardviewsuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean data=true;
                if (pNumber.getMapping().equalsIgnoreCase("yes")) {
                    context.startActivity(new Intent(context, AssignToLineActivity.class).putExtra("pur_doc_num",""+pNumber.getPurDocNum()));
                    activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                }

                      else {
                    if (nocount.size()==campaigns.size()) {
                        context.startActivity(new Intent(context, AssignToLineActivity.class).putExtra("pur_doc_num",""+pNumber.getPurDocNum()));
                        activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                    }
                       /* for (int i = 0; i < campaigns.size(); i++) {
                        if (campaigns.get(i).getMapping().equalsIgnoreCase("yes")) {
                            data=true;
                            break;
                        } else {
                            data=false;



                        }
                    }
                    Toast.makeText(context, ""+data, Toast.LENGTH_SHORT).show();

                    if (data)
                    {
                        context.startActivity(new Intent(context, AssignToLineActivity.class).putExtra("pur_doc_num",""+pNumber.getPurDocNum()));
                        activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                    }
                    else
                    {
                    }*/
                }

            }
        });

        holder.frame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean data=true;
                if (pNumber.getMapping().equalsIgnoreCase("yes")) {
                    context.startActivity(new Intent(context, AssignToLineActivity.class).putExtra("pur_doc_num",""+pNumber.getPurDocNum()));
                    activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                }
                else {
                    if (nocount.size()==campaigns.size()) {
                        context.startActivity(new Intent(context, AssignToLineActivity.class).putExtra("pur_doc_num",""+pNumber.getPurDocNum()));
                        activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                    }

                      /*  for (int i = 0; i < campaigns.size(); i++) {
                        if (campaigns.get(i).getMapping().equalsIgnoreCase("yes")) {
data=true;
                            break;
                        } else {
                             data=false;

                        }
                    }
                    if (data)
                    {
                        context.startActivity(new Intent(context, AssignToLineActivity.class).putExtra("pur_doc_num",""+pNumber.getPurDocNum()));
                        activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                    }*/
                }

            }
        });

        Log.d("dfsf",""+pNumber.getPurDocNum());
holder.ptxtnumber.setText(pNumber.getPurDocNum());
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        holder.rc_art.setLayoutManager(layoutManager);
        holder.rc_art.setAdapter(new ArticleAdapter(pNumber.getArticledata(),context));



    }

    private void checkCompletePo(final PNumber pNumber) {

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<CheckCompletePo> call=myInterface.checkCompletePo(pNumber.getPurDocNum());
        call.enqueue(new Callback<CheckCompletePo>() {
            @Override
            public void onResponse(Call<CheckCompletePo> call, Response<CheckCompletePo> response) {
                if (response.isSuccessful())
                {
                    if (response.body().getData().equalsIgnoreCase("yes"))
                    {
                        if (pNumber.getMapping().equalsIgnoreCase("yes")) {

                            tablayout.setVisibility(View.GONE);
                            textTitle.setText("MARK PO COMPLETE");
                            MarkPOFragment myPhotosFragment = new MarkPOFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("po_number", pNumber.getPurDocNum());
                            myPhotosFragment.setArguments(bundle);
                            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                                    .addToBackStack("HomeActivity")
                                    .replace(R.id.container, myPhotosFragment, "TAG")
                                    .commit();
                        }
                        else {

                            if (nocount.size()==campaigns.size())
                            {
                                tablayout.setVisibility(View.GONE);
                                textTitle.setText("MARK PO COMPLETE");
                                MarkPOFragment myPhotosFragment = new MarkPOFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("po_number", pNumber.getPurDocNum());
                                myPhotosFragment.setArguments(bundle);
                                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                                        .addToBackStack("HomeActivity")
                                        .replace(R.id.container, myPhotosFragment, "TAG")
                                        .commit();
                            }
                    /*for (int i=0;i<campaigns.size();i++)
                    {
                        if (campaigns.get(i).getMapping().equalsIgnoreCase("yes")){

                        }else
                        {
                            tablayout.setVisibility(View.GONE);
                            textTitle.setText("MARK PO COMPLETE");
                            MarkPOFragment myPhotosFragment = new MarkPOFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("po_number", pNumber.getPurDocNum());
                            myPhotosFragment.setArguments(bundle);
                            ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                                    .addToBackStack("HomeActivity")
                                    .replace(R.id.container, myPhotosFragment, "TAG")
                                    .commit();
                        }
                    }*/
                        }
                    }
                    else
                    {
                        Toast.makeText(context, "Please complete all the articles for this PO", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckCompletePo> call, Throwable t) {
                Toast.makeText(context, "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
CardView cardviewsuccess;
TextView ptxtnumber;
FrameLayout frame1,frame;
LinearLayout mark_as_complete;
RecyclerView rc_art;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            frame1=itemView.findViewById(R.id.frame1);
            frame=itemView.findViewById(R.id.frame);
            mark_as_complete=itemView.findViewById(R.id.mark_as_complete);
            cardviewsuccess=itemView.findViewById(R.id.cardviewsuccess);
            rc_art=itemView.findViewById(R.id.rc_art);
            ptxtnumber=itemView.findViewById(R.id.ptxtnumber);

        }
    }
}
