package com.example.hrsystem.support;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hrsystem.MySingleton;
import com.example.hrsystem.R;
import com.example.hrsystem.fullImage;
import com.example.hrsystem.support.adminhelp.DetailedPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DetailedPageEmp extends AppCompatActivity {
    String issue, empid;
    TextView issueE, empi, issueindetail, ans;
    ImageView imageView;
    String image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_page_emp);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Support");
        setSupportActionBar(toolbar);
        issue = getIntent().getStringExtra("issue");
        empid = getIntent().getStringExtra("id");
        sendrequest(issue, empid);
        issueE = (TextView) findViewById(R.id.issue);
        empi = (TextView) findViewById(R.id.empid);
        issueindetail = (TextView) findViewById(R.id.issueDetail);
        ans = (TextView) findViewById(R.id.ans);
        imageView = findViewById(R.id.imageView);
    }
    public void sendrequest(String issue, String id) {
        final ProgressDialog progressDialog = new ProgressDialog(DetailedPageEmp.this);
        progressDialog.setTitle("Receiving Data.......");
        progressDialog.setMessage("Loading Data!");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/Support/detailedView.php";
        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONArray ja = null;
                try {
                    ja = new JSONArray(response);
                    JSONObject jo = null;
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        issueE.setText("Issue :- "+jo.getString("issue"));
                        empi.setText("Employee Id:- "+jo.getString("emp_id"));
                        issueindetail.setText("Isse In detail:- "+jo.getString("issueindet"));
                        if (jo.getString("ans")!="null") {
                            ans.setText("Answer:- " + jo.getString("ans"));
                        }else {
                            ans.setText("Answer:- No Answer Yet!!");
                        }
                        image = (jo.getString("image"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                DetailedPageEmp.ImageLoadTask obj = new DetailedPageEmp.ImageLoadTask(image, imageView);
                obj.execute();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 3000);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailedPageEmp.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("emp_id", id);
                param.put("issue", issue);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(DetailedPageEmp.this).addToRequestQueue(request);

    }

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
                new Thread() {
                    public void run() {
                        DetailedPageEmp.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(DetailedPageEmp.this, fullImage.class);
                    intent.putExtra("imageurl",url);
                    startActivity(intent);
                }
            });
        }
    }
}