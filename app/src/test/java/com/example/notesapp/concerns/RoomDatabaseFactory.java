package com.example.notesapp.concerns;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.test.core.app.ApplicationProvider;

public class RoomDatabaseFactory {
    private RoomDatabaseFactory() {
    }

    public static <T extends RoomDatabase> T getInstance(Class<T> roomDatabaseClass) {
        Context context = ApplicationProvider.getApplicationContext();

        return Room.inMemoryDatabaseBuilder(context, roomDatabaseClass)
                .allowMainThreadQueries()
                .build();
    }

    public static <T extends RoomDatabase> T getInstance(Class<T> roomDatabaseClass, String name) {
        Context context = ApplicationProvider.getApplicationContext();

        return Room.databaseBuilder(context, roomDatabaseClass, name)
                .allowMainThreadQueries()
                .build();
    }
}
