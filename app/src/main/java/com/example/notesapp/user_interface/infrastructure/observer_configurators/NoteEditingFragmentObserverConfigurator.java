package com.example.notesapp.user_interface.infrastructure.observer_configurators;

import com.example.notesapp.user_interface.fragments.NoteEditingFragment;
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class NoteEditingFragmentObserverConfigurator {
    private NoteEditingFragmentObserverConfigurator() {
    }

    public static void setObservers(
            int noteId,
            TextInputEditText noteTitleTextInput,
            TextInputEditText noteBodyTextInput,
            NoteEditingViewModel noteEditingViewModel,
            NoteEditingFragment noteEditingFragment
    ) {
        noteEditingViewModel.getNote(noteId).observe(noteEditingFragment, note -> {
            noteTitleTextInput.setText(note.title);
            noteBodyTextInput.setText(note.body);
        });
    }
}
