package com.example.hrsystem.employee;
import com.google.gson.annotations.SerializedName;
public class DataModel {

    @SerializedName("id") private int Id;
    @SerializedName("name") private String Name;
    @SerializedName("username") private String empid;

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getEmpid() {
        return empid;
    }
}
