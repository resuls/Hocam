package com.hocam.ui.main;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hocam.R;
import com.hocam.models.Department;

import java.util.ArrayList;

public class DepartmentsRecyclerViewAdapter extends RecyclerView.Adapter<DepartmentsRecyclerViewAdapter.RecyclerViewItemHolder>
{
    private Context context;
    private ArrayList<Department> recyclerItemValues;

    public DepartmentsRecyclerViewAdapter(Context context, ArrayList<Department> values)
    {
        this.context = context;
        this.recyclerItemValues = values;
    }

    @Override
    public DepartmentsRecyclerViewAdapter.RecyclerViewItemHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        LayoutInflater inflator = LayoutInflater.from(viewGroup.getContext());

        View itemView = inflator.inflate(R.layout.departments_recycler, viewGroup, false);

        DepartmentsRecyclerViewAdapter.RecyclerViewItemHolder mViewHolder = new DepartmentsRecyclerViewAdapter.RecyclerViewItemHolder(itemView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(DepartmentsRecyclerViewAdapter.RecyclerViewItemHolder myRecyclerViewItemHolder, int i)
    {

        final Department sm = recyclerItemValues.get(i);

        myRecyclerViewItemHolder.deptName.setText(sm.getName());
        myRecyclerViewItemHolder.deptAbbr.setText(sm.getAbbr());

//        myRecyclerViewItemHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
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
        TextView deptName, deptAbbr;
        ConstraintLayout parentLayout;

        public RecyclerViewItemHolder(View itemView)
        {
            super(itemView);
            deptName = itemView.findViewById(R.id.deptName);
            deptAbbr = itemView.findViewById(R.id.deptAbbr);
            parentLayout = itemView.findViewById(R.id.recyclerItemLayout);
        }
    }
}
