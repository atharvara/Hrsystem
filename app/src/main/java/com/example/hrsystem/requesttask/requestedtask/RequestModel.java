package com.example.hrsystem.requesttask.requestedtask;

public class RequestModel {
    public String Task;
    public String dueDate,sts,requestName;

    public void task(String task,String dueDate,String sts,String requestName){
        this.Task=task;
        this.dueDate=dueDate;
        this.sts=sts;
        this.requestName=requestName;
    }

    public void setTask(String task) {
        Task = task;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getTask() {
        return Task;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }

    public String getSts() {
        return sts;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

}
