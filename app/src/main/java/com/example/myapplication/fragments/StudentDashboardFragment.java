package com.example.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.FragmentNavigationListener;
import com.example.myapplication.R;
import com.example.myapplication.model.Course;
import com.example.myapplication.model.Student;
import com.example.myapplication.utils.SessionManager;
import com.example.myapplication.viewmodel.StudentCourseViewModel;
import com.example.myapplication.viewmodel.StudentViewModel;

import java.util.List;

public class StudentDashboardFragment extends Fragment {

    private CardView cardViewProfile;
    private CardView cardViewEnrolledCourses;
    private CardView cardViewRegisterCourses;
    private TextView textViewStudentName;
    private TextView textViewStudentId;
    private TextView textViewEnrolledCoursesCount;

    private StudentViewModel studentViewModel;
    private StudentCourseViewModel studentCourseViewModel;
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
        View view = inflater.inflate(R.layout.fragment_student_dashboard, container, false);

        cardViewProfile = view.findViewById(R.id.card_view_profile);
        cardViewEnrolledCourses = view.findViewById(R.id.card_view_enrolled_courses);
        cardViewRegisterCourses = view.findViewById(R.id.card_view_register_courses);
        textViewStudentName = view.findViewById(R.id.text_view_student_name);
        textViewStudentId = view.findViewById(R.id.text_view_student_id);
        textViewEnrolledCoursesCount = view.findViewById(R.id.text_view_enrolled_courses_count);

        sessionManager = new SessionManager(requireContext());
        studentId = sessionManager.getStudentId();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().setTitle("Sinh viên");

        studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        studentCourseViewModel = new ViewModelProvider(this).get(StudentCourseViewModel.class);

        if (studentId > 0) {
            loadStudentData();
        }

        cardViewProfile.setOnClickListener(v -> {
            navigationListener.navigateToFragment(new StudentProfileFragment(), true);
        });

        cardViewEnrolledCourses.setOnClickListener(v -> {
            navigationListener.navigateToFragment(new EnrolledCoursesFragment(), true);
        });

        cardViewRegisterCourses.setOnClickListener(v -> {
            navigationListener.navigateToFragment(new RegisterCoursesFragment(), true);
        });
    }

    private void loadStudentData() {
        Student student = studentViewModel.getStudentById(studentId);
        if (student != null) {
            textViewStudentName.setText(student.getName());
            textViewStudentId.setText("MSSV: " + student.getStudentId());
        }

        studentCourseViewModel.getEnrolledCourses(studentId).observe(getViewLifecycleOwner(), courses -> {
            textViewEnrolledCoursesCount.setText(String.valueOf(courses.size()));
        });
    }
}

