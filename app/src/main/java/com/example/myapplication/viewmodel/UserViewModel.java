package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.model.User;
import com.example.myapplication.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;
    private LiveData<List<User>> allStudentUsers;
    
    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        allStudentUsers = repository.getAllStudentUsers();
    }
    
    public long insert(User user) {
        return repository.insert(user);
    }
    
    public void update(User user) {
        repository.update(user);
    }
    
    public void delete(User user) {
        repository.delete(user);
    }
    
    public LiveData<List<User>> getAllStudentUsers() {
        return allStudentUsers;
    }
    
    public User login(String email, String password) {
        return repository.login(email, password);
    }
    
    public User getUserByEmail(String email) {
        return repository.getUserByEmail(email);
    }
    
    public User getUserById(int id) {
        return repository.getUserById(id);
    }
}

