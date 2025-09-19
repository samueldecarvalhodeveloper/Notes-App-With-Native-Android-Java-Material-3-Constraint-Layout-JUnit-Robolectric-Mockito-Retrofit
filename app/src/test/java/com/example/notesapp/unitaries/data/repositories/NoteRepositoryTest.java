package com.example.notesapp.unitaries.data.repositories;

import static com.example.notesapp.concerns.WebServerMock.WEB_SERVER_MOCK_URL;
import static com.example.notesapp.constants.data.DomainAgnosticConstants.REQUEST_DELAY;
import static com.example.notesapp.constants.data.NoteDataConstants.LIST_OF_NOTES_JSON;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_CREATED_AT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ENTITY_OBJECT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ENTITY_WITH_WRONG_DATA_OBJECT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_JSON;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_UPDATED_AT;
import static com.example.notesapp.constants.data.UserDataConstants.USER_ID;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static java.net.HttpURLConnection.HTTP_OK;

import com.example.notesapp.concerns.RoomDatabaseFactory;
import com.example.notesapp.concerns.ThreadSleepingAdapter;
import com.example.notesapp.concerns.WebServerMock;
import com.example.notesapp.data.local_data_source.data_access_objects.NoteDataAccessObject;
import com.example.notesapp.data.local_data_source.databases.NoteDatabase;
import com.example.notesapp.data.local_data_source.entities.NoteEntity;
import com.example.notesapp.data.remote_data_source.gateways.NoteGateway;
import com.example.notesapp.data.remote_data_source.services.NoteService;
import com.example.notesapp.data.repositories.NoteRepository;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import io.reactivex.rxjava3.observers.TestObserver;

@RunWith(RobolectricTestRunner.class)
public class NoteRepositoryTest {
    private NoteDataAccessObject noteDataAccessObject;
    private NoteRepository noteRepository;

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
        NoteDatabase noteDatabase = RoomDatabaseFactory.getInstance(NoteDatabase.class);

        noteDataAccessObject = noteDatabase.getDataAccessObject();
        NoteGateway noteGateway = NoteService.getInstance(WEB_SERVER_MOCK_URL);

        noteRepository = new NoteRepository(noteGateway, noteDataAccessObject);
    }

    @Test
    public void testIfMethodFetchNotesFromServiceRetrievesUserNotesFromServiceAndStoresThemOnDatabase() {
        WebServerMock.enqueueResponse(LIST_OF_NOTES_JSON, HTTP_OK);

        noteDataAccessObject.createNote(NOTE_ENTITY_WITH_WRONG_DATA_OBJECT).blockingSubscribe();

        noteRepository.fetchNotesFromService(USER_ID).blockingSubscribe();

        ThreadSleepingAdapter.sleep(REQUEST_DELAY);

        TestObserver<List<NoteEntity>> notesFromDatabase = noteDataAccessObject.getNotes().test();

        notesFromDatabase.assertValue(noteEntities -> !noteEntities.isEmpty());
    }

    @Test
    public void testIfMethodGetNotesFetchesNotesFromDatabase() {
        TestObserver<List<NoteEntity>> notesFromDatabase = noteDataAccessObject.getNotes().test();

        notesFromDatabase.assertValue(List::isEmpty);
    }

    @Test
    public void testIfMethodGetNoteFetchesNoteFromDatabase() {
        noteDataAccessObject.createNote(NOTE_ENTITY_OBJECT).blockingSubscribe();

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
    public void testIfMethodGetCreatedNoteCreatesNotesOnServiceAndOnDatabase() {
        WebServerMock.enqueueResponse(NOTE_JSON, HTTP_CREATED);

        noteRepository.getCreatedNote(NOTE_TITLE, NOTE_BODY, USER_ID).blockingSubscribe();

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
    public void testIfMethodGetUpdatedNoteUpdatesNotesOnServiceAndOnDatabase() {
        WebServerMock.enqueueResponse(NOTE_JSON, HTTP_OK);

        noteDataAccessObject.createNote(NOTE_ENTITY_WITH_WRONG_DATA_OBJECT).blockingSubscribe();

        noteRepository.getUpdatedNote(NOTE_ID, NOTE_TITLE, NOTE_BODY, USER_ID).blockingSubscribe();

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
    public void testIfMethodDeleteNoteDeletesNotesOnServiceAndOnDatabase() {
        WebServerMock.enqueueResponse("", HTTP_NO_CONTENT);

        noteDataAccessObject.createNote(NOTE_ENTITY_OBJECT).blockingSubscribe();

        noteRepository.deleteNote(NOTE_ID, USER_ID).blockingSubscribe();

        TestObserver<List<NoteEntity>> notesFromDatabase = noteDataAccessObject.getNotes().test();

        notesFromDatabase.assertValue(List::isEmpty);
    }
}
