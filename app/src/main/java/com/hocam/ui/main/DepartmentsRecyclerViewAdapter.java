package com.hocam.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hocam.R;
import com.hocam.models.Department;

import java.util.ArrayList;

public class DepartmentsRecyclerViewAdapter extends RecyclerView.Adapter<DepartmentsRecyclerViewAdapter.ViewItemHolder>
{
    private Context context;
    private ArrayList<Department> deptList;

    public DepartmentsRecyclerViewAdapter(Context context, ArrayList<Department> deptList)
    {
        this.context = context;
        this.deptList = deptList;
    }

    @Override
    public ViewItemHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        LayoutInflater inflator = LayoutInflater.from(context);

        View view = inflator.inflate(R.layout.departments_recycler, viewGroup, false);

        return new ViewItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewItemHolder myRecyclerViewItemHolder, int i)
    {
        Department sm = deptList.get(i);

        myRecyclerViewItemHolder.deptName.setText(sm.getName());
        myRecyclerViewItemHolder.deptAbbr.setText(sm.getAbbr());
    }

    @Override
    public int getItemCount()
    {
        return deptList.size();
    }

    public class ViewItemHolder extends RecyclerView.ViewHolder
    {
        TextView deptName, deptAbbr;
        ConstraintLayout parentLayout;

        public ViewItemHolder(View itemView)
        {
            super(itemView);
            deptName = itemView.findViewById(R.id.deptName);
            deptAbbr = itemView.findViewById(R.id.deptAbbr);
            parentLayout = itemView.findViewById(R.id.recyclerItemLayout);
        }
    }
}
