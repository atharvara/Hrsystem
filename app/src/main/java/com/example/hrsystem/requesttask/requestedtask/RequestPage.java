package com.example.hrsystem.requesttask.requestedtask;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    String task,dueD,Sts,requesterS;
    TextView Task,dueDate,sts,requester;
    GlobalClass globalClass;
    String EmpId;
    Button decline,accept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_page2);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Task Overview Page");
        setSupportActionBar(toolbar);
        task=getIntent().getStringExtra("task");
        sts=findViewById(R.id.sts);
        dueD=getIntent().getStringExtra("due");
        Sts=getIntent().getStringExtra("Sts");
        requesterS=getIntent().getStringExtra("requester");
        Task=(TextView)findViewById(R.id.task);
        sts.setText("Status:-"+Sts);
        dueDate=(TextView)findViewById(R.id.dueDate);
        requester=findViewById(R.id.requesterName);
        requester.setText("Requester Name:-"+requesterS);
        Task.setText("Task:-"+" "+task);
        dueDate.setText("Due Date:-"+dueD);

        globalClass=(GlobalClass)getApplicationContext();
        EmpId=globalClass.getEmpid();
        decline=(Button)findViewById(R.id.decline);
        accept=(Button)findViewById(R.id.acc);
        if (Sts.equals("Accepted")){
            accept.setVisibility(View.GONE);
            decline.setVisibility(View.GONE);
            accept.setText("Completed");
            accept.setVisibility(View.VISIBLE);
        }
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accept.getText().equals("Accept")) {
                    String decison = "Accepted";
                    decison(task, EmpId, decison);

                }else if(accept.getText().equals("Completed")){
                    String decison = "Completed";
                    decison(task, EmpId, decison);
                }
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
                showCustomDialog(response);
                finish();

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
    private void showCustomDialog(String response) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.my_dialog, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        TextView txt= dialogView.findViewById(R.id.txtalert);
        txt.setText(response);

        Button btn=dialogView.findViewById(R.id.buttonOk);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
}