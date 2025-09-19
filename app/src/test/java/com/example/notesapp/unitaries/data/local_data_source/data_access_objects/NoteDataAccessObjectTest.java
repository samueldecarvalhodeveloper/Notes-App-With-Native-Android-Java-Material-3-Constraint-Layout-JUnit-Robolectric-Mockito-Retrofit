package com.example.notesapp.unitaries.data.local_data_source.data_access_objects;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_CREATED_AT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ENTITY_OBJECT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ENTITY_WITH_WRONG_DATA_OBJECT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_UPDATED_AT;
import static com.example.notesapp.constants.data.UserDataConstants.USER_ID;
import static org.junit.Assert.assertThrows;

import com.example.notesapp.concerns.RoomDatabaseFactory;
import com.example.notesapp.data.local_data_source.data_access_objects.NoteDataAccessObject;
import com.example.notesapp.data.local_data_source.databases.NoteDatabase;
import com.example.notesapp.data.local_data_source.entities.NoteEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import io.reactivex.rxjava3.observers.TestObserver;

@RunWith(RobolectricTestRunner.class)
public class NoteDataAccessObjectTest {
    private NoteDataAccessObject noteDataAccessObject;

    @Before
    public void beforeEach() {
        NoteDatabase noteDatabase = RoomDatabaseFactory.getInstance(NoteDatabase.class);

        noteDataAccessObject = noteDatabase.getDataAccessObject();
    }

    @Test
    public void testIfMethodGetNotesReturnsAllNotesFromDatabase() {
        TestObserver<List<NoteEntity>> notesFromDatabase = noteDataAccessObject.getNotes().test();

        notesFromDatabase.assertValue(List::isEmpty);
    }

    @Test
    public void testIfMethodGetNoteReturnsNoteFromDatabase() {
        noteDataAccessObject.createNote(NOTE_ENTITY_OBJECT).blockingAwait();

        TestObserver<NoteEntity> noteFromDatabase = noteDataAccessObject.getNote(NOTE_ID).test();

        noteFromDatabase.assertValue(noteEntity ->
                noteEntity.id == NOTE_ID &&
                        noteEntity.title.equals(NOTE_TITLE) &&
                        noteEntity.body.equals(NOTE_BODY) &&
                        noteEntity.createdAt == NOTE_CREATED_AT &&
                        noteEntity.updatedAt == NOTE_UPDATED_AT &&
                        noteEntity.userId == USER_ID
        );
    }

    @Test
    public void testIfMethodCreateNoteCreatesNoteOnDatabase() {
        noteDataAccessObject.createNote(NOTE_ENTITY_OBJECT).blockingAwait();

        TestObserver<NoteEntity> createdNote = noteDataAccessObject.getNote(NOTE_ID).test();

        createdNote.assertValue(noteEntity ->
                noteEntity.id == NOTE_ID &&
                        noteEntity.title.equals(NOTE_TITLE) &&
                        noteEntity.body.equals(NOTE_BODY) &&
                        noteEntity.createdAt == NOTE_CREATED_AT &&
                        noteEntity.updatedAt == NOTE_UPDATED_AT &&
                        noteEntity.userId == USER_ID
        );
    }

    @Test
    public void testIfMethodUpdateNoteUpdatesNoteOnDatabase() {
        noteDataAccessObject.createNote(NOTE_ENTITY_WITH_WRONG_DATA_OBJECT).blockingAwait();

        noteDataAccessObject.updateNote(NOTE_ENTITY_OBJECT).blockingAwait();

        TestObserver<NoteEntity> updatedNoteFromDatabase =
                noteDataAccessObject.getNote(NOTE_ID).test();

        updatedNoteFromDatabase.assertValue(noteEntity ->
                noteEntity.id == NOTE_ID &&
                        noteEntity.title.equals(NOTE_TITLE) &&
                        noteEntity.body.equals(NOTE_BODY) &&
                        noteEntity.createdAt == NOTE_CREATED_AT &&
                        noteEntity.updatedAt == NOTE_UPDATED_AT &&
                        noteEntity.userId == USER_ID
        );
    }

    @Test
    public void testIfMethodDeleteNoteDeletesNoteOnDatabase() {
        noteDataAccessObject.createNote(NOTE_ENTITY_OBJECT).blockingAwait();

        noteDataAccessObject.deleteNote(NOTE_ID).blockingAwait();

        assertThrows(AssertionError.class, () ->
                noteDataAccessObject.getNote(NOTE_ID).test().assertValue(noteEntity -> true)
        );
    }
}
