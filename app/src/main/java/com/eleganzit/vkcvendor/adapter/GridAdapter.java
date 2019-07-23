package com.eleganzit.vkcvendor.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eleganzit.vkcvendor.R;
import com.eleganzit.vkcvendor.model.plan.Article;
import com.eleganzit.vkcvendor.model.plan.Grid;

import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder> {

   List<Grid> campaigns;
    Context context;
    Activity activity;

    public GridAdapter(List<Grid> campaigns, Context context) {
        this.campaigns = campaigns;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_grid,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        Grid pNumber=campaigns.get(i);
        if (i==0)
        {
            holder.txtgrid.setText(" - "+pNumber.getGridValue());

        }
        else
        {
            holder.txtgrid.setText(", "+pNumber.getGridValue());

        }
holder.txtgridnum.setText(pNumber.getScheduledQuantity());


    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
TextView txtgrid,txtgridnum;
RecyclerView rc_grid;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtgrid=itemView.findViewById(R.id.txtgrid);
            txtgridnum=itemView.findViewById(R.id.txtgridnum);

        }
    }
}
