package com.example.myapplication.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.myapplication.data.StudentDao;
import com.example.myapplication.data.StudentDatabase;
import com.example.myapplication.model.Student;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StudentRepository {
    private StudentDao studentDao;
    private LiveData<List<Student>> allStudents;
    private final ExecutorService executorService;

    public StudentRepository(Application application) {
        StudentDatabase database = StudentDatabase.getInstance(application);
        studentDao = database.studentDao();
        allStudents = studentDao.getAllStudents();
        executorService = Executors.newSingleThreadExecutor();
    }

    public long insert(Student student) {
        Future<Long> future = executorService.submit(() -> studentDao.insert(student));
        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void update(Student student) {
        executorService.execute(() -> studentDao.update(student));
    }

    public void delete(Student student) {
        executorService.execute(() -> studentDao.delete(student));
    }

    public void deleteAllStudents() {
        executorService.execute(() -> studentDao.deleteAllStudents());
    }

    public LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }

    public Student getStudentById(int id) {
        Future<Student> future = executorService.submit(() -> {
            // Giả định StudentDao có phương thức getStudentById
            return studentDao.getStudentById(id);
        });
        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}