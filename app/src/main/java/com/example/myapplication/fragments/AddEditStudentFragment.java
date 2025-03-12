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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.FragmentNavigationListener;
import com.example.myapplication.R;
import com.example.myapplication.model.Student;
import com.example.myapplication.viewmodel.StudentViewModel;

public class AddEditStudentFragment extends Fragment {

    public static final String ARG_STUDENT_ID = "student_id";
    public static final String ARG_STUDENT_NAME = "student_name";
    public static final String ARG_STUDENT_ID_STR = "student_id_str";
    public static final String ARG_STUDENT_AGE = "student_age";
    public static final String ARG_STUDENT_COURSE = "student_course";

    private EditText editTextName;
    private EditText editTextStudentId;
    private NumberPicker numberPickerAge;
    private EditText editTextCourse;

    private StudentViewModel studentViewModel;
    private FragmentNavigationListener navigationListener;
    private boolean isEditMode = false;
    private int studentId = -1;

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
        View view = inflater.inflate(R.layout.fragment_add_edit_student, container, false);

        editTextName = view.findViewById(R.id.edit_text_name);
        editTextStudentId = view.findViewById(R.id.edit_text_student_id);
        numberPickerAge = view.findViewById(R.id.number_picker_age);
        editTextCourse = view.findViewById(R.id.edit_text_course);

        // Set up number picker for age
        numberPickerAge.setMinValue(16);
        numberPickerAge.setMaxValue(60);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);

        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_STUDENT_ID)) {
            isEditMode = true;
            studentId = args.getInt(ARG_STUDENT_ID);
            requireActivity().setTitle("Sửa Sinh viên");
            editTextName.setText(args.getString(ARG_STUDENT_NAME));
            editTextStudentId.setText(args.getString(ARG_STUDENT_ID_STR));
            numberPickerAge.setValue(args.getInt(ARG_STUDENT_AGE, 18));
            editTextCourse.setText(args.getString(ARG_STUDENT_COURSE));
        } else {
            requireActivity().setTitle("Thêm Sinh viên");
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_student_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_student) {
            saveStudent();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveStudent() {
        String name = editTextName.getText().toString();
        String studentIdStr = editTextStudentId.getText().toString();
        int age = numberPickerAge.getValue();
        String course = editTextCourse.getText().toString();

        if (name.trim().isEmpty() || studentIdStr.trim().isEmpty() || course.trim().isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        Student student = new Student(name, studentIdStr, age, course);

        if (isEditMode) {
            student.setId(studentId);
            studentViewModel.update(student);
            Toast.makeText(requireContext(), "Sinh viên đã được cập nhật", Toast.LENGTH_SHORT).show();
        } else {
            studentViewModel.insert(student);
            Toast.makeText(requireContext(), "Sinh viên đã được lưu", Toast.LENGTH_SHORT).show();
        }

        // Navigate back
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}

