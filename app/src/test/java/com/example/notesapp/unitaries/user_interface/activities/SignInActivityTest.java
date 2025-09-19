package com.example.notesapp.unitaries.user_interface.activities;

import static com.example.notesapp.constants.data.UserDataConstants.USER_ENTITY_OBJECT;
import static com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.widget.Button;

import com.example.notesapp.R;
import com.example.notesapp.data.local_data_source.data_access_objects.UserDataAccessObject;
import com.example.notesapp.data.local_data_source.entities.UserEntity;
import com.example.notesapp.user_interface.activities.SignInActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;

@RunWith(RobolectricTestRunner.class)
public class SignInActivityTest {
    private UserDataAccessObject userDataAccessObject;

    @Before
    public void beforeEach() {
        userDataAccessObject = mock(UserDataAccessObject.class);
    }

    @Test
    public void testIfObserversAreSet() {
        try (ActivityController<SignInActivity> controller = Robolectric.buildActivity(SignInActivity.class)) {
            controller.setup();

            SignInActivity activity = controller.get();

            Button createUserButton = activity.findViewById(R.id.create_user_button);
            TextInputLayout usernameTextInputLayout = activity.findViewById(R.id.username_text_input_layout);

            createUserButton.performClick();

            assertTrue(usernameTextInputLayout.isErrorEnabled());
        }
    }

    @Test
    public void testIfClickEventListenerIsSetOnCreateUserButton() {
        when(userDataAccessObject.getUsers())
                .thenReturn(Single.just(List.of(USER_ENTITY_OBJECT)));

        try (ActivityController<SignInActivity> controller = Robolectric.buildActivity(SignInActivity.class)) {
            controller.setup();

            SignInActivity activity = controller.get();

            Button createUserButton = activity.findViewById(R.id.create_user_button);
            TextInputEditText usernameTextInput = activity.findViewById(R.id.username_text_input);

            usernameTextInput.setText(USER_USERNAME);

            createUserButton.performClick();

            TestObserver<List<UserEntity>> listOfUsersFromDatabase = userDataAccessObject.getUsers().test();

            listOfUsersFromDatabase.assertValue(userEntities -> !userEntities.isEmpty());
        }
    }
}
