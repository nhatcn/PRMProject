package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentHolder> {
    private List<Student> students = new ArrayList<>();
    private OnStudentClickListener listener;

    public StudentAdapter(OnStudentClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_item, parent, false);
        return new StudentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder holder, int position) {
        Student currentStudent = students.get(position);
        holder.textViewName.setText(currentStudent.getName());
        holder.textViewStudentId.setText("MSSV: " + currentStudent.getStudentId());
        holder.textViewAge.setText("Tuổi: " + currentStudent.getAge());
        holder.textViewCourse.setText("Ngành: " + currentStudent.getCourse());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public void setStudents(List<Student> students) {
        this.students = students;
        notifyDataSetChanged();
    }

    class StudentHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewStudentId;
        private TextView textViewAge;
        private TextView textViewCourse;
        private ImageButton buttonDelete;

        public StudentHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewStudentId = itemView.findViewById(R.id.text_view_student_id);
            textViewAge = itemView.findViewById(R.id.text_view_age);
            textViewCourse = itemView.findViewById(R.id.text_view_course);
            buttonDelete = itemView.findViewById(R.id.button_delete);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onStudentClick(students.get(position));
                }
            });

            buttonDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(students.get(position));
                }
            });
        }
    }

    public interface OnStudentClickListener {
        void onStudentClick(Student student);
        void onDeleteClick(Student student);
    }
}

