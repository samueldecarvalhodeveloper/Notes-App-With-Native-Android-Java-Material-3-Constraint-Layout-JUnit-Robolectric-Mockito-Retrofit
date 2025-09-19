package com.example.notesapp.data.local_data_source.data_access_objects;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notesapp.data.local_data_source.entities.NoteEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface NoteDataAccessObject {
    @Query("SELECT * FROM NoteEntity Order By updated_at DESC")
    Single<List<NoteEntity>> getNotes();

    @Query("SELECT * FROM NoteEntity Where id = :id")
    Single<NoteEntity> getNote(int id);

    @Insert
    Completable createNote(NoteEntity note);

    @Update
    Completable updateNote(NoteEntity note);

    @Query("DELETE FROM NoteEntity Where id = :id")
    Completable deleteNote(int id);
}
