package com.example.hrsystem.support;

public class HelpModel {
    public String issue,empid,sts;

    public void data(String issue,String empid,String sts){
       this.issue=issue;
       this.empid=empid;
       this.sts=sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getSts() {
        return sts;
    }

    public String getEmpid() {
        return empid;
    }

    public String getIssue() {
        return issue;
    }
}
