package com.example.hrsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private TextView textView;
    private EditText username;
    private EditText password;
    private TextView login;
    CheckBox checkedStatus;
    ImageButton back;
    SharedPreferences sharedPreferences;
    GlobalClass globalClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalClass=(GlobalClass)getApplicationContext();
        setContentView(R.layout.activity_login);
      /*  Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Login");
        setSupportActionBar(toolbar);*/
        //back=findViewById(R.id.back);
       /* back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();

            }
        });*/
        textView = (TextView) findViewById(R.id.togotoregister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }

            private void openRegister() {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
        checkedStatus = findViewById(R.id.checkbox);
        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String us = (sharedPreferences.getString("username", ""));
        globalClass.setEmpid(us);
        String loginStatus = sharedPreferences.getString(getResources().getString(R.string.prefStatus),"");
        if (loginStatus.equals("loggedin")){
            startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
            finish();
        }
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tex_username = username.getText().toString();
                String tex_password = password.getText().toString();
                if (TextUtils.isEmpty(tex_username) || TextUtils.isEmpty(tex_password)){
                    Toast.makeText(LoginActivity.this, "All Fields Required", Toast.LENGTH_SHORT).show();
                }
                else{
                    login(tex_username,tex_password);
                }
            }
        });

    }


    private void login(final String username, final String password){
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Logining your account");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/LoginRegistration/login.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("Login Success")){
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    globalClass.setEmpid(username);


                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (checkedStatus.isChecked()){
                        editor.putString(getResources().getString(R.string.prefStatus),"loggedin");
                    }
                    else{
                        editor.putString(getResources().getString(R.string.prefStatus),"loggedout");
                    }
                    editor.putString("username", username);
                    editor.commit();
                    editor.apply();
                    startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
                    finish();
                    progressDialog.dismiss();

                }
                else {
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("username",username);
                param.put("password",password);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(LoginActivity.this).addToRequestQueue(request);
    }
}