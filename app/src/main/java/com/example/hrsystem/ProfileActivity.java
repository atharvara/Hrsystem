package com.example.hrsystem;

import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hrsystem.support.adminhelp.DetailedPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    CustomProgressDialog customProgressDialog;
    TextView emp_name,emp_phone,emp_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        
        setContentView(R.layout.activity_profile);
        TextView t=(TextView)findViewById(R.id.idemp);
        emp_name=findViewById(R.id.emp_name);
        emp_email=findViewById(R.id.email);
       // emp_phone=findViewById(R.id.phone);
        GlobalClass g=(GlobalClass)getApplicationContext();
        customProgressDialog=new CustomProgressDialog(ProfileActivity.this);
        t.setText("Employee Id: "+g.getEmpid());
        t.setTextColor(getResources().getColor(R.color.black));
        sendReq(g.getEmpid());

    }
    public void sendReq(String emp_id){
        customProgressDialog.show();
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/profile/profile.php";
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONArray ja = null;
                try {
                    ja = new JSONArray(response);
                    JSONObject jo = null;
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        emp_name.setText("Name :- "+jo.getString("fullname"));
                        emp_email.setText(jo.getString("email"));
                       /* if(jo.getString("phn").length()!=0) {
                            emp_phone.setText(jo.getString("phn"));
                        }
                        else{
                            emp_phone.setText("No Number");
                        }*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                customProgressDialog.dismiss();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                customProgressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("emp_id", emp_id);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(ProfileActivity.this).addToRequestQueue(request);
    }
}