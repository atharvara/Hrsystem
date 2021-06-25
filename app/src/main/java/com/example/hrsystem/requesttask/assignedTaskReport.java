package com.example.hrsystem.requesttask;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hrsystem.CustomProgressDialog;
import com.example.hrsystem.MySingleton;
import com.example.hrsystem.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class assignedTaskReport extends AppCompatActivity {
String task,dueDate;
TextView txttask,txtdueDate;
CustomProgressDialog customProgressDialog;
    public List<assignedTaskModel> taskList;
    String data = "";
    TableLayout tl;
    TableRow tr;
    TextView label;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_task_report);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Task Status");
        setSupportActionBar(toolbar);
        task=getIntent().getStringExtra("task");
        dueDate=getIntent().getStringExtra("due");
        txttask=findViewById(R.id.task);
        txtdueDate=findViewById(R.id.dueDate);
        txttask.setText("Task:-"+task);
        txtdueDate.setText("Due Date:-"+dueDate);
        taskList=  new ArrayList<>();
        tl = (TableLayout) findViewById(R.id.maintable);
        customProgressDialog=new CustomProgressDialog(assignedTaskReport.this);
        reciveData(task,dueDate);
    }
    public void reciveData(String task,String dueDate){
        customProgressDialog.show();
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/RequestTask/test.php";
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray ja = new JSONArray(response);
                    JSONObject jo = null;
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        assignedTaskModel allDataModel=new assignedTaskModel();
                        allDataModel.setEmpid(jo.getString("empid"));
                        allDataModel.setSts(jo.getString("status"));
                        taskList.add(allDataModel);

                    }
                } catch (Exception ex) {

                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
                customProgressDialog.dismiss();
                addData(taskList);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(assignedTaskReport.this, error.toString(), Toast.LENGTH_SHORT).show();
                customProgressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("task", task);
                param.put("dueDate", dueDate);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(assignedTaskReport.this).addToRequestQueue(request);
    }
    void addHeader(){
        /** Create a TableRow dynamically **/
        tr = new TableRow(this);

        /** Creating a TextView to add to the row **/
        label = new TextView(this);
        label.setText("Employee Id");
        label.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        label.setPadding(5, 5, 5, 5);
        label.setBackgroundColor(Color.WHITE);
        LinearLayout Ll = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 5, 5, 5);
        //Ll.setPadding(10, 5, 5, 5);
        Ll.addView(label,params);
        tr.addView((View)Ll); // Adding textView to tablerow.

        /** Creating Qty Button **/
        TextView place = new TextView(this);
        place.setText("Status");
        place.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        place.setPadding(5, 5, 5, 5);
        place.setBackgroundColor(Color.WHITE);
        Ll = new LinearLayout(this);
        params = new LinearLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 5, 5, 5);
        //Ll.setPadding(10, 5, 5, 5);
        Ll.addView(place,params);
        tr.addView((View)Ll); // Adding textview to tablerow.




        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
    }

    @SuppressWarnings({ "rawtypes" })
    public void addData(List<assignedTaskModel> users) {

        addHeader();

        for (Iterator i = users.iterator(); i.hasNext();) {

            assignedTaskModel p = (assignedTaskModel) i.next();

            /** Create a TableRow dynamically **/
            tr = new TableRow(this);

            /** Creating a TextView to add to the row **/
            label = new TextView(this);
            label.setText(p.getEmpid());
            label.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            label.setPadding(5, 5, 5, 5);
            label.setBackgroundColor(Color.WHITE);
            LinearLayout Ll = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 2, 2, 2);
            //Ll.setPadding(10, 5, 5, 5);
            Ll.addView(label,params);
            tr.addView((View)Ll); // Adding textView to tablerow.

            /** Creating Qty Button **/
            TextView place = new TextView(this);
            place.setText(p.getSts());
            place.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            place.setPadding(5, 5, 5, 5);
            place.setBackgroundColor(Color.WHITE);
            Ll = new LinearLayout(this);
            params = new LinearLayout.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 2, 2, 2);
            //Ll.setPadding(10, 5, 5, 5);
            Ll.addView(place,params);
            tr.addView((View)Ll); // Adding textview to tablerow.




            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
        }
    }

}