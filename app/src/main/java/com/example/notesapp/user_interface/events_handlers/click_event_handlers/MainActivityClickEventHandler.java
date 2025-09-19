package com.example.notesapp.user_interface.events_handlers.click_event_handlers;

import static com.example.notesapp.user_interface.infrastructure.specifications.UserInterfaceSpecifications.isViewIdEqualsToCreateNoteFloatingActionButton;

import android.view.View;
import android.view.View.OnClickListener;

import androidx.lifecycle.LifecycleOwner;

import com.example.notesapp.user_interface.infrastructure.activity_starting_adapters.NoteActivityStartingAdapter;
import com.example.notesapp.user_interface.view_models.NotesListingViewModel;
import com.example.notesapp.user_interface.view_models.UserViewModel;

public class MainActivityClickEventHandler implements OnClickListener {
    private final UserViewModel userViewModel;
    private final NotesListingViewModel notesListingViewModel;
    private final LifecycleOwner lifecycleOwner;

    public MainActivityClickEventHandler(
            NotesListingViewModel notesListingViewModel,
            UserViewModel userViewModel,
            LifecycleOwner lifecycleOwner
    ) {
        this.notesListingViewModel = notesListingViewModel;
        this.userViewModel = userViewModel;
        this.lifecycleOwner = lifecycleOwner;
    }

    @Override
    public void onClick(View view) {
        if (isViewIdEqualsToCreateNoteFloatingActionButton(view)) {
            int userId = userViewModel.getUser().getValue().id;

            notesListingViewModel.createNote(userId);

            notesListingViewModel.getCreatedNote().observe(lifecycleOwner, createdNote ->
                    NoteActivityStartingAdapter.startNoteActivity(createdNote.id, view.getContext())
            );
        }
    }
}
