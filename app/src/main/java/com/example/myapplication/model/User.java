package com.example.myapplication.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {
    
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String email;
    private String password;
    private String role; // "ADMIN" or "STUDENT"
    private int studentId; // Reference to Student if role is STUDENT, 0 if ADMIN

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.studentId = 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
    
    public int getStudentId() {
        return studentId;
    }
    
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
    public boolean isAdmin() {
        return "ADMIN".equals(role);
    }
}

