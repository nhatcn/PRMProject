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
import com.example.myapplication.adapter.EnrolledCourseAdapter;
import com.example.myapplication.model.Course;
import com.example.myapplication.model.StudentCourse;
import com.example.myapplication.utils.SessionManager;
import com.example.myapplication.viewmodel.StudentCourseViewModel;

public class EnrolledCoursesFragment extends Fragment implements EnrolledCourseAdapter.OnCourseUnenrollListener {

    private StudentCourseViewModel studentCourseViewModel;
    private EnrolledCourseAdapter adapter;
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
        View view = inflater.inflate(R.layout.fragment_enrolled_courses, container, false);

        sessionManager = new SessionManager(requireContext());
        studentId = sessionManager.getStudentId();

        // Set up RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_enrolled_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);

        // Set up adapter
        adapter = new EnrolledCourseAdapter(this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().setTitle("Môn học đã đăng ký");

        // Set up ViewModel
        studentCourseViewModel = new ViewModelProvider(this).get(StudentCourseViewModel.class);
        studentCourseViewModel.getEnrolledCourses(studentId).observe(getViewLifecycleOwner(), courses -> {
            adapter.setCourses(courses);
        });
    }

    @Override
    public void onUnenrollClick(Course course) {
        StudentCourse studentCourse = new StudentCourse(studentId, course.getId(), "");
        studentCourseViewModel.delete(studentCourse);

        Toast.makeText(requireContext(), "Hủy đăng ký môn học thành công", Toast.LENGTH_SHORT).show();
    }
}

