package com.example.myapplication.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table")
public class Course {
    
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String courseCode;
    private String courseName;
    private String description;
    private int credits;
    
    public Course(String courseCode, String courseName, String description, int credits) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.description = description;
        this.credits = credits;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getCredits() {
        return credits;
    }
}

