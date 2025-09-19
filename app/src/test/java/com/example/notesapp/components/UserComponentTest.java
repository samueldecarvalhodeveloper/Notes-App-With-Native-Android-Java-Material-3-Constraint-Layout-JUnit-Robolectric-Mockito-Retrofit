package com.example.notesapp.components;

import static com.example.notesapp.concerns.WebServerMock.WEB_SERVER_MOCK_URL;
import static com.example.notesapp.constants.data.UserDataConstants.USER_ENTITY_OBJECT;
import static com.example.notesapp.constants.data.UserDataConstants.USER_ID;
import static com.example.notesapp.constants.data.UserDataConstants.USER_JSON;
import static com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME;
import static org.junit.Assert.assertEquals;
import static java.net.HttpURLConnection.HTTP_CREATED;

import com.example.notesapp.concerns.RoomDatabaseFactory;
import com.example.notesapp.concerns.WebServerMock;
import com.example.notesapp.data.external_models.User;
import com.example.notesapp.data.local_data_source.data_access_objects.UserDataAccessObject;
import com.example.notesapp.data.local_data_source.databases.UserDatabase;
import com.example.notesapp.data.remote_data_source.gateways.UserGateway;
import com.example.notesapp.data.remote_data_source.services.UserService;
import com.example.notesapp.data.repositories.UserRepository;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.observers.TestObserver;

@RunWith(RobolectricTestRunner.class)
public class UserComponentTest {
    private UserDataAccessObject userDataAccessObject;
    private UserRepository userRepository;

    @BeforeClass
    public static void beforeAll() {
        WebServerMock.startServer();
    }

    @AfterClass
    public static void afterAll() {
        WebServerMock.stopServer();
    }

    @Before
    public void beforeEach() {
        UserDatabase userDatabase = RoomDatabaseFactory.getInstance(UserDatabase.class);

        userDataAccessObject = userDatabase.getDataAccessObject();
        UserGateway userGateway = UserService.getInstance(WEB_SERVER_MOCK_URL);

        userRepository = new UserRepository(userGateway, userDataAccessObject);
    }

    @Test
    public void fetchingUserFromDatabase() {
        userDataAccessObject.createUser(USER_ENTITY_OBJECT).blockingSubscribe();

        TestObserver<User> userFromDatabase = userRepository.getUser().test();

        userFromDatabase.assertValue(user -> user.id == USER_ID && user.username.equals(USER_USERNAME));
    }

    @Test
    public void creatingUserOnServerAndOnDatabase() {
        WebServerMock.enqueueResponse(USER_JSON, HTTP_CREATED);

        AtomicInteger userCreatedOnServiceId = new AtomicInteger();
        AtomicReference<String> userCreatedOnServiceUsername = new AtomicReference<>();

        userRepository.getCreatedUser(USER_USERNAME).blockingSubscribe(userCreatedOnService -> {
            userCreatedOnServiceId.set(userCreatedOnService.id);
            userCreatedOnServiceUsername.set(userCreatedOnService.username);
        });

        assertEquals(USER_ID, userCreatedOnServiceId.get());
        assertEquals(USER_USERNAME, userCreatedOnServiceUsername.get());
    }
}
