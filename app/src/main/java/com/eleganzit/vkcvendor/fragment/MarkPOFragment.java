package com.eleganzit.vkcvendor.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eleganzit.vkcvendor.AssignToLineActivity;
import com.eleganzit.vkcvendor.HomeActivity;
import com.eleganzit.vkcvendor.HourWiseActivity;
import com.eleganzit.vkcvendor.MarkPOCompleteActivity;
import com.eleganzit.vkcvendor.R;
import com.eleganzit.vkcvendor.adapter.RecyclerViewWithFooterAdapter;
import com.eleganzit.vkcvendor.api.RetrofitAPI;
import com.eleganzit.vkcvendor.api.RetrofitInterface;
import com.eleganzit.vkcvendor.api.uploadMultupleImage.CallAPiActivity;
import com.eleganzit.vkcvendor.api.uploadMultupleImage.GetResponse;
import com.eleganzit.vkcvendor.model.AddHourWiseResponse;
import com.eleganzit.vkcvendor.model.AllHourlyDetail.AllHourlyDetailData;
import com.eleganzit.vkcvendor.model.AllHourlyDetail.AllHourlyDetailResponse;
import com.eleganzit.vkcvendor.model.AllHourlyDetail.ArticleData;
import com.eleganzit.vkcvendor.model.AllHourlyDetail.GridData;
import com.eleganzit.vkcvendor.model.articlemap.MapArticleResponse;
import com.eleganzit.vkcvendor.model.grid.SingleArtResponse;
import com.eleganzit.vkcvendor.util.CustomGridLayoutManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eleganzit.vkcvendor.HomeActivity.tablayout;
import static com.eleganzit.vkcvendor.HomeActivity.textTitle;

/**
 * A simple {@link Fragment} subclass.
 */
public class MarkPOFragment extends Fragment {
    int hour[]={8,9,10,11,12,13,14,15,16,17,18,19,20};

