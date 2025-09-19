package com.example.notesapp.unitaries.user_interface.view_models;

import static com.example.notesapp.constants.data.UserDataConstants.USER_EXTERNAL_MODEL_OBJECT;
import static com.example.notesapp.constants.data.UserDataConstants.USER_ID;
import static com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.example.notesapp.data.external_models.User;
import com.example.notesapp.data.repositories.UserRepository;
import com.example.notesapp.user_interface.view_models.UserViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import io.reactivex.rxjava3.core.Single;

@RunWith(RobolectricTestRunner.class)
public class UserViewModelTest {
    private UserRepository userRepository;
    private UserViewModel userViewModel;

    @Test
    public void testIfVerifyIfUserExistsCommandTurnsIsUserAlreadyCreatedStateToTrueIfUserExistsAndSetsUserState() {
        userRepository = mock();

        when(userRepository.getUser())
                .thenReturn(Single.just(USER_EXTERNAL_MODEL_OBJECT));

        userViewModel = new UserViewModel(userRepository);

        LiveData<User> user = userViewModel.getUser();

        shadowOf(Looper.getMainLooper()).idle();

        User userFromDatabase = user.getValue();

        LiveData<Boolean> isUserAlreadyCreated = userViewModel.getIsUserAlreadyCreated();

        assertEquals(USER_ID, userFromDatabase.id);
        assertEquals(USER_USERNAME, userFromDatabase.username);

        assertTrue(isUserAlreadyCreated.getValue());
    }

    @Test
    public void testIfMethodGetUserReturnsStateOfStoredUserFromDatabase() {
        userRepository = mock(UserRepository.class);

        when(userRepository.getUser())
                .thenReturn(Single.error(new Exception()));

        userViewModel = new UserViewModel(userRepository);

        when(userRepository.getUser())
                .thenReturn(Single.just(USER_EXTERNAL_MODEL_OBJECT));

        LiveData<User> user = userViewModel.getUser();

        shadowOf(Looper.getMainLooper()).idle();

        User userFromDatabase = user.getValue();

        assertEquals(USER_ID, userFromDatabase.id);
        assertEquals(USER_USERNAME, userFromDatabase.username);
    }

    @Test
    public void testIfMethodCreateUserTurnsIsUserUsernameInvalidStateTrueIfUsernameIsEmpty() {
        userRepository = mock(UserRepository.class);

        when(userRepository.getUser())
                .thenReturn(Single.error(new Exception()));

        userViewModel = new UserViewModel(userRepository);

        userViewModel.createUser("");

        boolean isUserUsernameInvalid = userViewModel.getIsUserUsernameInvalid().getValue();

        assertTrue(isUserUsernameInvalid);
    }

    @Test
    public void testIfMethodCreateUserTurnsIsInternetErrorRisenStateTrueIfServiceAccessIsNotAvailable() {
        userRepository = mock(UserRepository.class);

        when(userRepository.getUser())
                .thenReturn(Single.error(new Exception()));

        userViewModel = new UserViewModel(userRepository);

        when(userRepository.getCreatedUser(USER_USERNAME))
                .thenReturn(Single.error(new Exception()));

        userViewModel.createUser(USER_USERNAME);

        shadowOf(Looper.getMainLooper()).idle();

        boolean isInternetErrorRisen = userViewModel.getIsInternetErrorRisen().getValue();

        assertTrue(isInternetErrorRisen);
    }

    @Test
    public void testIfMethodCreateUserCreatesUserUserInterfaceStateAndTurnsIsUserCreationSuccessfulStateToTrue() {
        userRepository = mock(UserRepository.class);

        when(userRepository.getUser())
                .thenReturn(Single.error(new Exception()));

        userViewModel = new UserViewModel(userRepository);

        when(userRepository.getCreatedUser(USER_USERNAME))
                .thenReturn(Single.just(USER_EXTERNAL_MODEL_OBJECT));

        userViewModel.createUser(USER_USERNAME);

        shadowOf(Looper.getMainLooper()).idle();

        boolean isUserUsernameInvalid = userViewModel.getIsUserUsernameInvalid().getValue();
        boolean isUserCreationSuccessful = userViewModel.getIsUserCreationSuccessful().getValue();

        User user = userViewModel.getUser().getValue();

        assertEquals(USER_ID, user.id);
        assertEquals(USER_USERNAME, user.username);

        assertTrue(isUserCreationSuccessful);
        assertFalse(isUserUsernameInvalid);
    }
}
