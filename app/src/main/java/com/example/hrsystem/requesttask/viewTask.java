package com.example.hrsystem.requesttask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.example.hrsystem.requesttask.requestedtask.AllDataRequest;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class viewTask extends AppCompatActivity {
    GlobalClass globalClass;
    private RecyclerView mList;
    private ShimmerFrameLayout mShimmerViewContainer;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    public List<taskModel> taskList;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Task");
        setSupportActionBar(toolbar);
        mList=findViewById(R.id.recylerViewviewApp);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        globalClass=(GlobalClass)getApplicationContext();
        taskList=  new ArrayList<>();
        adapter = new taskAdapter(getApplicationContext(),taskList);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(),0);
        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.setAdapter(adapter);
        mShimmerViewContainer.startShimmerAnimation();
    }
    public void Viewtask(String emp_id){
       /* final ProgressDialog progressDialog = new ProgressDialog(viewTask.this);
        progressDialog.setTitle("Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();*/
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/RequestTask/ViewTask.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja = new JSONArray(response);
                    JSONObject jo = null;
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        taskModel Task=new taskModel();
                        Task.setTask(jo.getString("task"));
                        Task.setDueDate(jo.getString("dueDate"));
                        Task.setSts(jo.getString("status"));
                        taskList.add(Task);
                    }
                } catch (Exception ex) {

                    System.out.println("Exception"+ex);
                }
                adapter.notifyDataSetChanged();
               // progressDialog.dismiss();
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(viewTask.this, error.toString(), Toast.LENGTH_SHORT).show();
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
        MySingleton.getmInstance(viewTask.this).addToRequestQueue(request);

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
            startActivity(new Intent(getApplicationContext(), AllDataTask.class));
        }

        return super.onOptionsItemSelected(item);
    }


}