package com.hocam.ui.main;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.hocam.LoginActivity;
import com.hocam.R;
import com.hocam.RegisterActivity;

public class ResetPasswordDialog extends DialogFragment
{
    private EditText edtEmail;
    private TextView btnCancel;
    private TextView btnReset;

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.dialog_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        edtEmail = view.findViewById(R.id.edtEmail);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnReset = view.findViewById(R.id.btnReset);

        edtEmail.requestFocus();

        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getDialog().dismiss();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String email = edtEmail.getText().toString().trim();

                if (email.isEmpty())
                {
                    edtEmail.setError("E-mail required!");
                    edtEmail.requestFocus();
                    return;
                }

                if (!RegisterActivity.EMAIL.matcher(email).matches())
                {
                    edtEmail.setError("Enter a valid E-mail address!");
                    edtEmail.requestFocus();
                    return;
                }

                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(getContext(), "Reset password instructions has been sent to your email.", Toast.LENGTH_SHORT).show();
                                    ((LoginActivity) getActivity()).setEmailBox(email);

                                    getDialog().dismiss();
                                }
                                else
                                {
                                    Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
