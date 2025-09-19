package com.example.notesapp.user_interface.events_handlers.click_event_handlers;

import static com.example.notesapp.user_interface.infrastructure.specifications.UserInterfaceSpecifications.isViewIdEqualsToCreateUserButton;

import android.view.View;
import android.view.View.OnClickListener;

import com.example.notesapp.user_interface.view_models.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class SignInActivityClickEventHandler implements OnClickListener {
    private final TextInputEditText usernameTextInput;
    private final UserViewModel userViewModel;

    public SignInActivityClickEventHandler(TextInputEditText usernameTextInput, UserViewModel userViewModel) {
        this.usernameTextInput = usernameTextInput;
        this.userViewModel = userViewModel;
    }

    @Override
    public void onClick(View view) {
        if (isViewIdEqualsToCreateUserButton(view)) {
            String username = usernameTextInput.getText().toString();

            userViewModel.createUser(username);
        }
    }
}
