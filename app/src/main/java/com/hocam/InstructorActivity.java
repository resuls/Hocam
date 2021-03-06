package com.hocam;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.hocam.databinding.ActivityInstructorBinding;
import com.hocam.models.Instructor;
import com.hocam.ui.main.ReviewRecyclerViewAdapter;

import java.text.DecimalFormat;

public class InstructorActivity extends AppCompatActivity
{
    private Instructor instructor;
    private GestureDetector g;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_instructor);
        ActivityInstructorBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_instructor);
        g = new GestureDetector(this, new GestureListener());

        binding.fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(InstructorActivity.this, ReviewActivity.class);
                intent.putExtra("Instructor", instructor);
                intent.putExtra("course", getIntent().getStringExtra("course"));
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        instructor = (Instructor) intent.getSerializableExtra("Instructor");

        binding.toolbar.setTitle(intent.getStringExtra("course"));

        binding.name.setText(instructor.getName());
        binding.rating.setText(String.format("%s%s", new DecimalFormat("#0.00").format(instructor.getRating()), new String(Character.toChars(0x2B50))));

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        binding.recyclerView.setAdapter(new ReviewRecyclerViewAdapter(this, instructor.getReviews()));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        g.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class GestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public void onLongPress(MotionEvent e)
        {
            startActivity(new Intent(InstructorActivity.this, MainActivity.class));
        }
    }
}
