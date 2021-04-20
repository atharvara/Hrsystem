package com.example.hrsystem.support;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hrsystem.MySingleton;
import com.example.hrsystem.R;
import com.example.hrsystem.expense.ExpenseActivity;
import com.example.hrsystem.requesttask.requestedtask.AllDataRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SupportPage extends AppCompatActivity {
    EditText empid,empname,email,issue,issueindet;
    String Sempid,Sempname,Semail,Sissue,Sissueindet,uploadImage;
    ImageButton upload;
    ImageView imageView;
    Button submit;
    Bitmap bitmap;
    private Uri filePath;
    private int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_page);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Support And Help desk");
        setSupportActionBar(toolbar);
        empid=(EditText)findViewById(R.id.emp_id);
        empname=(EditText)findViewById(R.id.emp_name);
        email=(EditText)findViewById(R.id.email);
        issue=(EditText)findViewById(R.id.issue);
        issueindet=(EditText)findViewById(R.id.issueDetail);
        upload = (ImageButton) findViewById(R.id.upload);
        imageView = (ImageView) findViewById(R.id.imageView);
        submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(v -> {


            Sempid = empid.getText().toString();
            Sempname = empname.getText().toString();
            Semail = email.getText().toString();
            Sissue = issue.getText().toString();
            Sissueindet = issueindet.getText().toString();
            uploadImage=getStringImage(bitmap);

            sendData(Sempid, Sempname, Semail,Sissue,Sissueindet,uploadImage);

        });
        upload.setOnClickListener(view -> showFileChooser());
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
            Toast.makeText(SupportPage.this,"Image required",Toast.LENGTH_LONG).show();
        }
        return encodedImage;
    }
    public void sendData(String sempid,String sempname,String semail,String sissue,String sissueindet,String uploadImage){
        final ProgressDialog progressDialog = new ProgressDialog(SupportPage.this);
        progressDialog.setTitle("Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String uRl = "https://shinetech.site/shinetech.site/hrmskbp/Support/supportPage.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SupportPage.this, response, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SupportPage.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("empid", sempid);
                param.put("empname", sempname);
                param.put("email", semail);
                param.put("issue", sissue);
                param.put("issueindet", sissueindet);
                param.put("image", uploadImage);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(SupportPage.this).addToRequestQueue(request);

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
            startActivity(new Intent(getApplicationContext(), AppStsHelp.class));
        }

        return super.onOptionsItemSelected(item);
    }
}