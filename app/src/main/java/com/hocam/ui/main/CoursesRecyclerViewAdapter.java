package com.hocam.ui.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hocam.CourseActivity;
import com.hocam.MainActivity;
import com.hocam.R;
import com.hocam.models.Course;

import java.util.ArrayList;

public class CoursesRecyclerViewAdapter extends RecyclerView.Adapter<CoursesRecyclerViewAdapter.RecyclerViewItemHolder>
{
    private Context context;
    private ArrayList<Course> courseList;

    public CoursesRecyclerViewAdapter(Context context, ArrayList<Course> courseList)
    {
        this.context = context;
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public RecyclerViewItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        LayoutInflater inflator = LayoutInflater.from(context);

        View itemView = inflator.inflate(R.layout.courses_recyler, viewGroup, false);

        return new RecyclerViewItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewItemHolder myRecyclerViewItemHolder, int i)
    {
        Course sm = courseList.get(i);

        myRecyclerViewItemHolder.courseCode.setText(sm.getCode());
        myRecyclerViewItemHolder.courseName.setText(sm.getName());
        myRecyclerViewItemHolder.ratingBar.setRating(sm.getAverage());
    }

    @Override
    public int getItemCount()
    {
        return courseList.size();
    }

    class RecyclerViewItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView courseCode, courseName;
        RatingBar ratingBar;

        public RecyclerViewItemHolder(View itemView)
        {
            super(itemView);
            courseCode = itemView.findViewById(R.id.courseCode);
            courseName = itemView.findViewById(R.id.courseName);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            ratingBar.setIsIndicator(true);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(context, CourseActivity.class);
            intent.putExtra("course", courseCode.getText().toString());
            context.startActivity(intent);
        }
    }

    private void makeAndShowDialogBox(Float avg) {
        AlertDialog.Builder mDialogBox = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.rating, null);

        RatingBar bar = dialogView.findViewById(R.id.ratingBar3);
        bar.setRating(avg);

        mDialogBox.setView(dialogView);

        // Set three option buttons
        mDialogBox.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // whatever should be done when answering "YES" goes
                        // here
                    }
                });
        mDialogBox.create();
        mDialogBox.show();
    }
}
