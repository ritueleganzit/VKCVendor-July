package com.eleganzit.vkcvendor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.vkcvendor.api.RetrofitAPI;
import com.eleganzit.vkcvendor.api.RetrofitInterface;
import com.eleganzit.vkcvendor.model.AddHourWiseResponse;
import com.eleganzit.vkcvendor.model.ListSlotsResponse;
import com.eleganzit.vkcvendor.model.OTPResponse;
import com.eleganzit.vkcvendor.model.grid.Articledatum;
import com.eleganzit.vkcvendor.model.grid.Datum;
import com.eleganzit.vkcvendor.model.grid.GridDatum;
import com.eleganzit.vkcvendor.model.grid.SingleArtResponse;
import com.eleganzit.vkcvendor.model.plan.Grid;
import com.eleganzit.vkcvendor.util.UserLoggedInSession;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HourWiseActivity extends AppCompatActivity {
    RecyclerView rc_hour;
    String pur_doc_num, article, lineid, linename;
    ArrayList<GridDatum> arrayList = new ArrayList<>();
    UserLoggedInSession userLoggedInSession;
    ProgressDialog progressDialog;
    TextView po_number, artname, linnum;
    LinearLayout savelin;
    EditText ed_selectstarttime;
ArrayList<String> gridproduced;
LinearLayout check;
ImageView imgcheck;
ArrayList<String> grid_value;
    private String markascomplete="1";
    boolean chk=false;
    String stateid,start_time,end_time,curdate,item;
    List<String> stateArrayList=new ArrayList();
    List<String> stateArrayListnum=new ArrayList();
    List<String> slot_start_time=new ArrayList();
    List<String> slot_end_time=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hour_wise);
        rc_hour = findViewById(R.id.rc_hour_list);
        check = findViewById(R.id.check);
        imgcheck = findViewById(R.id.imgcheck);
        article = getIntent().getStringExtra("article");
        item = getIntent().getStringExtra("item");
        pur_doc_num = getIntent().getStringExtra("pur_doc_num");
        linename = getIntent().getStringExtra("linename");
        lineid = getIntent().getStringExtra("lineid");
        userLoggedInSession = new UserLoggedInSession(HourWiseActivity.this);
        savelin = findViewById(R.id.savelin);
        po_number = findViewById(R.id.po_number);
        artname = findViewById(R.id.artname);
        linnum = findViewById(R.id.linnum);
        ed_selectstarttime = findViewById(R.id.ed_selectstarttime);
        progressDialog = new ProgressDialog(HourWiseActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HourWiseActivity.this, LinearLayoutManager.VERTICAL, false);
        rc_hour.setLayoutManager(layoutManager);

        //Toast.makeText(this, ""+item, Toast.LENGTH_SHORT).show();
        //RecyclerViewWithFooterAdapter

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk)
                {
                    chk=false;
                    markascomplete="1";
                    imgcheck.setImageDrawable(getResources().getDrawable(R.mipmap.ic_uncheck));

                }
                else
                {
                    chk=true;
                    markascomplete="2";
                    imgcheck.setImageDrawable(getResources().getDrawable(R.mipmap.ic_checkbox));

                }
            }
        });
        getGridData(pur_doc_num, article);

findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        onBackPressed();
    }
});

        ed_selectstarttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final ListAdapter adapter = new ArrayAdapter(HourWiseActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, stateArrayList);

                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(new ContextThemeWrapper(HourWiseActivity.this, R.style.AlertDialogCustom));

                builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
                        curdate=curFormater.format(new Date());
                        ed_selectstarttime.setText(stateArrayList.get(i));
                        stateid=""+stateArrayListnum.get(i);
                        start_time=curdate+" "+slot_start_time.get(i);
                        end_time=curdate+" "+slot_end_time.get(i);
                        Log.d("sdfsfsfsdfs",""+curFormater.format(new Date()));
                        Log.d("sdfsfsfsdfs","--"+start_time);
                         //Toast.makeText(HourWiseActivity.this, start_time+"---"+end_time+"---"+stateid, Toast.LENGTH_SHORT).show();




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
                        Toast.makeText(HourWiseActivity.this, "No time slot available", Toast.LENGTH_SHORT).show();
                    }
                }


               /* SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                        "Select start date and time",
                        "OK",
                        "Cancel"
                );
                dateTimeDialogFragment.startAtCalendarView();
                dateTimeDialogFragment.set24HoursMode(true);
                dateTimeDialogFragment.setMinimumDateTime(Calendar.getInstance().getTime());
                dateTimeDialogFragment.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());
                dateTimeDialogFragment.setDefaultDateTime(Calendar.getInstance().getTime());
                try {
                    dateTimeDialogFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("dd MMMM", Locale.getDefault()));
                } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
                    Log.e("Hourly", e.getMessage());
                }

                dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Date date) {
                        // Date is get on positive button click
                        // Do something
                        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        ed_selectstarttime.setText("" + curFormater.format(date));

                        long milli = date.getTime() + 3600000;

                        Log.d("selectedmili", date.getTime() + "   " + milli);

                        Date date1 = new Date(milli);


                        //ed_selectendtime.setText(curFormater.format(date1));


                    }

                    @Override
                    public void onNegativeButtonClick(Date date) {
                        // Date is get on negative button click
                    }
                });

                dateTimeDialogFragment.show(getSupportFragmentManager(), "dialog_time");*/
            }

        });

        linnum.setText("" + linename);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSlotList();
    }

    private void getGridData(String pur_doc_num, String article) {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SingleArtResponse> call = myInterface.getHourswiseEntry(pur_doc_num, article,item);
        call.enqueue(new Callback<SingleArtResponse>() {
            @Override
            public void onResponse(Call<SingleArtResponse> call, Response<SingleArtResponse> response) {

                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().getData() != null) {

                        Log.d("dfgds", "" + response.body().getData().size());
                        for (int i = 0; i < response.body().getData().size(); i++) {

                            Log.d("dfgsfsfds", "" + response.body().getData().get(i).getArticledata().get(i).getGridData().size());

                            rc_hour.setAdapter(new HourGridAdapter(response.body().getData().get(i).getArticledata().get(i).getGridData(),HourWiseActivity.this));
                            po_number.setText(response.body().getData().get(i).getPurDocNum());
                            artname.setText(response.body().getData().get(i).getArticleName()+"-"+item);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<SingleArtResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(HourWiseActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public class HourGridAdapter extends RecyclerView.Adapter<HourGridAdapter.MyViewHolder> {

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

            int total= Integer.parseInt(pNumber.getScheduledQuantity());
            int produced= Integer.parseInt(pNumber.getQualityProduced());
            int pending=total-produced;


            if (pending < 0) {
                holder.overproductiontr.setVisibility(View.VISIBLE);
                holder.overproduction.setText(""+Math.abs(pending));
                holder.scheduled_quantity.setText("0(Total Qty:"+pNumber.getScheduledQuantity()+")");
               // pNumber.setQualityProduced("0");

                Log.d("cafafa","negative"+pending);
                // negative
            } else {
                holder.overproductiontr.setVisibility(View.GONE);
                holder.scheduled_quantity.setText(pending+"(Total Qty:"+pNumber.getScheduledQuantity()+")");

                Log.d("cafafa","positive"+pending);
                // it's a positive
            }
            holder.gridvalue.setText(pNumber.getGridValue());
            holder.quantity_produced.setText("0");
            pNumber.setQualityProduced("0");
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

            savelin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (ed_selectstarttime.getText().toString().equals("")) {
                        Toast.makeText(HourWiseActivity.this, "Please Select Slot", Toast.LENGTH_SHORT).show();

                        YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(ed_selectstarttime);

                        ed_selectstarttime.requestFocus();
                    }
                    else
                    {
                        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
                        curdate=curFormater.format(new Date());
                        //ed_selectstarttime.setText(stateArrayList.get(i));
                        if (stateArrayListnum!=null)
                        {
                            if (stateArrayListnum.size()==1)
                            {
                                stateid=""+stateArrayListnum.get(0);
                                start_time=curdate+" "+slot_start_time.get(0);
                                end_time=curdate+" "+slot_end_time.get(0);
                            }
                        }

                        if (markascomplete.equalsIgnoreCase("2"))
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(HourWiseActivity.this);
                            builder.setMessage("Are you sure you want to mark article as complete");

                            // add a button
                            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    gridproduced=new ArrayList<>();
                                    grid_value=new ArrayList<>();
                                    for (int i=0;i<gridData.size();i++)
                                    {
                                        gridproduced.add(gridData.get(i).getQualityProduced());
                                        grid_value.add(gridData.get(i).getGridValue());

                                    }
                                    Log.d("sssssssssssss",grid_value+""+gridproduced);
                                    addHourlyArticleData();
                                }
                            });

                            // create and show the alert dialog
                            AlertDialog dialog = builder.create();
                            dialog.show();

                            /* Log.d("afasghasd",po_number+"   "+line_id+"   "+article_name+"  "+sb+"   "+sb1+"    "+imagesList);*/

                        }
                        else {
                            gridproduced=new ArrayList<>();
                            grid_value=new ArrayList<>();
                            for (int i=0;i<gridData.size();i++)
                            {
                                gridproduced.add(gridData.get(i).getQualityProduced());
                                grid_value.add(gridData.get(i).getGridValue());

                            }
                            Log.d("sssssssssssss",grid_value+""+gridproduced);
                            addHourlyArticleData();
                        }
                       // Toast.makeText(HourWiseActivity.this, ""+lineid, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return gridData.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView gridvalue,scheduled_quantity,overproduction;
            EditText quantity_produced;
            TableRow overproductiontr;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                gridvalue=itemView.findViewById(R.id.gridvalue);
                scheduled_quantity=itemView.findViewById(R.id.scheduled_quantity);
                overproduction=itemView.findViewById(R.id.overproduction);
                overproductiontr=itemView.findViewById(R.id.overproductiontr);

                quantity_produced=itemView.findViewById(R.id.quantity_produced);

            }
        }
    }



public void getSlotList(){

    progressDialog.show();
    Date c = Calendar.getInstance().getTime();
    System.out.println("Current time => " + c);

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = df.format(c);
    RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
    Log.d("sfds",""+userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID)+" "+item+" "+article+" "+pur_doc_num);
    Call<ListSlotsResponse> call=myInterface.listSlots(formattedDate,userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID),item,article,pur_doc_num);
    call.enqueue(new Callback<ListSlotsResponse>() {
        @Override
        public void onResponse(Call<ListSlotsResponse> call, Response<ListSlotsResponse> response) {
            progressDialog.dismiss();
            if (response.isSuccessful()) {
                Log.d("asffsafdsfs","if"+response.body().getData());

                if (response.body().getData()!=null)
                {

                    if (response.body().getData().size()>0)
                    {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            stateArrayList.add(response.body().getData().get(i).getSlotName());
                            stateArrayListnum.add(response.body().getData().get(i).getSlotId());
                            slot_start_time.add(response.body().getData().get(i).getSlotStartTime());
                            slot_end_time.add(response.body().getData().get(i).getSlotEndTime());
                            ed_selectstarttime.setText(stateArrayList.get(0));
                            SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
                            curdate=curFormater.format(new Date());
                            stateid = "" + stateArrayListnum.get(0);
                            start_time = curdate+" " + slot_start_time.get(0);
                            end_time = curdate+" " + slot_end_time.get(0);

                        }


                    }
                }
            }
            else
            {
                Log.d("asffsafdsfs","else");

            }
        }

        @Override
        public void onFailure(Call<ListSlotsResponse> call, Throwable t) {
            progressDialog.dismiss();
            Toast.makeText(HourWiseActivity.this,"Server or Internet Error", Toast.LENGTH_SHORT).show();

            Log.d("asffsafdsfs","asdsadf"+t.getMessage());

        }
    });

}

    private void updateHourslot() {
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<OTPResponse> call=myInterface.updateHourslot(stateid);
        call.enqueue(new Callback<OTPResponse>() {
            @Override
            public void onResponse(Call<OTPResponse> call, Response<OTPResponse> response) {
              if (response.isSuccessful())
              {
                  progressDialog.dismiss();
                  Toast.makeText(HourWiseActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                  startActivity(new Intent(HourWiseActivity.this,HourWiseEntry.class).putExtra("pur_doc_num",pur_doc_num));


                  overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                  finish();
              }
              else
              {
                  progressDialog.dismiss();
              }
            }

            @Override
            public void onFailure(Call<OTPResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    private void addHourlyArticleData() {

        StringBuilder sb = new StringBuilder();
        for (int i=0;i<gridproduced.size();i++)
        {
            if (i==gridproduced.size()-1)
            {
                sb.append(gridproduced.get(i)).append("");
            }
            else {
                sb.append(gridproduced.get(i)).append(",");

            }
        }StringBuilder sb1 = new StringBuilder();
        for (int i=0;i<grid_value.size();i++)
        {
            if (i==grid_value.size()-1)
            {
                sb1.append(grid_value.get(i)).append("");
            }
            else {
                sb1.append(grid_value.get(i)).append(",");

            }
        }
        String art[]=artname.getText().toString().split("-");

       /* Log.d("ddddddddddddd",""+start_time);
        Log.d("ddddddddddddd",""+end_time);
        Log.d("ddddddddddddd",""+pur_doc_num);
        Log.d("ddddddddddddd",""+lineid);
        Log.d("ddddddddddddd","art"+art[0]);
        Log.d("ddddddddddddd","item"+art[1]);*/
        Log.d("ddddddddddddd",""+sb);
        Log.d("ddddddddddddd",""+sb1);
        Log.d("ddddddddddddd",""+ed_selectstarttime.getText().toString());

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<AddHourWiseResponse> call = myInterface.addHourlyArticleData(pur_doc_num,lineid,art[1],art[0],start_time,end_time,""+sb1,""+sb,markascomplete);
        call.enqueue(new Callback<AddHourWiseResponse>() {
            @Override
            public void onResponse(Call<AddHourWiseResponse> call, Response<AddHourWiseResponse> response) {
                if (response.isSuccessful())
                {
                    updateHourslot();

                }
                else
                {
                    Toast.makeText(HourWiseActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddHourWiseResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(HourWiseActivity.this, "Server or Internet error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
