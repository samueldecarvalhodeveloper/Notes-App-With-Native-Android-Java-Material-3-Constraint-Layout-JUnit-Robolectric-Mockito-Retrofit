package com.example.notesapp.user_interface.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.notesapp.databinding.FragmentNoteEditingBinding;
import com.example.notesapp.user_interface.events_handlers.text_change_event_handlers.NoteBodyTextInputTextChangeEventHandler;
import com.example.notesapp.user_interface.events_handlers.text_change_event_handlers.NoteTitleTextInputTextChangeEventHandler;
import com.example.notesapp.user_interface.infrastructure.observer_configurators.NoteEditingFragmentObserverConfigurator;
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel;

public class NoteEditingFragment extends Fragment {
    private final int noteId;
    private final NoteEditingViewModel noteEditingViewModel;
    private FragmentNoteEditingBinding binding;

    public NoteEditingFragment(int noteId, NoteEditingViewModel noteEditingViewModel) {
        this.noteId = noteId;
        this.noteEditingViewModel = noteEditingViewModel;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNoteEditingBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        NoteTitleTextInputTextChangeEventHandler noteTitleTextInputTextChangeEventHandler =
                new NoteTitleTextInputTextChangeEventHandler(noteEditingViewModel);
        NoteBodyTextInputTextChangeEventHandler noteBodyTextInputTextChangeEventHandler =
                new NoteBodyTextInputTextChangeEventHandler(noteEditingViewModel);

        NoteEditingFragmentObserverConfigurator.setObservers(
                noteId,
                binding.noteTitleTextInput,
                binding.noteBodyTextInput,
                noteEditingViewModel,
                this
        );

        binding.noteTitleTextInput.addTextChangedListener(noteTitleTextInputTextChangeEventHandler);
        binding.noteBodyTextInput.addTextChangedListener(noteBodyTextInputTextChangeEventHandler);
    }
}