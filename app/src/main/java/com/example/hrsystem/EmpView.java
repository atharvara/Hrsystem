package com.example.hrsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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

    private static String emp_id[];
    private static String emp_name[];
    private static String payment[];
    private static String status[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_view);
        lv = (ListView) findViewById(R.id.lv);
        globalClass=(GlobalClass)getApplicationContext();
        Sid=globalClass.getEmpid();
        Emp(Sid);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Expense Status");
        setSupportActionBar(toolbar);



    }

    private void Emp(String Semp_id){
        final ProgressDialog progressDialog = new ProgressDialog(EmpView.this);
        progressDialog.setTitle("Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/expense/ViewUser.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja = new JSONArray(response);
                    JSONObject jo = null;

                    emp_id = new String[ja.length()];
                    emp_name = new String[ja.length()];
                    payment = new String[ja.length()];
                    status = new String[ja.length()];

                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        emp_id[i] = jo.getString("emp_id");
                        emp_name[i] = jo.getString("emp_name");
                        payment[i] = jo.getString("money");
                        status[i] = jo.getString("status");

                    }
                    myadapter adptr = new EmpView.myadapter(getApplicationContext(), emp_id, emp_name, payment, status);
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
                param.put("emp_id",Semp_id);
                return param;

            }
        };


        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(EmpView.this).addToRequestQueue(request);

    }




    class myadapter extends ArrayAdapter<String> {
        Context context;
        String id[];
        String name[];
        String payment[];
        String sts[];


        myadapter(Context c, String emp_id[], String emp_name[], String payment[], String status[]) {
            super(c, R.layout.emp_row, R.id.emp_id,emp_id);
            context = c;
            this.id = emp_id;
            this.name = emp_name;
            this.payment = payment;
            this.sts = status;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.emp_row, parent, false);
            ImageView img=row.findViewById(R.id.imageView);
            TextView tv1 = row.findViewById(R.id.emp_id);
            TextView tv2 = row.findViewById(R.id.emp_name);
            TextView tv3 = row.findViewById(R.id.payment);
            TextView tv4 = row.findViewById(R.id.status);


            tv1.setText("ID:"+id[position]);
            tv2.setText("Name:\n"+name[position]);
            tv3.setText("Payment:\nRs."+payment[position]);
            tv4.setText("Status: \n"+sts[position]);




            return row;
        }
    }
}