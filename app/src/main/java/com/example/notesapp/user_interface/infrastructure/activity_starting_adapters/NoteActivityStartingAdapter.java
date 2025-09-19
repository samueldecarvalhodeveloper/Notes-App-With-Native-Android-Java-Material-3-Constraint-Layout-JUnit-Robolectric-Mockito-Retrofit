package com.example.notesapp.user_interface.infrastructure.activity_starting_adapters;

import static com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_ID_INTENT_KEY;

import android.content.Context;
import android.content.Intent;

import com.example.notesapp.user_interface.activities.NoteActivity;

public class NoteActivityStartingAdapter {
    private NoteActivityStartingAdapter() {
    }

    public static void startNoteActivity(int noteId, Context currentActivity) {
        Intent noteActivityIntent = new Intent(currentActivity, NoteActivity.class);

        noteActivityIntent.putExtra(NOTE_ID_INTENT_KEY, noteId);

        currentActivity.startActivity(noteActivityIntent);
    }
}
