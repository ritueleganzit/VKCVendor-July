package com.eleganzit.vkcvendor.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.vkcvendor.AssignToLineActivity;
import com.eleganzit.vkcvendor.GalleryActivity;
import com.eleganzit.vkcvendor.HourlyProductionActivity;
import com.eleganzit.vkcvendor.R;
import com.eleganzit.vkcvendor.model.searchCompletedPO.CompletedPOData;

import java.util.ArrayList;
import java.util.List;

public class CompletedPOAdapter extends RecyclerView.Adapter<CompletedPOAdapter.MyViewHolder> {
    List<CompletedPOData> campaigns;
    Context context;
    Activity activity;

    public CompletedPOAdapter(List<CompletedPOData> campaigns, Context context) {
        this.campaigns = campaigns;
        this.context = context;
        activity = (Activity) context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_completed_po, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        final CompletedPOData completedPOData=campaigns.get(i);

        holder.txt_po_number.setText(completedPOData.getPoDocNum());
        holder.txt_date.setText(completedPOData.getDate());
        holder.txt_line_number.setText(completedPOData.getLineNumber());
        holder.txt_article.setText(completedPOData.getArticle()+"-"+completedPOData.getItem());
        holder.txt_quantity.setText(completedPOData.getQuality_produced()+"");
        if (completedPOData.getCountDefect()!=null)
        {
            holder.txt_defects.setText(completedPOData.getCountDefect()+"");

        }

        final ArrayList<String> arrayList=new ArrayList<>();

        if (completedPOData.getJsonImage().getDataImage()!=null)
        {
            for(int j=0;j<completedPOData.getJsonImage().getDataImage().size();j++)
            {
                arrayList.add(completedPOData.getJsonImage().getDataImage().get(j).getDefectProductImage());
            }
            Log.d("efghsgdjsd",arrayList+"");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, HourlyProductionActivity.class)
                        .putExtra("pur_doc_num",completedPOData.getPoDocNum())
                        .putExtra("item",completedPOData.getItem())
                .putExtra("article",completedPOData.getArticle()));

            }
        });

        holder.txt_defects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (completedPOData.getCountDefect().toString().equalsIgnoreCase("0"))
                {
                    Toast.makeText(context, "No Defects", Toast.LENGTH_SHORT).show();
                }else {
                    context.startActivity(new Intent(context, GalleryActivity.class).putStringArrayListExtra("images",arrayList));

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView txt_po_number,txt_date,txt_line_number,txt_article,txt_quantity,txt_defects;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_po_number=itemView.findViewById(R.id.txt_po_number);
            txt_date=itemView.findViewById(R.id.txt_date);
            txt_line_number=itemView.findViewById(R.id.txt_line_number);
            txt_article=itemView.findViewById(R.id.txt_article);
            txt_quantity=itemView.findViewById(R.id.txt_quantity);
            txt_defects=itemView.findViewById(R.id.txt_defects);

        }
    }
}
