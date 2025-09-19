package com.example.notesapp.data.local_data_source.databases;

import static com.example.notesapp.constants.data.UserDataConstants.USER_DATABASE_NAME;
import static com.example.notesapp.constants.data.UserDataConstants.USER_DATABASE_VERSION;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notesapp.data.local_data_source.data_access_objects.UserDataAccessObject;
import com.example.notesapp.data.local_data_source.entities.UserEntity;

@Database(entities = {UserEntity.class}, version = USER_DATABASE_VERSION)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase instance = null;

    public static UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context, UserDatabase.class, USER_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public abstract UserDataAccessObject getDataAccessObject();
}