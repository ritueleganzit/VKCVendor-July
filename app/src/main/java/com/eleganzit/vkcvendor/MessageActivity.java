package com.eleganzit.vkcvendor;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.vkcvendor.R;
import com.eleganzit.vkcvendor.api.RetrofitAPI;
import com.eleganzit.vkcvendor.api.RetrofitInterface;
import com.eleganzit.vkcvendor.model.LoginRespose;
import com.eleganzit.vkcvendor.util.UserLoggedInSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    UserLoggedInSession userLoggedInSession;
    EditText ed_message;
    LinearLayout save;
    TextView datetime;
    String str_datetime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_message);
        userLoggedInSession=new UserLoggedInSession(MessageActivity.this);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        datetime=findViewById(R.id.datetime);
        ed_message=findViewById(R.id.ed_message);
        save=findViewById(R.id.save);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy h:mm a");
        str_datetime = sdf.format(new Date());

        datetime.setText(str_datetime);
        progressDialog=new ProgressDialog(MessageActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ed_message.getText().toString().isEmpty())
                {
                    addFeedback();
                }

            }
        });

    }

    public String parseDateToddMMyyyy(String time) {

        String inputPattern = "dd/MM/yyyy h:mm a";
        String outputPattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    private void addFeedback() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<LoginRespose> call=myInterface.addFeedback(userLoggedInSession.getUserDetails().get(UserLoggedInSession.USER_ID),ed_message.getText().toString(),parseDateToddMMyyyy(str_datetime));
        call.enqueue(new Callback<LoginRespose>() {
            @Override
            public void onResponse(Call<LoginRespose> call, Response<LoginRespose> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1"))
                    {
                        Toast.makeText(MessageActivity.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                    }
                    else
                    {
                        Toast.makeText(MessageActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }



                }
            }

            @Override
            public void onFailure(Call<LoginRespose> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MessageActivity.this, "Server or Internet Error"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
