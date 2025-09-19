package com.example.notesapp.data.remote_data_source.gateways;

import static com.example.notesapp.constants.data.UserDataConstants.USER_ROUTE;

import com.example.notesapp.data.external_models.User;
import com.example.notesapp.data.remote_data_source.models.UserModel;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserGateway {
    @POST(USER_ROUTE)
    Single<User> createUser(@Body UserModel user);
}
