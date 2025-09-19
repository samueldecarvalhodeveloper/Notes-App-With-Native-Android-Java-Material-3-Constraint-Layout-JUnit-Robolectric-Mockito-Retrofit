package com.example.notesapp.unitaries.user_interface.view_models;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_CREATED_AT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_UPDATED_AT;
import static com.example.notesapp.constants.data.UserDataConstants.USER_ID;
import static com.example.notesapp.constants.user_interface.UserInterfaceConstants.IS_NOTE_MANIPULATION_UNABLE_FIELD_NAME;
import static com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_BODY_BEING_MANIPULATED_FIELD_NAME;
import static com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_FIELD_NAME;
import static com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_TITLE_BEING_MANIPULATED_FIELD_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.notesapp.data.external_models.Note;
import com.example.notesapp.data.repositories.NoteRepository;
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.lang.reflect.Field;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@RunWith(RobolectricTestRunner.class)
public class NoteEditingViewModelTest {
    private NoteEditingViewModel noteEditingViewModel;
    private NoteRepository noteRepository;
    private static Field noteField;
    private static Field noteTitleBeingManipulatedField;
    private static Field noteBodyBeingManipulatedField;

    @BeforeClass
    public static void beforeAll() throws Exception {
        noteTitleBeingManipulatedField =
                NoteEditingViewModel.class.getDeclaredField(NOTE_TITLE_BEING_MANIPULATED_FIELD_NAME);
        noteBodyBeingManipulatedField =
                NoteEditingViewModel.class.getDeclaredField(NOTE_BODY_BEING_MANIPULATED_FIELD_NAME);
        noteField = NoteEditingViewModel.class.getDeclaredField(NOTE_FIELD_NAME);

        noteTitleBeingManipulatedField.setAccessible(true);
        noteBodyBeingManipulatedField.setAccessible(true);
        noteField.setAccessible(true);
    }

    @Before
    public void beforeEach() {
        noteRepository = mock(NoteRepository.class);

        noteEditingViewModel = new NoteEditingViewModel(noteRepository);
    }

    @Test
    public void testIfMethodGetNoteReturnsNoteFromDatabaseAndSetsCurrentNoteBeingEditedAndTurnsIsNoteLoadedToTrue()
            throws Exception {
        when(noteRepository.getNote(NOTE_ID))
                .thenReturn(Single.just(NOTE_OBJECT));

        LiveData<Note> noteFromDatabase = noteEditingViewModel.getNote(NOTE_ID);

        shadowOf(Looper.getMainLooper()).idle();

        Note note = noteFromDatabase.getValue();

        LiveData<Boolean> isNoteLoaded = noteEditingViewModel.getIsNoteLoaded();

        String noteTitleBeingManipulated =
                (String) noteTitleBeingManipulatedField.get(noteEditingViewModel);
        String noteBodyBeingManipulated =
                (String) noteBodyBeingManipulatedField.get(noteEditingViewModel);

        assertEquals(NOTE_ID, note.id);
        assertEquals(NOTE_TITLE, note.title);
        assertEquals(NOTE_BODY, note.body);
        assertEquals(NOTE_CREATED_AT, note.createdAt);
        assertEquals(NOTE_UPDATED_AT, note.updatedAt);
        assertEquals(USER_ID, note.userId);

        assertEquals(NOTE_TITLE, noteTitleBeingManipulated);
        assertEquals(NOTE_BODY, noteBodyBeingManipulated);

        assertTrue(isNoteLoaded.getValue());
    }

    @Test
    public void testIfMethodUpdateNoteTitleUpdatesNoteBeingManipulatedTitle() throws Exception {
        noteEditingViewModel.updateNoteTitle(NOTE_TITLE);

        String noteTitleBeingManipulated =
                (String) noteTitleBeingManipulatedField.get(noteEditingViewModel);

        assertEquals(NOTE_TITLE, noteTitleBeingManipulated);
    }

    @Test
    public void testIfMethodUpdateNoteBodyUpdatesNoteBeingManipulatedBody()
            throws Exception {
        noteEditingViewModel.updateNoteBody(NOTE_BODY);

        String noteBodyBeingManipulated =
                (String) noteBodyBeingManipulatedField.get(noteEditingViewModel);

        assertEquals(NOTE_BODY, noteBodyBeingManipulated);
    }

