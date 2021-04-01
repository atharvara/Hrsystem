package com.example.hrsystem.requesttask.requestedtask;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hrsystem.DashboardActivity;
import com.example.hrsystem.GlobalClass;
import com.example.hrsystem.MySingleton;
import com.example.hrsystem.R;

import java.util.HashMap;
import java.util.Map;

public class RequestPage extends AppCompatActivity {
    String task,dueD;
    TextView Task,dueDate;
    GlobalClass globalClass;
    String EmpId;
    Button decline,accept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_page2);
        task=getIntent().getStringExtra("task");
        dueD=getIntent().getStringExtra("due");
        Task=(TextView)findViewById(R.id.task);
        dueDate=(TextView)findViewById(R.id.dueDate);
        Task.setText("Task:-"+" "+task);
        dueDate.setText("Due Date:-"+dueD);
        globalClass=(GlobalClass)getApplicationContext();
        EmpId=globalClass.getEmpid();
        decline=(Button)findViewById(R.id.decline);
        accept=(Button)findViewById(R.id.acc);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String decison="Accepted";
                decison(task,EmpId,decison);
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String decison="Decline";
                decison(task,EmpId,decison);

            }
        });


    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RequestPage.this, viewRequestedTask.class);
        startActivity(intent);
        finish();
    }

    public void decison(String task,String EmpId,String decision){
        final ProgressDialog progressDialog = new ProgressDialog(RequestPage.this);
        progressDialog.setTitle("Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/RequestTask/stsRequest.php";
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                if (response.equals("Declined")){
                    Intent intent = new Intent(RequestPage.this, viewRequestedTask.class);
                    startActivity(intent);
                    finish();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RequestPage.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("emp_id", EmpId);
                param.put("task", task);
                param.put("sts", decision);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(RequestPage.this).addToRequestQueue(request);


    }
}