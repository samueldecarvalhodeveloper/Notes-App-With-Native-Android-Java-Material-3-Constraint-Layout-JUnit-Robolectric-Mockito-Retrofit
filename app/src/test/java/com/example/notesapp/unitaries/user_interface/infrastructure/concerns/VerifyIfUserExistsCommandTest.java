package com.example.notesapp.unitaries.user_interface.infrastructure.concerns;

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

import androidx.lifecycle.MutableLiveData;

import com.example.notesapp.data.external_models.User;
import com.example.notesapp.data.repositories.UserRepository;
import com.example.notesapp.user_interface.infrastructure.concerns.VerifyIfUserExistsCommand;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import io.reactivex.rxjava3.core.Single;

@RunWith(RobolectricTestRunner.class)
public class VerifyIfUserExistsCommandTest {
    private UserRepository userRepository;

    @Before
    public void beforeEach() {
        userRepository = mock(UserRepository.class);
    }

    @Test
    public void testIfMethodExecuteVerifiesIfUserExistsOnDatabaseAndSetsUserStateAndIsUserAlreadyCreatedState() {
        MutableLiveData<User> userState = new MutableLiveData<>();
        MutableLiveData<Boolean> isUserAlreadyCreatedState = new MutableLiveData<>(false);

        when(userRepository.getUser())
                .thenReturn(Single.just(USER_EXTERNAL_MODEL_OBJECT));

        VerifyIfUserExistsCommand.execute(userRepository, userState, isUserAlreadyCreatedState);

        shadowOf(Looper.getMainLooper()).idle();

        User userFromDatabase = userState.getValue();
        boolean isUserAlreadyCreated = isUserAlreadyCreatedState.getValue();

        assertEquals(USER_ID, userFromDatabase.id);
        assertEquals(USER_USERNAME, userFromDatabase.username);
        assertTrue(isUserAlreadyCreated);
    }

    @Test
    public void testIfMethodExecuteTurnsIsUserAlreadyCreatedToFalseIfUserDoesNotExist() {
        MutableLiveData<User> userState = new MutableLiveData<>();
        MutableLiveData<Boolean> isUserAlreadyCreatedState = new MutableLiveData<>(false);

        when(userRepository.getUser())
                .thenReturn(Single.error(new Exception()));

        VerifyIfUserExistsCommand.execute(userRepository, userState, isUserAlreadyCreatedState);

        shadowOf(Looper.getMainLooper()).idle();

        boolean isUserAlreadyCreated = isUserAlreadyCreatedState.getValue();

        assertFalse(isUserAlreadyCreated);
    }
}
