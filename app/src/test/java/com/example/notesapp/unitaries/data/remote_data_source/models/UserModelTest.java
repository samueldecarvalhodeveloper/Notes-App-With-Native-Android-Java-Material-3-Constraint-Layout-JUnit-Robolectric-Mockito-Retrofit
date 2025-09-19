package com.example.notesapp.unitaries.data.remote_data_source.models;

import static com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME;
import static org.junit.Assert.assertEquals;

import com.example.notesapp.data.remote_data_source.models.UserModel;

import org.junit.Test;

public class UserModelTest {
    @Test
    public void testIfModelDescribesHowUserShouldHoldDataToTheService() {
        UserModel userModel = new UserModel(USER_USERNAME);

        assertEquals(USER_USERNAME, userModel.username);
    }
}