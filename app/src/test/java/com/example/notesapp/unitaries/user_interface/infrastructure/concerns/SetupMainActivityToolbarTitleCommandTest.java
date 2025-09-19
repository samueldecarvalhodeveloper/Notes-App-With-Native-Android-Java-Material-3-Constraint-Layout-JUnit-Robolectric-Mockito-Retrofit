package com.example.notesapp.unitaries.user_interface.infrastructure.concerns;

import static com.example.notesapp.constants.data.UserDataConstants.USER_DATABASE_NAME;
import static com.example.notesapp.constants.data.UserDataConstants.USER_ENTITY_OBJECT;
import static com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME;
import static com.example.notesapp.constants.user_interface.UserInterfaceConstants.TOOLBAR_TITLE;
import static org.junit.Assert.assertEquals;

import com.example.notesapp.R;
import com.example.notesapp.concerns.RoomDatabaseFactory;
import com.example.notesapp.data.local_data_source.data_access_objects.UserDataAccessObject;
import com.example.notesapp.data.local_data_source.databases.UserDatabase;
import com.example.notesapp.user_interface.activities.MainActivity;
import com.google.android.material.appbar.MaterialToolbar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

@RunWith(RobolectricTestRunner.class)
public class SetupMainActivityToolbarTitleCommandTest {

    @Before
    public void beforeEach() {
        UserDatabase userDatabase = RoomDatabaseFactory.getInstance(UserDatabase.class, USER_DATABASE_NAME);

        UserDataAccessObject userDataAccessObject = userDatabase.getDataAccessObject();

        userDataAccessObject.createUser(USER_ENTITY_OBJECT).blockingSubscribe();
    }

    @Test
    public void testIfMethodExecuteSetsToolbarTitleWithUserUsername() {
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup();

            MainActivity activity = controller.get();

            String toolbarTitleWithUserUsername = activity.getString(R.string.toolbar_greetings_text);
            String toolbarTitle = ((MaterialToolbar) activity.findViewById(R.id.main_activity_toolbar)).getTitle().toString();

            assertEquals(TOOLBAR_TITLE(toolbarTitleWithUserUsername, USER_USERNAME), toolbarTitle);
        }
    }
}
