package com.example.notesapp.user_interface.activities;

import static com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_ID_INTENT_KEY;
import static com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOT_EXISTING_NOTE_ID;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.notesapp.databinding.ActivityNoteBinding;
import com.example.notesapp.user_interface.events_handlers.menu_click_event_handlers.NoteActivityMenuClickEventHandler;
import com.example.notesapp.user_interface.infrastructure.observer_configurators.NoteActivityObserverConfigurator;
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityNoteBinding binding = ActivityNoteBinding.inflate(getLayoutInflater());
        ConstraintLayout rootLayout = binding.getRoot();

        setContentView(rootLayout);

        int noteId = getIntent().getIntExtra(NOTE_ID_INTENT_KEY, NOT_EXISTING_NOTE_ID);

        NoteEditingViewModel noteEditingViewModel =
                new ViewModelProvider(this).get(NoteEditingViewModel.class);

        NoteActivityMenuClickEventHandler noteActivityMenuClickEventHandler =
                new NoteActivityMenuClickEventHandler(noteEditingViewModel, this);

        NoteActivityObserverConfigurator.setObservers(noteId, binding.noteActivityToolbar, noteEditingViewModel, this);

        binding.noteActivityToolbar.setNavigationOnClickListener(noteActivityMenuClickEventHandler);
        binding.noteActivityToolbar.setOnMenuItemClickListener(noteActivityMenuClickEventHandler);
    }
}