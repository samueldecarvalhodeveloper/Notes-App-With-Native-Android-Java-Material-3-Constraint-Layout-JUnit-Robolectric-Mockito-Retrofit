package com.example.notesapp.unitaries.user_interface.infrastructure.observer_configurators;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;

import com.example.notesapp.user_interface.activities.SignInActivity;
import com.example.notesapp.user_interface.infrastructure.observer_configurators.SignInActivityObserverConfigurator;
import com.example.notesapp.user_interface.view_models.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class SignInActivityObserverConfiguratorTest {
    private UserViewModel userViewModel;
    private TextInputLayout usernameTextInputLayout;
    private TextInputEditText usernameTextInput;
    private SignInActivity signInActivity;

    @Before
    public void beforeEach() {
        userViewModel = mock(UserViewModel.class);
        usernameTextInputLayout = mock(TextInputLayout.class);
        usernameTextInput = mock(TextInputEditText.class);
        signInActivity = mock(SignInActivity.class);
    }

    @Test
    public void testIfMethodSetObserversSetsAllViewModelObservers() {
        when(signInActivity.getLifecycle())
                .thenReturn(mock(Lifecycle.class));
        when(userViewModel.getIsUserAlreadyCreated())
                .thenReturn(new MutableLiveData<>(false));
        when(userViewModel.getIsUserCreationSuccessful())
                .thenReturn(new MutableLiveData<>(false));
        when(userViewModel.getIsUserUsernameInvalid())
                .thenReturn(new MutableLiveData<>(false));
        when(userViewModel.getIsInternetErrorRisen())
                .thenReturn(new MutableLiveData<>(false));
        when(usernameTextInputLayout.isErrorEnabled())
                .thenReturn(false);

        SignInActivityObserverConfigurator.setObservers(
                usernameTextInputLayout,
                usernameTextInput,
                userViewModel,
                signInActivity
        );

        assertFalse(usernameTextInputLayout.isErrorEnabled());
    }
}
