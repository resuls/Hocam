package com.hocam.ui.main;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ResetPasswordService extends IntentService
{
    public ResetPasswordService()
    {
        super("Reset password");
    }

    @Override
    protected void onHandleIntent(final Intent intent)
    {
        FirebaseAuth.getInstance().sendPasswordResetEmail(intent.getStringExtra("email"))
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        Intent broadCastIntent = new Intent();
                        if (task.isSuccessful())
                        {
                            broadCastIntent.putExtra("result", 1);
                        }
                        else
                        {
                            broadCastIntent.putExtra("result", 0);
                            broadCastIntent.putExtra("message", Objects.requireNonNull(task.getException()).getMessage());
                        }

                        broadCastIntent.setAction("PASS");
                        getBaseContext().sendBroadcast(broadCastIntent);
                    }
                });
    }
}
