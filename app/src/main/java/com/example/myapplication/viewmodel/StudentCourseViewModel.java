package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.model.Course;
import com.example.myapplication.model.StudentCourse;
import com.example.myapplication.repository.StudentCourseRepository;

import java.util.List;

public class StudentCourseViewModel extends AndroidViewModel {
    private StudentCourseRepository repository;
    
    public StudentCourseViewModel(@NonNull Application application) {
        super(application);
        repository = new StudentCourseRepository(application);
    }
    
    public void insert(StudentCourse studentCourse) {
        repository.insert(studentCourse);
    }
    
    public void delete(StudentCourse studentCourse) {
        repository.delete(studentCourse);
    }
    
    public LiveData<List<StudentCourse>> getCoursesForStudent(int studentId) {
        return repository.getCoursesForStudent(studentId);
    }
    
    public LiveData<List<Course>> getEnrolledCourses(int studentId) {
        return repository.getEnrolledCourses(studentId);
    }
    
    public LiveData<List<Course>> getAvailableCourses(int studentId) {
        return repository.getAvailableCourses(studentId);
    }
    
    public boolean isStudentEnrolledInCourse(int studentId, int courseId) {
        return repository.isStudentEnrolledInCourse(studentId, courseId);
    }
}

