package com.example.notesapp.user_interface.events_handlers.click_event_handlers;

import static com.example.notesapp.user_interface.infrastructure.specifications.UserInterfaceSpecifications.isViewIdEqualsToNoteItemContainer;

import android.view.View;
import android.view.View.OnClickListener;

import com.example.notesapp.user_interface.infrastructure.activity_starting_adapters.NoteActivityStartingAdapter;

public class NotesRecyclerViewNoteItemClickEventHandler implements OnClickListener {
    private final int noteId;

    public NotesRecyclerViewNoteItemClickEventHandler(int noteId) {
        this.noteId = noteId;
    }

    @Override
    public void onClick(View view) {
        if (isViewIdEqualsToNoteItemContainer(view)) {
            NoteActivityStartingAdapter.startNoteActivity(noteId, view.getContext());
        }
    }
}
