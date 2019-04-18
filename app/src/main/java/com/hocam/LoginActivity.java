package com.hocam;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import com.hocam.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity
{
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }

    public void load(View view)
    {
        animateButtonWidth();
        fadeOutTextandSetProgressDialog();
        nextAction();
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
        }, 2000);
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
}
