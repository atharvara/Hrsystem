package com.example.hrsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
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

public class AllData extends AppCompatActivity {
    private static final String apiurl = "https://shinetech.site/shinetech.site/hrmskbp/expense/alluser.php";
    ListView lv;

    private static String emp_id[];
    private static String emp_name[];
    private static String payment[];
    private static String status[];
    private static String img[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_data);
        lv = (ListView) findViewById(R.id.lv);


        fetch_data_into_array(lv);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            String s = lv.getItemAtPosition(position).toString();

            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        });
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("View Request");
        setSupportActionBar(toolbar);


    }

    public void fetch_data_into_array(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(AllData.this);
        progressDialog.setTitle("Loading");
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
                    payment = new String[ja.length()];
                    status = new String[ja.length()];
                    img = new String[ja.length()];
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        emp_id[i] = jo.getString("emp_id");
                        emp_name[i] = jo.getString("emp_name");
                        payment[i] = jo.getString("money");
                        status[i] = jo.getString("status");
                        img[i] =jo.getString("image");


                    }

                    AllData.myadapter adptr = new AllData.myadapter(getApplicationContext(), emp_id, emp_name, payment, status,img);
                    lv.setAdapter(adptr);
                    progressDialog.dismiss();
                } catch (Exception ex) {

                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
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
        String payment[];
        String sts[];
        String rimg[];
        myadapter(Context c, String[] emp_id, String[] emp_name, String[] payment, String[] status,String[] rimg) {
            super(c, R.layout.row, R.id.emp_id,emp_id);
            context = c;
            this.id = emp_id;
            this.name = emp_name;
            this.payment = payment;
            this.sts = status;
            this.rimg=rimg;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.all_row, parent, false);
            ImageView img=row.findViewById(R.id.imageView);
            TextView tv1 = row.findViewById(R.id.emp_id);
            TextView tv2 = row.findViewById(R.id.emp_name);
            TextView tv3 = row.findViewById(R.id.payment);
            TextView tv4 = row.findViewById(R.id.status);

            tv1.setText("ID:"+id[position]);
            tv2.setText("Name:\n"+name[position]);
            tv3.setText("Payment:\nRs."+payment[position]);
            tv4.setText("Status: \n"+sts[position]);
            String url=rimg[position];
            class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
                private String url;
                private ImageView imageView;

                public ImageLoadTask(String url, ImageView imageView) {
                    this.url = url;
                    this.imageView = imageView;
                }

                @Override
                protected Bitmap doInBackground(Void... params) {
                    try {
                        URL connection = new URL(url);
                        InputStream input = connection.openStream();
                        Bitmap myBitmap = BitmapFactory.decodeStream(input);
                        Bitmap resized = Bitmap.createScaledBitmap(myBitmap, 400, 400, true);
                        return resized;
                    } catch (Exception e) {
                        new Thread()
                        {
                            public void run()
                            {
                                AllData.this.runOnUiThread(new Runnable()
                                {
                                    public void run()
                                    {
                                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }.start();
                    }
                    return null;
                }
                @Override
                protected void onPostExecute(Bitmap result) {
                    super.onPostExecute(result);
                    imageView.setImageBitmap(result);
                }
            }
            ImageLoadTask obj=new ImageLoadTask(url,img);
            obj.execute();
            return row;

        }
    }
}