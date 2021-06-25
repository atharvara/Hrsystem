package com.example.hrsystem.requesttask;

public class assignedTaskModel {
    String empid;
    String sts;


    public void assignedTaskModel(String empid, String sts) {
        this.empid = empid;
        this.sts = sts;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }
}
