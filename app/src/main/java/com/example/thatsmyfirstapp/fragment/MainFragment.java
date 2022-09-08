package com.example.thatsmyfirstapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.thatsmyfirstapp.R;
import com.example.thatsmyfirstapp.databinding.FragmentMainBinding;
import com.example.thatsmyfirstapp.util.NavigationHelper;
import com.google.firebase.auth.FirebaseAuth;


public class MainFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    private FragmentMainBinding binding;

    public MainFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);


        firebaseAuth = FirebaseAuth.getInstance();
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    NavigationHelper.navigate(new HomeFragment(), NavigationHelper.NavType.Replace,
                            NavigationHelper.NavStack.BoatAdd, NavigationHelper.NavAnim.Fade, R.id.container);
                    return true;
                case R.id.profile:
                    NavigationHelper.navigate(new ProfileFragment(), NavigationHelper.NavType.Replace,
                            NavigationHelper.NavStack.BoatAdd, NavigationHelper.NavAnim.Fade, R.id.container);
                    return true;
                case R.id.setting:
                    NavigationHelper.navigate(new SettingsFragment(), NavigationHelper.NavType.Replace,
                            NavigationHelper.NavStack.BoatAdd, NavigationHelper.NavAnim.Fade, R.id.container);
                    return true;
            }
            return false;
        });
        return binding.getRoot();
    }
}