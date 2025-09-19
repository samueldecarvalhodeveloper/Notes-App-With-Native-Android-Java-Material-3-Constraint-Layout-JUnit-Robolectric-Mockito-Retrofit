package com.example.notesapp.user_interface.infrastructure.start_fragment_on_container_command;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapp.user_interface.fragments.NoNoteFragment;

public class StartNoNoteFragmentOnContainerCommand {
    private StartNoNoteFragmentOnContainerCommand() {
    }

    public static void execute(int containerId, AppCompatActivity activity) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, new NoNoteFragment())
                .commit();
    }
}
