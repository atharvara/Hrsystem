package com.example.hrsystem.leavemanagment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hrsystem.GlobalClass;
import com.example.hrsystem.R;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hrsystem.expense.ExpenseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText username,empdept,email,phoneno,Reasonofleave,descriptionbox,sdate,edate;
    GlobalClass globalClass;
    String Sid;
    Calendar c;
    DatePickerDialog dpd;
    private Button startdate,enddate,save,btnview;
    private String sendUrl="https://shinetech.site/shinetech.site/hrmskbp/leave/getData.php";
    private RequestQueue requestQueue;
    private  static  final  String TAG=MainActivity.class.getSimpleName();

    private String TAG_SUCESS="sucess";
    private String TAG_MESSAGE="message";
    private String tag_json_obj="json_obj_req";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        globalClass=(GlobalClass)getApplicationContext();
        Sid=globalClass.getEmpid();
        username=findViewById(R.id.txtUsername);
        empdept=findViewById(R.id.empdept);
        email=findViewById(R.id.email);
        phoneno=findViewById(R.id.empphone);
        Reasonofleave=findViewById(R.id.leavereason);
        descriptionbox=(EditText) findViewById(R.id.description);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Leave Application");
        setSupportActionBar(toolbar);
        sdate=findViewById(R.id.sd);
        edate=findViewById(R.id.ed);


        startdate=findViewById(R.id.startdate);
        enddate=findViewById(R.id.enddate);
        save=findViewById(R.id.btnSave);
        btnview=findViewById(R.id.btnview);

        requestQueue=Volley.newRequestQueue(getApplicationContext());

        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c= Calendar.getInstance();
                int  day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog( MainActivity.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear,int mMonth, int mDay){
                        sdate.setText(mYear + "-" +(mMonth+1) + "-" +mDay );
                    }
                }, day, month,year);
                dpd.show();
            }
        });
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c= Calendar.getInstance();
                int  day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog( MainActivity.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear,int mMonth, int mDay){
                        edate.setText(mYear + "-" +(mMonth+1) + "-" +mDay );
                    }
                }, day, month,year);
                dpd.show();
            }
        });




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendData(Sid,username.getText().toString(),empdept.getText().toString(),email.getText().toString(),phoneno.getText().toString(),Reasonofleave.getText().toString(),sdate.getText().toString(),edate.getText().toString(),descriptionbox.getText().toString());
            }
        });

        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EmpView.class));
            }
        });

    }
    private  void sendData(final String empid,final String username,final String empdept,final String email,final String phoneno,final String Reasonofleave,final String sdate,final String edate,final String descriptionbox){
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Sending Data...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        StringRequest request=new StringRequest(Request.Method.POST, sendUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("You are registered successfully")){
                    showCustomDialog(response);
                    progressDialog.dismiss();
                }else {
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            public Map<String,String> getParams(){
                Map<String, String> params=new HashMap<String, String>();
                params.put("empid",empid);
                params.put("username",username);
                params.put("empdept",empdept);
                params.put("email",email);
                params.put("phoneno",phoneno);
                params.put("Reasonofleave",Reasonofleave);
                params.put("sdate",sdate);
                params.put("edate",edate);
                params.put("descriptionbox",descriptionbox);


                Log.e("params",params.toString());
                return params;

            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10000,1,1.0f));
        requestQueue.add(request);

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