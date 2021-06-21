package com.example.hrsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hrsystem.adminpass.CredentialsChange;
import com.example.hrsystem.adminpass.CredintialsModel;

import java.util.HashMap;
import java.util.Map;

public class AdminLogin extends AppCompatActivity {
private TextView login;
private EditText email;
private EditText password;
CredintialsModel credentialsChange;
CustomProgressDialog customProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
      
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login=findViewById(R.id.login);
        login.setOnClickListener(v -> validate(email.getText().toString(), password.getText().toString()));
        customProgressDialog=new CustomProgressDialog(AdminLogin.this);

    }


    private void validate(String email, String password) {
        customProgressDialog.show();
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/adminlogin/admin_login.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("Login Success")){
                    Toast.makeText(AdminLogin.this, response, Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(AdminLogin.this,AdminDashboard.class));
                    finish();
                    customProgressDialog.dismiss();

                }
                else {
                    Toast.makeText(AdminLogin.this, response, Toast.LENGTH_SHORT).show();
                    customProgressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdminLogin.this, error.toString(), Toast.LENGTH_SHORT).show();
                customProgressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("username",email);
                param.put("password",password);
                return param;

            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(AdminLogin.this).addToRequestQueue(request);
    }
}