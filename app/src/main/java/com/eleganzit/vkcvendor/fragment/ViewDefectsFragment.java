package com.eleganzit.vkcvendor.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.vkcvendor.GalleryActivity;
import com.eleganzit.vkcvendor.HourWiseEntry;
import com.eleganzit.vkcvendor.R;
import com.eleganzit.vkcvendor.api.RetrofitAPI;
import com.eleganzit.vkcvendor.api.RetrofitInterface;
import com.eleganzit.vkcvendor.model.defect.DefectArtResponse;
import com.eleganzit.vkcvendor.model.searcharticle.SerchArticleListResponse;
import com.eleganzit.vkcvendor.util.UserLoggedInSession;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eleganzit.vkcvendor.HomeActivity.tablayout;
import static com.eleganzit.vkcvendor.HomeActivity.textTitle;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewDefectsFragment extends Fragment {

LinearLayout alldefects,submit,viewown;
    public ViewDefectsFragment() {
        // Required empty public constructor
    }
    UserLoggedInSession userLoggedInSession;
    ProgressDialog progressDialog;
TextInputEditText search_art;
TextView total_defects;
CardView viewdefectscard;
ArrayList<String> gal;
    List<String> stateArrayList;
    List<String> stateArrayListnum=new ArrayList();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tablayout.setVisibility(View.VISIBLE);
        textTitle.setText("HOME");
        View v=inflater.inflate(R.layout.fragment_view_defects, container, false);
        alldefects=v.findViewById(R.id.alldefects);
        viewown=v.findViewById(R.id.viewown);
        submit=v.findViewById(R.id.submit);
        viewdefectscard=v.findViewById(R.id.viewdefectscard);
        total_defects=v.findViewById(R.id.total_defects);
        search_art=v.findViewById(R.id.search_art);
        userLoggedInSession=new UserLoggedInSession(getActivity());

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        viewdefectscard.setVisibility(View.GONE);
        progressDialog.setCanceledOnTouchOutside(false);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (search_art.getText().toString().equals("")) {
                    search_art.setError(""+getResources().getString(R.string.Please_enter_search));


                    YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(search_art);

                    search_art.requestFocus();
                }
                else
                {
                    String art[]=search_art.getText().toString().split("-");

                    viewdefectscard.setVisibility(View.VISIBLE);
                    gettotal_defects(art[0],art[1]);

                }
            }
        });
        viewown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search_art.getText().toString().equals("")) {
                    search_art.setError(""+getResources().getString(R.string.Please_enter_search));

                    YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(search_art);

                    search_art.requestFocus();
                }
                else
                {
                    String art[]=search_art.getText().toString().split("-");

                    getVendortotal_defects(art[0],art[1]);

                }
            }
        });
        alldefects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search_art.getText().toString().equals("")) {
                    search_art.setError(""+getResources().getString(R.string.Please_enter_search));

                    YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(search_art);

                    search_art.requestFocus();
                }
                else {
                    String art[]=search_art.getText().toString().split("-");
                    getAlltotal_defects(art[0],art[1]);

                }

            }
        });
        search_art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ListAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, stateArrayList);

                final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));

                builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();


                        search_art.setText(stateArrayList.get(i));

                        viewdefectscard.setVisibility(View.GONE);

                    }
                });
                if (stateArrayList.size()>0)
                builder.show();
                else
                    Toast.makeText(getActivity(), "No Articles", Toast.LENGTH_SHORT).show();
            }
        });
        serchArticleList();
        return v;
    }

    private void serchArticleList() {
        stateArrayList=new ArrayList();
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SerchArticleListResponse> call=myInterface.serchArticleList(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID));
        call.enqueue(new Callback<SerchArticleListResponse>() {
            @Override
            public void onResponse(Call<SerchArticleListResponse> call, Response<SerchArticleListResponse> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().getData()!=null)
                    {
                        for (int i=0;i<response.body().getData().size();i++)
                        {
                            stateArrayList.add(response.body().getData().get(i).getArticle()+"-"+response.body().getData().get(i).getItem());



                        }
                        search_art.setText(stateArrayList.get(0));


                    }
                }
            }

            @Override
            public void onFailure(Call<SerchArticleListResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void gettotal_defects(String art,String item) {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<DefectArtResponse> call=myInterface.defectEntry(art,userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID),item);
        call.enqueue(new Callback<DefectArtResponse>() {
            @Override
            public void onResponse(Call<DefectArtResponse> call, Response<DefectArtResponse> response) {
                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    if (response.body().getCountData()!=null)
                    {
                        if (response.body().getCountData().toString().equalsIgnoreCase("1"))
                        {
                            total_defects.setText("There is "+response.body().getCountData()+" defect recorded for this article so far!!!");
                        }
                        else
                        {
                            total_defects.setText("There are "+response.body().getCountData()+" defects recorded for this article so far!!!");
                        }

                    }
                    else
                    {
                        total_defects.setText("There is 0 defect recorded for this article so far!!!");

                    }

                }
            }

            @Override
            public void onFailure(Call<DefectArtResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAlltotal_defects(String art,String item) {
        gal=new ArrayList<>();
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<DefectArtResponse> call=myInterface.defectEntry(art,"",item);
        call.enqueue(new Callback<DefectArtResponse>() {
            @Override
            public void onResponse(Call<DefectArtResponse> call, Response<DefectArtResponse> response) {
                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    if (response.body().getData()!=null)
                    {
                        for (int i=0;i<response.body().getData().size();i++)
                        {

                            gal.add(response.body().getData().get(i).getDefectProductImage());
                        }
                        startActivity(new Intent(getActivity(), GalleryActivity.class).putStringArrayListExtra("gallery",gal));

                    }


                }
            }

            @Override
            public void onFailure(Call<DefectArtResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
private void getVendortotal_defects(String art,String item) {
        progressDialog.show();
    gal=new ArrayList<>();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<DefectArtResponse> call=myInterface.defectEntry(art,userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID),item);
        call.enqueue(new Callback<DefectArtResponse>() {
            @Override
            public void onResponse(Call<DefectArtResponse> call, Response<DefectArtResponse> response) {
                if (response.isSuccessful())
                {
                    progressDialog.dismiss();
                    if (response.body().getData()!=null)
                    {
                        for (int i=0;i<response.body().getData().size();i++)
                        {

                            gal.add(response.body().getData().get(i).getDefectProductImage());
                        }
                        if (gal!=null)
                        {
                            startActivity(new Intent(getActivity(), GalleryActivity.class).putStringArrayListExtra("gallery",gal));
                        }


                    }


                }
            }

            @Override
            public void onFailure(Call<DefectArtResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
