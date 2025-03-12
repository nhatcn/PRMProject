package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.model.Course;
import com.example.myapplication.repository.CourseRepository;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    private CourseRepository repository;
    private LiveData<List<Course>> allCourses;
    
    public CourseViewModel(@NonNull Application application) {
        super(application);
        repository = new CourseRepository(application);
        allCourses = repository.getAllCourses();
    }
    
    public long insert(Course course) {
        return repository.insert(course);
    }
    
    public void update(Course course) {
        repository.update(course);
    }
    
    public void delete(Course course) {
        repository.delete(course);
    }
    
    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }
    
    public Course getCourseById(int id) {
        return repository.getCourseById(id);
    }
}

