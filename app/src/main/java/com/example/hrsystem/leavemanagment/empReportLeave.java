package com.example.hrsystem.leavemanagment;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hrsystem.CustomProgressDialog;
import com.example.hrsystem.GlobalClass;
import com.example.hrsystem.MySingleton;
import com.example.hrsystem.R;
import com.example.hrsystem.leavemanagment.admin.AdminReport;
import com.example.hrsystem.leavemanagment.admin.ReportAdapter;
import com.example.hrsystem.leavemanagment.admin.ReportData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class empReportLeave extends AppCompatActivity {
    EditText sdate,edate;
    Button btn;
    RecyclerView recyclerView;
    Integer mYear,mMonth,mDay,eYear,eMonth,eDay;
    CustomProgressDialog customProgressDialog;
    GlobalClass globalClass;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    public List<ReportData> reportDataa;
    private RecyclerView.Adapter adapter;
    String txtSdate,txtEdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_report_leave);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Leave Report");
        setSupportActionBar(toolbar);
        sdate = findViewById(R.id.sdate);
        btn = findViewById(R.id.btn);
        edate = findViewById(R.id.edate);
        globalClass=(GlobalClass) getApplicationContext();
        recyclerView = findViewById(R.id.recyclerView);
        reportDataa = new ArrayList<>();
        adapter = new ReportAdapter(getApplicationContext(), reportDataa);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), 0);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        customProgressDialog = new CustomProgressDialog(empReportLeave.this);
        
        sdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(empReportLeave.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                sdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }

                        }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });
        edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                eYear = c.get(Calendar.YEAR);
                eMonth = c.get(Calendar.MONTH);
                eDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(empReportLeave.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                edate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }

                        }, eYear, eMonth, eDay);
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSdate = sdate.getText().toString();
                txtEdate = edate.getText().toString();
                sendDates(txtSdate, txtEdate);
                btn.setEnabled(false);

            }
        });
        sdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                reportDataa.clear();
                btn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                reportDataa.clear();
                btn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void sendDates(String txtSdate, String txtEdate) {
        customProgressDialog.show();
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/leave/emp_report.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja = new JSONArray(response);
                    JSONObject jo = null;
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        ReportData reportData=new ReportData();
                        reportData.setEmpid(jo.getString("empid"));
                        reportData.setEmpname(jo.getString("username"));
                        reportData.setSts(jo.getString("status"));
                        reportDataa.add(reportData);
                    }
                } catch (Exception ex) {

                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
                adapter.notifyDataSetChanged();
                // progressDialog.dismiss();
                customProgressDialog.dismiss();
            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(empReportLeave.this, error.toString(), Toast.LENGTH_SHORT).show();
                //  progressDialog.dismiss();
                customProgressDialog.dismiss();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("sdate",txtSdate);
                param.put("edate",txtEdate);
                param.put("empid",globalClass.getEmpid());
                return param;

            }
        };


        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(empReportLeave.this).addToRequestQueue(request);

    }
    protected void onResume() {
        super.onResume();
        reportDataa.clear();
        //your code here
        adapter.notifyDataSetChanged();
    }
    
}