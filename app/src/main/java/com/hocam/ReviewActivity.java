package com.hocam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hocam.databinding.ActivityReviewBinding;
import com.hocam.models.Instructor;
import com.hocam.models.Review;

public class ReviewActivity extends AppCompatActivity {
    private ActivityReviewBinding binding;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_review);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_review);

        final Intent intent = getIntent();
        final Instructor instructor = (Instructor) intent.getSerializableExtra("Instructor");
        binding.course.setText(intent.getStringExtra("course"));


        binding.teacherName.setText(instructor.getName());

        binding.submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(binding.reviewText.getText().toString().equals("") || binding.ratingBar.getRating() == 0.0) {
                    Toast.makeText(ReviewActivity.this, "Please write valid review and select rating before submitting.", Toast.LENGTH_SHORT).show();
                    return;
                }

                ProgressDialog dialog = ProgressDialog.show(ReviewActivity.this, "",
                        "Loading. Please wait...", true);
                String course = binding.course.getText().toString();
                mDatabase = FirebaseDatabase.getInstance().getReference("departments")
                        .child(course.split(" ")[0])
                        .child(course).child("teachers").child(instructor.getName());
                Review review = new Review(FirebaseAuth.getInstance().getCurrentUser().getEmail(), instructor.getName(),
                        course, Integer.parseInt(binding.yearSpinner.getSelectedItem().toString()),
                        binding.semesterSpinner.getSelectedItem().toString(), binding.reviewText.getText().toString(), (int)binding.ratingBar.getRating(),
                        binding.isAnonymus.isChecked());
                if (binding.isAnonymus.isChecked()) {
                    review.setFrom("Anonymous");
                }
                mDatabase.push().setValue(review);
                dialog.dismiss();
                try {
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    r.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(ReviewActivity.this, MainActivity.class));
            }
        });
    }
}
