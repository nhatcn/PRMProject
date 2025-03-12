package com.example.myapplication.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.data.CourseDao;
import com.example.myapplication.data.StudentDatabase;
import com.example.myapplication.model.Course;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CourseRepository {
    private CourseDao courseDao;
    private LiveData<List<Course>> allCourses;
    
    public CourseRepository(Application application) {
        StudentDatabase database = StudentDatabase.getInstance(application);
        courseDao = database.courseDao();
        allCourses = courseDao.getAllCourses();
    }
    
    public long insert(Course course) {
        try {
            return new InsertCourseAsyncTask(courseDao).execute(course).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public void update(Course course) {
        new UpdateCourseAsyncTask(courseDao).execute(course);
    }
    
    public void delete(Course course) {
        new DeleteCourseAsyncTask(courseDao).execute(course);
    }
    
    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }
    
    public Course getCourseById(int id) {
        try {
            return new GetCourseByIdAsyncTask(courseDao).execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static class InsertCourseAsyncTask extends AsyncTask<Course, Void, Long> {
        private CourseDao courseDao;
        
        private InsertCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }
        
        @Override
        protected Long doInBackground(Course... courses) {
            return courseDao.insert(courses[0]);
        }
    }
    
    private static class UpdateCourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDao courseDao;
        
        private UpdateCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }
        
        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.update(courses[0]);
            return null;
        }
    }
    
    private static class DeleteCourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDao courseDao;
        
        private DeleteCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }
        
        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.delete(courses[0]);
            return null;
        }
    }
    
    private static class GetCourseByIdAsyncTask extends AsyncTask<Integer, Void, Course> {
        private CourseDao courseDao;
        
        private GetCourseByIdAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }
        
        @Override
        protected Course doInBackground(Integer... params) {
            return courseDao.getCourseById(params[0]);
        }
    }
}

