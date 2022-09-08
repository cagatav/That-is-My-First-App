package com.example.thatsmyfirstapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.thatsmyfirstapp.AppManager;
import com.example.thatsmyfirstapp.R;
import com.example.thatsmyfirstapp.data.model.UserModel;
import com.example.thatsmyfirstapp.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        UserModel userModel = AppManager.getUserModel();
        binding.setUser(userModel);

        return binding.getRoot();
    }
}