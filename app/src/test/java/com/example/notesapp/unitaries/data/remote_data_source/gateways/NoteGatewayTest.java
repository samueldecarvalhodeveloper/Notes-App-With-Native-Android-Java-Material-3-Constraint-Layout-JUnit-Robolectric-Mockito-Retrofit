package com.example.notesapp.unitaries.data.remote_data_source.gateways;

import static com.example.notesapp.concerns.WebServerMock.WEB_SERVER_MOCK_URL;
import static com.example.notesapp.constants.data.DomainAgnosticConstants.REQUEST_DELAY;
import static com.example.notesapp.constants.data.NoteDataConstants.LIST_OF_NOTES_JSON;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_CREATED_AT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_JSON;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_MODEL_OBJECT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_UPDATED_AT;
import static com.example.notesapp.constants.data.UserDataConstants.USER_ID;
import static org.junit.Assert.assertEquals;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static java.net.HttpURLConnection.HTTP_OK;

import com.example.notesapp.concerns.ThreadSleepingAdapter;
import com.example.notesapp.concerns.WebServerMock;
import com.example.notesapp.data.remote_data_source.gateways.NoteGateway;
import com.example.notesapp.data.remote_data_source.services.NoteService;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.observers.TestObserver;

public class NoteGatewayTest {
    private static NoteGateway noteGateway;

    @BeforeClass
    public static void beforeAll() {
        WebServerMock.startServer();

        noteGateway = NoteService.getInstance(WEB_SERVER_MOCK_URL);
    }

    @AfterClass
    public static void afterAll() {
        WebServerMock.stopServer();
    }

    @Test
    public void testIfMethodGetNotesSuccessFetchAllUserNotesFromService() {
        WebServerMock.enqueueResponse(LIST_OF_NOTES_JSON, HTTP_OK);

        AtomicInteger noteId = new AtomicInteger();
        AtomicReference<String> noteTitle = new AtomicReference<>();
        AtomicReference<String> noteBody = new AtomicReference<>();
        AtomicInteger noteCreatedAt = new AtomicInteger();
        AtomicInteger noteUpdatedAt = new AtomicInteger();
        AtomicInteger noteUserId = new AtomicInteger();

        noteGateway.getNotes(USER_ID).blockingSubscribe(notes -> {
            noteId.set(notes.get(0).id);
            noteTitle.set(notes.get(0).title);
            noteBody.set(notes.get(0).body);
            noteCreatedAt.set(notes.get(0).createdAt);
            noteUpdatedAt.set(notes.get(0).updatedAt);
            noteUserId.set(notes.get(0).userId);
        });

        assertEquals(NOTE_ID, noteId.get());
        assertEquals(NOTE_TITLE, noteTitle.get());
        assertEquals(NOTE_BODY, noteBody.get());
        assertEquals(NOTE_CREATED_AT, noteCreatedAt.get());
        assertEquals(NOTE_UPDATED_AT, noteUpdatedAt.get());
        assertEquals(USER_ID, noteUserId.get());
    }

    @Test
    public void testIfMethodGetCreatedNoteCreatesNotesOnService() {
        WebServerMock.enqueueResponse(NOTE_JSON, HTTP_CREATED);

        AtomicInteger createdNoteId = new AtomicInteger();
        AtomicReference<String> createdNoteTitle = new AtomicReference<>();
        AtomicReference<String> createdNoteBody = new AtomicReference<>();
        AtomicInteger createdNoteCreatedAt = new AtomicInteger();
        AtomicInteger createdNoteUpdatedAt = new AtomicInteger();
        AtomicInteger createdNoteUserId = new AtomicInteger();

        noteGateway.getCreatedNote(USER_ID, NOTE_MODEL_OBJECT)
                .blockingSubscribe(note -> {
                    createdNoteId.set(note.id);
                    createdNoteTitle.set(note.title);
                    createdNoteBody.set(note.body);
                    createdNoteCreatedAt.set(note.createdAt);
                    createdNoteUpdatedAt.set(note.updatedAt);
                    createdNoteUserId.set(note.userId);
                });

        assertEquals(NOTE_ID, createdNoteId.get());
        assertEquals(NOTE_TITLE, createdNoteTitle.get());
        assertEquals(NOTE_BODY, createdNoteBody.get());
        assertEquals(NOTE_CREATED_AT, createdNoteCreatedAt.get());
        assertEquals(NOTE_UPDATED_AT, createdNoteUpdatedAt.get());
        assertEquals(USER_ID, createdNoteUserId.get());
    }

    @Test
    public void testIfMethodGetUpdatedNoteUpdatesNotesOnService() {
        WebServerMock.enqueueResponse(NOTE_JSON, HTTP_OK);

        AtomicInteger updatedNoteId = new AtomicInteger();
        AtomicReference<String> updatedNoteTitle = new AtomicReference<>();
        AtomicReference<String> updatedNoteBody = new AtomicReference<>();
        AtomicInteger updatedNoteCreatedAt = new AtomicInteger();
        AtomicInteger updatedNoteUpdatedAt = new AtomicInteger();
        AtomicInteger updatedNoteUserId = new AtomicInteger();

        noteGateway.getUpdatedNote(NOTE_ID, USER_ID, NOTE_MODEL_OBJECT)
                .blockingSubscribe(note -> {
                    updatedNoteId.set(note.id);
                    updatedNoteTitle.set(note.title);
                    updatedNoteBody.set(note.body);
                    updatedNoteCreatedAt.set(note.createdAt);
                    updatedNoteUpdatedAt.set(note.updatedAt);
                    updatedNoteUserId.set(note.userId);
                });

        assertEquals(NOTE_ID, updatedNoteId.get());
        assertEquals(NOTE_TITLE, updatedNoteTitle.get());
        assertEquals(NOTE_BODY, updatedNoteBody.get());
        assertEquals(NOTE_CREATED_AT, updatedNoteCreatedAt.get());
        assertEquals(NOTE_UPDATED_AT, updatedNoteUpdatedAt.get());
        assertEquals(USER_ID, updatedNoteUserId.get());
    }

    @Test
    public void testIfMethodDeleteNoteDeletesNoteOnService() {
        WebServerMock.enqueueResponse("", HTTP_NO_CONTENT);

        TestObserver<Void> noteDeletingTask = noteGateway.deleteNote(NOTE_ID, USER_ID).test();

        ThreadSleepingAdapter.sleep(REQUEST_DELAY);

        noteDeletingTask.assertComplete();
    }
}
