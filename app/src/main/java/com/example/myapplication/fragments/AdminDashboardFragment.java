package com.example.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.FragmentNavigationListener;
import com.example.myapplication.R;

public class AdminDashboardFragment extends Fragment {

    private CardView cardManageStudents;
    private CardView cardManageCourses;
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
        View view = inflater.inflate(R.layout.fragment_admin_dashboard, container, false);

        cardManageStudents = view.findViewById(R.id.card_manage_students);
        cardManageCourses = view.findViewById(R.id.card_manage_courses);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().setTitle("Quản trị viên");

        cardManageStudents.setOnClickListener(v -> {
            navigationListener.navigateToFragment(new StudentListFragment(), true);
        });

        cardManageCourses.setOnClickListener(v -> {
            navigationListener.navigateToFragment(new CourseListFragment(), true);
        });
    }
}

