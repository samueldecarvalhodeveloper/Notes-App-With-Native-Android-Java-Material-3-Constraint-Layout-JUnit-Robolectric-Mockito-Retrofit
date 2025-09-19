package com.example.notesapp.user_interface.infrastructure.start_fragment_on_container_command;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapp.data.external_models.Note;
import com.example.notesapp.user_interface.fragments.NotesFragment;

import java.util.List;

public class StartNotesFragmentOnContainerCommand {
    private StartNotesFragmentOnContainerCommand() {
    }

    public static void execute(List<Note> listOfNotes, int containerId, AppCompatActivity activity) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, new NotesFragment(listOfNotes))
                .commit();
    }
}
