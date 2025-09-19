package com.example.notesapp.unitaries.data.local_data_source.databases;

import com.example.notesapp.concerns.RoomDatabaseFactory;
import com.example.notesapp.data.local_data_source.data_access_objects.UserDataAccessObject;
import com.example.notesapp.data.local_data_source.databases.UserDatabase;
import com.example.notesapp.data.local_data_source.entities.UserEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import io.reactivex.rxjava3.observers.TestObserver;

@RunWith(RobolectricTestRunner.class)
public class UserDatabaseTest {
    private UserDatabase userDatabase;

    @Before
    public void beforeEach() {
        userDatabase = RoomDatabaseFactory.getInstance(UserDatabase.class);
    }

    @Test
    public void testIfMethodGetDataAccessObjectReturnsUserDataAccessObjectImplementation() {
        UserDataAccessObject userDataAccessObject = userDatabase.getDataAccessObject();

        TestObserver<List<UserEntity>> usersFromDatabase = userDataAccessObject.getUsers().test();

        usersFromDatabase.assertValue(List::isEmpty);
    }
}