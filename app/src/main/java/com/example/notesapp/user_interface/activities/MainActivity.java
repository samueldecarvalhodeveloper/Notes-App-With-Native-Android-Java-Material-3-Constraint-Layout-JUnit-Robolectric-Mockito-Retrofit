package com.example.notesapp.user_interface.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.notesapp.R;
import com.example.notesapp.databinding.ActivityMainBinding;
import com.example.notesapp.user_interface.events_handlers.click_event_handlers.MainActivityClickEventHandler;
import com.example.notesapp.user_interface.infrastructure.concerns.SetupMainActivityToolbarTitleCommand;
import com.example.notesapp.user_interface.infrastructure.observer_configurators.MainActivityObserverConfigurator;
import com.example.notesapp.user_interface.infrastructure.start_fragment_on_container_command.StartLoadingFragmentOnContainerCommand;
import com.example.notesapp.user_interface.view_models.NotesListingViewModel;
import com.example.notesapp.user_interface.view_models.UserViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        ConstraintLayout rootLayout = binding.getRoot();

        setContentView(rootLayout);

        UserViewModel userViewModel =
                new ViewModelProvider(this).get(UserViewModel.class);
        NotesListingViewModel notesListingViewModel =
                new ViewModelProvider(this).get(NotesListingViewModel.class);

        MainActivityClickEventHandler mainActivityClickEventHandler = new MainActivityClickEventHandler(
                notesListingViewModel,
                userViewModel,
                this
        );

        SetupMainActivityToolbarTitleCommand.execute(userViewModel, binding, this);

        StartLoadingFragmentOnContainerCommand.execute(R.id.notes_fragment_layout, this);

        MainActivityObserverConfigurator.setObservers(
                binding.createNoteFloatingActionButton,
                userViewModel,
                notesListingViewModel,
                this
        );

        binding.createNoteFloatingActionButton.setOnClickListener(mainActivityClickEventHandler);
    }
}