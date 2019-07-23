package com.eleganzit.vkcvendor.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.eleganzit.vkcvendor.AssignToLineActivity;
import com.eleganzit.vkcvendor.HourWiseActivity;
import com.eleganzit.vkcvendor.HourWiseEntry;
import com.eleganzit.vkcvendor.R;
import com.eleganzit.vkcvendor.model.plan.PNumber;

import java.util.ArrayList;
import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.MyViewHolder> {


    List<PNumber> campaigns;
    Context context;
    Activity activity;
    String vendor_name;

    public EntryAdapter(List<PNumber> campaigns, Context context,String vendor_name) {
        this.campaigns = campaigns;
        this.context = context;
        this.vendor_name = vendor_name;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_entry,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        final PNumber pNumber = campaigns.get(i);
        holder.ptxtnumber.setText(pNumber.getPurDocNum());
        holder.vendor_name.setText(vendor_name);
        if (i == 0) {
            holder.txtRegister.setBackgroundColor(Color.parseColor("#2B9E30"));
            holder.cardviewsuccess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, HourWiseEntry.class).putExtra("pur_doc_num",pNumber.getPurDocNum()));
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });
            holder.frame1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, HourWiseEntry.class).putExtra("pur_doc_num",pNumber.getPurDocNum()));
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });
        } else {
            holder.txtRegister.setBackgroundColor(Color.parseColor("#88683e"));

            holder.frame.setBackgroundColor(context.getResources().getColor(R.color.transparent));

        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        holder.rc_art.setLayoutManager(layoutManager);
        holder.rc_art.setAdapter(new ArticleAdapter(pNumber.getArticledata(), context));
        holder.rc_art.setNestedScrollingEnabled(false);

    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardviewsuccess;
        FrameLayout frame,frame1;
        TextView ptxtnumber,vendor_name,txtRegister;
        RecyclerView rc_art;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardviewsuccess = itemView.findViewById(R.id.cardviewsuccess);
            frame = itemView.findViewById(R.id.frame);
            frame1 = itemView.findViewById(R.id.frame1);
            txtRegister = itemView.findViewById(R.id.txtRegister);
            rc_art = itemView.findViewById(R.id.rc_art);
            ptxtnumber = itemView.findViewById(R.id.ptxtnumber);
            vendor_name = itemView.findViewById(R.id.vendor_name);

        }
    }
}
