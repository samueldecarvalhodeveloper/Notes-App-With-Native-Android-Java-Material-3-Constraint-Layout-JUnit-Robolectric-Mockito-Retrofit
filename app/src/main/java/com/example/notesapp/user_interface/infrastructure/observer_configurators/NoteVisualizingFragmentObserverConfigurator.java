package com.example.notesapp.user_interface.infrastructure.observer_configurators;

import android.widget.TextView;

import com.example.notesapp.user_interface.fragments.NoteVisualizingFragment;
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel;

public class NoteVisualizingFragmentObserverConfigurator {
    private NoteVisualizingFragmentObserverConfigurator() {
    }

    public static void setObservers(
            int noteId,
            TextView noteTitleText,
            TextView noteBodyText,
            NoteEditingViewModel noteEditingViewModel,
            NoteVisualizingFragment noteVisualizingFragment
    ) {
        noteEditingViewModel.getNote(noteId).observe(noteVisualizingFragment, note -> {
            noteTitleText.setText(note.title);
            noteBodyText.setText(note.body);
        });
    }
}
