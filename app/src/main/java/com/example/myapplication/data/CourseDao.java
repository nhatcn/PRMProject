package com.example.myapplication.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.Course;

import java.util.List;

@Dao
public interface CourseDao {
    
    @Insert
    long insert(Course course);
    
    @Update
    void update(Course course);
    
    @Delete
    void delete(Course course);
    
    @Query("SELECT * FROM course_table ORDER BY courseName ASC")
    LiveData<List<Course>> getAllCourses();
    
    @Query("SELECT * FROM course_table WHERE id = :id LIMIT 1")
    Course getCourseById(int id);
}