    List<ArticleData> arrayList=new ArrayList<>();
    LinearLayout next,cancel;
    TextView txt_next;
    RecyclerView rc_po_list;
    CustomGridLayoutManager layoutManager;
    ProgressDialog progressDialog;
    String pur_doc_num;
    RecyclerView rc_image;
    CallAPiActivity callAPiActivity;
    public static String URL_COMPLETE = " http://itechgaints.com/VKC-API/markComplatedPO";
    ArrayList<String> gridList=new ArrayList<>();
    ArrayList<String> producedList=new ArrayList<>();
    ArrayList<String> imagesList=new ArrayList<>();
    HorizontalRecyclerViewPOAdapter horizontalRecyclerViewPOAdapter;
    private static final int REQUEST_IMAGE1 = 101;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION1 = 102;
    private ArrayList<String> mSelectPath1;
    private String str_path1="";
    private static final int REQUEST_IMAGE2 = 201;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION2 = 202;
    private ArrayList<String> mSelectPath2;
    private String str_path2="";
    private static final int REQUEST_IMAGE3 = 301;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION3 =302;
    private ArrayList<String> mSelectPath3;
    private String str_path3="";
    public MarkPOFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tablayout.setVisibility(View.GONE);
        textTitle.setText("MARK PO COMPLETE");
        View v=inflater.inflate(R.layout.fragment_mark_po, container, false);
        pur_doc_num = getArguments().getString("po_number");
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        rc_po_list=v.findViewById(R.id.horizontal_rc_po_list);
        next=v.findViewById(R.id.next);
        cancel=v.findViewById(R.id.cancel);
        txt_next=v.findViewById(R.id.txt_next);
        callAPiActivity = new CallAPiActivity(getActivity());
        layoutManager=new CustomGridLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);

        rc_po_list.setLayoutManager(layoutManager);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PlanFragment myPhotosFragment = new PlanFragment();
                getActivity().getSupportFragmentManager().popBackStackImmediate();
                /*getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, myPhotosFragment, "TAG")
                        .commit();*/
            }
        });

        final int[] i = {0};

        rc_po_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("onScrollStateChanged",(i[0]++)+"  onScrollStateChanged");
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //Log.d("onScrollStateChanged","onScrolled");
            }
        });

        getAllHourlyDetail();

        return v;
    }

    private void getAllHourlyDetail() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<AllHourlyDetailResponse> call=myInterface.getAllHourlyDetail(pur_doc_num);
        call.enqueue(new Callback<AllHourlyDetailResponse>() {
            @Override
            public void onResponse(Call<AllHourlyDetailResponse> call, Response<AllHourlyDetailResponse> response) {


                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    if(response.body().getStatus().toString().equalsIgnoreCase("1"))
                    {
                        if (response.body().getData()!=null)
                        {
                            for (int i=0;i<response.body().getData().size();i++)
                            {

                                horizontalRecyclerViewPOAdapter=new HorizontalRecyclerViewPOAdapter(getActivity(),response.body().getData().get(i).getArticledata(),response.body().getData().get(i).getPurDocNum());
                                rc_po_list.setAdapter(horizontalRecyclerViewPOAdapter);
                                arrayList=response.body().getData().get(i).getArticledata();
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();

                        getActivity().getSupportFragmentManager().popBackStackImmediate();
                    }

                }

            }

            @Override
            public void onFailure(Call<AllHourlyDetailResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public class HorizontalRecyclerViewPOAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewPOAdapter.MyViewHolder> {

        private static final int HEADER_VIEW = 1;
        private static final int FOOTER_VIEW = 11;
        private List<ArticleData> data; // Take any list that matches your requirement.
        private Context context;
        private String po_number;

        // Define a constructor
        public HorizontalRecyclerViewPOAdapter(Context context, List<ArticleData> data,String po_number) {
            this.context = context;
            this.data = data;
            this.po_number = po_number;
        }

        // Define a ViewHolder for Footer view
        public class FooterViewHolder extends MyViewHolder {
            RecyclerView rc_po_list;
            TextView txt_po_number,txt_line_number,txt_article;
            EditText ed_email;
            public FooterViewHolder(final View itemView) {
                super(itemView);

                rc_po_list=itemView.findViewById(R.id.rc_po_list);
                txt_po_number=itemView.findViewById(R.id.txt_po_number);
                txt_line_number=itemView.findViewById(R.id.txt_line_number);
                txt_article=itemView.findViewById(R.id.txt_article);
                rc_image=itemView.findViewById(R.id.rc_image);
                ed_email=itemView.findViewById(R.id.ed_email);

            }
        }

        public class HeaderViewHolder extends MyViewHolder {

            public HeaderViewHolder(final View itemView) {
                super(itemView);

            }
        }

        // Now define the ViewHolder for Normal list item
        public class NormalViewHolder extends MyViewHolder {

            RecyclerView rc_po_list;
            TextView txt_po_number,txt_line_number,txt_article;

            public NormalViewHolder(View itemView) {
                super(itemView);

                rc_po_list=itemView.findViewById(R.id.rc_po_list);
                txt_po_number=itemView.findViewById(R.id.txt_po_number);
                txt_line_number=itemView.findViewById(R.id.txt_line_number);
                txt_article=itemView.findViewById(R.id.txt_article);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Do whatever you want on clicking the normal items
                    }
                });
            }
        }

        // And now in onCreateViewHolder you have to pass the correct view
        // while populating the list item.

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v;

       /* if (viewType == HEADER_VIEW) {
            v = LayoutInflater.from(context).inflate(R.layout.list_item_footer_po, parent, false);
            HeaderViewHolder vh = new HeaderViewHolder(v);
            return vh;
        } else */if (viewType == FOOTER_VIEW) {

                v = LayoutInflater.from(context).inflate(R.layout.last_horizontal_list_item_normal, parent, false);
                FooterViewHolder vh = new FooterViewHolder(v);
                return vh;
            } else {
                v = LayoutInflater.from(context).inflate(R.layout.horizontal_list_item_normal, parent, false);

                NormalViewHolder vh = new NormalViewHolder(v);

                return vh;
            }
        }

        // Now bind the ViewHolder in onBindViewHolder
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            try {
                if (holder instanceof NormalViewHolder) {
                    ArticleData articleData=data.get(position);
                    NormalViewHolder vh = (NormalViewHolder) holder;

                    vh.rc_po_list.setAdapter(new RecyclerViewPOAdapter(context,articleData.getGridData(),po_number,articleData.getLineId(),articleData.getArticleName(),articleData.getItem()));
                    vh.txt_po_number.setText(po_number);
                    vh.txt_line_number.setText(articleData.getLineNumber());
                    vh.txt_article.setText(articleData.getArticleName()+"-"+articleData.getItem());
                    vh.bindView(position);
                } else if (holder instanceof FooterViewHolder) {
                    txt_next.setText("");
                    ArticleData articleData=data.get(position);
                    FooterViewHolder vh = (FooterViewHolder) holder;

                    vh.rc_po_list.setAdapter(new RecyclerViewPOAdapter(context,articleData.getGridData(),po_number,articleData.getLineId(),articleData.getArticleName(),articleData.getItem()));
                    vh.txt_po_number.setText(po_number);
                    vh.txt_line_number.setText(articleData.getLineNumber());
                    vh.txt_article.setText(articleData.getArticleName()+"-"+articleData.getItem());

                    txt_next.setText("Mark PO Completed");
                    RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
                    rc_image.setLayoutManager(layoutManager);

                    vh.ed_email.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pickImage1();

                        }
                    });


                    vh.bindView(position);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Now the critical part. You have return the exact item count of your list
        // I've only one footer. So I returned data.size() + 1
        // If you've multiple headers and footers, you've to return total count
        // like, headers.size() + data.size() + footers.size()

        @Override
        public int getItemCount() {
        /*if (data == null) {
            return 0;
        }

        if (data.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }
*/
            // Add extra view to show the footer view
            return data.size();
        }

        // Now define getItemViewType of your own.

        @Override
        public int getItemViewType(int position) {
        /*if (position == 0) {
            // This is where we'll add footer.
            return HEADER_VIEW;
        }*/
            if (position == data.size()-1) {
                // This is where we'll add footer.
                return FOOTER_VIEW;
            }

            return super.getItemViewType(position);
        }

        // So you're done with adding a footer and its action on onClick.
        // Now set the default ViewHolder for NormalViewHolder

        public class MyViewHolder extends RecyclerView.ViewHolder {
            // Define elements of a row here
            public MyViewHolder(View itemView) {
                super(itemView);
                // Find view by ID and initialize here
            }

            public void bindView(int position) {
                // bindView() method to implement actions
            }
        }


    }

    public class RecyclerViewPOAdapter extends RecyclerView.Adapter<RecyclerViewPOAdapter.MyViewHolder> {

        private static final int HEADER_VIEW = 1;
        private static final int FOOTER_VIEW = 11;
        private List<GridData> data; // Take any list that matches your requirement.
        private Context context;
        String po_number;
        String line_id;
        String article_name;
        String item;
        // Define a constructor
        public RecyclerViewPOAdapter(Context context, List<GridData> data,String po_number,String line_id,String article_name,String item) {
            this.context = context;
            this.data = data;
            this.item= item;
            this.po_number = po_number;
            this.line_id = line_id;
            this.article_name = article_name;
        }

        // Define a ViewHolder for Footer view
        public class FooterViewHolder extends MyViewHolder {
            TextView txt_grid_value,txt_qty_pending,overproduction;
            EditText ed_qty_produced,overproductiontr;
            public FooterViewHolder(final View itemView) {
                super(itemView);
                txt_grid_value=itemView.findViewById(R.id.txt_grid_value);
                ed_qty_produced=itemView.findViewById(R.id.ed_qty_produced);
                txt_qty_pending=itemView.findViewById(R.id.txt_qty_pending);
                overproduction=itemView.findViewById(R.id.overproduction);
                overproductiontr=itemView.findViewById(R.id.overproductiontr);

            }
        }

        public class HeaderViewHolder extends MyViewHolder {

            public HeaderViewHolder(final View itemView) {
                super(itemView);

            }
        }

        // Now define the ViewHolder for Normal list item
        public class NormalViewHolder extends MyViewHolder {

            TextView txt_grid_value,txt_qty_pending,overproduction;
            EditText ed_qty_produced;
            TableRow overproductiontr;
            public NormalViewHolder(View itemView) {
                super(itemView);

                txt_grid_value=itemView.findViewById(R.id.txt_grid_value);
                ed_qty_produced=itemView.findViewById(R.id.ed_qty_produced);
                txt_qty_pending=itemView.findViewById(R.id.txt_qty_pending);
                overproduction=itemView.findViewById(R.id.overproduction);
                overproductiontr=itemView.findViewById(R.id.overproductiontr);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Do whatever you want on clicking the normal items
                    }
                });
            }
        }

        // And now in onCreateViewHolder you have to pass the correct view
        // while populating the list item.

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v;

       /* if (viewType == HEADER_VIEW) {
            v = LayoutInflater.from(context).inflate(R.layout.list_item_footer_po, parent, false);
            HeaderViewHolder vh = new HeaderViewHolder(v);
            return vh;
        } else */if (viewType == FOOTER_VIEW) {
                v = LayoutInflater.from(context).inflate(R.layout.last_list_item_normal, parent, false);
                FooterViewHolder vh = new FooterViewHolder(v);
                return vh;
            } else {
                v = LayoutInflater.from(context).inflate(R.layout.list_item_normal, parent, false);

                NormalViewHolder vh = new NormalViewHolder(v);

                return vh;
            }
        }

        // Now bind the ViewHolder in onBindViewHolder
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            try {
                if (holder instanceof NormalViewHolder) {
                    final GridData gridData=data.get(position);
                    NormalViewHolder vh = (NormalViewHolder) holder;

                    vh.txt_grid_value.setText(gridData.getGridValue());
                    vh.ed_qty_produced.setText(gridData.getQualityProduced());

                    vh.ed_qty_produced.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                            gridData.setQualityProduced(s.toString());

                        }
                    });


                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            gridList=new ArrayList<>();
                            producedList=new ArrayList<>();
                            for(int i=0;i<data.size();i++)
                            {
                                gridList.add(data.get(i).getGridValue());
                                producedList.add(data.get(i).getQualityProduced());
                            }

                            final StringBuilder sb = new StringBuilder();
                            for (int i=0;i<gridList.size();i++)
                            {
                                if (i==gridList.size()-1)
                                {
                                    sb.append(gridList.get(i)).append("");
                                }
                                else {
                                    sb.append(gridList.get(i)).append(",");            }
                            }

                            final StringBuilder sb1 = new StringBuilder();
                            for (int i=0;i<producedList.size();i++)
                            {
                                if (i==producedList.size()-1)
                                {
                                    sb1.append(producedList.get(i)).append("");
                                }
                                else {
                                    sb1.append(producedList.get(i)).append(",");
                                }
                            }

                            if(txt_next.getText().toString().equalsIgnoreCase("next"))
                            {
                                addMarkCompleteDetail(item,po_number,line_id,article_name,sb,sb1);
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("Are you sure you want to mark PO as complete");

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
                                        if (mSelectPath1!=null) {
                                            if (mSelectPath1.size() >= 1) {
                                                //Toast.makeText(context, ""+mSelectPath1, Toast.LENGTH_SHORT).show();
                                                addMarkCompleteDetail2(item,po_number, line_id, article_name, sb, sb1);

                                            }
                                            else
                                            {
                                                Toast.makeText(context, "Select min 1 image", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else {
                                           Toast.makeText(context, "Select min 1 image", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                                // create and show the alert dialog
                                AlertDialog dialog = builder.create();
                                dialog.show();

                               /* Log.d("afasghasd",po_number+"   "+line_id+"   "+article_name+"  "+sb+"   "+sb1+"    "+imagesList);*/
                            }

                        }
                    });

                    int total= Integer.parseInt(gridData.getScheduledQuantity());
                    int produced= Integer.parseInt(gridData.getQualityProduced());
                    int pending=total-produced;


                    if (pending < 0) {
                        vh.overproductiontr.setVisibility(View.VISIBLE);
                        vh.overproduction.setText(""+Math.abs(pending));
                        vh.txt_qty_pending.setText("0(Total Qty:"+gridData.getScheduledQuantity()+")");
                        // pNumber.setQualityProduced("0");

                        Log.d("cafafa","negative"+pending);
                        // negative
                    } else {
                        vh.overproductiontr.setVisibility(View.GONE);
                        vh.txt_qty_pending.setText(pending+"(Total Qty:"+gridData.getScheduledQuantity()+")");

                        Log.d("cafafa","positive"+pending);
                        // it's a positive
                    }
                  //  vh.txt_qty_pending.setText(Integer.parseInt(gridData.getScheduledQuantity())-Integer.parseInt(gridData.getQualityProduced())+"(Total Qty:"+gridData.getScheduledQuantity()+")");
                } else if (holder instanceof FooterViewHolder) {
                    GridData gridData=data.get(position);
                    NormalViewHolder vh = (NormalViewHolder) holder;

                    vh.txt_grid_value.setText(gridData.getGridValue());
                    vh.ed_qty_produced.setText(gridData.getQualityProduced());
                  //  vh.txt_qty_pending.setText(Integer.parseInt(gridData.getScheduledQuantity())-Integer.parseInt(gridData.getQualityProduced())+"(Total Qty:"+gridData.getScheduledQuantity()+")");
                    int total= Integer.parseInt(gridData.getScheduledQuantity());
                    int produced= Integer.parseInt(gridData.getQualityProduced());
                    int pending=total-produced;


                    if (pending < 0) {
                        vh.overproductiontr.setVisibility(View.VISIBLE);
                        vh.overproduction.setText(""+Math.abs(pending));
                        vh.txt_qty_pending.setText("0(Total Qty:"+gridData.getScheduledQuantity()+")");
                        // pNumber.setQualityProduced("0");

                        Log.d("cafafa","negative"+pending);
                        // negative
                    } else {
                        vh.overproductiontr.setVisibility(View.GONE);
                        vh.txt_qty_pending.setText(pending+"(Total Qty:"+gridData.getScheduledQuantity()+")");

                        Log.d("cafafa","positive"+pending);
                        // it's a positive
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Now the critical part. You have return the exact item count of your list
        // I've only one footer. So I returned data.size() + 1
        // If you've multiple headers and footers, you've to return total count
        // like, headers.size() + data.size() + footers.size()

        @Override
        public int getItemCount() {
       /* if (data == null) {
            return 0;
        }

        if (data.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }
*/
            // Add extra view to show the footer view
            return data.size();
        }

        // Now define getItemViewType of your own.

        @Override
        public int getItemViewType(int position) {
/*
        if (position == data.size()-1) {
            // This is where we'll add footer.
            return FOOTER_VIEW;
        }*/

            return super.getItemViewType(position);
        }

        // So you're done with adding a footer and its action on onClick.
        // Now set the default ViewHolder for NormalViewHolder

        public class MyViewHolder extends RecyclerView.ViewHolder {
            // Define elements of a row here
            public MyViewHolder(View itemView) {
                super(itemView);
                // Find view by ID and initialize here
            }

            public void bindView(int position) {
                // bindView() method to implement actions
            }
        }


    }


    public void addMarkCompleteDetail(String item,String po_number,String line_id,String article_name,StringBuilder gridList,StringBuilder producedList) {
        Log.d("sdfsf", "" + po_number);
        Log.d("sdfsf", "" + line_id);
        Log.d("sdfsf", "" + article_name);
        Log.d("sdfsf", "" + gridList);
        Log.d("sdfsf", "" + producedList);
        Log.d("sdfsfitem", "" + item);

        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<AddHourWiseResponse> mapArticleResponseCall = myInterface.addMarkCompleteDetail(item,po_number, line_id, article_name, gridList.toString(), producedList.toString());
        mapArticleResponseCall.enqueue(new Callback<AddHourWiseResponse>() {
            @Override
            public void onResponse(Call<AddHourWiseResponse> call, Response<AddHourWiseResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if(response.body().getStatus().toString().equalsIgnoreCase("1"))
                    {
                        Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        //rc_po_list.scrollToPosition(layoutManager.findLastCompletelyVisibleItemPosition() + 1);
                        if (layoutManager.findLastCompletelyVisibleItemPosition() < (horizontalRecyclerViewPOAdapter.getItemCount() - 1)) {
                            layoutManager.scrollToPosition(layoutManager.findLastCompletelyVisibleItemPosition() + 1);
                            //smoothScroller.setTargetPosition(layoutManager.findLastCompletelyVisibleItemPosition() + 1);
                            //rc_po_list.smoothScrollToPosition(layoutManager.findLastCompletelyVisibleItemPosition() + 1);
                          //  Toast.makeText(getActivity(), ""+layoutManager.findLastCompletelyVisibleItemPosition() + 1, Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<AddHourWiseResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void addMarkCompleteDetail2(String item,String po_number,String line_id,String article_name,StringBuilder gridList,StringBuilder producedList){

        progressDialog.show();
        Log.d("sdfsf",""+mSelectPath1);
        HashMap<String, String> map = new HashMap<>();

        map.put("po_doc_num", po_number+ "");
        map.put("line_id", line_id);
        map.put("article", article_name);
        map.put("grid_value", gridList.toString());
        map.put("quality_produced", producedList.toString());
        map.put("item", item);

        callAPiActivity.doPostWithFiles(getActivity(), map, URL_COMPLETE, mSelectPath1, "image[]", new GetResponse() {

            @Override
            public void onSuccesResult(JSONObject result) throws JSONException {
                progressDialog.dismiss();
                String status = result.getString("status");
                if (status.equalsIgnoreCase("1")) {
                    Log.d("dddd","cancel");
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.clear();
                    editor.commit();
                    for (int j=0;j<hour.length;j++) {
                        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

                        Intent myIntent = new Intent(getActivity(), HomeActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                getActivity(), hour[j], myIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                        alarmManager.cancel(pendingIntent);
                    }

                    Toast.makeText(getActivity(), "Successfully updated", Toast.LENGTH_SHORT).show();
                    //PlanFragment myPhotosFragment = new PlanFragment();
                    //getActivity().getSupportFragmentManager().popBackStackImmediate();
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                    getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
/*
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, myPhotosFragment, "TAG")
                            .commit();*/
                }
                else
                {
                    Toast.makeText(getActivity(), ""+result.getString("message"), Toast.LENGTH_SHORT).show();
                }

                Log.d("messageeeeeeeeeee", "succccccccessss" + status);
            }

            @Override
            public void onFailureResult(JSONObject result) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
                Log.d("messageeeeeeeeeee", result.toString());

            }

            @Override
            public void onException(String message) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
                Log.d("messageeeeeeeeeee", message);

            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tablayout.setVisibility(View.GONE);
        textTitle.setText("MARK PO COMPLETE");
    }

    private void pickImage1() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION1);
        } else {

            MultiImageSelector selector = MultiImageSelector.create(getActivity());
            selector.showCamera(true);

            selector.multi();
            selector.count(6);

            selector.origin(mSelectPath1);
            selector.start(MarkPOFragment.this, REQUEST_IMAGE1);



        }
    }
    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>
    {
        ArrayList<String> img;
        Context context;

        public ImageAdapter(ArrayList<String> img, Context context) {
            this.img = img;
            this.context = context;
        }

        @NonNull
        @Override
        public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_upload_defects, viewGroup, false);
            ImageAdapter.ViewHolder myViewHolder = new ImageAdapter.ViewHolder(v);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder viewHolder, final int i) {

            Glide.with(getActivity()).load(img.get(i)).into(viewHolder.imageView);

            viewHolder.mRemoveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAt(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return img.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public ImageButton mRemoveButton;
            public ImageView imageView;

            public ViewHolder(View v){
                super(v);
                imageView = (ImageView) v.findViewById(R.id.img);
                mRemoveButton = (ImageButton) v.findViewById(R.id.ib_remove);
            }
        }

        private void removeAt(int position) {

            img.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, img.size());
            notifyItemChanged(position);
        }

    }

    private void pickImage2() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION2);
        } else {

            MultiImageSelector selector = MultiImageSelector.create(getActivity());
            selector.single();
            selector.showCamera(false);

            selector.origin(mSelectPath2);
            selector.start(MarkPOFragment.this, REQUEST_IMAGE2);
        }
    }



    private void pickImage3() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION3);
        } else {

            MultiImageSelector selector = MultiImageSelector.create(getActivity());
            selector.single();
            selector.showCamera(false);

            selector.origin(mSelectPath3);
            selector.start(MarkPOFragment.this, REQUEST_IMAGE3);
        }
    }


    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_IMAGE1) {
                if (resultCode == Activity.RESULT_OK) {
                    mSelectPath1 = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                    StringBuilder sb = new StringBuilder();
                    for (String p : mSelectPath1) {
                        sb.append(p);
                        sb.append("\n");
                    }


                    str_path1 = "" + sb.toString().trim();

                    rc_image.setAdapter(new ImageAdapter(mSelectPath1,getActivity()));

                }
            }

            if (requestCode == REQUEST_IMAGE2) {
                if (resultCode == Activity.RESULT_OK) {
                    mSelectPath2 = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                    StringBuilder sb = new StringBuilder();
                    for (String p : mSelectPath2) {
                        sb.append(p);
                        sb.append("\n");
                    }


                    str_path2 = "" + sb.toString().trim();



                    Log.d("mediapathhhhhhhh", "" + str_path2);
                }
            }
            if (requestCode == REQUEST_IMAGE3) {
                if (resultCode == Activity.RESULT_OK) {
                    mSelectPath3 = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                    StringBuilder sb = new StringBuilder();
                    for (String p : mSelectPath3) {
                        sb.append(p);
                        sb.append("\n");
                    }


                    str_path3 = "" + sb.toString().trim();


                    Log.d("mediapathhhhhhhh", "" + str_path3);
                }
            }

        }
    }

}
