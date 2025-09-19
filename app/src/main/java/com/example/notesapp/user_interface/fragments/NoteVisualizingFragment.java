package com.example.notesapp.user_interface.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.notesapp.databinding.FragmentNoteVisualizingBinding;
import com.example.notesapp.user_interface.infrastructure.observer_configurators.NoteVisualizingFragmentObserverConfigurator;
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel;

public class NoteVisualizingFragment extends Fragment {
    private final int noteId;
    private final NoteEditingViewModel noteEditingViewModel;
    private FragmentNoteVisualizingBinding binding;

    public NoteVisualizingFragment(int noteId, NoteEditingViewModel noteEditingViewModel) {
        this.noteEditingViewModel = noteEditingViewModel;
        this.noteId = noteId;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNoteVisualizingBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        NoteVisualizingFragmentObserverConfigurator.setObservers(
                noteId,
                binding.noteTitleText,
                binding.noteBodyText,
                noteEditingViewModel,
                this
        );

    }
}