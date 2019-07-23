package com.eleganzit.vkcvendor.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eleganzit.vkcvendor.AssignToLineActivity;
import com.eleganzit.vkcvendor.R;
import com.eleganzit.vkcvendor.model.plan.Article;
import com.eleganzit.vkcvendor.model.plan.PNumber;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {

   List<Article> campaigns;
    Context context;
    Activity activity;

    public ArticleAdapter(List<Article> campaigns, Context context) {
        this.campaigns = campaigns;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_article,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        Article pNumber=campaigns.get(i);

holder.txtart.setText(pNumber.getArticleName()+"-"+pNumber.getItem());
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager();
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        holder.rc_grid.setLayoutManager(layoutManager);
        holder.rc_grid.setAdapter(new GridAdapter(pNumber.getGridData(),context));
holder.rc_grid.setNestedScrollingEnabled(false);
    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
TextView txtart;
RecyclerView rc_grid;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtart=itemView.findViewById(R.id.txtart);
            rc_grid=itemView.findViewById(R.id.rc_grid);

        }
    }
}
