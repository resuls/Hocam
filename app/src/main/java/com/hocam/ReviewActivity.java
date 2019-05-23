package com.hocam;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hocam.databinding.ActivityLoginBinding;
import com.hocam.databinding.ActivityReviewBinding;
import com.hocam.models.Instructor;

public class ReviewActivity extends AppCompatActivity {
    private ActivityReviewBinding binding;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_review);

        Intent intent = getIntent();
        final Instructor instructor = (Instructor) intent.getSerializableExtra("Instructor");


        binding.teacherName.setText(instructor.getName());

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String course = binding.courseSpinner.getSelectedItem().toString();
                mDatabase = FirebaseDatabase.getInstance().getReference("departments")
                        .child(course.split(" ")[0])
                        .child(course).child("teachers").child(instructor.getName());
                
            }
        });
    }
}
