package com.example.hrsystem;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setContentView(R.layout.activity_profile);
        TextView t=(TextView)findViewById(R.id.idemp);
        GlobalClass g=(GlobalClass)getApplicationContext();
        t.setText("Employee Id: "+g.getEmpid());
        t.setTextColor(getResources().getColor(R.color.black));
    }
}