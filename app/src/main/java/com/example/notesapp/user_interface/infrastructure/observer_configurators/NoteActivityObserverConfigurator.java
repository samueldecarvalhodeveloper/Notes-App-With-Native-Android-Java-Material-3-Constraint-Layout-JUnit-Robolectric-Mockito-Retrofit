package com.example.notesapp.user_interface.infrastructure.observer_configurators;

import com.example.notesapp.R;
import com.example.notesapp.user_interface.activities.NoteActivity;
import com.example.notesapp.user_interface.infrastructure.activity_starting_adapters.MainActivityStartingAdapter;
import com.example.notesapp.user_interface.infrastructure.start_fragment_on_container_command.StartLoadingFragmentOnContainerCommand;
import com.example.notesapp.user_interface.infrastructure.start_fragment_on_container_command.StartNoteEditingFragmentOnContainerCommand;
import com.example.notesapp.user_interface.infrastructure.start_fragment_on_container_command.StartNoteVisualizingFragmentOnContainerCommand;
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel;
import com.google.android.material.appbar.MaterialToolbar;

public class NoteActivityObserverConfigurator {
    private NoteActivityObserverConfigurator() {
    }

    public static void setObservers(
            int noteId,
            MaterialToolbar noteActivityToolbar,
            NoteEditingViewModel noteEditingViewModel,
            NoteActivity activity
    ) {
        noteEditingViewModel.getIsNoteLoaded().observe(activity, isNoteLoaded -> {
            if (isNoteLoaded) {
                noteActivityToolbar.getMenu().getItem(0).setVisible(false);
                noteActivityToolbar.getMenu().getItem(1).setVisible(true);
                noteActivityToolbar.getMenu().getItem(2).setVisible(true);

                StartNoteVisualizingFragmentOnContainerCommand.execute(
                        noteId,
                        noteEditingViewModel,
                        R.id.note_handling_fragment,
                        activity
                );
            } else {
                noteActivityToolbar.getMenu().getItem(0).setVisible(false);
                noteActivityToolbar.getMenu().getItem(1).setVisible(false);
                noteActivityToolbar.getMenu().getItem(2).setVisible(false);

                StartLoadingFragmentOnContainerCommand.execute(R.id.note_handling_fragment, activity);
            }
        });

        noteEditingViewModel.getIsNoteBeingManipulated().observe(activity, isNoteBeingManipulated -> {
            if (isNoteBeingManipulated) {
                noteActivityToolbar.getMenu().getItem(0).setVisible(true);
                noteActivityToolbar.getMenu().getItem(1).setVisible(false);
                noteActivityToolbar.getMenu().getItem(2).setVisible(true);

                StartNoteEditingFragmentOnContainerCommand.execute(
                        noteId,
                        noteEditingViewModel,
                        R.id.note_handling_fragment,
                        activity
                );
            } else {
                noteActivityToolbar.getMenu().getItem(0).setVisible(false);
                noteActivityToolbar.getMenu().getItem(1).setVisible(true);
                noteActivityToolbar.getMenu().getItem(2).setVisible(true);

                StartNoteVisualizingFragmentOnContainerCommand.execute(
                        noteId,
                        noteEditingViewModel,
                        R.id.note_handling_fragment,
                        activity
                );
            }
        });

        noteEditingViewModel.getIsNoteManipulationUnable().observe(activity, isNoteManipulationUnable -> {
            if (isNoteManipulationUnable) {
                noteActivityToolbar.getMenu().getItem(0).setVisible(false);
                noteActivityToolbar.getMenu().getItem(1).setVisible(false);
                noteActivityToolbar.getMenu().getItem(2).setVisible(false);

                StartNoteVisualizingFragmentOnContainerCommand.execute(
                        noteId,
                        noteEditingViewModel,
                        R.id.note_handling_fragment,
                        activity
                );
            }
        });

        noteEditingViewModel.getIsNoteDeleted().observe(activity, isNoteDeleted -> {
            if (isNoteDeleted) {
                MainActivityStartingAdapter.startMainActivity(activity);
            }
        });
    }
}
