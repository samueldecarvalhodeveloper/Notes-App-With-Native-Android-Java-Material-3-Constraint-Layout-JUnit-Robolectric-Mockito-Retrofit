package com.example.notesapp.user_interface.events_handlers.menu_click_event_handlers;

import static com.example.notesapp.user_interface.infrastructure.specifications.UserInterfaceSpecifications.isMenuViewIdEqualsToConcludeNoteMenuItem;
import static com.example.notesapp.user_interface.infrastructure.specifications.UserInterfaceSpecifications.isMenuViewIdEqualsToDeleteNoteMenuItem;
import static com.example.notesapp.user_interface.infrastructure.specifications.UserInterfaceSpecifications.isMenuViewIdEqualsToEditNoteMenuItem;

import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener;

import com.example.notesapp.user_interface.infrastructure.activity_starting_adapters.MainActivityStartingAdapter;
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel;

public class NoteActivityMenuClickEventHandler implements OnMenuItemClickListener, OnClickListener {
    private final AppCompatActivity activity;
    NoteEditingViewModel noteEditingViewModel;

    public NoteActivityMenuClickEventHandler(NoteEditingViewModel noteEditingViewModel, AppCompatActivity activity) {
        this.noteEditingViewModel = noteEditingViewModel;
        this.activity = activity;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (isMenuViewIdEqualsToConcludeNoteMenuItem(item)) {
            noteEditingViewModel.concludeNoteEditing();

            return true;
        } else if (isMenuViewIdEqualsToEditNoteMenuItem(item)) {
            noteEditingViewModel.manipulateNote();

            return true;
        } else if (isMenuViewIdEqualsToDeleteNoteMenuItem(item)) {
            noteEditingViewModel.deleteNote();

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        MainActivityStartingAdapter.startMainActivity(activity);
    }
}
