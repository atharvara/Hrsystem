package com.example.hrsystem.requesttask;

import android.app.ProgressDialog;
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
import com.example.hrsystem.MySingleton;
import com.example.hrsystem.R;

import java.util.HashMap;
import java.util.Map;

public class RequestPage extends AppCompatActivity {
    EditText task,dueDate,Empid;
    String txtTask,txtDue,txtEmp;
    String[] EmpId;
    Button sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_page);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Request Task");
        setSupportActionBar(toolbar);
        task=(EditText)findViewById(R.id.txtTask);
        dueDate=(EditText)findViewById(R.id.dueDate);
        Empid=(EditText)findViewById(R.id.empID);
        sub=(Button)findViewById(R.id.btnSubmit);

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTask=task.getText().toString();
                txtEmp=Empid.getText().toString();
                EmpId=txtEmp.split(",");
                txtDue=dueDate.getText().toString();
                int i=0;
                while (i<EmpId.length) {

                    sendData(txtTask, EmpId[i], txtDue);
                    i++;
                }
            }
        });
    }
    public void sendData(String txttask,String EmpID,String txtDue){
        final ProgressDialog progressDialog = new ProgressDialog(RequestPage.this);
        progressDialog.setTitle("Sending Request");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/RequestTask/requestTask.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(RequestPage.this, response, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
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
                HashMap<String, String> param=new HashMap<>();

                param.put("task", txttask);
                param.put("empid", EmpID);
                param.put("dueDate", txtDue);

                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(RequestPage.this).addToRequestQueue(request);

    }

}