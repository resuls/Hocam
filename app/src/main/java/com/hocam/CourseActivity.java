package com.hocam;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hocam.models.Instructor;
import com.hocam.models.Review;
import com.hocam.ui.main.InstructorRecyclerViewAdapter;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {
    private String course;
    private DatabaseReference mDatabase;
    private ArrayList<Instructor> list = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_course);

        recyclerView = findViewById(R.id.courseRecyclerView);
        course = getIntent().getStringExtra("course");
        mDatabase = FirebaseDatabase.getInstance().getReference("departments")
                .child(course.split(" ")[0])
                .child(course).child("teachers");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                fillTeachers(dataSnapshot);
                LinearLayoutManager lm = new LinearLayoutManager(CourseActivity.this);
                lm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(lm);
                recyclerView.addItemDecoration(new DividerItemDecoration(CourseActivity.this, LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(new InstructorRecyclerViewAdapter(CourseActivity.this, list, course));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

    }

    public void fillTeachers(DataSnapshot data)
    {
        for (DataSnapshot snapshot : data.getChildren())
        {
            ArrayList<Review> r = new ArrayList<>();

            for (DataSnapshot i : snapshot.getChildren())
            {
                r.add(i.getValue(Review.class));
            }

            list.add(new Instructor(snapshot.getKey(), r));

        }
    }
}
