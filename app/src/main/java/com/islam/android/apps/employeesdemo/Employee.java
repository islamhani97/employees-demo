package com.islam.android.apps.employeesdemo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "employee",
        indices = {@Index(value = {"data"},
        unique = true)})
public class Employee {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "job_title")
    private String jobTitle;

    @ColumnInfo(name = "working_hours")
    private Integer workingHours;

    @ColumnInfo(name = "data")
    private String data;

    public Employee() {
    }

    @Ignore
    public Employee(Long id, String name, String jobTitle, Integer workingHours, String data) {
        this.id = id;
        this.name = name;
        this.jobTitle = jobTitle;
        this.workingHours = workingHours;
        this.data = data;
    }


    @Ignore
    public Employee( String name, String jobTitle, Integer workingHours, String data) {
        this.name = name;
        this.jobTitle = jobTitle;
        this.workingHours = workingHours;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Integer workingHours) {
        this.workingHours = workingHours;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Ignore
    public Employee getInstance() {
        return new Employee(id, name, jobTitle, workingHours, data);
    }
}
