package com.example.notesapp.unitaries.data.external_models;

import static com.example.notesapp.constants.data.UserDataConstants.USER_ID;
import static com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME;
import static org.junit.Assert.assertEquals;

import com.example.notesapp.data.external_models.User;
import com.example.notesapp.data.local_data_source.entities.UserEntity;

import org.junit.Test;

public class UserTest {
    @Test
    public void testIfEntityDescribesHowUserShouldBeUsedByExternalDomains() {
        User user = new User(USER_ID, USER_USERNAME);

        assertEquals(USER_ID, user.id);
        assertEquals(USER_USERNAME, user.username);
    }

    @Test
    public void testIfMethodGetUserEntityReturnsCastedDatabaseEntity() {
        User user = new User(USER_ID, USER_USERNAME);

        UserEntity userEntity = user.getUserEntity();

        assertEquals(USER_ID, userEntity.id);
        assertEquals(USER_USERNAME, userEntity.username);
    }
}
