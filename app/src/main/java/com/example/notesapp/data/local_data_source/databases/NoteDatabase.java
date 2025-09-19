package com.example.notesapp.data.local_data_source.databases;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_DATABASE_NAME;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_DATABASE_VERSION;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notesapp.data.local_data_source.data_access_objects.NoteDataAccessObject;
import com.example.notesapp.data.local_data_source.entities.NoteEntity;

@Database(entities = {NoteEntity.class}, version = NOTE_DATABASE_VERSION)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance = null;

    public static NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context, NoteDatabase.class, NOTE_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }

    public abstract NoteDataAccessObject getDataAccessObject();
}
