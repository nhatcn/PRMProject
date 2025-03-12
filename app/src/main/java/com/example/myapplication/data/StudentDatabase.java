package com.example.myapplication.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.model.Course;
import com.example.myapplication.model.Student;
import com.example.myapplication.model.StudentCourse;
import com.example.myapplication.model.User;

@Database(entities = {Student.class, User.class, Course.class, StudentCourse.class}, version = 1, exportSchema = false)
public abstract class StudentDatabase extends RoomDatabase {
    
    private static StudentDatabase instance;
    
    public abstract StudentDao studentDao();
    public abstract UserDao userDao();
    public abstract CourseDao courseDao();
    public abstract StudentCourseDao studentCourseDao();
    
    public static synchronized StudentDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    StudentDatabase.class, "student_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private StudentDao studentDao;
        private UserDao userDao;
        private CourseDao courseDao;
        
        private PopulateDbAsyncTask(StudentDatabase db) {
            studentDao = db.studentDao();
            userDao = db.userDao();
            courseDao = db.courseDao();
        }
        
        @Override
        protected Void doInBackground(Void... voids) {
            // Add default admin
            User admin = new User("admin@example.com", "admin123", "ADMIN");
            userDao.insert(admin);
            
            // Add sample students
            Student student1 = new Student("Nguyễn Văn A", "SV001", 20, "Công nghệ thông tin");
            Student student2 = new Student("Trần Thị B", "SV002", 19, "Kinh tế");
            Student student3 = new Student("Lê Văn C", "SV003", 21, "Kỹ thuật điện");
            
            long student1Id = studentDao.insert(student1);
            long student2Id = studentDao.insert(student2);
            long student3Id = studentDao.insert(student3);
            
            // Add student users
            User studentUser1 = new User("student1@example.com", "student1", "STUDENT");
            studentUser1.setStudentId((int)student1Id);
            User studentUser2 = new User("student2@example.com", "student2", "STUDENT");
            studentUser2.setStudentId((int)student2Id);
            User studentUser3 = new User("student3@example.com", "student3", "STUDENT");
            studentUser3.setStudentId((int)student3Id);
            
            userDao.insert(studentUser1);
            userDao.insert(studentUser2);
            userDao.insert(studentUser3);
            
            // Add sample courses
            courseDao.insert(new Course("CS101", "Lập trình cơ bản", "Giới thiệu về lập trình", 3));
            courseDao.insert(new Course("CS201", "Cấu trúc dữ liệu", "Học về các cấu trúc dữ liệu", 4));
            courseDao.insert(new Course("CS301", "Cơ sở dữ liệu", "Thiết kế và quản lý cơ sở dữ liệu", 4));
            courseDao.insert(new Course("CS401", "Trí tuệ nhân tạo", "Giới thiệu về AI và machine learning", 3));
            
            return null;
        }
    }
}

