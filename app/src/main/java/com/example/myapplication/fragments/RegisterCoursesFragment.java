package com.example.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.FragmentNavigationListener;
import com.example.myapplication.R;
import com.example.myapplication.adapter.AvailableCourseAdapter;
import com.example.myapplication.model.Course;
import com.example.myapplication.model.StudentCourse;
import com.example.myapplication.utils.SessionManager;
import com.example.myapplication.viewmodel.StudentCourseViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegisterCoursesFragment extends Fragment implements AvailableCourseAdapter.OnCourseRegisterListener {

    private StudentCourseViewModel studentCourseViewModel;
    private AvailableCourseAdapter adapter;
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
        View view = inflater.inflate(R.layout.fragment_register_courses, container, false);

        sessionManager = new SessionManager(requireContext());
        studentId = sessionManager.getStudentId();

        // Set up RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_available_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);

        // Set up adapter
        adapter = new AvailableCourseAdapter(this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().setTitle("Đăng ký môn học");

        // Set up ViewModel
        studentCourseViewModel = new ViewModelProvider(this).get(StudentCourseViewModel.class);
        studentCourseViewModel.getAvailableCourses(studentId).observe(getViewLifecycleOwner(), courses -> {
            adapter.setCourses(courses);
        });
    }

    @Override
    public void onRegisterClick(Course course) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = sdf.format(new Date());

        StudentCourse studentCourse = new StudentCourse(studentId, course.getId(), currentDate);
        studentCourseViewModel.insert(studentCourse);

        Toast.makeText(requireContext(), "Đăng ký môn học thành công", Toast.LENGTH_SHORT).show();
    }
}

