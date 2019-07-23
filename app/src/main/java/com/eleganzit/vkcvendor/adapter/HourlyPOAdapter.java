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
import com.eleganzit.vkcvendor.model.griddata.GridData;

import java.util.List;

public class HourlyPOAdapter extends RecyclerView.Adapter<HourlyPOAdapter.MyViewHolder> {

    List<GridData> campaigns;
    Context context;
    Activity activity;

    public HourlyPOAdapter(List<GridData> campaigns, Context context) {
        this.campaigns = campaigns;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_hourlypo,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {
GridData gridData=campaigns.get(i);
holder.grid_value.setText(""+gridData.getGridValue());
holder.scheduled_quantity.setText(""+gridData.getScheduledQuantity());
holder.quality_produced.setText(""+gridData.getQualityProduced());
holder.start_time.setText(""+gridData.getStartTime());
holder.end_time.setText(""+gridData.getEndTime());

    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView grid_value,scheduled_quantity,quality_produced,start_time,end_time,complete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            grid_value=itemView.findViewById(R.id.grid_value);
            scheduled_quantity=itemView.findViewById(R.id.scheduled_quantity);
            quality_produced=itemView.findViewById(R.id.quality_produced);
            start_time=itemView.findViewById(R.id.start_time);
            end_time=itemView.findViewById(R.id.end_time);
            complete=itemView.findViewById(R.id.complete);

        }
    }
}
