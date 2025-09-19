package com.example.notesapp.user_interface.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.notesapp.databinding.FragmentLoadingBinding;

public class LoadingFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentLoadingBinding binding = FragmentLoadingBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}