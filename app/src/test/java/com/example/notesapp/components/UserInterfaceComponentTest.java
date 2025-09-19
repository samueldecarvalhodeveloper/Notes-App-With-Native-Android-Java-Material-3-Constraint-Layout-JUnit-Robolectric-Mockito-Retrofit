package com.example.notesapp.components;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_CREATED_AT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_DATABASE_NAME;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ENTITY_OBJECT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_UPDATED_AT;
import static com.example.notesapp.constants.data.UserDataConstants.USER_DATABASE_NAME;
import static com.example.notesapp.constants.data.UserDataConstants.USER_ENTITY_OBJECT;
import static com.example.notesapp.constants.data.UserDataConstants.USER_ID;
import static com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME;
import static com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_ID_INTENT_KEY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Intent;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.concerns.RoomDatabaseFactory;
import com.example.notesapp.data.local_data_source.data_access_objects.NoteDataAccessObject;
import com.example.notesapp.data.local_data_source.data_access_objects.UserDataAccessObject;
import com.example.notesapp.data.local_data_source.databases.NoteDatabase;
import com.example.notesapp.data.local_data_source.databases.UserDatabase;
import com.example.notesapp.data.local_data_source.entities.NoteEntity;
import com.example.notesapp.data.local_data_source.entities.UserEntity;
import com.example.notesapp.user_interface.activities.MainActivity;
import com.example.notesapp.user_interface.activities.NoteActivity;
import com.example.notesapp.user_interface.activities.SignInActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.fakes.RoboMenuItem;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;

@RunWith(RobolectricTestRunner.class)
public class UserInterfaceComponentTest {
    private UserDataAccessObject userDataAccessObject;
    private NoteDataAccessObject noteDataAccessObject;

    @Before
    public void beforeEach() {
        UserDatabase userDatabase = RoomDatabaseFactory.getInstance(UserDatabase.class, USER_DATABASE_NAME);
        NoteDatabase noteDatabase = RoomDatabaseFactory.getInstance(NoteDatabase.class, NOTE_DATABASE_NAME);

        userDataAccessObject = userDatabase.getDataAccessObject();
        noteDataAccessObject = noteDatabase.getDataAccessObject();

        userDataAccessObject.createUser(USER_ENTITY_OBJECT).blockingSubscribe();
        noteDataAccessObject.createNote(NOTE_ENTITY_OBJECT).blockingSubscribe();

        userDataAccessObject = mock(UserDataAccessObject.class);
        noteDataAccessObject = mock(NoteDataAccessObject.class);
    }

    @Test
    public void creatingUser() {
        when(userDataAccessObject.getUsers())
                .thenReturn(Single.just(List.of(USER_ENTITY_OBJECT)));

        try (ActivityController<SignInActivity> controller = Robolectric.buildActivity(SignInActivity.class)) {
            controller.setup();

            SignInActivity activity = controller.get();

            Button createUserButton = activity.findViewById(R.id.create_user_button);
            TextInputEditText usernameTextInput = activity.findViewById(R.id.username_text_input);

            usernameTextInput.setText(USER_USERNAME);

            createUserButton.performClick();

            TestObserver<List<UserEntity>> listOfUsersFromDatabase = userDataAccessObject.getUsers().test();

            listOfUsersFromDatabase.assertValue(userEntities -> !userEntities.isEmpty());
        }
    }

