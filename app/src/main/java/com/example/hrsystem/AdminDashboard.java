package com.example.hrsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.hrsystem.Report.BeforeAdminReport;
import com.example.hrsystem.adminpass.CredentialsChange;
import com.example.hrsystem.leavemanagment.ViewApp;
import com.example.hrsystem.leavemanagment.admin.AdminReport;
import com.example.hrsystem.performance.AdminEmpPerformance;
import com.example.hrsystem.requesttask.TaskPageAdmin;
import com.example.hrsystem.support.adminhelp.SupportList;


public class AdminDashboard extends AppCompatActivity {
ImageView expense,leave,request,help,perfor,creden,report;
LinearLayout l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Admin Dashboard");
        setSupportActionBar(toolbar);
        expense=findViewById(R.id.expense);
        leave=findViewById(R.id.leavebtn);
        request=findViewById(R.id.request);
        creden=findViewById(R.id.adminPass);
        help=findViewById(R.id.help);
        report=findViewById(R.id.reportbtn);
        perfor=findViewById(R.id.perfor);
        report.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), BeforeAdminReport.class)));
        expense.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),HRView.class)));
        leave.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ViewApp.class)));
        request.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), TaskPageAdmin.class)));
        help.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SupportList.class)));
        perfor.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AdminEmpPerformance.class)));
        creden.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), CredentialsChange.class)));
    }
}