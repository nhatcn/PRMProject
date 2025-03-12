package com.example.myapplication.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.data.StudentCourseDao;
import com.example.myapplication.data.StudentDatabase;
import com.example.myapplication.model.Course;
import com.example.myapplication.model.StudentCourse;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class StudentCourseRepository {
    private StudentCourseDao studentCourseDao;
    
    public StudentCourseRepository(Application application) {
        StudentDatabase database = StudentDatabase.getInstance(application);
        studentCourseDao = database.studentCourseDao();
    }
    
    public void insert(StudentCourse studentCourse) {
        new InsertStudentCourseAsyncTask(studentCourseDao).execute(studentCourse);
    }
    
    public void delete(StudentCourse studentCourse) {
        new DeleteStudentCourseAsyncTask(studentCourseDao).execute(studentCourse);
    }
    
    public LiveData<List<StudentCourse>> getCoursesForStudent(int studentId) {
        return studentCourseDao.getCoursesForStudent(studentId);
    }
    
    public LiveData<List<Course>> getEnrolledCourses(int studentId) {
        return studentCourseDao.getEnrolledCourses(studentId);
    }
    
    public LiveData<List<Course>> getAvailableCourses(int studentId) {
        return studentCourseDao.getAvailableCourses(studentId);
    }
    
    public boolean isStudentEnrolledInCourse(int studentId, int courseId) {
        try {
            return new IsStudentEnrolledAsyncTask(studentCourseDao).execute(studentId, courseId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private static class InsertStudentCourseAsyncTask extends AsyncTask<StudentCourse, Void, Void> {
        private StudentCourseDao studentCourseDao;
        
        private InsertStudentCourseAsyncTask(StudentCourseDao studentCourseDao) {
            this.studentCourseDao = studentCourseDao;
        }
        
        @Override
        protected Void doInBackground(StudentCourse... studentCourses) {
            studentCourseDao.insert(studentCourses[0]);
            return null;
        }
    }
    
    private static class DeleteStudentCourseAsyncTask extends AsyncTask<StudentCourse, Void, Void> {
        private StudentCourseDao studentCourseDao;
        
        private DeleteStudentCourseAsyncTask(StudentCourseDao studentCourseDao) {
            this.studentCourseDao = studentCourseDao;
        }
        
        @Override
        protected Void doInBackground(StudentCourse... studentCourses) {
            studentCourseDao.delete(studentCourses[0]);
            return null;
        }
    }
    
    private static class IsStudentEnrolledAsyncTask extends AsyncTask<Integer, Void, Boolean> {
        private StudentCourseDao studentCourseDao;
        
        private IsStudentEnrolledAsyncTask(StudentCourseDao studentCourseDao) {
            this.studentCourseDao = studentCourseDao;
        }
        
        @Override
        protected Boolean doInBackground(Integer... params) {
            return studentCourseDao.isStudentEnrolledInCourse(params[0], params[1]);
        }
    }
}

