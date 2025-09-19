package com.example.notesapp.user_interface.dependency_injection.modules;

import android.content.Context;

import com.example.notesapp.data.local_data_source.data_access_objects.NoteDataAccessObject;
import com.example.notesapp.data.local_data_source.databases.NoteDatabase;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class NoteDataAccessObjectModule {
    @Provides
    public NoteDataAccessObject provideNoteDataAccessObject(@ApplicationContext Context context) {
        return NoteDatabase.getInstance(context).getDataAccessObject();
    }
}
