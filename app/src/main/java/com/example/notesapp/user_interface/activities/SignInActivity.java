package com.example.notesapp.user_interface.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.notesapp.databinding.ActivitySignInBinding;
import com.example.notesapp.user_interface.events_handlers.click_event_handlers.SignInActivityClickEventHandler;
import com.example.notesapp.user_interface.infrastructure.observer_configurators.SignInActivityObserverConfigurator;
import com.example.notesapp.user_interface.view_models.UserViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignInActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySignInBinding binding = ActivitySignInBinding.inflate(getLayoutInflater());
        ConstraintLayout rootLayout = binding.getRoot();

        setContentView(rootLayout);

        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        SignInActivityClickEventHandler signInActivityClickEventHandler =
                new SignInActivityClickEventHandler(binding.usernameTextInput, userViewModel);

        SignInActivityObserverConfigurator.setObservers(
                binding.usernameTextInputLayout,
                binding.usernameTextInput,
                userViewModel,
                this
        );

        binding.createUserButton.setOnClickListener(signInActivityClickEventHandler);
    }
}