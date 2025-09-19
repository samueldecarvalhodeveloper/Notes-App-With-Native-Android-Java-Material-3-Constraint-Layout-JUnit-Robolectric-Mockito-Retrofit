package com.example.notesapp.unitaries.data.local_data_source.entities;

import static com.example.notesapp.constants.data.UserDataConstants.USER_ID;
import static com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME;
import static org.junit.Assert.assertEquals;

import androidx.room.Entity;

import com.example.notesapp.data.external_models.User;
import com.example.notesapp.data.local_data_source.entities.UserEntity;

import org.junit.Test;

public class UserEntityTest {
    @Test
    public void testIfEntityDescribesHowUserEntityShouldBeUsed() {
        UserEntity userEntity = new UserEntity(USER_ID, USER_USERNAME);

        assertEquals(userEntity.id, USER_ID);
        assertEquals(userEntity.username, USER_USERNAME);
    }

    @Test
    public void testIfMethodGetNoteExternalModelReturnsCastedExternalModel() {
        UserEntity userEntity = new UserEntity(USER_ID, USER_USERNAME);

        User user = userEntity.getUserExternalModel();

        assertEquals(user.id, USER_ID);
        assertEquals(user.username, USER_USERNAME);
    }
}