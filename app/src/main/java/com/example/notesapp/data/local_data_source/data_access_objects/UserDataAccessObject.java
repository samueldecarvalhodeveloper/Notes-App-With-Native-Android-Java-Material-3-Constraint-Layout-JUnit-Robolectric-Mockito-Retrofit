package com.example.notesapp.data.local_data_source.data_access_objects;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.notesapp.data.local_data_source.entities.UserEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface UserDataAccessObject {
    @Query("SELECT * FROM UserEntity")
    Single<List<UserEntity>> getUsers();

    @Insert
    Completable createUser(UserEntity user);
}
