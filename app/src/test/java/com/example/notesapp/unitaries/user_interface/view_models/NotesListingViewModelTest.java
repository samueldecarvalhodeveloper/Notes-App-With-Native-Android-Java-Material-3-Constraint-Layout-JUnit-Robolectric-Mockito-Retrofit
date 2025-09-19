package com.example.notesapp.unitaries.user_interface.view_models;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_CREATED_AT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_UPDATED_AT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_WITH_EMPTY_DATA;
import static com.example.notesapp.constants.data.UserDataConstants.USER_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

import android.os.Looper;

import com.example.notesapp.data.external_models.Note;
import com.example.notesapp.data.repositories.NoteRepository;
import com.example.notesapp.user_interface.view_models.NotesListingViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@RunWith(RobolectricTestRunner.class)
public class NotesListingViewModelTest {
    private NoteRepository noteRepository;
    private NotesListingViewModel notesListingViewModel;

    @Before
    public void beforeEach() {
        noteRepository = mock(NoteRepository.class);

        notesListingViewModel = new NotesListingViewModel(noteRepository);
    }

    @Test
    public void testIfMethodFetchNotesFromServerRetrievesUserNotesFromServiceAndStoresThemOnDatabaseAndAddsThemToListOfNotesState() {
        when(noteRepository.fetchNotesFromService(USER_ID))
                .thenReturn(Completable.complete());
        when(noteRepository.getNotes())
                    .thenReturn(Single.just(List.of(NOTE_OBJECT)));

        notesListingViewModel.fetchNotesFromServer(USER_ID);

        shadowOf(Looper.getMainLooper()).idle();

        List<Note> listOfNotesState =
                notesListingViewModel.getListOfNotes().getValue();
        boolean isListOfNotesLoaded =
                notesListingViewModel.getIsListOfNotesLoaded().getValue();

        assertFalse(listOfNotesState.isEmpty());
        assertTrue(isListOfNotesLoaded);
    }

    @Test
    public void testIfMethodCreateNoteCreatesNoteOnServiceAndAddsItToCreatedNoteState() {
        when(noteRepository.getCreatedNote("", "", USER_ID))
                .thenReturn(Single.just(NOTE_WITH_EMPTY_DATA));

        notesListingViewModel.createNote(USER_ID);

        shadowOf(Looper.getMainLooper()).idle();

        Note createdNote = notesListingViewModel.getCreatedNote().getValue();

        assertEquals(NOTE_ID, createdNote.id);
        assertEquals("", createdNote.title);
        assertEquals("", createdNote.body);
        assertEquals(NOTE_CREATED_AT, createdNote.createdAt);
        assertEquals(NOTE_UPDATED_AT, createdNote.updatedAt);
        assertEquals(USER_ID, createdNote.userId);
    }

    @Test
    public void testIfMethodCreateNoteTurnsIsNoteCreationCurrentlyUnableStateToTrueIfNoteCreationFails() {
        when(noteRepository.getCreatedNote("", "", USER_ID))
                .thenReturn(Single.error(new Exception()));

        notesListingViewModel.createNote(USER_ID);

        shadowOf(Looper.getMainLooper()).idle();

        boolean isNoteCreationCurrentlyUnable =
                notesListingViewModel.getIsNoteCreationCurrentlyUnable().getValue();

        assertTrue(isNoteCreationCurrentlyUnable);
    }
}