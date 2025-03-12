package com.example.myapplication.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "majors")
public class Major {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String majorCode;
    private String majorName;


    public Major(String majorCode, String majorName) {
        this.majorCode = majorCode;
        this.majorName = majorName;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMajorCode() {
        return majorCode;
    }

    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }


    @Override
    public String toString() {
        return majorName;
    }
}

