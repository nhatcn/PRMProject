package com.example.myapplication.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.data.StudentDatabase;
import com.example.myapplication.data.UserDao;
import com.example.myapplication.model.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allStudentUsers;
    
    public UserRepository(Application application) {
        StudentDatabase database = StudentDatabase.getInstance(application);
        userDao = database.userDao();
        allStudentUsers = userDao.getAllStudentUsers();
    }
    
    public long insert(User user) {
        try {
            return new InsertUserAsyncTask(userDao).execute(user).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public void update(User user) {
        new UpdateUserAsyncTask(userDao).execute(user);
    }
    
    public void delete(User user) {
        new DeleteUserAsyncTask(userDao).execute(user);
    }
    
    public LiveData<List<User>> getAllStudentUsers() {
        return allStudentUsers;
    }
    
    public User login(String email, String password) {
        try {
            return new LoginUserAsyncTask(userDao).execute(email, password).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public User getUserByEmail(String email) {
        try {
            return new GetUserByEmailAsyncTask(userDao).execute(email).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public User getUserById(int id) {
        try {
            return new GetUserByIdAsyncTask(userDao).execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Long> {
        private UserDao userDao;
        
        private InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }
        
        @Override
        protected Long doInBackground(User... users) {
            return userDao.insert(users[0]);
        }
    }
    
    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;
        
        private UpdateUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }
        
        @Override
        protected Void doInBackground(User... users) {
            userDao.update(users[0]);
            return null;
        }
    }
    
    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;
        
        private DeleteUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }
        
        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }
    
    private static class LoginUserAsyncTask extends AsyncTask<String, Void, User> {
        private UserDao userDao;
        
        private LoginUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }
        
        @Override
        protected User doInBackground(String... params) {
            return userDao.login(params[0], params[1]);
        }
    }
    
    private static class GetUserByEmailAsyncTask extends AsyncTask<String, Void, User> {
        private UserDao userDao;
        
        private GetUserByEmailAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }
        
        @Override
        protected User doInBackground(String... params) {
            return userDao.getUserByEmail(params[0]);
        }
    }
    
    private static class GetUserByIdAsyncTask extends AsyncTask<Integer, Void, User> {
        private UserDao userDao;
        
        private GetUserByIdAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }
        
        @Override
        protected User doInBackground(Integer... params) {
            return userDao.getUserById(params[0]);
        }
    }
}

