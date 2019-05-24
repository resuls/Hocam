package com.hocam.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hocam.R;
import com.hocam.models.Review;

import java.util.ArrayList;

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.RecyclerViewItemHolder>
{
    private Context context;
    private ArrayList<Review> recyclerItemValues;

    public ReviewRecyclerViewAdapter(Context context, ArrayList<Review> values)
    {
        this.context = context;
        this.recyclerItemValues = values;
    }

    @NonNull
    @Override
    public RecyclerViewItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        LayoutInflater inflator = LayoutInflater.from(context);

        View itemView = inflator.inflate(R.layout.review_recycler, viewGroup, false);

        return new RecyclerViewItemHolder(itemView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewItemHolder myRecyclerViewItemHolder, int i)
    {

        final Review sm = recyclerItemValues.get(i);

        myRecyclerViewItemHolder.from.setText(sm.getFrom());
        myRecyclerViewItemHolder.semester.setText(String.format("%d %s", sm.getYear(), sm.getSemester()));
        myRecyclerViewItemHolder.content.setText(sm.getText());
        myRecyclerViewItemHolder.rating.setText(String.format("%d%s", sm.getRating(), new String(Character.toChars(0x2B50))));
    }

    @Override
    public int getItemCount()
    {
        return recyclerItemValues.size();
    }

    class RecyclerViewItemHolder extends RecyclerView.ViewHolder
    {
        TextView from, semester, rating, content;

        RecyclerViewItemHolder(View itemView)
        {
            super(itemView);
            from = itemView.findViewById(R.id.from);
            semester = itemView.findViewById(R.id.semester);
            content = itemView.findViewById(R.id.content);
            rating = itemView.findViewById(R.id.rating);
        }
    }
}
