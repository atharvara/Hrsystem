package com.example.hrsystem.leavemanagment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


import com.example.hrsystem.AdminDashboard;
import com.example.hrsystem.R;

public class ViewApp extends AppCompatActivity {
    private static final String apiurl = "https://shinetech.site/shinetech.site/hrmskbp/leave/ExtractData.php";
    ListView lv;

    private static String emp_id[];
    private static String emp_name[];
    private static String status[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_app);
        lv = (ListView) findViewById(R.id.lv);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Leave Application");
        setSupportActionBar(toolbar);


        fetch_data_into_array(lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = lv.getItemAtPosition(position).toString();
                Intent intent=new Intent(ViewApp.this,userview.class);
                intent.putExtra("ID",s);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, AdminDashboard.class));
        finish();
    }
    public void fetch_data_into_array(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(ViewApp.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Receiving Data");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        class dbManager extends AsyncTask<String, Void, String> {

            protected void onPostExecute(String data) {
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;

                    emp_id = new String[ja.length()];
                    emp_name = new String[ja.length()];
                    status = new String[ja.length()];
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        emp_id[i] = jo.getString("empid");
                        emp_name[i] = jo.getString("username");
                        status[i] = jo.getString("status");


                    }

                    myadapter adptr = new myadapter(getApplicationContext(), emp_id, emp_name, status);
                    lv.setAdapter(adptr);
                    progressDialog.dismiss();
                } catch (Exception ex) {

                    System.out.println("Exception"+ex);
                    progressDialog.dismiss();
                }
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


    class myadapter extends ArrayAdapter<String> {
        Context context;
        String id[];
        String name[];
        String sts[];
        myadapter(Context c, String[] emp_id, String[] emp_name, String[] status) {
            super(c, R.layout.leave_row, R.id.emp_id,emp_id);
            context = c;
            this.id = emp_id;
            this.name = emp_name;
            this.sts = status;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.leave_row, parent, false);
            TextView tv1 = row.findViewById(R.id.emp_id);
            TextView tv2 = row.findViewById(R.id.emp_name);
            TextView tv4 = row.findViewById(R.id.status);

            tv1.setText("ID:"+id[position]);
            tv2.setText("Name:\n"+name[position]);
            tv4.setText("Status: \n"+sts[position]);

            return row;

        }

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
            startActivity(new Intent(getApplicationContext(), AllDataLeave.class));
        }

        return super.onOptionsItemSelected(item);
    }
}