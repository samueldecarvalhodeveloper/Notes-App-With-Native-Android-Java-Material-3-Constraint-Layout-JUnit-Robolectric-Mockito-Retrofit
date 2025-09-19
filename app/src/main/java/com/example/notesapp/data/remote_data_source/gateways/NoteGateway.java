package com.example.notesapp.data.remote_data_source.gateways;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID_PATH_PARAMETER;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ROUTE;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_SPECIFIC_ROUTE;
import static com.example.notesapp.constants.data.NoteDataConstants.USER_ID_PATH_PARAMETER;

import com.example.notesapp.data.external_models.Note;
import com.example.notesapp.data.remote_data_source.models.NoteModel;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NoteGateway {
    @GET(NOTE_ROUTE)
    Single<List<Note>> getNotes(@Path(USER_ID_PATH_PARAMETER) int userId);

    @POST(NOTE_ROUTE)
    Single<Note> getCreatedNote(@Path(USER_ID_PATH_PARAMETER) int userId, @Body NoteModel note);

    @PATCH(NOTE_SPECIFIC_ROUTE)
    Single<Note> getUpdatedNote(
            @Path(NOTE_ID_PATH_PARAMETER) int noteId,
            @Path(USER_ID_PATH_PARAMETER) int userId,
            @Body NoteModel note
    );

    @DELETE(NOTE_SPECIFIC_ROUTE)
    Completable deleteNote(
            @Path(NOTE_ID_PATH_PARAMETER) int noteId,
            @Path(USER_ID_PATH_PARAMETER) int userId
    );
}
