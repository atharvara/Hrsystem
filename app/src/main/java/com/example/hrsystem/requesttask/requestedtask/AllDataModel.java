package com.example.hrsystem.requesttask.requestedtask;

public class AllDataModel {
    public String Task;
    public String dueDate,sts;

    public void task(String task,String dueDate,String sts){
        this.Task=task;
        this.dueDate=dueDate;
        this.sts=sts;
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
}
