package com.example.notesapp.data.external_models;

import com.example.notesapp.data.local_data_source.entities.NoteEntity;

public class Note {
    public final int id;
    public final String title;
    public final String body;
    public final int createdAt;
    public final int updatedAt;
    public final int userId;

    public Note(int id, String title, String body, int createdAt, int updatedAt, int userId) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
    }

    public NoteEntity getNoteEntity() {
        return new NoteEntity(id, title, body, createdAt, updatedAt, userId);
    }
}