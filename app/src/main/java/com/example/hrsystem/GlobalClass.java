package com.example.hrsystem;

import android.app.Application;

public class GlobalClass extends Application {
    private String emp_id;
    private String emp_name;

    public String getEmpid()
    {
        return emp_id;
    }
    public void setEmpid(String emp_id){
        this.emp_id=emp_id;
    }
    public String getEmpName(){ return emp_name;}
    public void setEmp_name(String emp_name){
        this.emp_name=emp_name;
    }
}
