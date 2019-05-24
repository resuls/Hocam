package com.hocam.ui.main;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.hocam.R;
import com.hocam.RegisterActivity;

import java.util.Objects;

public class ResetPasswordDialog extends DialogFragment
{
    private EditText edtEmail;
    private String email;
    private Dialog dialog;
    private BroadcastReceiver myIntentBroadCastReciver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            int result = intent.getIntExtra("result", 0);

            if (result != 0)
            {
                Toast.makeText(context, "Reset password instructions has been sent to your email.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
            else
            {
                Toast.makeText(context, intent.getStringExtra("message"), Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.dialog_forgot_password, container, false);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        edtEmail = view.findViewById(R.id.edtEmail);
        TextView btnCancel = view.findViewById(R.id.btnCancel);
        TextView btnReset = view.findViewById(R.id.btnReset);

        edtEmail.requestFocus();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("PASS");

        Objects.requireNonNull(getContext()).registerReceiver(myIntentBroadCastReciver, intentFilter);

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
                email = edtEmail.getText().toString().trim();

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

                Intent intent = new Intent(getContext(), ResetPasswordService.class);
                intent.putExtra("email", email);

                Objects.requireNonNull(getContext()).startService(intent);
            }
        });
    }
}
