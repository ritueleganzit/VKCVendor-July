package com.eleganzit.vkcvendor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.vkcvendor.model.PhotoData;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {
    RecyclerView your_rv_2;
    ImageView full_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        full_image=findViewById(R.id.full_image);
        your_rv_2=findViewById(R.id.your_rv_2);
        RecyclerView.LayoutManager layoutManager1=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        your_rv_2.setLayoutManager(layoutManager1);
        your_rv_2.setTag(1);
        ArrayList<String> arrayList1=getIntent().getStringArrayListExtra("gallery");
        if (arrayList1!=null) {

            your_rv_2.setAdapter(new rcAdapter2(arrayList1, this));
            Glide
                    .with(GalleryActivity.this)
                    .load(arrayList1.get(0))
                    .into(full_image);

        }
        ArrayList<String> arrayList2 = getIntent().getExtras().getStringArrayList("images");

        ArrayList<PhotoData> arrayList3 = new ArrayList<>();
        if (arrayList2!=null)
        {
            for(int i=0;i<arrayList2.size();i++)
            {
                PhotoData photoData=new PhotoData(arrayList2.get(i));

                arrayList3.add(photoData);
            }
        }


        if (arrayList2!=null)
        {
            your_rv_2.setAdapter(new rcAdapter2(arrayList2, this));

                Glide
                        .with(GalleryActivity.this)
                        .load(arrayList2.get(0))
                        .into(full_image);



        }

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public class rcAdapter2 extends RecyclerView.Adapter<rcAdapter2.MyViewHolder>
    {
        ArrayList<String> arrayList;
        Context context;
        int selectedPosition=-1;


        public rcAdapter2(ArrayList<String> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_gallery,viewGroup,false);
            MyViewHolder myViewHolder=new MyViewHolder(v);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {



            Glide
                    .with(context)
                    .load(arrayList.get(i))
                    .into(myViewHolder.img2);

myViewHolder.img2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Glide
                .with(context)
                .load(arrayList.get(i))
                .into(full_image);
    }
});

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {


            ImageView img2;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                img2=itemView.findViewById(R.id.img2);

            }
        }

    }
}
