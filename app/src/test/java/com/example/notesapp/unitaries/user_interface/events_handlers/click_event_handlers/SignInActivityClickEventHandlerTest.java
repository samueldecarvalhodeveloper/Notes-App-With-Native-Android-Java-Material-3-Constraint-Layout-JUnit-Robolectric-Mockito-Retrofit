package com.example.notesapp.unitaries.user_interface.events_handlers.click_event_handlers;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.text.Editable;
import android.view.View;

import com.example.notesapp.R;
import com.example.notesapp.data.local_data_source.data_access_objects.UserDataAccessObject;
import com.example.notesapp.data.repositories.UserRepository;
import com.example.notesapp.user_interface.events_handlers.click_event_handlers.SignInActivityClickEventHandler;
import com.example.notesapp.user_interface.view_models.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import io.reactivex.rxjava3.core.Single;

@RunWith(RobolectricTestRunner.class)
public class SignInActivityClickEventHandlerTest {
    private View view;
    private TextInputEditText usernameTextInput;
    private UserViewModel userViewModel;

    @Before
    public void beforeEach() {
        UserRepository userRepository = mock();
        view = mock(View.class);
        usernameTextInput = mock(TextInputEditText.class);

        when(userRepository.getUser())
                .thenReturn(Single.error(new Exception()));

        userViewModel = new UserViewModel(userRepository);
    }

    @Test
    public void testIfMethodOnClickDispatchesUserCreationIfViewIdEqualsToCreateUserButton() {
        when(view.getId()).thenReturn(R.id.create_user_button);

        when(usernameTextInput.getText()).thenReturn(mock(Editable.class));
        when(usernameTextInput.getText().toString()).thenReturn("");

        SignInActivityClickEventHandler signInActivityClickEventHandler =
                new SignInActivityClickEventHandler(usernameTextInput, userViewModel);

        signInActivityClickEventHandler.onClick(view);

        boolean isUserUsernameInvalid = userViewModel.getIsUserUsernameInvalid().getValue();

        assertTrue(isUserUsernameInvalid);
    }
}
