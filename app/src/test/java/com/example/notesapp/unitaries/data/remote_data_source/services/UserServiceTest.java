package com.example.notesapp.unitaries.data.remote_data_source.services;

import static com.example.notesapp.concerns.WebServerMock.WEB_SERVER_MOCK_URL;
import static com.example.notesapp.constants.data.UserDataConstants.USER_ID;
import static com.example.notesapp.constants.data.UserDataConstants.USER_JSON;
import static com.example.notesapp.constants.data.UserDataConstants.USER_MODEL_OBJECT;
import static com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME;
import static org.junit.Assert.assertEquals;
import static java.net.HttpURLConnection.HTTP_CREATED;

import com.example.notesapp.concerns.WebServerMock;
import com.example.notesapp.data.remote_data_source.gateways.UserGateway;
import com.example.notesapp.data.remote_data_source.services.UserService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class UserServiceTest {
    @Before
    public void beforeEach() {
        WebServerMock.startServer();
    }

    @After
    public void afterEach() {
        WebServerMock.stopServer();
    }

    @Test
    public void testIfMethodGetInstanceReturnsWorkingInstance() {
        WebServerMock.enqueueResponse(USER_JSON, HTTP_CREATED);

        UserGateway userGateway = UserService.getInstance(WEB_SERVER_MOCK_URL);

        AtomicInteger userId = new AtomicInteger();
        AtomicReference<String> userUsername = new AtomicReference<>();

        userGateway.createUser(USER_MODEL_OBJECT).blockingSubscribe(user -> {
            userId.set(user.id);
            userUsername.set(user.username);
        });

        assertEquals(USER_ID, userId.get());
        assertEquals(USER_USERNAME, userUsername.get());
    }
}
