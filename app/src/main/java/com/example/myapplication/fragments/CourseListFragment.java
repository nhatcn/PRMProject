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
import com.example.myapplication.adapter.CourseAdapter;
import com.example.myapplication.model.Course;
import com.example.myapplication.viewmodel.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CourseListFragment extends Fragment implements CourseAdapter.OnCourseClickListener {

    private CourseViewModel courseViewModel;
    private CourseAdapter adapter;
    private FragmentNavigationListener navigationListener;

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
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);

        // Set up RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);

        // Set up adapter
        adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);

        // Set up FAB for adding new courses
        FloatingActionButton buttonAddCourse = view.findViewById(R.id.fab_add_course);
        buttonAddCourse.setOnClickListener(v -> {
            AddEditCourseFragment fragment = new AddEditCourseFragment();
            navigationListener.navigateToFragment(fragment, true);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().setTitle("Quản lý Môn học");

        // Set up ViewModel
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getAllCourses().observe(getViewLifecycleOwner(), courses -> {
            adapter.setCourses(courses);
        });
    }

    @Override
    public void onCourseClick(Course course) {
        AddEditCourseFragment fragment = new AddEditCourseFragment();
        Bundle args = new Bundle();
        args.putInt(AddEditCourseFragment.ARG_COURSE_ID, course.getId());
        args.putString(AddEditCourseFragment.ARG_COURSE_CODE, course.getCourseCode());
        args.putString(AddEditCourseFragment.ARG_COURSE_NAME, course.getCourseName());
        args.putString(AddEditCourseFragment.ARG_COURSE_DESCRIPTION, course.getDescription());
        args.putInt(AddEditCourseFragment.ARG_COURSE_CREDITS, course.getCredits());
        fragment.setArguments(args);
        navigationListener.navigateToFragment(fragment, true);
    }

    @Override
    public void onDeleteClick(Course course) {
        courseViewModel.delete(course);
        Toast.makeText(requireContext(), "Môn học đã bị xóa", Toast.LENGTH_SHORT).show();
    }
}

