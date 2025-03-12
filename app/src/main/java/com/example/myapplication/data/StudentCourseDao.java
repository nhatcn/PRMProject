package com.example.myapplication.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapplication.model.Course;
import com.example.myapplication.model.StudentCourse;

import java.util.List;

@Dao
public interface StudentCourseDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StudentCourse studentCourse);
    
    @Delete
    void delete(StudentCourse studentCourse);
    
    @Query("SELECT * FROM student_course_table WHERE studentId = :studentId")
    LiveData<List<StudentCourse>> getCoursesForStudent(int studentId);
    
    @Query("SELECT c.* FROM course_table c INNER JOIN student_course_table sc ON c.id = sc.courseId WHERE sc.studentId = :studentId")
    LiveData<List<Course>> getEnrolledCourses(int studentId);
    
    @Query("SELECT c.* FROM course_table c WHERE c.id NOT IN (SELECT courseId FROM student_course_table WHERE studentId = :studentId)")
    LiveData<List<Course>> getAvailableCourses(int studentId);
    
    @Query("SELECT EXISTS(SELECT 1 FROM student_course_table WHERE studentId = :studentId AND courseId = :courseId)")
    boolean isStudentEnrolledInCourse(int studentId, int courseId);
}

