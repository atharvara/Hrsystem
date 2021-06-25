package com.example.hrsystem.leavemanagment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.hrsystem.AllData;
import com.example.hrsystem.CustomProgressDialog;
import com.example.hrsystem.GlobalClass;
import com.example.hrsystem.R;
import com.example.hrsystem.leavemanagment.admin.ReportData;
import com.example.hrsystem.requesttask.AllDataTaskAdapter;
import com.example.hrsystem.requesttask.AllDataTaskModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AllDataLeave extends AppCompatActivity {
    private RecyclerView mList;
    String apiurl="https://shinetech.site/shinetech.site/hrmskbp/leave/alldata.php";
CustomProgressDialog customProgressDialog;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    public List<AllDataModel> taskList;
    private RecyclerView.Adapter adapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_data_leave);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("All Application");
        setSupportActionBar(toolbar);
        customProgressDialog=new CustomProgressDialog(AllDataLeave.this);
        mList=findViewById(R.id.recylerView);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        taskList=  new ArrayList<>();
        adapter = new AllDataAdapter(getApplicationContext(),taskList);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(),0);
        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.setAdapter(adapter);
        mShimmerViewContainer.startShimmerAnimation();
        fetch_data_into_array();
    }
    public void fetch_data_into_array() {

        class dbManager extends AsyncTask<String, Void, String> {

            protected void onPostExecute(String data) {
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        AllDataModel allDataModel=new AllDataModel();
                        allDataModel.setEmpid(jo.getString("empid"));
                        allDataModel.setEmpname(jo.getString("username"));
                        allDataModel.setSts(jo.getString("status"));
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
}