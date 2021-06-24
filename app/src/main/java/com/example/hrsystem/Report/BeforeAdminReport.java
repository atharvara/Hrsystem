package com.example.hrsystem.Report;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.hrsystem.HRView;
import com.example.hrsystem.R;
import com.example.hrsystem.expense.AdminExpenseReport;
import com.example.hrsystem.leavemanagment.admin.AdminReport;

public class BeforeAdminReport extends AppCompatActivity {
ImageView leave,expense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_admin_report);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Report");
        setSupportActionBar(toolbar);
        leave=findViewById(R.id.leavebtn);
        expense=findViewById(R.id.expense);
        leave.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AdminReport.class)));
        expense.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AdminExpenseReport.class)));

    }
}