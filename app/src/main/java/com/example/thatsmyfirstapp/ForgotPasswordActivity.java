package com.example.thatsmyfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    FirebaseAuth auth;
    private EditText emailEditText;
    private Button reset_pass;
    private Button back_log;
    private Button create_acc;
    private ProgressBar progressBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = findViewById(R.id.resetPassEmail);
        reset_pass = findViewById(R.id.resetPassBtn);
        create_acc = findViewById(R.id.resetPassCreateAccBtn);
        back_log = findViewById(R.id.resetPassLoginBtn);

        auth = FirebaseAuth.getInstance();

        create_acc.setOnClickListener((v)-> startActivity(new Intent(ForgotPasswordActivity.this, RegisterActivity.class)));

        reset_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }
    private void resetPassword(){
        String email = emailEditText.getText().toString().trim();

        if (email.isEmpty()){
            emailEditText.setError("Email is required!");
            emailEditText.requestFocus();
            return;}

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this, "Check your email to reset your password.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ForgotPasswordActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

