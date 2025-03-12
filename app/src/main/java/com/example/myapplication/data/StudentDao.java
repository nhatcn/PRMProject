package com.example.myapplication.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.Student;

import java.util.List;

@Dao
public interface StudentDao {
    @Insert
    long insert(Student student);

    @Update
    void update(Student student);

    @Delete
    void delete(Student student);

    @Query("DELETE FROM student_table")
    void deleteAllStudents();

    @Query("SELECT * FROM student_table ORDER BY name ASC")
    LiveData<List<Student>> getAllStudents();

    @Query("SELECT * FROM student_table WHERE id = :id LIMIT 1")
    Student getStudentById(int id);
}