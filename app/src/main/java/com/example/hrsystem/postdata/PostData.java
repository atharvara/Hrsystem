package com.example.hrsystem.postdata;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.service.autofill.TextValueSanitizer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import com.example.hrsystem.R;

public class PostData extends AppCompatActivity implements
        View.OnClickListener {
    private ProgressDialog progress;


    TextView tvName;
    TextView tvCountry;
    TextView tvCity;
    TextView tvaddress;
    TextView tvzipcode;
    TextView tvphone;
    TextView tvemail;
    TextView tvmstatus;
    TextView tvage;
    TextView department;
    String depart;
    Button btnDatePicker,upload;
    EditText txtDate;
    String date;
    Button button;
    String name;
    String country;
    String  address;
    String  city;
    String zipcode;
    String   phone;
    String mstatus,uploadImage;


    RadioButton radioButton1;
    RadioGroup radioGroup33;

    String  email;
    String getgender;
    String study;
    String course;
    DatePicker picker;

    EditText ssc;
    EditText yearscl;
    EditText resultscl;
    EditText sclname;

    EditText clgcourse;
    EditText yearclg;
    EditText resultclg;
    EditText clgname;

    EditText degcourse;
    EditText yeardeg;
    EditText resultdeg;
    EditText degname;

    EditText mstcourse;
    EditText yearmst;
    EditText resultmst;
    EditText mstname;

    EditText title1;
    EditText yearemp;
    EditText compname1;

    EditText title2;
    EditText yearemp2;
    EditText compname2;

    String Stitle1;
    String Syearemp;
    String Scompname1;

    String Stitle2;
    String Syearemp2;
    String Scompname2;
    public int mYear, mMonth, mDay;
    ImageView imageView;
    Bitmap bitmap;
    private Uri filePath;
    private static String img[];
    public ProgressDialog pd;

    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pd = new ProgressDialog(PostData.this);

        final RadioGroup rbg=(RadioGroup) findViewById(R.id.radioGroup1);
        final RadioGroup radioGroup3 = findViewById(R.id.radioGroup3);
        button=(Button)findViewById(R.id.btn_submit);
        tvName=(EditText)findViewById(R.id.input_name);
        tvCountry=(EditText)findViewById(R.id.input_country);
        tvaddress = (EditText)findViewById(R.id.input_address);
        tvCity =(EditText)findViewById(R.id.input_city);
        tvzipcode=(EditText)findViewById(R.id.input_zipcode);
        tvphone=(EditText)findViewById(R.id.input_phone);
        tvemail=(EditText)findViewById(R.id.input_email);
        department=(EditText)findViewById(R.id.input_department);
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        radioGroup33 = (RadioGroup) findViewById(R.id.radioGroup3);


        tvage =(TextView) findViewById(R.id.input_age);
        txtDate=(EditText)findViewById(R.id.in_date);
        btnDatePicker.setOnClickListener(this);
        tvmstatus=(EditText)findViewById(R.id.input_mstatus);

        //education
        ssc=(EditText)findViewById(R.id.input_course_scl);
        yearscl=(EditText)findViewById(R.id.input_scl_year);
        resultscl=(EditText)findViewById(R.id.input_result_scl);
        sclname=(EditText)findViewById(R.id.input_sclname);

        clgcourse=(EditText)findViewById(R.id.input_course_clg);
        yearclg=(EditText)findViewById(R.id.input_year_clg);
        resultclg=(EditText)findViewById(R.id.input_result_clg);
        clgname=(EditText)findViewById(R.id.input_clgname);

        degcourse=(EditText)findViewById(R.id.input_course_deg);
        yeardeg=(EditText)findViewById(R.id.input_year_deg);
        resultdeg=(EditText)findViewById(R.id.input_result_deg);
        degname=(EditText)findViewById(R.id.input_degname);

        mstcourse=(EditText)findViewById(R.id.input_course_mst);
        yearmst=(EditText)findViewById(R.id.input_year_mst);
        resultmst=(EditText)findViewById(R.id.input_result_mst);
        mstname=(EditText)findViewById(R.id.input_mstname);


        title1=(EditText)findViewById(R.id.input_title1);
        yearemp=(EditText)findViewById(R.id.input_emp_year);
        compname1=(EditText)findViewById(R.id.input_comp_name);

        title2=(EditText)findViewById(R.id.input_title2);
        yearemp2=(EditText)findViewById(R.id.input_year_emp);
        compname2=(EditText)findViewById(R.id.input_comp_name2);
        upload=(Button)findViewById(R.id.btn_upload);
        imageView=(ImageView)findViewById(R.id.imageView);

        final RadioGroup radioGroup = findViewById(R.id.radioGroup1);
        if (radioGroup != null) {
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> getgender = (R.id.radioMale == checkedId) ? "male" : "female");
        }

        final RadioGroup radioGroup2 = findViewById(R.id.radioGroup2);
        if (radioGroup2 != null) {
            radioGroup2.setOnCheckedChangeListener((group, checkedId) -> study = (R.id.radioSYes == checkedId) ? "Yes" : "No");
        }


        upload.setOnClickListener(view -> showFileChooser());


        button.setOnClickListener((View.OnClickListener) v -> {

            pd.setMessage("loading");
            pd.show();


            if (tvName.getText().toString().isEmpty()) {

                tvName.setError(" Name is required!");
                Toast.makeText(PostData.this,tvName.getError().toString(),Toast.LENGTH_LONG).show();
                pd.dismiss();

            }else if(tvCountry.getText().toString().isEmpty()){
                tvCountry.setError(" Country is required!");
                Toast.makeText(PostData.this,tvCountry.getError().toString(),Toast.LENGTH_LONG).show();
                pd.dismiss();

            }else if(tvaddress.getText().toString().isEmpty()){
                tvaddress.setError("Address  is required!");
                Toast.makeText(PostData.this,tvaddress.getError().toString(),Toast.LENGTH_LONG).show();
                pd.dismiss();
            }else if (tvCity.getText().toString().isEmpty()) {
                tvCity.setError("City is required!");
                Toast.makeText(PostData.this,tvCity.getError().toString(),Toast.LENGTH_LONG).show();
                pd.dismiss();
            }else if (tvemail.getText().toString().isEmpty()) {
                tvemail.setError("Email is required!");
                Toast.makeText(PostData.this,tvemail.getError().toString(),Toast.LENGTH_LONG).show();
                pd.dismiss();
            }else if (tvzipcode.getText().toString().isEmpty()) {
                tvzipcode.setError("Pincode is required!");
                Toast.makeText(PostData.this,tvzipcode.getError().toString(),Toast.LENGTH_LONG).show();
                pd.dismiss();

            }else if (tvphone.getText().toString().isEmpty()) {
                tvphone.setError("Phone NO. is required!");
                Toast.makeText(PostData.this,tvphone.getError().toString(),Toast.LENGTH_LONG).show();
                pd.dismiss();
            }else if (tvmstatus.getText().toString().isEmpty()) {


                tvmstatus.setError("Marital Status is required!");
                Toast.makeText(PostData.this,tvmstatus.getError().toString(),Toast.LENGTH_LONG).show();
                pd.dismiss();
            } else if (department.getText().toString().isEmpty()) {


            department.setError("Marital Status is required!");
            Toast.makeText(PostData.this,department.getError().toString(),Toast.LENGTH_LONG).show();
            pd.dismiss();
        } else {
                int selectedId =radioGroup33.getCheckedRadioButtonId();
                radioButton1 = (RadioButton) findViewById(selectedId);
                mstatus = tvmstatus.getText().toString();
                phone = "+91" + tvphone.getText().toString();
                zipcode = tvzipcode.getText().toString();
                name = tvName.getText().toString();
                uploadImage=getStringImage(bitmap);
                country = tvCountry.getText().toString();
                address = tvaddress.getText().toString();
                depart=department.getText().toString();
                Stitle1=  title1.getText().toString();
                city = tvCity.getText().toString();
                Syearemp    = yearemp.getText().toString();
                Scompname1  = compname1.getText().toString();
                email = tvemail.getText().toString();
                Stitle2    = title2.getText().toString();
                Syearemp2     = yearemp2.getText().toString();
                Scompname2  =  compname2.getText().toString();
                new AlertDialog.Builder(PostData.this)
                        .setTitle("Confimation")
                        .setMessage("Are You Sure ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> new SendRequest().execute())
                        .setNegativeButton(android.R.string.no, null).show();

            }

        });

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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
            Toast.makeText(PostData.this,"Image required",Toast.LENGTH_LONG).show();
        }
        return encodedImage;
    }

    public class SendRequest extends AsyncTask<String, Void, String> {



        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {
            pd.setMessage("loading");
            pd.show();

            try{

                URL url = new URL("https://shinetech.site/shinetech.site/hrmskbp/onboarding/onboarding.php");

                JSONObject postDataParams = new JSONObject();



                postDataParams.put("name",name);
                postDataParams.put("address",address);
                postDataParams.put("city",city);
                postDataParams.put("zipcode",zipcode);
                postDataParams.put("country",country);
                postDataParams.put("phone",phone);
                postDataParams.put("email",email);
                postDataParams.put("department",depart);
                postDataParams.put("gender",getgender);
                postDataParams.put("dob",txtDate.getText().toString());
                postDataParams.put("mstatus",mstatus);
                postDataParams.put("study",study);
                postDataParams.put("course",radioButton1.getText().toString());
                postDataParams.put("age",tvage.getText().toString());


                postDataParams.put("ssc",ssc.getText().toString());
                postDataParams.put("sclyear",yearscl.getText().toString());
                postDataParams.put("sclresult",resultscl.getText().toString());
                postDataParams.put("sclname",sclname.getText().toString());

                postDataParams.put("clgcourse",clgcourse.getText().toString());
                postDataParams.put("clgyear",yearclg.getText().toString());
                postDataParams.put("clgresult",resultclg.getText().toString());
                postDataParams.put("clgname",clgname.getText().toString());

                postDataParams.put("degcourse",degcourse.getText().toString());
                postDataParams.put("degyear",yeardeg.getText().toString());
                postDataParams.put("degresult",resultdeg.getText().toString());
                postDataParams.put("degname",degname.getText().toString());

                postDataParams.put("mstcourse",mstcourse.getText().toString());
                postDataParams.put("mstyear",yearmst.getText().toString());
                postDataParams.put("mstresult",resultmst.getText().toString());
                postDataParams.put("mstname",mstname.getText().toString());

                postDataParams.put("etitle1",Stitle1);
                postDataParams.put("eYear1",Syearemp);
                postDataParams.put("eComp1",Scompname1);
                postDataParams.put("etitle2",Stitle2);
                postDataParams.put("eYear2",Syearemp2);
                postDataParams.put("eComp2",Scompname2);
                postDataParams.put("image",uploadImage);
                Log.e("Title:",Syearemp);

                Log.e("params",postDataParams.toString());



                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();
                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("<br />"))
            {
                Toast.makeText(getApplicationContext(),"Successfully Registered",Toast.LENGTH_LONG).show();
                pd.dismiss();
            }else{
                Toast.makeText(getApplicationContext(), result,
                        Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();

    }


    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);



            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {


                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            getAge( year, monthOfYear, dayOfMonth);

                        }

                    }, mYear, mMonth, mDay);

            datePickerDialog.show();


        }

    }
    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();
        tvage.setText(ageS);
        return ageS;

    }




}

