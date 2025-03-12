package com.example.myapplication.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.User;

import java.util.List;

@Dao
public interface UserDao {
    
    @Insert
    long insert(User user);
    
    @Update
    void update(User user);
    
    @Delete
    void delete(User user);
    
    @Query("SELECT * FROM user_table WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);
    
    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password LIMIT 1")
    User login(String email, String password);
    
    @Query("SELECT * FROM user_table WHERE role = 'STUDENT'")
    LiveData<List<User>> getAllStudentUsers();
    
    @Query("SELECT * FROM user_table WHERE id = :id LIMIT 1")
    User getUserById(int id);
}

