package com.example.thatsmyfirstapp;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.thatsmyfirstapp.data.model.UserModel;
import com.example.thatsmyfirstapp.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                        this, R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User..");

        mAuth = FirebaseAuth.getInstance();

        binding.registerCreateAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.registerEmail.getText().toString().trim();
                String password = binding.registerPass.getText().toString().trim();
                String confirmPassword = binding.registerConfirmPass.getText().toString();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    binding.registerEmail.setError("Invalid Email!");
                    binding.registerEmail.setFocusable(true);
                }
                else if (password.length()<6){
                    binding.registerPass.setError("Password length at least 6 characters.");
                    binding.registerPass.setFocusable(true);
                }
                else if (!password.equals(confirmPassword)){
                    binding.registerConfirmPass.setError("Passwords are not matched.");
                    binding.registerConfirmPass.setFocusable(true);
                }
                else {
                    registerUser (email,password);
                }
            }
        });
        binding.registerLoginBtn.setOnClickListener(v-> finish());

    }

    private void registerUser(String email, String password) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email,password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    FirebaseUser user = mAuth.getCurrentUser();
                    registerUser(user);

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this,"Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this,""+e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void registerUser(FirebaseUser user) {
        UserModel userModel = new UserModel();
        userModel.setMail(user.getEmail());
        userModel.setFullname(binding.registerName.getText().toString());
        userModel.setPhone(binding.registerPhone.getText().toString());
        userModel.setInstagram("");
        userModel.setTelegram("");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference washingtonRef = db.collection("users").document(userModel.getMail());
        washingtonRef.set(userModel)
                .addOnSuccessListener(aVoid -> {
                    AppManager.setUserModel(userModel);
                    Toast.makeText(RegisterActivity.this,"Registered..\n" +
                            user.getEmail(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Kullanıcı Oluşturulamadı.", Toast.LENGTH_LONG).show());
    }
}

