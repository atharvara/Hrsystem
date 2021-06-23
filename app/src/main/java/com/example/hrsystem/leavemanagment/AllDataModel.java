package com.example.hrsystem.leavemanagment;

public class AllDataModel {
    String empid,empname,sts;

    public void AllDataModel(String empid, String empname, String sts) {
        this.empid = empid;
        this.empname = empname;
        this.sts = sts;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }
}
