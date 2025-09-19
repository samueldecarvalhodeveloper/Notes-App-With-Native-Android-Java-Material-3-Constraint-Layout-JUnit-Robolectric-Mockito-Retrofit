package com.example.notesapp.data.local_data_source.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.notesapp.data.external_models.User;

@Entity
public class UserEntity {
    @PrimaryKey()
    public int id;
    public String username;

    public UserEntity(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public User getUserExternalModel() {
        return new User(id, username);
    }
}