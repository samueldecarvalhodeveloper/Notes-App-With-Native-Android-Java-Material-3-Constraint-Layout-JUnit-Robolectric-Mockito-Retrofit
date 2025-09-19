package com.example.notesapp.user_interface.dependency_injection.modules;

import android.content.Context;

import com.example.notesapp.data.local_data_source.data_access_objects.UserDataAccessObject;
import com.example.notesapp.data.local_data_source.databases.UserDatabase;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class UserDataAccessObjectModule {
    @Provides
    public UserDataAccessObject provideUserDataAccessObject(@ApplicationContext Context context) {
        return UserDatabase.getInstance(context).getDataAccessObject();
    }
}
