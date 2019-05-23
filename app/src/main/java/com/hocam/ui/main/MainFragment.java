package com.hocam.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hocam.MainActivity;
import com.hocam.R;
import com.hocam.models.Course;
import com.hocam.models.Department;

import java.util.ArrayList;

public class MainFragment extends Fragment
{
    private ProgressBar progressBar;
    private ArrayList<Department> deptList = new ArrayList<>();
    private ArrayList<Course> courseList = new ArrayList<>();
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static MainFragment newInstance(int index)
    {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        int index = 1;
        if (getArguments() != null)
        {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();
        progressBar = root.findViewById(R.id.indeterminateBar);

        int index = 1;

        if (getArguments() != null)
        {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        RecyclerView.Adapter adapter;
        if (index == 1)
        {
            adapter = new CoursesRecyclerViewAdapter(root.getContext(), courseList);
            recyclerView.setAdapter(adapter);
        }
        else
        {
            adapter = new DepartmentsRecyclerViewAdapter(root.getContext(), deptList);
            recyclerView.setAdapter(adapter);
        }

        readData(adapter);

        return root;
    }

    private void readData(final RecyclerView.Adapter adapter)
    {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("departments");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                deptList.clear();
                courseList.clear();

                for (DataSnapshot department : dataSnapshot.getChildren())
                {
                    if (department.getKey().equals(MainActivity.getCurrentUser().getDepartment()))
                    {
                        for (DataSnapshot course : department.getChildren())
                        {
                            String name = (String) course.child("name").getValue();
                            if (name != null)
                            {
                                String avg = course.child("avg").getValue().toString();
                                courseList.add(new Course(name, course.getKey(), Float.parseFloat(avg)));
                            }
                        }
                    }
                    deptList.add(new Department(department.child("name").getValue().toString(), department.getKey()));
                }

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}