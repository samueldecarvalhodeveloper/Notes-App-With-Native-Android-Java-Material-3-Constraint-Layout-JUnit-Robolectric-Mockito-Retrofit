package com.example.notesapp.unitaries.data.local_data_source.data_access_objects;

import static com.example.notesapp.constants.data.UserDataConstants.USER_ENTITY_OBJECT;
import static com.example.notesapp.constants.data.UserDataConstants.USER_ID;
import static com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME;

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
public class UserDataAccessObjectTest {
    private UserDataAccessObject userDataAccessObject;

    @Before
    public void beforeEach() {
        UserDatabase userDatabase = RoomDatabaseFactory.getInstance(UserDatabase.class);

        userDataAccessObject = userDatabase.getDataAccessObject();
    }

    @Test
    public void testIfMethodGetUsersReturnsAllUsersFromDatabase() {
        TestObserver<List<UserEntity>> usersFromDatabase = userDataAccessObject.getUsers().test();

        usersFromDatabase.assertValue(List::isEmpty);
    }

    @Test
    public void testIfMethodCreateUserReturnsCreatesUserOnDatabase() {
        userDataAccessObject.createUser(USER_ENTITY_OBJECT).blockingAwait();

        userDataAccessObject.getUsers().test().assertValue(userEntities -> {
                    UserEntity createdUser = userEntities.get(0);

                    return createdUser.id == USER_ID && createdUser.username.equals(USER_USERNAME);
                }
        );
    }
}