    @Test
    public void listingNotes() {
        RecyclerView notesRecyclerView = mock(RecyclerView.class);

        when(notesRecyclerView.getLayoutManager())
                .thenReturn(mock(RecyclerView.LayoutManager.class));
        when(notesRecyclerView.getLayoutManager().getChildAt(0))
                .thenReturn(mock(View.class));

        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup();


            View noteFromDatabaseListItem = notesRecyclerView.getLayoutManager().getChildAt(0);

            assertNotNull(noteFromDatabaseListItem);
        }
    }

    @Test
    public void fetchingNoteFromDatabase() {
        Intent noteActivityIntent =
                new Intent(RuntimeEnvironment.getApplication(), NoteActivity.class);

        noteActivityIntent.putExtra(NOTE_ID_INTENT_KEY, NOTE_ID);

        try (ActivityController<NoteActivity> controller = Robolectric.buildActivity(NoteActivity.class, noteActivityIntent)) {
            controller.setup();

            NoteActivity activity = mock(NoteActivity.class);

            when(activity.findViewById(R.id.note_title_text_input))
                    .thenReturn(mock(TextInputEditText.class));
            when(((TextInputEditText) activity.findViewById(R.id.note_title_text_input))
                    .getText())
                    .thenReturn(mock(Editable.class));
            when(((TextInputEditText) activity.findViewById(R.id.note_title_text_input))
                    .getText().toString())
                    .thenReturn(NOTE_TITLE);
            when(activity.findViewById(R.id.note_body_text_input))
                    .thenReturn(mock(TextInputEditText.class));
            when(((TextInputEditText) activity.findViewById(R.id.note_body_text_input))
                    .getText())
                    .thenReturn(mock(Editable.class));
            when(((TextInputEditText) activity.findViewById(R.id.note_body_text_input))
                    .getText().toString())
                    .thenReturn(NOTE_BODY);

            TextView noteTitleTextInput = activity.findViewById(R.id.note_title_text_input);

            TextView noteBodyTextInput = activity.findViewById(R.id.note_body_text_input);

            assertEquals(NOTE_TITLE, noteTitleTextInput.getText().toString());
            assertEquals(NOTE_BODY, noteBodyTextInput.getText().toString());
        }
    }

    @Test
    public void creatingNote() {
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

    @Test
    public void updatingNote() {
        when(noteDataAccessObject.getNote(NOTE_ID))
                .thenReturn(Single.just(NOTE_ENTITY_OBJECT));

        Intent noteActivityIntent =
                new Intent(RuntimeEnvironment.getApplication(), NoteActivity.class);

        noteActivityIntent.putExtra(NOTE_ID_INTENT_KEY, NOTE_ID);

        try (ActivityController<NoteActivity> controller = Robolectric.buildActivity(NoteActivity.class, noteActivityIntent)) {
            controller.setup();

            NoteActivity activity = controller.get();

            MaterialToolbar toolbar = activity.findViewById(R.id.note_activity_toolbar);

            RoboMenuItem toolbarEditNoteMenuItem =
                    new RoboMenuItem(toolbar.getMenu().getItem(1).getItemId());
            RoboMenuItem toolbarConcludeNoteMenuItem =
                    new RoboMenuItem(toolbar.getMenu().getItem(1).getItemId());

            toolbarEditNoteMenuItem.click();

            TextInputEditText noteTitleTextInput = mock(TextInputEditText.class);
            TextInputEditText noteBodyTextInput = mock(TextInputEditText.class);

            noteTitleTextInput.setText(NOTE_TITLE);
            noteBodyTextInput.setText(NOTE_BODY);

            toolbarConcludeNoteMenuItem.click();

            TestObserver<NoteEntity> listOfNotesFromDatabase = noteDataAccessObject.getNote(NOTE_ID).test();

            listOfNotesFromDatabase.assertValue(noteEntity ->
                    noteEntity.id == NOTE_ID &&
                            noteEntity.title.equals(NOTE_TITLE) &&
                            noteEntity.body.equals(NOTE_BODY) &&
                            noteEntity.createdAt == NOTE_CREATED_AT &&
                            noteEntity.updatedAt == NOTE_UPDATED_AT &&
                            noteEntity.userId == USER_ID
            );
        }
    }

    @Test
    public void deletingNote() {
        when(noteDataAccessObject.getNotes())
                .thenReturn(Single.just(List.of()));

        Intent noteActivityIntent =
                new Intent(RuntimeEnvironment.getApplication(), NoteActivity.class);

        noteActivityIntent.putExtra(NOTE_ID_INTENT_KEY, NOTE_ID);

        try (ActivityController<NoteActivity> controller = Robolectric.buildActivity(NoteActivity.class, noteActivityIntent)) {
            controller.setup();

            NoteActivity activity = controller.get();

            MaterialToolbar toolbar = activity.findViewById(R.id.note_activity_toolbar);

            RoboMenuItem toolbarDeleteNoteMenuItem =
                    new RoboMenuItem(toolbar.getMenu().getItem(toolbar.getMenu().size() - 1).getItemId());

            toolbarDeleteNoteMenuItem.click();

            TestObserver<List<NoteEntity>> listOfNotesFromDatabase = noteDataAccessObject.getNotes().test();

            listOfNotesFromDatabase.assertValue(List::isEmpty);
        }
    }
}
