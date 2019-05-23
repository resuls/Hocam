package com.hocam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hocam.models.Course;
import com.hocam.ui.main.CoursesRecyclerViewAdapter;

import java.util.ArrayList;

public class DepartmentActivity extends AppCompatActivity {
    private ArrayList<Course> courseList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Intent intent;
    private CoursesRecyclerViewAdapter mRecyclerAdapter;
    private String dept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        intent = getIntent();
        dept = intent.getStringExtra("dept");

        recyclerView = findViewById(R.id.courseRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();

        mRecyclerAdapter = new CoursesRecyclerViewAdapter(this, courseList);
        recyclerView.setAdapter(mRecyclerAdapter);

        readData(mRecyclerAdapter);

    }

    private void readData(final RecyclerView.Adapter adapter)
    {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("departments");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot department : dataSnapshot.getChildren())
                {
                    if (department.getKey().equals(dept))
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
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}
