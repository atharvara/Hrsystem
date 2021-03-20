package com.example.hrsystem.requesttask;

public class taskModel {
    public String Task;
    public String dueDate;

    public void task(String task,String dueDate){
        this.Task=task;
        this.dueDate=dueDate;
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
}
