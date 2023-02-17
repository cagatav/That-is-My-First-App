package com.example.thatsmyfirstapp;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thatsmyfirstapp.fragment.LoginFragment;
import com.example.thatsmyfirstapp.fragment.MainFragment;
import com.example.thatsmyfirstapp.util.NavigationHelper;
import com.example.thatsmyfirstapp.util.UtilHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class MainActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppManager.setContext(this);
        AppManager.setActivity(this);
        NavigationHelper.setManager(getSupportFragmentManager());

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            NavigationHelper.navigate(new MainFragment(), NavigationHelper.NavType.Replace,
                    NavigationHelper.NavStack.BoatAdd, NavigationHelper.NavAnim.Fade);
        } else {
            NavigationHelper.navigate(new LoginFragment(), NavigationHelper.NavType.Replace,
                    NavigationHelper.NavStack.BoatAdd, NavigationHelper.NavAnim.Fade);
        }
    }
    @Override
    public void onBackPressed() {
        UtilHelper.hideKeyboard(this);
        if (NavigationHelper.getLast().getClass() == MainFragment.class || NavigationHelper.
                getLast().getClass() == LoginFragment.class) {
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getResources().getString(R.string.onbackpress_again), Toast.LENGTH_SHORT).show();
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else
            NavigationHelper.removeLast();
    }
}