    @Test
    public void testIfMethodManipulateNoteTurnsIsNoteBeingManipulatedStateToTrueIfIsNoteManipulationUnableStateIsFalse()
            throws Exception {
        Field isNoteManipulationUnableField =
                NoteEditingViewModel.class.getDeclaredField(IS_NOTE_MANIPULATION_UNABLE_FIELD_NAME);

        isNoteManipulationUnableField.set(noteEditingViewModel, new MutableLiveData<>(false));

        noteEditingViewModel.manipulateNote();

        boolean isNoteBeingManipulated = noteEditingViewModel.getIsNoteBeingManipulated().getValue();

        assertTrue(isNoteBeingManipulated);
    }

    @Test
    public void testIfMethodConcludeNoteEditingUpdatesNoteAndTurnsIsNoteBeingManipulatedStateToFalseAndUpdatesNoteState()
            throws Exception {
        noteField.set(noteEditingViewModel, new MutableLiveData<>(NOTE_OBJECT));

        when(noteRepository.getUpdatedNote(NOTE_ID, "", "", USER_ID))
                .thenReturn(Single.just(NOTE_OBJECT));

        noteEditingViewModel.updateNoteTitle("");
        noteEditingViewModel.updateNoteBody("");

        noteEditingViewModel.concludeNoteEditing();

        LiveData<Note> noteState = noteEditingViewModel.getNote(NOTE_ID);

        shadowOf(Looper.getMainLooper()).idle();

        Note note = noteState.getValue();

        boolean isNoteBeingManipulated = noteEditingViewModel.getIsNoteBeingManipulated().getValue();

        assertEquals(NOTE_ID, note.id);
        assertEquals(NOTE_TITLE, note.title);
        assertEquals(NOTE_BODY, note.body);
        assertEquals(NOTE_CREATED_AT, note.createdAt);
        assertEquals(NOTE_UPDATED_AT, note.updatedAt);
        assertEquals(USER_ID, note.userId);

        assertFalse(isNoteBeingManipulated);
    }

    @Test
    public void testIfMethodConcludeNoteEditingOnFailingTurnsIsNoteManipulationUnableStateToTrueAndIsNoteBeingManipulatedStateToFalse()
            throws Exception {
        noteField.set(noteEditingViewModel, new MutableLiveData<>(NOTE_OBJECT));

        when(noteRepository.getUpdatedNote(NOTE_ID, "", "", USER_ID))
                .thenReturn(Single.error(new Exception()));

        noteEditingViewModel.updateNoteTitle("");
        noteEditingViewModel.updateNoteBody("");

        noteEditingViewModel.concludeNoteEditing();

        shadowOf(Looper.getMainLooper()).idle();

        boolean isNoteManipulationUnable =
                noteEditingViewModel.getIsNoteManipulationUnable().getValue();
        boolean isNoteBeingManipulated =
                noteEditingViewModel.getIsNoteBeingManipulated().getValue();

        assertTrue(isNoteManipulationUnable);
        assertFalse(isNoteBeingManipulated);
    }

    @Test
    public void testIfMethodDeleteNoteDeletesNoteAndTurnsIsNoteDeletedStateToTrue()
            throws Exception {
        noteField.set(noteEditingViewModel, new MutableLiveData<>(NOTE_OBJECT));

        when(noteRepository.deleteNote(NOTE_ID, USER_ID))
                .thenReturn(Completable.complete());

        noteEditingViewModel.deleteNote();

        shadowOf(Looper.getMainLooper()).idle();

        boolean isNoteBeingManipulated = noteEditingViewModel.getIsNoteBeingManipulated().getValue();

        assertFalse(isNoteBeingManipulated);
    }

    @Test
    public void testIfMethodDeleteNoteTurnsIsNoteBeingManipulatedStateToFalseAndIsNoteManipulationUnableStateToTrueIfDeleteFails()
            throws Exception {
        noteField.set(noteEditingViewModel, new MutableLiveData<>(NOTE_OBJECT));

        when(noteRepository.deleteNote(NOTE_ID, USER_ID))
                .thenReturn(Completable.error(new Exception()));

        noteEditingViewModel.deleteNote();

        shadowOf(Looper.getMainLooper()).idle();

        boolean isNoteBeingManipulated =
                noteEditingViewModel.getIsNoteBeingManipulated().getValue();
        boolean isNoteManipulationUnable =
                noteEditingViewModel.getIsNoteManipulationUnable().getValue();

        assertFalse(isNoteBeingManipulated);
        assertTrue(isNoteManipulationUnable);
    }
}
