package com.example.notesapp.unitaries.data.local_data_source.entities;

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

public class NoteEntityTest {
    @Test
    public void testIfEntityDescribesHowNoteEntityShouldBeUsed() {
        NoteEntity noteEntity = new NoteEntity(NOTE_ID, NOTE_TITLE, NOTE_BODY, NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID);

        assertEquals(noteEntity.id, NOTE_ID);
        assertEquals(noteEntity.title, NOTE_TITLE);
        assertEquals(noteEntity.body, NOTE_BODY);
        assertEquals(noteEntity.createdAt, NOTE_CREATED_AT);
        assertEquals(noteEntity.updatedAt, NOTE_UPDATED_AT);
        assertEquals(noteEntity.userId, USER_ID);
    }

    @Test
    public void testIfMethodGetNoteExternalModelReturnsCastedExternalModel() {
        NoteEntity noteEntity = new NoteEntity(NOTE_ID, NOTE_TITLE, NOTE_BODY, NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID);

        Note note = noteEntity.getNoteExternalModel();

        assertEquals(note.id, NOTE_ID);
        assertEquals(note.title, NOTE_TITLE);
        assertEquals(note.body, NOTE_BODY);
        assertEquals(note.createdAt, NOTE_CREATED_AT);
        assertEquals(note.updatedAt, NOTE_UPDATED_AT);
        assertEquals(note.userId, USER_ID);
    }
}