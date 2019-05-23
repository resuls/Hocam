package com.hocam;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.hocam.databinding.ActivityInstructorBinding;
import com.hocam.models.Instructor;
import com.hocam.ui.main.ReviewRecyclerViewAdapter;

public class InstructorActivity extends AppCompatActivity {

    private ActivityInstructorBinding binding;
    private Intent intent;
    private RecyclerView recyclerView;
    private Instructor instructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorActivity.this, ReviewActivity.class);
                intent.putExtra("Instructor", instructor);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);

        intent = getIntent();
        instructor = (Instructor) intent.getSerializableExtra("Instructor");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_instructor);
        binding.toolbarLayout
                .setTitle(instructor.getName()+ "\n\n\n" +
                          instructor.getRating()+
                          new String(Character.toChars(0x2B50)));
        binding.subtitleTv.setText("Department: " + intent.getStringExtra("department"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        Log.i("ddd", instructor.getReviews()+"");
        recyclerView.setAdapter(new ReviewRecyclerViewAdapter(this, instructor.getReviews()));
    }
}
