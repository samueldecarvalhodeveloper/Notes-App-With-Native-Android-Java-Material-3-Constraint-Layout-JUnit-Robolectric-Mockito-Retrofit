package com.example.notesapp.user_interface.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.notesapp.databinding.FragmentNoNoteBinding;

public class NoNoteFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentNoNoteBinding binding = FragmentNoNoteBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}