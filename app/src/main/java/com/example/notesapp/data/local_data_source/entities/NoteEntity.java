package com.example.notesapp.data.local_data_source.entities;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_CREATED_AT_COLUMN_NAME;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_UPDATED_AT_COLUMN_NAME;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_USER_ID_COLUMN_NAME;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.notesapp.data.external_models.Note;

@Entity
public class NoteEntity {
    @PrimaryKey()
    public int id;
    public String title;
    public String body;
    @ColumnInfo(name = NOTE_CREATED_AT_COLUMN_NAME)
    public int createdAt;
    @ColumnInfo(name = NOTE_UPDATED_AT_COLUMN_NAME)
    public int updatedAt;
    @ColumnInfo(name = NOTE_USER_ID_COLUMN_NAME)
    public int userId;

    public NoteEntity(int id, String title, String body, int createdAt, int updatedAt, int userId) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
    }

    public Note getNoteExternalModel() {
        return new Note(id, title, body, createdAt, updatedAt, userId);
    }
}