package com.example.hrsystem.leavemanagment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hrsystem.GlobalClass;
import com.example.hrsystem.MySingleton;
import com.example.hrsystem.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
public class EmpView extends AppCompatActivity {
    ListView lv;
    EditText id;
    Button submit;
    String Sid;
    GlobalClass globalClass;

    private static String empid[];
    private static String empname[];
    private static String empdept[];
    private static String Reasonofleave[];
    private static String sdate[];
    private static String edate[];
    private static String descriptionbox[];
    private static String status[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_view2);
        lv = (ListView) findViewById(R.id.lv);

        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Leave Application");
        setSupportActionBar(toolbar);
        globalClass=(GlobalClass)getApplicationContext();
        Sid=globalClass.getEmpid();
        Emp(Sid);
    }
    private void Emp(final String Semp_id){
        final ProgressDialog progressDialog = new ProgressDialog(EmpView.this);
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

                    empid = new String[ja.length()];
                    empname = new String[ja.length()];
                    empdept = new String[ja.length()];
                    Reasonofleave = new String[ja.length()];
                    sdate = new String[ja.length()];
                    edate = new String[ja.length()];
                    descriptionbox = new String[ja.length()];
                    status = new String[ja.length()];




                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        empid[i] = jo.getString("empid");
                        empname[i] = jo.getString("username");
                        empdept[i] = jo.getString("empdept");
                        Reasonofleave[i] = jo.getString("Reasonofleave");
                        sdate[i] =jo.getString("sdate");
                        edate[i] =jo.getString("edate");
                        descriptionbox[i] =jo.getString("descriptionbox");
                        status[i] =jo.getString("status");



                    }
                    myadapter adptr = new EmpView.myadapter(getApplicationContext(),empid,empname,empdept,Reasonofleave,sdate,edate,descriptionbox,status);
                    lv.setAdapter(adptr);

                } catch (Exception ex) {

                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EmpView.this, error.toString(), Toast.LENGTH_SHORT).show();
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
        MySingleton.getmInstance(EmpView.this).addToRequestQueue(request);

    }




    class myadapter extends ArrayAdapter<String> {
        Context context;
        String ID[];
        String Name[];
        String Department[];
        String Reasonofleave[];
        String Start_Date[];
        String End_Date[];
        String Description[];
        String Status[];




        myadapter(Context c, String empid[], String empname[], String empdept[], String Reasonofleave[],String sdate[],String edate[],String descriptionbox[],String status[]) {
            super(c, R.layout.emprow, R.id.empid,empid);
            context = c;
            this.ID = empid;
            this.Name = empname;
            this.Department = empdept;
            this.Reasonofleave = Reasonofleave;
            this.Start_Date = sdate;
            this.End_Date = edate;
            this.Description = descriptionbox;
            this.Status = status;


        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.emprow, parent, false);
            TextView tv1 = row.findViewById(R.id.empid);
            TextView tv2 = row.findViewById(R.id.empname);
            TextView tv3 = row.findViewById(R.id.empdept);
            TextView tv4 = row.findViewById(R.id.leavereason);
            TextView tv5 = row.findViewById(R.id.sdate);
            TextView tv6 = row.findViewById(R.id.edate);
            TextView tv7 = row.findViewById(R.id.descriptionbox);
            TextView tv8 = row.findViewById(R.id.sts);



            tv1.setText("ID:"+ID[position]);
            tv2.setText("Name:\n"+Name[position]);
            tv3.setText("Department:\n"+Department[position]);
            tv4.setText("Reason : \n"+Reasonofleave[position]);
            tv5.setText("Start Date : \n"+Start_Date[position]);
            tv6.setText("End Date: \n"+End_Date[position]);
            tv7.setText("Description: \n"+Description[position]);
            tv8.setText(Status[position]);






            return row;
        }
    }
}