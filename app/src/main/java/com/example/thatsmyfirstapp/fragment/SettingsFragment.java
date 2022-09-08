package com.example.thatsmyfirstapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.thatsmyfirstapp.R;
import com.example.thatsmyfirstapp.databinding.FragmentSettingsBinding;
import com.example.thatsmyfirstapp.util.NavigationHelper;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);

        binding.logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(),"Logging Out",Toast.LENGTH_SHORT).show();
                NavigationHelper.removeAll();
                NavigationHelper.navigate(new LoginFragment(), NavigationHelper.NavType.Replace,
                        NavigationHelper.NavStack.BoatAdd, NavigationHelper.NavAnim.Fade);
            }
        });
        return binding.getRoot();
    }


}