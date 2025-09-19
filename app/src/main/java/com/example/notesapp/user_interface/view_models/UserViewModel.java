package com.example.notesapp.user_interface.view_models;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.notesapp.data.external_models.User;
import com.example.notesapp.data.repositories.UserRepository;
import com.example.notesapp.user_interface.infrastructure.concerns.VerifyIfUserExistsCommand;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

@SuppressLint("CheckResult")
@HiltViewModel
public class UserViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final MutableLiveData<User> user = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isInternetErrorRisen = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isUserUsernameInvalid = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isUserCreationSuccessful = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isUserAlreadyCreated = new MutableLiveData<>(false);

    @Inject
    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;

        VerifyIfUserExistsCommand.execute(userRepository, user, isUserAlreadyCreated);
    }

    public LiveData<User> getUser() {
        if (!user.isInitialized()) {
            userRepository.getUser()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(user::setValue);
        }

        return user;
    }

    public void createUser(String username) {
        if (username.isEmpty()) {
            isUserUsernameInvalid.setValue(true);
        } else {
            isUserUsernameInvalid.setValue(false);

            userRepository.getCreatedUser(username)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            createdUser -> {
                                this.user.setValue(createdUser);

                                isUserCreationSuccessful.setValue(true);
                            }, throwable -> isInternetErrorRisen.setValue(true)
                    );
        }
    }

    public LiveData<Boolean> getIsInternetErrorRisen() {
        return isInternetErrorRisen;
    }

    public LiveData<Boolean> getIsUserUsernameInvalid() {
        return isUserUsernameInvalid;
    }

    public LiveData<Boolean> getIsUserCreationSuccessful() {
        return isUserCreationSuccessful;
    }

    public MutableLiveData<Boolean> getIsUserAlreadyCreated() {
        return isUserAlreadyCreated;
    }
}
