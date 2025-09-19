package com.example.notesapp.user_interface.infrastructure.concerns;

import static com.example.notesapp.constants.user_interface.UserInterfaceConstants.TOOLBAR_TITLE;

import androidx.lifecycle.LifecycleOwner;

import com.example.notesapp.databinding.ActivityMainBinding;
import com.example.notesapp.user_interface.view_models.UserViewModel;

public class SetupMainActivityToolbarTitleCommand {
    private SetupMainActivityToolbarTitleCommand() {
    }

    public static void execute(UserViewModel userViewModel, ActivityMainBinding binding, LifecycleOwner owner) {
        String toolbarTitleWithoutUserUsername = binding.mainActivityToolbar.getTitle().toString();

        userViewModel.getUser().observe(owner, user ->
                binding.mainActivityToolbar
                        .setTitle(TOOLBAR_TITLE(toolbarTitleWithoutUserUsername, user.username))
        );
    }
}
