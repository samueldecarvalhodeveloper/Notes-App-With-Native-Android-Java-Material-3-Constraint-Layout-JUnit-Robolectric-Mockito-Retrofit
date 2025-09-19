package com.example.notesapp.user_interface.infrastructure.concerns;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.example.notesapp.data.external_models.User;
import com.example.notesapp.data.repositories.UserRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

@SuppressLint("CheckResult")
public class VerifyIfUserExistsCommand {
    private VerifyIfUserExistsCommand() {
    }

    public static void execute(
            UserRepository userRepository,
            MutableLiveData<User> userState,
            MutableLiveData<Boolean> isUserAlreadyCreatedState
    ) {
        userRepository.getUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                user -> {
                    userState.setValue(user);

                    isUserAlreadyCreatedState.setValue(true);
                },
                throwable -> isUserAlreadyCreatedState.setValue(false)
        );
    }
}