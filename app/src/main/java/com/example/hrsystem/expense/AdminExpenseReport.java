package com.example.hrsystem.expense;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrsystem.CustomProgressDialog;
import com.example.hrsystem.R;
import com.example.hrsystem.leavemanagment.AllDataModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AdminExpenseReport extends AppCompatActivity {
    TextView expmonth,expyear;
    CustomProgressDialog customProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_expense_report);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Expense Report");
        setSupportActionBar(toolbar);
        expmonth=findViewById(R.id.expmonth);
        expyear=findViewById(R.id.expyear);

        customProgressDialog=new CustomProgressDialog(AdminExpenseReport.this);
        customProgressDialog.show();
        getMonth();
    }
    public void  getMonth(){
        class dbManager extends AsyncTask<String, Void, String> {

            protected void onPostExecute(String data) {
                expmonth.setText(data);
                // progressDialog.dismiss();
                getYear();
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
        obj.execute("https://shinetech.site/shinetech.site/hrmskbp/expense/expense_report.php");
    }
    public void  getYear(){
        class dbManager extends AsyncTask<String, Void, String> {

            protected void onPostExecute(String data) {
                expyear.setText(data);
                customProgressDialog.dismiss();

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
        obj.execute("https://shinetech.site/shinetech.site/hrmskbp/expense/expense_year_report.php");
    }
}