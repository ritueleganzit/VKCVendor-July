package com.eleganzit.vkcvendor.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.eleganzit.vkcvendor.R;
import com.eleganzit.vkcvendor.model.grid.GridDatum;
import com.eleganzit.vkcvendor.model.plan.Grid;

import java.util.List;

public class HourGridAdapter
{
/*extends RecyclerView.Adapter<HourGridAdapter.MyViewHolder> {

   List<GridDatum> gridData;
    Context context;
    Activity activity;

    public HourGridAdapter(List<GridDatum> gridData, Context context) {
        this.gridData = gridData;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_hour,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        final GridDatum pNumber=gridData.get(i);

        holder.gridvalue.setText(pNumber.getGridValue());
        holder.scheduled_quantity.setText("Total Qty:"+pNumber.getScheduledQuantity());
        holder.quantity_produced.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                pNumber.setQualityProduced(s.toString());

            }
        });
    }

    @Override
    public int getItemCount() {
        return gridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
TextView gridvalue,scheduled_quantity;
EditText quantity_produced;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            gridvalue=itemView.findViewById(R.id.gridvalue);
            scheduled_quantity=itemView.findViewById(R.id.scheduled_quantity);
            quantity_produced=itemView.findViewById(R.id.quantity_produced);

        }
    }*/
}
