package com.example.notesapp.constants.data;

import static com.example.notesapp.constants.data.UserDataConstants.USER_ID;

import com.example.notesapp.data.external_models.Note;
import com.example.notesapp.data.local_data_source.entities.NoteEntity;
import com.example.notesapp.data.remote_data_source.models.NoteModel;

public class NoteDataConstants {
    public static final int NOTE_DATABASE_VERSION = 1;

    public static final String NOTE_DATABASE_NAME = "note";

    public static final String NOTE_CREATED_AT_COLUMN_NAME = "created_at";

    public static final String NOTE_UPDATED_AT_COLUMN_NAME = "updated_at";

    public static final String NOTE_USER_ID_COLUMN_NAME = "user_id";

    public static final String NOTE_ROUTE = "/notes/{id}/";

    public static final String USER_ID_PATH_PARAMETER = "id";

    public static final String NOTE_SPECIFIC_ROUTE = "/notes/{id}/{note_id}/";

    public static final String NOTE_ID_PATH_PARAMETER = "note_id";

    public static final int NOTE_ID = 20;

    public static final String NOTE_TITLE = "Title";

    public static final String NOTE_BODY = "Body";

    public static final int NOTE_CREATED_AT = 0;

    public static final int NOTE_UPDATED_AT = 0;

    public static final NoteModel NOTE_MODEL_OBJECT = new NoteModel(NOTE_TITLE, NOTE_BODY);

    public static final String LIST_OF_NOTES_JSON = "[{\"id\":" + NOTE_ID +
            ",\"title\":\"" + NOTE_TITLE +
            "\",\"body\":\"" + NOTE_BODY +
            "\",\"createdAt\":" + NOTE_CREATED_AT +
            ",\"updatedAt\":" + NOTE_UPDATED_AT +
            ",\"userId\":" + USER_ID + "}]";

    public static final String NOTE_JSON = "{\"id\":" + NOTE_ID +
            ",\"title\":\"" + NOTE_TITLE +
            "\",\"body\":\"" + NOTE_BODY +
            "\",\"createdAt\":" + NOTE_CREATED_AT +
            ",\"updatedAt\":" + NOTE_UPDATED_AT +
            ",\"userId\":" + USER_ID + "}";

    public static final String NOTE_WITH_TITLE_AND_BODY_EMPTY_JSON = "{\"id\":" + NOTE_ID +
            ",\"title\":\"" +
            "\",\"body\":\"" +
            "\",\"createdAt\":" + NOTE_CREATED_AT +
            ",\"updatedAt\":" + NOTE_UPDATED_AT +
            ",\"userId\":" + USER_ID + "}";

    public static final NoteEntity NOTE_ENTITY_WITH_WRONG_DATA_OBJECT =
            new NoteEntity(NOTE_ID, "", "", NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID);

    public static final Note NOTE_OBJECT =
            new Note(NOTE_ID, NOTE_TITLE, NOTE_BODY, NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID);

    public static final Note NOTE_OBJECT_WITH_WRONG_TITLE =
            new Note(NOTE_ID, "", NOTE_BODY, NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID);

    public static final Note NOTE_OBJECT_WITH_WRONG_BODY =
            new Note(NOTE_ID, NOTE_TITLE, "", NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID);

    public static final NoteEntity NOTE_ENTITY_OBJECT =
            new NoteEntity(NOTE_ID, NOTE_TITLE, NOTE_BODY, NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID);

    public static final Note NOTE_WITH_EMPTY_DATA = 
            new Note(NOTE_ID, "", "", NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID);

    private NoteDataConstants() {
    }
}
