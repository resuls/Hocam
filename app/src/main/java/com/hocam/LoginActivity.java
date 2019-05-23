package com.hocam;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hocam.databinding.ActivityLoginBinding;
import com.hocam.ui.main.ResetPasswordDialog;

public class LoginActivity extends AppCompatActivity
{
    private final int REGISTER_REQUEST = 100;
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        user = mAuth.getCurrentUser();

        if (user != null)
        {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        setContentView(R.layout.activity_login);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

    public void signIn(View view)
    {
        final String email = binding.email.getText().toString().trim();
        final String password = binding.password.getText().toString();

        if (email.isEmpty())
        {
            binding.email.setError("E-mail required!");
            binding.email.requestFocus();
            return;
        }

        if (!RegisterActivity.EMAIL.matcher(email).matches())
        {
            binding.email.setError("Enter a valid E-mail address!");
            binding.email.requestFocus();
            return;
        }

        if (password.isEmpty())
        {
            binding.password.setError("Password required!");
            binding.password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            if (user.isEmailVerified())
                            {
                                animateButtonWidth();
                                fadeOutTextandSetProgressDialog();
                                nextAction();
                            }
                            else
                            {
                                makeToast("Please verify your email!");
                            }
                        }
                        else
                        {
                            makeToast(task.getException().getMessage());
                        }
                    }
                });
    }

    private void animateButtonWidth()
    {
        ValueAnimator anim = ValueAnimator.ofInt(binding.signInBtn.getMeasuredWidth(), getFinalWidth());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int value = (Integer) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = binding.signInBtn.getLayoutParams();
                layoutParams.width = value;
                binding.signInBtn.requestLayout();
            }
        });

        anim.setDuration(250);
        anim.start();
    }

    private void fadeOutTextandSetProgressDialog()
    {
        binding.signInText.animate().alpha(0f).setDuration(250).setListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                super.onAnimationEnd(animation);
                showProgressDialog();
            }
        }).start();
    }

    private void showProgressDialog()
    {
        binding.progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE,
                PorterDuff.Mode.SRC_IN);
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void nextAction()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                revealButton();
                fadeOutProgressDialog();
                delayedStartNextActivity();
            }
        }, 1000);
    }

    private void revealButton()
    {
        binding.signInBtn.setElevation(0f);
        binding.revealView.setVisibility(View.VISIBLE);

        int x = binding.revealView.getWidth();
        int y = binding.revealView.getHeight();

        int startX = (int) (getFinalWidth() / 2 + binding.signInBtn.getX());
        int startY = (int) (getFinalWidth() / 2 + binding.signInBtn.getY());

        float radius = Math.max(x, y) * 1.2f;

        Animator reveal = ViewAnimationUtils.createCircularReveal(binding.revealView, startX, startY,
                getFinalWidth(), radius);
        reveal.setDuration(350);
        reveal.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                super.onAnimationEnd(animation);
                finish();
            }
        });

        reveal.start();
    }

    private void fadeOutProgressDialog()
    {
        binding.progressBar.animate().alpha(0f).setDuration(200).start();
    }

    private void delayedStartNextActivity()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        }, 100);
    }

    private int getFinalWidth()
    {
        return (int) getResources().getDimension(R.dimen.get_width);
    }

    public void registerActivity(View view)
    {
        startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class),
                REGISTER_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTER_REQUEST)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                String email = data.getStringExtra("email");
                String password = data.getStringExtra("password");
                binding.email.setText(email);
                binding.password.setText(password);
            }
        }
    }

    private void makeToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void setEmailBox(String email)
    {
        binding.email.setText(email);
    }

    public void forgotPassword(View view)
    {
        ResetPasswordDialog dialog = new ResetPasswordDialog();
        dialog.show(getSupportFragmentManager(), "ResetDialog");
    }
}
