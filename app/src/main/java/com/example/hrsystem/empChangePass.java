package com.example.hrsystem;

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
import com.example.hrsystem.adminpass.CredentialsChange;

import java.util.HashMap;
import java.util.Map;

public class empChangePass extends AppCompatActivity {
    EditText ed1,ed2,ed3;
    GlobalClass globalClass;
    Button btn;
    CustomProgressDialog customProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_change_pass);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Change Credentials");
        setSupportActionBar(toolbar);
        btn=findViewById(R.id.btn);
        ed1=findViewById(R.id.ed1);
        ed2=findViewById(R.id.ed2);
        ed3=findViewById(R.id.ed3);
        customProgressDialog=new CustomProgressDialog(empChangePass.this);
        globalClass=(GlobalClass)getApplicationContext();
        ed1.setText(globalClass.getEmpid());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldid=ed1.getText().toString();
                String oldpass=ed2.getText().toString();
                String newpass=ed3.getText().toString();
                changePass(oldid,oldpass,newpass);
            }
        });
    }
    public void changePass(String oldid,String oldpass,String newPass){
        customProgressDialog.show();
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/leave/emppasschange.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(empChangePass.this, response, Toast.LENGTH_SHORT).show();
                customProgressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(empChangePass.this, error.toString(), Toast.LENGTH_SHORT).show();
                customProgressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("oldid",oldid);
                param.put("oldpass",oldpass);
                param.put("newpass",newPass);
                return param;

            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(empChangePass.this).addToRequestQueue(request);
    }
}