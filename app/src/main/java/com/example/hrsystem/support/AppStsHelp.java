package com.example.hrsystem.support;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.example.hrsystem.requesttask.AllDataTask;
import com.example.hrsystem.requesttask.AllDataTaskAdapter;
import com.example.hrsystem.requesttask.AllDataTaskModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppStsHelp extends AppCompatActivity {
    GlobalClass globalClass;
    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    public List<HelpModel> helpList;
    private RecyclerView.Adapter adapter;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_sts_help);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Support");
        setSupportActionBar(toolbar);
        globalClass=(GlobalClass)getApplicationContext();
        mList=findViewById(R.id.recylerView);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        helpList=  new ArrayList<>();
        adapter = new HelpAdapter(getApplicationContext(),helpList);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(),0);
        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.setAdapter(adapter);
        mShimmerViewContainer.startShimmerAnimation();
    }
    public void ViewHelp(String emp_id){
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/Support/emp_view.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja = new JSONArray(response);
                    JSONObject jo = null;
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        HelpModel help=new HelpModel();
                        help.setIssue(jo.getString("issue"));
                        help.setEmpid(jo.getString("emp_id"));
                        help.setSts(jo.getString("sts"));
                        helpList.add(help);
                    }
                } catch (Exception ex) {

                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
                adapter.notifyDataSetChanged();
                // progressDialog.dismiss();
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AppStsHelp.this, error.toString(), Toast.LENGTH_SHORT).show();
                //  progressDialog.dismiss();
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("empid",emp_id);
                return param;

            }
        };


        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(AppStsHelp.this).addToRequestQueue(request);

    }

    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //your code here
        helpList.clear();
        ViewHelp(globalClass.getEmpid());
        adapter.notifyDataSetChanged();
    }
}