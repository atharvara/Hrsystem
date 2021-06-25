package com.example.hrsystem.Report;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.hrsystem.R;

public class BeforeReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_report);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Report");
        setSupportActionBar(toolbar);
        MyListData[] myListData = new MyListData[] {
                new MyListData("Leave Report",R.drawable.l_eave),
                new MyListData("Expense Report", R.drawable.r_eimbursement),
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter(BeforeReportActivity.this,myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}