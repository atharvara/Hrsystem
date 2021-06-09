package com.example.hrsystem;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class CustomProgressDialog extends Dialog {
    public CustomProgressDialog(@NonNull Context context) {
        super(context);
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(params);
        setCancelable(false);
        setTitle(null);
        setOnCancelListener(null);
        View view= LayoutInflater.from(context).inflate(R.layout.progreess_dialog,null);
        setContentView(view);

    }
}
