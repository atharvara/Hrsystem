package com.example.hrsystem.requesttask;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class viewTask extends AppCompatActivity {
    RecyclerView recyclerView;
    GlobalClass globalClass;

    private static String task[];
    private static String dueDate[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Task");
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.recylerViewviewApp);
        globalClass=(GlobalClass)getApplicationContext();

        Viewtask(globalClass.getEmpid());
    }
    public void Viewtask(String emp_id){
        final ProgressDialog progressDialog = new ProgressDialog(viewTask.this);
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

                    task = new String[ja.length()];
                    dueDate = new String[ja.length()];


                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        task[i] = jo.getString("task");
                        dueDate[i] = jo.getString("dueDate");
                    }
                    viewTask.myadapter adptr = new viewTask.myadapter(getApplicationContext(), task, dueDate);
                    recyclerView.setAdapter(adptr);

                } catch (Exception ex) {

                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(viewTask.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
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

    public class myadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        Context context;
        String task[];
        String dueDate[];
        TextView tv1;
        TextView tv2;
        public myadapter(Context c, String[] task, String[] dueDate) {
            super(c, R.layout.task_view, R.id.taskE,task);
            context = c;
            this.task = task;
            this.dueDate = dueDate;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.task_view, viewGroup, false);
             tv1=view.findViewById(R.id.taskE);
             tv2=view.findViewById(R.id.dueDate);
            return new myadapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            tv1.setText();
        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View view) {
                super(view);
            }
        }
    }


    }