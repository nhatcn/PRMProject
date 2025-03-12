package com.example.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.FragmentNavigationListener;
import com.example.myapplication.R;
import com.example.myapplication.model.Student;
import com.example.myapplication.utils.SessionManager;
import com.example.myapplication.viewmodel.StudentViewModel;

public class StudentProfileFragment extends Fragment {

    private EditText editTextName;
    private EditText editTextStudentId;
    private EditText editTextAge;
    private EditText editTextCourse;
    private Button buttonUpdate;

    private StudentViewModel studentViewModel;
    private FragmentNavigationListener navigationListener;
    private SessionManager sessionManager;
    private int studentId;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigationListener) {
            navigationListener = (FragmentNavigationListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement FragmentNavigationListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_profile, container, false);

        editTextName = view.findViewById(R.id.edit_text_profile_name);
        editTextStudentId = view.findViewById(R.id.edit_text_profile_student_id);
        editTextAge = view.findViewById(R.id.edit_text_profile_age);
        editTextCourse = view.findViewById(R.id.edit_text_profile_course);
        buttonUpdate = view.findViewById(R.id.button_update_profile);

        sessionManager = new SessionManager(requireContext());
        studentId = sessionManager.getStudentId();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().setTitle("Thông tin cá nhân");

        studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);

        if (studentId > 0) {
            loadStudentData();
        }

        buttonUpdate.setOnClickListener(v -> updateProfile());
    }

    private void loadStudentData() {
        Student student = studentViewModel.getStudentById(studentId);
        if (student != null) {
            editTextName.setText(student.getName());
            editTextStudentId.setText(student.getStudentId());
            editTextAge.setText(String.valueOf(student.getAge()));
            editTextCourse.setText(student.getCourse());
        }
    }

    private void updateProfile() {
        String name = editTextName.getText().toString().trim();
        String studentIdStr = editTextStudentId.getText().toString().trim();
        String ageStr = editTextAge.getText().toString().trim();
        String course = editTextCourse.getText().toString().trim();

        if (name.isEmpty() || studentIdStr.isEmpty() || ageStr.isEmpty() || course.isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr);

        Student student = new Student(name, studentIdStr, age, course);
        student.setId(studentId);
        studentViewModel.update(student);

        Toast.makeText(requireContext(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}

