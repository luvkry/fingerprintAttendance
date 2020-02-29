package com.example.parsaniahardik.signature_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentViewHolder> {

    private Context mCtx;
    private List<Student> studentList;

    public StudentsAdapter(Context mCtx, List<Student> studentList) {
        this.mCtx = mCtx;
        this.studentList = studentList;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.student_list, null);
        return new StudentViewHolder(view);
    }

    @Override
    public int getItemCount() {
        int x =0;
        return x;
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        Student student = studentList.get(position);

        holder.textViewTitle.setText(student.getName());
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;

        public StudentViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
        }
    }
}
