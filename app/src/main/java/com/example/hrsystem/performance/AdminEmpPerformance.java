package com.example.hrsystem.performance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hrsystem.R;

public class AdminEmpPerformance extends AppCompatActivity {
Button Submit;
EditText empid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_emp_performance);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Performance");
        setSupportActionBar(toolbar);
        empid=findViewById(R.id.empid);
        Submit=findViewById(R.id.submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String empidS=empid.getText().toString();
                Intent intent=new Intent(AdminEmpPerformance.this,AdminPerformanceView.class);
                intent.putExtra("Empid",empidS);
                startActivity(intent);
            }
        });
    }
}