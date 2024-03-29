package com.example.hrsystem.requesttask;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hrsystem.GlobalClass;
import com.example.hrsystem.MySingleton;
import com.example.hrsystem.R;
import com.example.hrsystem.requesttask.requestedtask.RequestPage;

import java.util.HashMap;
import java.util.Map;

public class TaskOverview extends AppCompatActivity {
    String task,dueD,Sts;
    TextView Task,dueDate,sts;
    GlobalClass globalClass;
    String EmpId;
    Button comp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_overview);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Task Overview Page");
        setSupportActionBar(toolbar);
        task=getIntent().getStringExtra("task");
        sts=findViewById(R.id.sts);
        dueD=getIntent().getStringExtra("due");
        Sts=getIntent().getStringExtra("Sts");
        Task=(TextView)findViewById(R.id.task);
        sts.setText("Status:-"+Sts);
        dueDate=(TextView)findViewById(R.id.dueDate);
        Task.setText("Task:-"+" "+task);
        dueDate.setText("Due Date:-"+dueD);

        globalClass=(GlobalClass)getApplicationContext();
        EmpId=globalClass.getEmpid();
        comp=(Button)findViewById(R.id.comp);
        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String decison = "Completed";
                    decison(task, EmpId, decison);
        }});
}
    public void decison(String task,String EmpId,String decision){
        final ProgressDialog progressDialog = new ProgressDialog(TaskOverview.this);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/RequestTask/stsTask.php";
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                finish();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TaskOverview.this, error.toString(), Toast.LENGTH_SHORT).show();
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
        MySingleton.getmInstance(TaskOverview.this).addToRequestQueue(request);


    }
}