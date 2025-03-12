package com.example.myapplication.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "student_course_table",
        primaryKeys = {"studentId", "courseId"},
        foreignKeys = {
                @ForeignKey(entity = Student.class,
                        parentColumns = "id",
                        childColumns = "studentId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Course.class,
                        parentColumns = "id",
                        childColumns = "courseId",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("courseId")})
public class StudentCourse {
    
    private int studentId;
    private int courseId;
    private String registrationDate;
    
    public StudentCourse(int studentId, int courseId, String registrationDate) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.registrationDate = registrationDate;
    }
    
    public int getStudentId() {
        return studentId;
    }
    
    public int getCourseId() {
        return courseId;
    }
    
    public String getRegistrationDate() {
        return registrationDate;
    }
}

