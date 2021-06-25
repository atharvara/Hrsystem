package com.example.hrsystem.requesttask;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hrsystem.MySingleton;
import com.example.hrsystem.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TaskPageAdmin extends AppCompatActivity {
    EditText task,txtDate,Empid;
    String txtTask,txtDue,txtEmp;
    String[] EmpId;
    Button sub;
    int mYear,mMonth,mDay;
    Button btnDatePicker;
    TextView viewtask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_page_admin);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Assign Task");
        setSupportActionBar(toolbar);
        task=(EditText)findViewById(R.id.txtTask);
        txtDate=(EditText)findViewById(R.id.in_date);
        Empid=(EditText)findViewById(R.id.empID);
        sub=(Button)findViewById(R.id.btnSubmit);
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        viewtask=findViewById(R.id.viewTask);
        viewtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaskPageAdmin.this,viewAssignedTask.class));
            }
        });
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog datePickerDialog = new DatePickerDialog(TaskPageAdmin.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                if(monthOfYear < 10 && dayOfMonth<10){

                                    txtDate.setText(year + "/0" + (monthOfYear + 1) + "/0" + dayOfMonth);
                                }
                                else if(monthOfYear < 10 && dayOfMonth >10){

                                    txtDate.setText(year + "/0" + (monthOfYear + 1) + "/" + dayOfMonth);
                                }
                                else if(monthOfYear > 10 && dayOfMonth <10){

                                    txtDate.setText(year + "/" + (monthOfYear + 1) + "/0" + dayOfMonth);
                                }
                                else{
                                    txtDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                                }




                            }

                        }, mYear, mMonth, mDay);

                datePickerDialog.show();

            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTask=task.getText().toString();
                txtEmp=Empid.getText().toString();
                String allemp=txtEmp;

                EmpId=txtEmp.split(",");
                txtDue=txtDate.getText().toString();
                sendData1(txtTask, allemp, txtDue);
                int i=0;
                while (i<EmpId.length) {

                    sendData(txtTask, EmpId[i], txtDue);
                    i++;
                }
            }
        });
    }
    public void sendData(String txttask,String EmpID,String txtDue){
        final ProgressDialog progressDialog = new ProgressDialog(TaskPageAdmin.this);
        progressDialog.setTitle("Sending Request");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/RequestTask/assignTask.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(TaskPageAdmin.this, response, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TaskPageAdmin.this, error.toString(), Toast.LENGTH_SHORT).show();
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
        MySingleton.getmInstance(TaskPageAdmin.this).addToRequestQueue(request);

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
    public void sendData1(String txttask,String EmpID,String txtDue){

        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/RequestTask/alldatapush.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TaskPageAdmin.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param=new HashMap<>();

                param.put("task", txttask);
                param.put("allemp", EmpID);
                param.put("dueDate", txtDue);

                return param;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(TaskPageAdmin.this).addToRequestQueue(request);

    }

}