package com.example.notesapp.user_interface.infrastructure.start_fragment_on_container_command;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapp.user_interface.fragments.NoteEditingFragment;
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel;

public class StartNoteEditingFragmentOnContainerCommand {
    private StartNoteEditingFragmentOnContainerCommand() {
    }

    public static void execute(
            int noteId,
            NoteEditingViewModel noteEditingViewModel,
            int containerId,
            AppCompatActivity activity
    ) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, new NoteEditingFragment(noteId, noteEditingViewModel))
                .commit();
    }
}
