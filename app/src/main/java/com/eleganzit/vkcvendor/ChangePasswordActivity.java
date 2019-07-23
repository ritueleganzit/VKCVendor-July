package com.eleganzit.vkcvendor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eleganzit.vkcvendor.api.RetrofitAPI;
import com.eleganzit.vkcvendor.api.RetrofitInterface;
import com.eleganzit.vkcvendor.model.OTPResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText password,cnfpassword;
    LinearLayout submit;
    String email;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        password=findViewById(R.id.password);
        email=getIntent().getStringExtra("email");
        progressDialog = new ProgressDialog(ChangePasswordActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        cnfpassword=findViewById(R.id.cnfpassword);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equalsIgnoreCase(""))
                {
                    password.setError("Please Enter Password");
                    password.requestFocus();
                }
                else if(cnfpassword.getText().toString().equalsIgnoreCase("") || !(cnfpassword.getText().toString().equals(password.getText().toString())))
                {
                    cnfpassword.setError("Password Doesn't match");
                    cnfpassword.requestFocus();
                }
                else
                {
                    changepassword();
                }
            }
        });

    }

    private void changepassword() {
        progressDialog.show();
        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<OTPResponse> call=myInterface.resetVendorPassowrd(password.getText().toString(),email);
        call.enqueue(new Callback<OTPResponse>() {
            @Override
            public void onResponse(Call<OTPResponse> call, Response<OTPResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    if (response.body().getStatus().toString().equals("1")) {
                        Toast.makeText(ChangePasswordActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

                                // Staring Login Activity
                        );
                        finish();
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                    else
                    {
                        Toast.makeText(ChangePasswordActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
                else
                {
                    Toast.makeText(ChangePasswordActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<OTPResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ChangePasswordActivity.this, "Server or Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
