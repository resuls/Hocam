package com.hocam.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hocam.CourseActivity;
import com.hocam.R;
import com.hocam.models.Course;

import java.util.ArrayList;

public class CoursesRecyclerViewAdapter extends RecyclerView.Adapter<CoursesRecyclerViewAdapter.RecyclerViewItemHolder> {
    private Context context;
    private ArrayList<Course> courseList;

    public CoursesRecyclerViewAdapter(Context context, ArrayList<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
    }

    @Override
    public RecyclerViewItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflator = LayoutInflater.from(context);

        View itemView = inflator.inflate(R.layout.courses_recyler, viewGroup, false);

        RecyclerViewItemHolder mViewHolder = new RecyclerViewItemHolder(itemView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewItemHolder myRecyclerViewItemHolder, int i) {
        Course sm = courseList.get(i);

        myRecyclerViewItemHolder.courseCode.setText(sm.getCode());
        myRecyclerViewItemHolder.courseName.setText(sm.getName());
        myRecyclerViewItemHolder.average.setText(Double.toString(sm.getAverage()));

    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class RecyclerViewItemHolder extends RecyclerView.ViewHolder {
        TextView courseCode, courseName, average;
        ConstraintLayout parentLayout;

        public RecyclerViewItemHolder(View itemView) {
            super(itemView);
            courseCode = itemView.findViewById(R.id.courseCode);
            courseName = itemView.findViewById(R.id.courseName);
            average = itemView.findViewById(R.id.average);
            parentLayout = itemView.findViewById(R.id.recyclerItemLayout);

            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CourseActivity.class);
                    intent.putExtra("course", courseCode.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }
}
