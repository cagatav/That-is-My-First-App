package com.example.thatsmyfirstapp.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.thatsmyfirstapp.AppManager;
import com.example.thatsmyfirstapp.R;
import com.example.thatsmyfirstapp.data.model.UserModel;
import com.example.thatsmyfirstapp.databinding.FragmentLoginBinding;
import com.example.thatsmyfirstapp.util.NavigationHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginFragment extends Fragment {

    ProgressDialog pd;
    private FragmentLoginBinding binding;
    private FirebaseAuth mAuth;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login,
                container, false);

        pd = new ProgressDialog(getContext());
        pd.setMessage("Logging In..");

        mAuth = FirebaseAuth.getInstance();

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.loginEmail.getText().toString();
                String password = binding.loginPass.getText().toString();
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    binding.loginEmail.setError("Invalid Email!");
                    binding.loginEmail.setFocusable(true);
                }

                loginUser(email,password);

            }
        });
        binding.loginRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return binding.getRoot();
    }

    private void loginUser(String email, String password) {
        pd.show();
        mAuth.signInWithEmailAndPassword(email,password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            pd.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            getUserInformation(user.getEmail());
                        }
                        else{
                            pd.dismiss();
                            Toast.makeText(getContext(),"Authentication Failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();

                Toast.makeText(getContext(),""+e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getUserInformation(String mail) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(mail);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                UserModel user = null;
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    user = document.toObject(UserModel.class);
                }

                AppManager.setUserModel(user);
                NavigationHelper.navigate(new MainFragment(), NavigationHelper.NavType.Add,
                        NavigationHelper.NavStack.BoatAdd, NavigationHelper.NavAnim.Fade);
            } else {
                Log.d("TAG", "get failed with ", task.getException());
            }
        });
    }
}