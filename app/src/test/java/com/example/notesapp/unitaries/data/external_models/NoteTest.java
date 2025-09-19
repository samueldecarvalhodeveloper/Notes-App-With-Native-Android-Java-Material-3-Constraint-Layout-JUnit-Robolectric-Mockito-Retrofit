package com.example.notesapp.unitaries.data.external_models;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_CREATED_AT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_UPDATED_AT;
import static com.example.notesapp.constants.data.UserDataConstants.USER_ID;
import static org.junit.Assert.assertEquals;

import com.example.notesapp.data.external_models.Note;
import com.example.notesapp.data.local_data_source.entities.NoteEntity;

import org.junit.Test;

public class NoteTest {
    @Test
    public void testIfEntityDescribesHowNoteShouldBeUsedByExternalDomains() {
        Note note = new Note(NOTE_ID, NOTE_TITLE, NOTE_BODY, NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID);

        assertEquals(NOTE_ID, note.id);
        assertEquals(NOTE_TITLE, note.title);
        assertEquals(NOTE_BODY, note.body);
        assertEquals(NOTE_CREATED_AT, note.createdAt);
        assertEquals(NOTE_UPDATED_AT, note.updatedAt);
        assertEquals(USER_ID, note.userId);
    }

    @Test
    public void testIfMethodGetNoteEntityReturnsCastedDatabaseEntity() {
        Note note = new Note(NOTE_ID, NOTE_TITLE, NOTE_BODY, NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID);

        NoteEntity noteEntity = note.getNoteEntity();

        assertEquals(NOTE_ID, noteEntity.id);
        assertEquals(NOTE_TITLE, noteEntity.title);
        assertEquals(NOTE_BODY, noteEntity.body);
        assertEquals(NOTE_CREATED_AT, noteEntity.createdAt);
        assertEquals(NOTE_UPDATED_AT, noteEntity.updatedAt);
        assertEquals(USER_ID, noteEntity.userId);
    }
}