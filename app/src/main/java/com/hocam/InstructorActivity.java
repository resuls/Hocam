package com.hocam;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hocam.databinding.ActivityInstructorBinding;

public class InstructorActivity extends AppCompatActivity {

    private ActivityInstructorBinding binding;
    private Intent intent;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        intent = getIntent();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_instructor);
        binding.toolbarLayout
                .setTitle(intent.getStringExtra("instructor")+
                          intent.getIntExtra("rating", 0)+
                          new String(Character.toChars(0x2B50)));
        binding.subtitleTv.setText("Department: " + intent.getStringExtra("department"));
    }
}
