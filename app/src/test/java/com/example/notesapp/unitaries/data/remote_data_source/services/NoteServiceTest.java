package com.example.notesapp.unitaries.data.remote_data_source.services;

import static com.example.notesapp.concerns.WebServerMock.WEB_SERVER_MOCK_URL;
import static com.example.notesapp.constants.data.NoteDataConstants.LIST_OF_NOTES_JSON;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_CREATED_AT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_UPDATED_AT;
import static com.example.notesapp.constants.data.UserDataConstants.USER_ID;
import static org.junit.Assert.assertEquals;
import static java.net.HttpURLConnection.HTTP_OK;

import com.example.notesapp.concerns.WebServerMock;
import com.example.notesapp.data.remote_data_source.gateways.NoteGateway;
import com.example.notesapp.data.remote_data_source.services.NoteService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.mockwebserver.MockWebServer;

public class NoteServiceTest {
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
        WebServerMock.enqueueResponse(LIST_OF_NOTES_JSON, HTTP_OK);

        NoteGateway noteGateway = NoteService.getInstance(WEB_SERVER_MOCK_URL);

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
}
