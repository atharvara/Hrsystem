package com.example.hrsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {
    EditText fullName,userName,emailAddress,password;
    Button register;
    GlobalClass globalClass;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Register");
        setSupportActionBar(toolbar);
        globalClass=new GlobalClass();

        fullName = findViewById(R.id.fullname);
        userName = findViewById(R.id.username);
        emailAddress = findViewById(R.id.email);
        password = findViewById(R.id.password);


        register = findViewById(R.id.signin);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullname = fullName.getText().toString();
                final String username = userName.getText().toString();
                final String email = emailAddress.getText().toString();
                final String txt_password = password.getText().toString();



                register(fullname,username,email,txt_password);
            }




        });

    }

    private void register(final String fullname, final String username, final String email, final String password){
        final ProgressDialog progressDialog = new ProgressDialog(RegistrationActivity.this);
        progressDialog.setTitle("Registering your account");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/LoginRegistration/signup.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("You are registered successfully")){
                    Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                    progressDialog.dismiss();
                    finish();
                }else {
                    Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegistrationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("fullname",fullname);
                globalClass.setEmp_name(fullname);
                param.put("username",username);
                param.put("password",password);
                param.put("email",email);

                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(RegistrationActivity.this).addToRequestQueue(request);

    }
}