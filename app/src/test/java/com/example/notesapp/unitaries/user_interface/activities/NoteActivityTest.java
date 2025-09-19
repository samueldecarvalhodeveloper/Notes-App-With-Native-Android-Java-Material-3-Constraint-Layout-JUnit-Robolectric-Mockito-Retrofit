package com.example.notesapp.unitaries.user_interface.activities;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_DATABASE_NAME;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ENTITY_OBJECT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE;
import static com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_ID_INTENT_KEY;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Intent;
import android.widget.TextView;

import com.example.notesapp.R;
import com.example.notesapp.concerns.RoomDatabaseFactory;
import com.example.notesapp.data.local_data_source.data_access_objects.NoteDataAccessObject;
import com.example.notesapp.data.local_data_source.databases.NoteDatabase;
import com.example.notesapp.data.local_data_source.entities.NoteEntity;
import com.example.notesapp.user_interface.activities.NoteActivity;
import com.google.android.material.appbar.MaterialToolbar;

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
public class NoteActivityTest {
    private NoteDataAccessObject noteDataAccessObject;

    @Before
    public void beforeEach() {
        NoteDatabase noteDatabase = RoomDatabaseFactory.getInstance(NoteDatabase.class, NOTE_DATABASE_NAME);

        noteDataAccessObject = noteDatabase.getDataAccessObject();

        noteDataAccessObject.createNote(NOTE_ENTITY_OBJECT).blockingSubscribe();

        noteDataAccessObject = mock(NoteDataAccessObject.class);
    }

    @Test
    public void testIfObserversAreSet() {
        Intent noteActivityIntent =
                new Intent(RuntimeEnvironment.getApplication(), NoteActivity.class);

        noteActivityIntent.putExtra(NOTE_ID_INTENT_KEY, NOTE_ID);

        try (ActivityController<NoteActivity> controller = Robolectric.buildActivity(NoteActivity.class, noteActivityIntent)) {
            controller.setup();

            NoteActivity activity = controller.get();

            TextView noteTitleText = activity.findViewById(R.id.note_title_text);
            TextView noteBodyText = activity.findViewById(R.id.note_body_text);

            assertEquals(NOTE_TITLE, noteTitleText.getText());
            assertEquals(NOTE_BODY, noteBodyText.getText());
        }
    }

    @Test
    public void testIfToolbarNavigationButtonClickEventListenerIsSet() {
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
