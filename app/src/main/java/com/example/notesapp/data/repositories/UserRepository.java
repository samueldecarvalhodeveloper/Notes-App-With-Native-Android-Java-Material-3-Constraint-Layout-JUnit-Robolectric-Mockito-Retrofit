package com.example.notesapp.data.repositories;

import com.example.notesapp.data.external_models.User;
import com.example.notesapp.data.local_data_source.data_access_objects.UserDataAccessObject;
import com.example.notesapp.data.local_data_source.entities.UserEntity;
import com.example.notesapp.data.remote_data_source.gateways.UserGateway;
import com.example.notesapp.data.remote_data_source.models.UserModel;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class UserRepository {
    private final UserGateway userGateway;
    private final UserDataAccessObject userDataAccessObject;

    @Inject
    public UserRepository(UserGateway userGateway, UserDataAccessObject userDataAccessObject) {
        this.userGateway = userGateway;
        this.userDataAccessObject = userDataAccessObject;
    }

    public Single<User> getUser() {
        return userDataAccessObject.getUsers()
                .flatMap(userEntities -> Single.just(userEntities.get(0)))
                .map(UserEntity::getUserExternalModel);
    }

    public Single<User> getCreatedUser(String username) {
        UserModel userDataTransferObject = new UserModel(username);

        return userGateway.createUser(userDataTransferObject)
                .doOnSuccess(userModel ->
                        userDataAccessObject.createUser(userModel.getUserEntity()).subscribe()
                );
    }
}
