package com.hocam;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hocam.databinding.ActivityRegisterBinding;
import com.hocam.models.User;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity
{
    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;

    private static final Pattern EMAIL = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}@ug.bilkent.edu.tr");


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if (mAuth.getCurrentUser() != null)
        {
            // User already signed-in
        }
    }

    public void register(View view)
    {
        final String name = binding.edtName.getText().toString().trim();
        final String surname = binding.edtSurname.getText().toString().trim();
        final String department = binding.spinner.getSelectedItem().toString().trim();
        final String email = binding.edtEmail.getText().toString().trim();
        String password = binding.edtPassword.getText().toString();

        if (name.isEmpty())
        {
            binding.edtName.setError("Name required!");
            binding.edtName.requestFocus();
            return;
        }

        if (surname.isEmpty())
        {
            binding.edtSurname.setError("Surname required!");
            binding.edtSurname.requestFocus();
            return;
        }

        if (email.isEmpty())
        {
            binding.edtEmail.setError("E-mail required!");
            binding.edtEmail.requestFocus();
            return;
        }

        if (!EMAIL.matcher(email).matches())
        {
            binding.edtEmail.setError("Enter a valid E-mail address!");
            binding.edtEmail.requestFocus();
            return;
        }

        if (password.isEmpty())
        {
            binding.edtPassword.setError("Password required!");
            binding.edtPassword.requestFocus();
            return;
        }

        if (password.length() < 6)
        {
            binding.edtPassword.setError("Password length must be greater than 6!");
            binding.edtPassword.requestFocus();
            return;
        }

        binding.pbRegister.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        binding.pbRegister.setVisibility(View.GONE);
                        if (task.isSuccessful())
                        {
                            User user = new User(name, surname, department, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    binding.pbRegister.setVisibility(View.GONE);
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this, getString(R.string.register_success),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
