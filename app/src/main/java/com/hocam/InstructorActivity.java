package com.hocam;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_instructor);

        binding.fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorActivity.this, ReviewActivity.class);
                intent.putExtra("Instructor", instructor);
                intent.putExtra("course", getIntent().getStringExtra("course"));
                startActivity(intent);
            }
        });

        intent = getIntent();
        instructor = (Instructor) intent.getSerializableExtra("Instructor");

        binding.name.setText(instructor.getName());
        binding.rating.setText(instructor.getRating() + new String(Character.toChars(0x2B50)));

        binding.department.setText("Department: " + intent.getStringExtra("department"));

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        Log.i("", instructor.getReviews() + "");
        binding.recyclerView.setAdapter(new ReviewRecyclerViewAdapter(this, instructor.getReviews()));
    }
}
