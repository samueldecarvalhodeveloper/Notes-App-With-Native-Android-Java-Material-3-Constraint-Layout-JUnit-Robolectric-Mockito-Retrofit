package com.example.notesapp.unitaries.user_interface.activities;

import static android.view.View.VISIBLE;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ENTITY_OBJECT;
import static com.example.notesapp.constants.data.UserDataConstants.USER_DATABASE_NAME;
import static com.example.notesapp.constants.data.UserDataConstants.USER_ENTITY_OBJECT;
import static com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME;
import static com.example.notesapp.constants.user_interface.UserInterfaceConstants.TOOLBAR_TITLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.widget.ProgressBar;

import com.example.notesapp.R;
import com.example.notesapp.concerns.RoomDatabaseFactory;
import com.example.notesapp.data.local_data_source.data_access_objects.NoteDataAccessObject;
import com.example.notesapp.data.local_data_source.data_access_objects.UserDataAccessObject;
import com.example.notesapp.data.local_data_source.databases.UserDatabase;
import com.example.notesapp.data.local_data_source.entities.NoteEntity;
import com.example.notesapp.user_interface.activities.MainActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    private NoteDataAccessObject noteDataAccessObject;

    @Before
    public void beforeEach() {
        UserDatabase userDatabase = RoomDatabaseFactory.getInstance(UserDatabase.class, USER_DATABASE_NAME);

        UserDataAccessObject userDataAccessObject = userDatabase.getDataAccessObject();
        noteDataAccessObject = mock(NoteDataAccessObject.class);

        userDataAccessObject.createUser(USER_ENTITY_OBJECT).blockingSubscribe();
    }

    @Test
    public void testIfToolbarTitleIsSetWithUserUsername() {
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup();

            MainActivity activity = mock(MainActivity.class);

            String defaultGreetingText =
                    controller.get().getText(R.string.toolbar_greetings_text).toString();

            when(activity.findViewById(R.id.main_activity_toolbar))
                    .thenReturn(mock(MaterialToolbar.class));
            when(((MaterialToolbar) activity.findViewById(R.id.main_activity_toolbar)).getTitle())
                    .thenReturn(mock(CharSequence.class));
            when(((MaterialToolbar) activity.findViewById(R.id.main_activity_toolbar)).getTitle().toString())
                    .thenReturn(TOOLBAR_TITLE(defaultGreetingText, USER_USERNAME));

            String toolbarTitle = ((MaterialToolbar)
                    activity.findViewById(R.id.main_activity_toolbar)).getTitle().toString();

            assertEquals(TOOLBAR_TITLE(defaultGreetingText, USER_USERNAME), toolbarTitle);
        }
    }

    @Test
    public void testIfLoadingFragmentIsDisplayed() {
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup();

            MainActivity activity = controller.get();

            ProgressBar loadingProgressBar = activity.findViewById(R.id.loading_progress_bar);

            assertNotNull(loadingProgressBar);
        }
    }

    @Test
    public void testIfObserversAreSet() {
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup();

            MainActivity activity = controller.get();

            ExtendedFloatingActionButton createNoteFloatingActionButton =
                    activity.findViewById(R.id.create_note_floating_action_button);

            assertEquals(VISIBLE, createNoteFloatingActionButton.getVisibility());
        }
    }

    @Test
    public void testIfClickEventListenerIsSetOnCreateNoteFloatingActionButton() {
        when(noteDataAccessObject.getNotes())
                .thenReturn(Single.just(List.of(NOTE_ENTITY_OBJECT)));

        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup();

            MainActivity activity = mock(MainActivity.class);

            when(activity.findViewById(R.id.create_note_floating_action_button))
                    .thenReturn(mock(ExtendedFloatingActionButton.class));

            ExtendedFloatingActionButton createNoteFloatingActionButton =
                    activity.findViewById(R.id.create_note_floating_action_button);

            createNoteFloatingActionButton.performClick();

            TestObserver<List<NoteEntity>> notesFromDatabase = noteDataAccessObject.getNotes().test();

            notesFromDatabase.assertValue(noteEntities -> !noteEntities.isEmpty());
        }
    }
}
