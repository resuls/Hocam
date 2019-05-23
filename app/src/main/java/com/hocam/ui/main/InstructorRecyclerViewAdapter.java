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

import java.util.ArrayList;

public class InstructorRecyclerViewAdapter extends RecyclerView.Adapter<InstructorRecyclerViewAdapter.RecyclerViewItemHolder>
{
    private Context context;
    private ArrayList<Instructor> recyclerItemValues;
    private String course;

    public InstructorRecyclerViewAdapter(Context context, ArrayList<Instructor> values, String course)
    {
        this.context = context;
        this.recyclerItemValues = values;
        this.course = course;
    }

    @Override
    public InstructorRecyclerViewAdapter.RecyclerViewItemHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        LayoutInflater inflator = LayoutInflater.from(viewGroup.getContext());

        View itemView = inflator.inflate(R.layout.instructor_recycler, viewGroup, false);

        InstructorRecyclerViewAdapter.RecyclerViewItemHolder mViewHolder = new InstructorRecyclerViewAdapter.RecyclerViewItemHolder(itemView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewItemHolder myRecyclerViewItemHolder, int i)
    {

        final Instructor sm = recyclerItemValues.get(i);

        myRecyclerViewItemHolder.name.setText(sm.getName());
        myRecyclerViewItemHolder.rating.setText(String.valueOf(sm.getRating()));

        myRecyclerViewItemHolder.parentLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context, InstructorActivity.class);
                intent.putExtra("Instructor", sm);
                intent.putExtra("department", sm.getReviews().get(0).getCourse().split(" ")[0]);
                intent.putExtra("course", course);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return recyclerItemValues.size();
    }

    class RecyclerViewItemHolder extends RecyclerView.ViewHolder
    {
        TextView name, rating;
        ConstraintLayout parentLayout;

        public RecyclerViewItemHolder(View itemView)
        {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.layout);
            name = itemView.findViewById(R.id.name);
            rating = itemView.findViewById(R.id.rating);
        }
    }
}
