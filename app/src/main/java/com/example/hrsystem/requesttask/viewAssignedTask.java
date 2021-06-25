package com.example.hrsystem.requesttask;

import android.os.AsyncTask;
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
import com.example.hrsystem.leavemanagment.AllDataModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class viewAssignedTask extends AppCompatActivity {
    private RecyclerView mList;
    private ShimmerFrameLayout mShimmerViewContainer;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    public List<assignedModel> taskList;
    private RecyclerView.Adapter adapter;
    String apiurl="https://shinetech.site/shinetech.site/hrmskbp/RequestTask/viewAssignedTask.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assigned_task);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Assigned Task");
        setSupportActionBar(toolbar);
        mList=findViewById(R.id.recylerViewviewApp);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        taskList=  new ArrayList<>();
        adapter = new assignedAdapter(getApplicationContext(),taskList);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(),0);
        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.setAdapter(adapter);
        mShimmerViewContainer.startShimmerAnimation();
    }
    public void Viewtask(){
        class dbManager extends AsyncTask<String, Void, String> {

            protected void onPostExecute(String data) {
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        assignedModel allDataModel=new assignedModel();
                        allDataModel.setTask(jo.getString("task"));
                        allDataModel.setEmpid(jo.getString("allemp"));
                        allDataModel.setDuedate(jo.getString("dueDate"));
                        taskList.add(allDataModel);
                        adapter.notifyDataSetChanged();
                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {

                    System.out.println("Exception"+ex);
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                }

                // progressDialog.dismiss();

            }

            @Override
            protected String doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuffer data = new StringBuffer();
                    String line;

                    while ((line = br.readLine()) != null) {
                        data.append(line + "\n");
                    }
                    br.close();

                    return data.toString();

                } catch (Exception ex) {
                    return ex.getMessage();
                }

            }

        }
        dbManager obj = new dbManager();
        obj.execute(apiurl);

    }
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }
    public void onResume() {
        super.onResume();
        taskList.clear();
        Viewtask();

        adapter.notifyDataSetChanged();

    }
}