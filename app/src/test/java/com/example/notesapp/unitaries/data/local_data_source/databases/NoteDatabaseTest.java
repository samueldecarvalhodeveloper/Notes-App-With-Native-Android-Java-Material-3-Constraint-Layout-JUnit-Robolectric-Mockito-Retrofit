package com.example.notesapp.unitaries.data.local_data_source.databases;

import com.example.notesapp.concerns.RoomDatabaseFactory;
import com.example.notesapp.data.local_data_source.data_access_objects.NoteDataAccessObject;
import com.example.notesapp.data.local_data_source.databases.NoteDatabase;
import com.example.notesapp.data.local_data_source.entities.NoteEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import io.reactivex.rxjava3.observers.TestObserver;

@RunWith(RobolectricTestRunner.class)
public class NoteDatabaseTest {
    private NoteDatabase noteDatabase;

    @Before
    public void beforeEach() {
        noteDatabase = RoomDatabaseFactory.getInstance(NoteDatabase.class);
    }

    @Test
    public void testIfMethodGetDataAccessObjectReturnsNoteDataAccessObjectImplementation() {
        NoteDataAccessObject noteDataAccessObject = noteDatabase.getDataAccessObject();

        TestObserver<List<NoteEntity>> notesFromDatabase = noteDataAccessObject.getNotes().test();

        notesFromDatabase.assertValue(List::isEmpty);
    }
}