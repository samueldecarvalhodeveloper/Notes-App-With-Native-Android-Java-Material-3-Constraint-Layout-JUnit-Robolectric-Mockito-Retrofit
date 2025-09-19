package com.example.notesapp.unitaries.data.remote_data_source.models;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE;
import static org.junit.Assert.assertEquals;

import com.example.notesapp.data.remote_data_source.models.NoteModel;

import org.junit.Test;

public class NoteModelTest {
    @Test
    public void testIfModelDescribesHowNoteShouldHoldDataToTheService() {
        NoteModel noteModel = new NoteModel(NOTE_TITLE, NOTE_BODY);

        assertEquals(NOTE_TITLE, noteModel.title);
        assertEquals(NOTE_BODY, noteModel.body);
    }
}