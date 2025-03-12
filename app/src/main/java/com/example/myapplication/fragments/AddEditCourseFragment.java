package com.example.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.FragmentNavigationListener;
import com.example.myapplication.R;
import com.example.myapplication.model.Course;
import com.example.myapplication.viewmodel.CourseViewModel;

public class AddEditCourseFragment extends Fragment {

    public static final String ARG_COURSE_ID = "course_id";
    public static final String ARG_COURSE_CODE = "course_code";
    public static final String ARG_COURSE_NAME = "course_name";
    public static final String ARG_COURSE_DESCRIPTION = "course_description";
    public static final String ARG_COURSE_CREDITS = "course_credits";

    private EditText editTextCourseCode;
    private EditText editTextCourseName;
    private EditText editTextDescription;
    private NumberPicker numberPickerCredits;

    private CourseViewModel courseViewModel;
    private FragmentNavigationListener navigationListener;
    private boolean isEditMode = false;
    private int courseId = -1;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigationListener) {
            navigationListener = (FragmentNavigationListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement FragmentNavigationListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_edit_course, container, false);

        editTextCourseCode = view.findViewById(R.id.edit_text_course_code);
        editTextCourseName = view.findViewById(R.id.edit_text_course_name);
        editTextDescription = view.findViewById(R.id.edit_text_description);
        numberPickerCredits = view.findViewById(R.id.number_picker_credits);

        // Set up number picker for credits
        numberPickerCredits.setMinValue(1);
        numberPickerCredits.setMaxValue(6);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_COURSE_ID)) {
            isEditMode = true;
            courseId = args.getInt(ARG_COURSE_ID);
            requireActivity().setTitle("Sửa Môn học");
            editTextCourseCode.setText(args.getString(ARG_COURSE_CODE));
            editTextCourseName.setText(args.getString(ARG_COURSE_NAME));
            editTextDescription.setText(args.getString(ARG_COURSE_DESCRIPTION));
            numberPickerCredits.setValue(args.getInt(ARG_COURSE_CREDITS, 3));
        } else {
            requireActivity().setTitle("Thêm Môn học");
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_course_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_course) {
            saveCourse();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveCourse() {
        String courseCode = editTextCourseCode.getText().toString();
        String courseName = editTextCourseName.getText().toString();
        String description = editTextDescription.getText().toString();
        int credits = numberPickerCredits.getValue();

        if (courseCode.trim().isEmpty() || courseName.trim().isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng nhập mã và tên môn học", Toast.LENGTH_SHORT).show();
            return;
        }

        Course course = new Course(courseCode, courseName, description, credits);

        if (isEditMode) {
            course.setId(courseId);
            courseViewModel.update(course);
            Toast.makeText(requireContext(), "Môn học đã được cập nhật", Toast.LENGTH_SHORT).show();
        } else {
            courseViewModel.insert(course);
            Toast.makeText(requireContext(), "Môn học đã được lưu", Toast.LENGTH_SHORT).show();
        }

        // Navigate back
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}

