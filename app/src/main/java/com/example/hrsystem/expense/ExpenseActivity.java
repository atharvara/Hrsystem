package com.example.hrsystem.expense;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hrsystem.EmpView;
import com.example.hrsystem.GlobalClass;
import com.example.hrsystem.MySingleton;
import com.example.hrsystem.R;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class ExpenseActivity extends AppCompatActivity {
    Button submit, viewu, empview, upload;
    EditText  emp_name, payment;
    String Semp_id, Semp_name, Spayment,uploadImage;
    ImageView imageView;
    Bitmap bitmap;
    private Uri filePath;
    private static String img[];

    private int PICK_IMAGE_REQUEST = 1;
GlobalClass g;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        submit = (Button) findViewById(R.id.submit);
        emp_name = (EditText) findViewById(R.id.emp_name);
        payment = (EditText) findViewById(R.id.payment);
    g=(GlobalClass)getApplicationContext();
        empview = (Button) findViewById(R.id.emp_view);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Expense");
        setSupportActionBar(toolbar);

        upload = (Button) findViewById(R.id.upload);
        imageView = (ImageView) findViewById(R.id.imageView);

        submit.setOnClickListener(v -> {


            Semp_id = g.getEmpid();
            Semp_name = emp_name.getText().toString();
            Spayment = payment.getText().toString();

            uploadImage=getStringImage(bitmap);

            pay(Semp_id, Semp_name, Spayment, uploadImage);

        });
        upload.setOnClickListener(view -> showFileChooser());

        empview.setOnClickListener(v -> startActivity(new Intent(ExpenseActivity.this, EmpView.class)));

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        String encodedImage = null;
        if(bitmap!=null) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;

        }else{
            Toast.makeText(ExpenseActivity.this,"Image required",Toast.LENGTH_LONG).show();
        }
        return encodedImage;
    }

    private void pay(String Semp_id, String Semp_name, String Spayment,String uploadImage) {
        final ProgressDialog progressDialog = new ProgressDialog(ExpenseActivity.this);
        progressDialog.setTitle("Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/expense/payment.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ExpenseActivity.this, response, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ExpenseActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("emp_id", Semp_id);
                param.put("emp_name", Semp_name);
                param.put("payment", Spayment);
                param.put("image", uploadImage);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(ExpenseActivity.this).addToRequestQueue(request);

    }
}
