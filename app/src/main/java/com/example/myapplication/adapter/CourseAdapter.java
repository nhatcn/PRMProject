package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    
    private List<Course> courses = new ArrayList<>();
    private OnCourseClickListener listener;
    
    public CourseAdapter(OnCourseClickListener listener) {
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);
        return new CourseHolder(itemView);
    }
    
    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        Course currentCourse = courses.get(position);
        holder.textViewCourseCode.setText(currentCourse.getCourseCode());
        holder.textViewCourseName.setText(currentCourse.getCourseName());
        holder.textViewDescription.setText(currentCourse.getDescription());
        holder.textViewCredits.setText(currentCourse.getCredits() + " tín chỉ");
    }
    
    @Override
    public int getItemCount() {
        return courses.size();
    }
    
    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }
    
    class CourseHolder extends RecyclerView.ViewHolder {
        private TextView textViewCourseCode;
        private TextView textViewCourseName;
        private TextView textViewDescription;
        private TextView textViewCredits;
        private ImageButton buttonDelete;
        
        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            textViewCourseCode = itemView.findViewById(R.id.text_view_course_code);
            textViewCourseName = itemView.findViewById(R.id.text_view_course_name);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewCredits = itemView.findViewById(R.id.text_view_credits);
            buttonDelete = itemView.findViewById(R.id.button_delete_course);
            
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onCourseClick(courses.get(position));
                    }
                }
            });
            
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(courses.get(position));
                    }
                }
            });
        }
    }
    
    public interface OnCourseClickListener {
        void onCourseClick(Course course);
        void onDeleteClick(Course course);
    }
}

