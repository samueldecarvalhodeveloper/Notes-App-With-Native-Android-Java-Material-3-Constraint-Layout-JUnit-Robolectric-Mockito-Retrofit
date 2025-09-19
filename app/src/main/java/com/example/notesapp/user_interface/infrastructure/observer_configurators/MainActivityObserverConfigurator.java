package com.example.notesapp.user_interface.infrastructure.observer_configurators;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import com.example.notesapp.R;
import com.example.notesapp.user_interface.activities.MainActivity;
import com.example.notesapp.user_interface.infrastructure.start_fragment_on_container_command.StartLoadingFragmentOnContainerCommand;
import com.example.notesapp.user_interface.infrastructure.start_fragment_on_container_command.StartNoNoteFragmentOnContainerCommand;
import com.example.notesapp.user_interface.infrastructure.start_fragment_on_container_command.StartNotesFragmentOnContainerCommand;
import com.example.notesapp.user_interface.view_models.NotesListingViewModel;
import com.example.notesapp.user_interface.view_models.UserViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class MainActivityObserverConfigurator {
    private MainActivityObserverConfigurator() {
    }

    public static void setObservers(
            ExtendedFloatingActionButton createNoteFloatingActionButton,
            UserViewModel userViewModel,
            NotesListingViewModel notesListingViewModel,
            MainActivity activity
    ) {
        userViewModel.getUser().observe(activity, user ->
                notesListingViewModel.fetchNotesFromServer(user.id)
        );

        notesListingViewModel.getIsListOfNotesLoaded().observe(activity, isListOfNotesLoaded -> {
            if (isListOfNotesLoaded) {
                notesListingViewModel.getListOfNotes().observe(
                        activity,
                        listOfNotes -> {
                            if (listOfNotes.isEmpty()) {
                                StartNoNoteFragmentOnContainerCommand.execute(R.id.notes_fragment_layout, activity);
                            } else {
                                StartNotesFragmentOnContainerCommand.execute(listOfNotes, R.id.notes_fragment_layout, activity);
                            }
                        }
                );
            } else {
                StartLoadingFragmentOnContainerCommand.execute(R.id.notes_fragment_layout, activity);
            }
        });

        notesListingViewModel.getIsNoteCreationCurrentlyUnable()
                .observe(
                        activity,
                        isNoteCreationCurrentlyUnable ->
                                createNoteFloatingActionButton.setVisibility(
                                        isNoteCreationCurrentlyUnable ? GONE : VISIBLE
                                )
                );
    }
}
