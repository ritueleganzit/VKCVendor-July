package com.eleganzit.vkcvendor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eleganzit.vkcvendor.api.RetrofitAPI;
import com.eleganzit.vkcvendor.api.RetrofitInterface;
import com.eleganzit.vkcvendor.model.OTPResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    TextView back_tosign;
    LinearLayout submit;
    ProgressDialog progressDialog;
    TextInputEditText ed_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        back_tosign = findViewById(R.id.back_tosign);
        progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        ed_email = findViewById(R.id.ed_email);
        submit = findViewById(R.id.submit);
        back_tosign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid())
                {

                    sendCode();


                }


            }
        });
    }

    private void sendCode() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<OTPResponse> call=myInterface.sendCode(ed_email.getText().toString());
        call.enqueue(new Callback<OTPResponse>() {
            @Override
            public void onResponse(Call<OTPResponse> call, Response<OTPResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    if (response.body().getStatus().toString().equals("1"))
                    {

                        Toast.makeText(ForgotPasswordActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ForgotPasswordActivity.this, OTPActivity.class)
                                .putExtra("email",ed_email.getText().toString()));
                        finish();
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                    else
                    {
                        Toast.makeText(ForgotPasswordActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
                else
                {
                    Toast.makeText(ForgotPasswordActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OTPResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ForgotPasswordActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean isValid() {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(ed_email.getText().toString());

        if (ed_email.getText().toString().equals("")) {
            ed_email.setError("" + getResources().getString(R.string.Please_enter_email));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(ed_email);

            ed_email.requestFocus();
            return false;
        } else if (!matcher.matches()) {
            ed_email.setError("" + getResources().getString(R.string.Please_Enter_Valid_Email));

            YoYo.with(Techniques.Shake).duration(700).repeat(0).playOn(ed_email);

            ed_email.requestFocus();
            return false;
        }
        return true;
    }

}