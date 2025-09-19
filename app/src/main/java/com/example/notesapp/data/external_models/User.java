package com.example.notesapp.data.external_models;

import com.example.notesapp.data.local_data_source.entities.UserEntity;

public class User {
    public final int id;
    public final String username;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserEntity getUserEntity() {
        return new UserEntity(id, username);
    }
}
