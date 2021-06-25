package com.example.hrsystem.requesttask;

public class assignedModel {
    String task,duedate,empid;

    public void assignedModel(String task, String duedate, String empid) {
        this.task = task;
        this.duedate = duedate;
        this.empid = empid;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }
}
