package com.example.hrsystem.expense;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hrsystem.CustomProgressDialog;
import com.example.hrsystem.DashboardActivity;
import com.example.hrsystem.GlobalClass;
import com.example.hrsystem.MySingleton;
import com.example.hrsystem.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class EmpExpenseReport extends AppCompatActivity {
    TextView expmonth,expyear;
    GlobalClass globalClass;
    CustomProgressDialog customProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_expense_report);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Expense Report");
        setSupportActionBar(toolbar);
        expmonth=findViewById(R.id.expmonth);
        expyear=findViewById(R.id.expyear);
        globalClass=(GlobalClass)getApplicationContext();
        customProgressDialog=new CustomProgressDialog(EmpExpenseReport.this);
        customProgressDialog.show();
        getMonth(globalClass.getEmpid());
    }
    public void  getMonth(String Emp_id){
        customProgressDialog.show();
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/expense/empmonthreport.php";
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                customProgressDialog.dismiss();
                expmonth.setText(response);
                getYear(globalClass.getEmpid());
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EmpExpenseReport.this, error.toString(), Toast.LENGTH_SHORT).show();
                customProgressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("emp_id", Emp_id);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(EmpExpenseReport.this).addToRequestQueue(request);
    }
    public void  getYear(String Emp_id){
        customProgressDialog.show();
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/expense/empyearreport.php";
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                customProgressDialog.dismiss();
                expyear.setText(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EmpExpenseReport.this, error.toString(), Toast.LENGTH_SHORT).show();
                customProgressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("emp_id", Emp_id);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(EmpExpenseReport.this).addToRequestQueue(request);
    }
}