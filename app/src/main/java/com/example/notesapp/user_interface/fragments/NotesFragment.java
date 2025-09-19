package com.example.notesapp.user_interface.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.notesapp.data.external_models.Note;
import com.example.notesapp.databinding.FragmentNotesBinding;
import com.example.notesapp.user_interface.infrastructure.concerns.SetupNotesRecyclerViewCommand;

import java.util.List;

public class NotesFragment extends Fragment {
    private final List<Note> listOfNotes;
    private FragmentNotesBinding binding;

    public NotesFragment(List<Note> listOfNotes) {
        this.listOfNotes = listOfNotes;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotesBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        SetupNotesRecyclerViewCommand.execute(binding.notesRecyclerView, listOfNotes, getContext());
    }
}