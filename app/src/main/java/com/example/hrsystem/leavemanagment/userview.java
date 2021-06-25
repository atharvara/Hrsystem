package com.example.hrsystem.leavemanagment;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


import com.example.hrsystem.AllData;
import com.example.hrsystem.MySingleton;
import com.example.hrsystem.R;

public class userview extends AppCompatActivity {
    String Sid;

    private static String emp_id[];
    private static String emp_name[];
    private static String department[];
    private static String sdate[];
    private static String reason[];
    private static String edate[];
    private static String des[];
    private static String sts[];
    TextView empid,empname,empdept,ereason,esdate,eedate,edes,estatus;
    Button yes,no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userview);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Leave Description");
        setSupportActionBar(toolbar);

        empid=(TextView)findViewById(R.id.empid);
        empname=(TextView)findViewById(R.id.empname);
        empdept=(TextView)findViewById(R.id.empdept);
        ereason=(TextView)findViewById(R.id.reason);
        esdate=(TextView)findViewById(R.id.sdate);
        eedate=(TextView)findViewById(R.id.edate);
        edes=(TextView)findViewById(R.id.des);
        estatus=(TextView)findViewById(R.id.status);
        yes=(Button)findViewById(R.id.yes);
        no=(Button)findViewById(R.id.no);

        Sid=getIntent().getStringExtra("ID");

        Emp(Sid);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ViewApp.class));
        finish();
    }
    private void Emp(final String Semp_id){
        final ProgressDialog progressDialog = new ProgressDialog(userview.this);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Receiving Data");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/leave/getUser.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja = new JSONArray(response);
                    JSONObject jo = null;

                    emp_id = new String[ja.length()];
                    emp_name = new String[ja.length()];
                    department = new String[ja.length()];
                    reason = new String[ja.length()];
                    sdate = new String[ja.length()];
                    edate = new String[ja.length()];
                    des = new String[ja.length()];
                    sts= new String[ja.length()];
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        empid.setText("ID: "+ (emp_id[i] = jo.getString("empid")));
                        empname.setText("Name: "+(emp_name[i] = jo.getString("username")));
                        empdept.setText("Department: "+(department[i] = jo.getString("empdept")));
                        ereason.setText("Reason: "+(reason[i] = jo.getString("Reasonofleave")));
                        esdate.setText("Start Date: "+(sdate[i] = jo.getString("sdate")));
                        eedate.setText("End Date: "+(edate[i] = jo.getString("edate")));
                        edes.setText("Description:\n"+(des[i] = jo.getString("descriptionbox")));
                        estatus.setText("Status:\n"+(sts[i] = jo.getString("status")));
                        stsA(emp_id[i]);
                        stsD(emp_id[i]);
                    }



                } catch (Exception ex) {

                    System.out.println("Exception"+ex);
                }
                progressDialog.dismiss();
            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(userview.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("empid",Semp_id);
                return param;

            }
        };


        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(userview.this).addToRequestQueue(request);

    }

    public void stsA(final String id){
        final String iid=id;
        final String sts="Approved";
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fsts(iid, sts);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });



    }
    public void stsD(final String id){
        final String iid=id;
        final String sts="Declined";
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fsts(iid, sts);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });

    }

    private void Fsts(final String iid, final String sts){
        final ProgressDialog progressDialog = new ProgressDialog(userview.this);
        progressDialog.setTitle("Sending Request");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/leave/approve.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(userview.this, response, Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(userview.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("emp_id",iid);
                param.put("status",sts);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(userview.this).addToRequestQueue(request);
    }

}