package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.model.Student;
import com.example.myapplication.repository.StudentRepository;

import java.util.List;

public class StudentViewModel extends AndroidViewModel {
    private StudentRepository repository;
    private LiveData<List<Student>> allStudents;

    public StudentViewModel(@NonNull Application application) {
        super(application);
        repository = new StudentRepository(application);
        allStudents = repository.getAllStudents();
    }

    public long insert(Student student) {
        return repository.insert(student);
    }

    public void update(Student student) {
        repository.update(student);
    }

    public void delete(Student student) {
        repository.delete(student);
    }

    public void deleteAllStudents() {
        repository.deleteAllStudents();
    }

    public LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }

    public Student getStudentById(int id) {
        return repository.getStudentById(id);
    }
}