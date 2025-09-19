package com.example.notesapp.data.repositories;

import android.annotation.SuppressLint;

import com.example.notesapp.data.external_models.Note;
import com.example.notesapp.data.local_data_source.data_access_objects.NoteDataAccessObject;
import com.example.notesapp.data.local_data_source.entities.NoteEntity;
import com.example.notesapp.data.remote_data_source.gateways.NoteGateway;
import com.example.notesapp.data.remote_data_source.models.NoteModel;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@SuppressLint("CheckResult")
public class NoteRepository {
    private final NoteGateway noteGateway;
    private final NoteDataAccessObject noteDataAccessObject;

    @Inject
    public NoteRepository(NoteGateway noteGateway, NoteDataAccessObject noteDataAccessObject) {
        this.noteGateway = noteGateway;
        this.noteDataAccessObject = noteDataAccessObject;
    }

    public Completable fetchNotesFromService(int userId) {
        return Completable.fromAction(() -> noteGateway.getNotes(userId)
                .onErrorResumeWith(observer -> {
                })
                .subscribe(notes -> {
                    noteDataAccessObject.getNotes().blockingSubscribe(
                            noteEntities -> noteEntities
                                    .forEach(noteEntity ->
                                            noteDataAccessObject.deleteNote(noteEntity.id)
                                                    .blockingSubscribe()
                                    )
                    );

                    notes.forEach(note ->
                            noteDataAccessObject.createNote(note.getNoteEntity())
                                    .blockingSubscribe()
                    );
                })
        );
    }

    public Single<List<Note>> getNotes() {
        return noteDataAccessObject.getNotes().map(noteEntities ->
                noteEntities.stream()
                        .map(NoteEntity::getNoteExternalModel)
                        .collect(Collectors.toList())
        );
    }

    public Single<Note> getNote(int id) {
        return noteDataAccessObject.getNote(id).map(NoteEntity::getNoteExternalModel);
    }

    public Single<Note> getCreatedNote(String title, String body, int userId) {
        NoteModel noteDataTransferObject = new NoteModel(title, body);

        return noteGateway.getCreatedNote(userId, noteDataTransferObject)
                .doOnSuccess(note ->
                        noteDataAccessObject.createNote(note.getNoteEntity()).subscribe()
                );
    }

    public Single<Note> getUpdatedNote(int id, String title, String body, int userId) {
        NoteModel noteDataTransferObject = new NoteModel(title, body);

        return noteGateway.getUpdatedNote(id, userId, noteDataTransferObject)
                .doOnSuccess(note ->
                        noteDataAccessObject.updateNote(note.getNoteEntity())
                                .subscribe()
                );
    }

    public Completable deleteNote(int id, int userId) {
        return noteGateway.deleteNote(id, userId)
                .doOnComplete(() -> noteDataAccessObject.deleteNote(id).subscribe());
    }
}
