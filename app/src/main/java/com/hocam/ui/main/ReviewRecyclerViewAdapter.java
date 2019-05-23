package com.hocam.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hocam.InstructorActivity;
import com.hocam.R;
import com.hocam.models.Instructor;
import com.hocam.models.Review;

import java.util.ArrayList;

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.RecyclerViewItemHolder>{
    private Context context;
    private ArrayList<Review> recyclerItemValues;

    public ReviewRecyclerViewAdapter(Context context, ArrayList<Review> values)
    {
        this.context = context;
        this.recyclerItemValues = values;
    }

    @Override
    public RecyclerViewItemHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        LayoutInflater inflator = LayoutInflater.from(viewGroup.getContext());

        View itemView = inflator.inflate(R.layout.review_recycler, viewGroup, false);

        RecyclerViewItemHolder mViewHolder = new RecyclerViewItemHolder(itemView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewItemHolder myRecyclerViewItemHolder, int i)
    {

        final Review sm = recyclerItemValues.get(i);

        myRecyclerViewItemHolder.from.setText(sm.getFrom());
        myRecyclerViewItemHolder.course.setText(sm.getCourse());
        myRecyclerViewItemHolder.instructor.setText(sm.getInstructor());
        myRecyclerViewItemHolder.semester.setText(sm.getYear()+" "+sm.getSemester());
        myRecyclerViewItemHolder.content.setText(sm.getText());
        myRecyclerViewItemHolder.rating.setText(String.valueOf(sm.getRating()));

//        myRecyclerViewItemHolder.parentLayout.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//
//            }
//        });
    }

    @Override
    public int getItemCount()
    {
        return recyclerItemValues.size();
    }

    class RecyclerViewItemHolder extends RecyclerView.ViewHolder
    {
        TextView from, course, instructor, semester, rating, content;
        ConstraintLayout parentLayout;

        public RecyclerViewItemHolder(View itemView)
        {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.layout);
            from = itemView.findViewById(R.id.from);
            course = itemView.findViewById(R.id.course);
            instructor = itemView.findViewById(R.id.instructor);
            semester = itemView.findViewById(R.id.semester);
            content = itemView.findViewById(R.id.content);
            rating = itemView.findViewById(R.id.rating);
        }
    }
}
