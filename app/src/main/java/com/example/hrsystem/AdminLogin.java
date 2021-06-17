package com.example.hrsystem;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {
private TextView login;
private EditText email;
private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
      
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login=findViewById(R.id.login);
        login.setOnClickListener(v -> validate(email.getText().toString(), password.getText().toString()));

    }


    private void validate(String email, String password) {
        if((email.equals("admin@gmail.com")) && password.equals("admin")){
            Intent intent;
            intent = new Intent(AdminLogin.this,AdminDashboard.class);
            startActivity(intent);
            finish();

        }else{
            Toast.makeText(AdminLogin.this,"Invalid Credentials",Toast.LENGTH_LONG).show();
        }
    }
}