package com.example.hrsystem.performance;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hrsystem.GlobalClass;
import com.example.hrsystem.MySingleton;
import com.example.hrsystem.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerformanceActivity extends AppCompatActivity {
    private TextView txtProgress;
    GlobalClass globalClass;
    private ProgressBar progressBar;
    String nooftask;
    String nooftaskcom;
    ProgressBar progressBar1;
    TextView txt1,txt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Performance");
        setSupportActionBar(toolbar);
        txtProgress = (TextView) findViewById(R.id.txtProgress);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar1=findViewById(R.id.prograss);
        globalClass=(GlobalClass)getApplicationContext();
        txt1=findViewById(R.id.totalass);
        txt2=findViewById(R.id.totalcom);
        viewPer(globalClass.getEmpid());
    }
    private void viewPer(String  i) {
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/performance/performance.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray ja = new JSONArray(response);
                    JSONObject jo = null;
                    jo = ja.getJSONObject(0);
                    // dataModel dataModel=new dataModel();
                    nooftask= jo.getString("nooftask");
                    nooftaskcom=jo.getString("nooftaskcom");
                    // taskList.add(dataModel);
                    Float nooftaska=Float.parseFloat(nooftask);
                    Float nooftaskcoma=Float.parseFloat(nooftaskcom);

                    float tot=(nooftaskcoma/nooftaska)*100;
                    //Toast.makeText(getApplicationContext(), Float.toString(tot), Toast.LENGTH_LONG).show();

                    int total=(int)tot;
                    progressBar.setProgress(total);
                    txtProgress.setText(total + " %");
                    txt1.setText(nooftask);
                    txt2.setText(nooftaskcom);
                    progressBar1.setVisibility(View.GONE);


                } catch (Exception ex) {

                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
                // progressDialog.dismiss();

            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PerformanceActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("emp_id",i);
                return param;

            }
        };


        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(PerformanceActivity.this).addToRequestQueue(request);

    }
}