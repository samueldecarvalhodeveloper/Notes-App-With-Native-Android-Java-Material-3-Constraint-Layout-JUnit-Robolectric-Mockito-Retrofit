package com.example.notesapp.constants.data;

import com.example.notesapp.data.external_models.User;
import com.example.notesapp.data.local_data_source.entities.UserEntity;
import com.example.notesapp.data.remote_data_source.models.UserModel;

public class UserDataConstants {
    public static final int USER_DATABASE_VERSION = 1;

    public static final String USER_DATABASE_NAME = "user";

    public static final String USER_ROUTE = "/users/";

    public static final int USER_ID = 10;

    public static final String USER_USERNAME = "Samuel de Carvalho";

    public static final UserModel USER_MODEL_OBJECT = new UserModel(USER_USERNAME);

    public static final String USER_JSON = "{\"id\":" + USER_ID + ",\"username\":\"" + USER_USERNAME + "\"}";

    public static final UserEntity USER_ENTITY_OBJECT = new UserEntity(USER_ID, USER_USERNAME);

    public static final User USER_EXTERNAL_MODEL_OBJECT = new User(USER_ID, USER_USERNAME);

    private UserDataConstants() {
    }
}
