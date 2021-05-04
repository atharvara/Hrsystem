package com.example.hrsystem.requesttask.requestedtask;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hrsystem.AllData;
import com.example.hrsystem.GlobalClass;
import com.example.hrsystem.MySingleton;
import com.example.hrsystem.R;
import com.example.hrsystem.requesttask.taskAdapter;
import com.example.hrsystem.requesttask.taskModel;
import com.example.hrsystem.requesttask.viewTask;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class viewRequestedTask extends AppCompatActivity {
    GlobalClass globalClass;
    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    public List<RequestModel> taskList;
    private RecyclerView.Adapter adapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requested_task);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Request");
        setSupportActionBar(toolbar);
        globalClass=(GlobalClass)getApplicationContext();
        mList=findViewById(R.id.recylerViewviewApp);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        taskList=  new ArrayList<>();
        adapter = new RequestAdapter(getApplicationContext(),taskList);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(),0);
        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.setAdapter(adapter);

        mShimmerViewContainer.startShimmerAnimation();


    }
    public void Viewtask(String emp_id){
        /*final ProgressDialog progressDialog = new ProgressDialog(viewRequestedTask.this);
        progressDialog.setTitle("Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();*/
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/RequestTask/ViewRequest.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja = new JSONArray(response);
                    JSONObject jo = null;
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        RequestModel Task=new RequestModel();
                        Task.setTask(jo.getString("task"));
                        Task.setDueDate(jo.getString("dueDate"));
                        Task.setSts(jo.getString("sts"));
                        Task.setRequestName(jo.getString("requester"));
                        taskList.add(Task);
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
                Toast.makeText(viewRequestedTask.this, error.toString(), Toast.LENGTH_SHORT).show();
                //  progressDialog.dismiss();
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("emp_id",emp_id);
                return param;

            }
        };


        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(viewRequestedTask.this).addToRequestQueue(request);

    }

    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }
    public void onResume() {
        super.onResume();
        taskList.clear();
        Viewtask(globalClass.getEmpid());

        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.all){
            startActivity(new Intent(getApplicationContext(), AllDataRequest.class));
        }

        return super.onOptionsItemSelected(item);
    }
}