package com.example.myapplication.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "students",
        foreignKeys = @ForeignKey(
                entity = Major.class,
                parentColumns = "id",
                childColumns = "majorId",
                onDelete = ForeignKey.SET_NULL
        ),
        indices = {@Index("majorId")})
public class Student {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String studentId;
    private int age;
    private String course;
    private Integer majorId;

    public Student(String name, String studentId, int age, String course, Integer majorId) {
        this.name = name;
        this.studentId = studentId;
        this.age = age;
        this.course = course;
        this.majorId = majorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }
}

