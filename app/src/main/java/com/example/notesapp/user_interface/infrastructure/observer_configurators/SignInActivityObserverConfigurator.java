package com.example.notesapp.user_interface.infrastructure.observer_configurators;

import static androidx.core.content.ContextCompat.getString;

import com.example.notesapp.R;
import com.example.notesapp.user_interface.activities.SignInActivity;
import com.example.notesapp.user_interface.infrastructure.activity_starting_adapters.MainActivityStartingAdapter;
import com.example.notesapp.user_interface.view_models.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignInActivityObserverConfigurator {
    private SignInActivityObserverConfigurator() {
    }

    public static void setObservers(
            TextInputLayout usernameTextInputLayout,
            TextInputEditText usernameTextInput,
            UserViewModel userViewModel,
            SignInActivity activity
    ) {
        userViewModel.getIsUserAlreadyCreated().observe(activity, isUserAlreadyCreated -> {
            if (isUserAlreadyCreated) {
                MainActivityStartingAdapter.startMainActivity(activity);
            }
        });

        userViewModel.getIsUserCreationSuccessful().observe(activity, isUserCreationSuccessful -> {
            if (isUserCreationSuccessful) {
                MainActivityStartingAdapter.startMainActivity(activity);
            }
        });

        userViewModel.getIsUserUsernameInvalid().observe(activity, isUserUsernameInvalid -> {
            if (isUserUsernameInvalid) {
                String errorMessage =
                        getString(activity, R.string.not_valid_username_error_message);

                usernameTextInputLayout.setError(errorMessage);
                usernameTextInput.setError(errorMessage);
                usernameTextInputLayout.setErrorEnabled(true);
            } else {
                usernameTextInputLayout.setErrorEnabled(false);
            }
        });

        userViewModel.getIsInternetErrorRisen().observe(activity, isInternetErrorRisen -> {
            if (isInternetErrorRisen) {
                String errorMessage =
                        getString(activity, R.string.no_internet_connection_error_message);

                usernameTextInputLayout.setError(errorMessage);
                usernameTextInput.setError(errorMessage);
                usernameTextInputLayout.setErrorEnabled(true);
            } else {
                usernameTextInputLayout.setErrorEnabled(false);
            }
        });
    }
}
