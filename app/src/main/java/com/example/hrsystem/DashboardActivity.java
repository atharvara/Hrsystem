package com.example.hrsystem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hrsystem.Report.BeforeReportActivity;
import com.example.hrsystem.employee.EmpList;
import com.example.hrsystem.expense.ExpenseActivity;
import com.example.hrsystem.leavemanagment.MainActivity;
import com.example.hrsystem.performance.PerformanceActivity;
import com.example.hrsystem.requesttask.RequestPage;
import com.example.hrsystem.requesttask.requestedtask.viewRequestedTask;
import com.example.hrsystem.requesttask.viewTask;
import com.example.hrsystem.support.SupportPage;

import java.io.BufferedReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {
    private ImageButton imageButton;
    Button employee,expense,leave,calendeR,Request,chat,help,performance,Compe,attend,hrd,report,reportDash,changepass;
    SharedPreferences sharedPreferences;
    TextView task,requestTxt;
    public int mYear, mMonth, mDay;
    GlobalClass globalClass;
    ProgressDialog progressDialog;
    CustomProgressDialog customProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        leave=(Button) findViewById(R.id.leave);
        employee=(Button)findViewById(R.id.employee);
        performance=findViewById(R.id.performance);
        expense=(Button)findViewById(R.id.expense);
        help=(Button)findViewById(R.id.help);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");
        setSupportActionBar(toolbar);
        calendeR=(Button)findViewById(R.id.calender);
        chat=(Button) findViewById(R.id.chat);
        Request=(Button)findViewById(R.id.request);
        task=(TextView)findViewById(R.id.task);
        requestTxt= findViewById(R.id.requested);
        changepass=findViewById(R.id.changepass);
        Compe=findViewById(R.id.compen);
       customProgressDialog=new CustomProgressDialog(DashboardActivity.this);
       /* progressDialog = new ProgressDialog(DashboardActivity.this);

        progressDialog.setContentView(R.layout.progreess_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);*/
        globalClass=(GlobalClass)getApplicationContext();
        attend=findViewById(R.id.attend);
        report=findViewById(R.id.report);
        hrd=findViewById(R.id.hrd);
       /* reportDash=findViewById(R.id.formdash);
        reportDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });*/
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, empChangePass.class));
            }
        });
        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });
        hrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, BeforeReportActivity.class));
            }
        });
        findTask(globalClass.getEmpid());
        findRequest(globalClass.getEmpid());
        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        calendeR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(DashboardActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                            }

                        }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });

        leave.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, MainActivity.class)));
        task.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, viewTask.class)));
        requestTxt.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, viewRequestedTask.class)));
        employee.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, EmpList.class)));
        expense.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, ExpenseActivity.class)));
        Request.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, RequestPage.class)));
        help.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, SupportPage.class)));
        performance.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, PerformanceActivity.class)));
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentWhatsapp = new Intent(Intent.ACTION_VIEW);
                String url = "https://chat.whatsapp.com/GB8NYA8uxBN2CC8gADsyAm";
                intentWhatsapp.setData(Uri.parse(url));
                intentWhatsapp.setPackage("com.whatsapp");
                startActivity(intentWhatsapp);
            }
        });
        Compe.setOnClickListener(v -> {
            showCustomDialog();
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        //your code here
        findTask(globalClass.getEmpid());
        findRequest(globalClass.getEmpid());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.toobar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(getResources().getString(R.string.prefStatus),"loggedout");
            editor.apply();

            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        if(item.getItemId()==R.id.profile){

            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

        }

        return super.onOptionsItemSelected(item);
    }
    public void findTask(String Emp_id){

        customProgressDialog.show();
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/RequestTask/countTask.php";
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                customProgressDialog.dismiss();
                task.setText(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DashboardActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
        MySingleton.getmInstance(DashboardActivity.this).addToRequestQueue(request);

    }
    public void findRequest(String Emp_id){
        customProgressDialog.show();
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/RequestTask/countRequest.php";
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                customProgressDialog.dismiss();
                requestTxt.setText(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DashboardActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
        MySingleton.getmInstance(DashboardActivity.this).addToRequestQueue(request);

    }
    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.under_construction, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button btn=dialogView.findViewById(R.id.buttonOk);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

}