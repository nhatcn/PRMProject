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
import com.example.myapplication.adapter.StudentAdapter;
import com.example.myapplication.model.Student;
import com.example.myapplication.viewmodel.StudentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StudentListFragment extends Fragment implements StudentAdapter.OnStudentClickListener {

    private StudentViewModel studentViewModel;
    private StudentAdapter adapter;
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
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);

        // Set up RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);

        // Set up adapter
        adapter = new StudentAdapter(this);
        recyclerView.setAdapter(adapter);

        // Set up FAB for adding new students
        FloatingActionButton buttonAddStudent = view.findViewById(R.id.fab_add_student);
        buttonAddStudent.setOnClickListener(v -> {
            AddEditStudentFragment fragment = new AddEditStudentFragment();
            navigationListener.navigateToFragment(fragment, true);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().setTitle("Quản lý Sinh viên");

        // Set up ViewModel
        studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        studentViewModel.getAllStudents().observe(getViewLifecycleOwner(), students -> {
            adapter.setStudents(students);
        });
    }

    @Override
    public void onStudentClick(Student student) {
        AddEditStudentFragment fragment = new AddEditStudentFragment();
        Bundle args = new Bundle();
        args.putInt(AddEditStudentFragment.ARG_STUDENT_ID, student.getId());
        args.putString(AddEditStudentFragment.ARG_STUDENT_NAME, student.getName());
        args.putString(AddEditStudentFragment.ARG_STUDENT_ID_STR, student.getStudentId());
        args.putInt(AddEditStudentFragment.ARG_STUDENT_AGE, student.getAge());
        args.putString(AddEditStudentFragment.ARG_STUDENT_COURSE, student.getCourse());
        fragment.setArguments(args);
        navigationListener.navigateToFragment(fragment, true);
    }

    @Override
    public void onDeleteClick(Student student) {
        studentViewModel.delete(student);
        Toast.makeText(requireContext(), "Sinh viên đã bị xóa", Toast.LENGTH_SHORT).show();
    }
}

