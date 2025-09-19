package com.example.notesapp.unitaries.user_interface.events_handlers.text_change_event_handlers;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY;
import static com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_BODY_BEING_MANIPULATED_FIELD_NAME;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import com.example.notesapp.data.repositories.NoteRepository;
import com.example.notesapp.user_interface.events_handlers.text_change_event_handlers.NoteBodyTextInputTextChangeEventHandler;
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.lang.reflect.Field;

@RunWith(RobolectricTestRunner.class)
public class NoteBodyTextInputTextChangeEventHandlerTest {
    private NoteEditingViewModel noteEditingViewModel;
    private NoteBodyTextInputTextChangeEventHandler noteBodyTextInputTextChangeEventHandler;
    private static Field noteBodyBeingManipulatedField;

    @BeforeClass
    public static void beforeAll() throws Exception {
        noteBodyBeingManipulatedField =
                NoteEditingViewModel.class.getDeclaredField(NOTE_BODY_BEING_MANIPULATED_FIELD_NAME);

        noteBodyBeingManipulatedField.setAccessible(true);
    }


    @Before
    public void beforeEach() {
        NoteRepository noteRepository = mock(NoteRepository.class);

        noteEditingViewModel = new NoteEditingViewModel(noteRepository);
        noteBodyTextInputTextChangeEventHandler =
                new NoteBodyTextInputTextChangeEventHandler(noteEditingViewModel);
    }

    @Test
    public void testIfMethodOnTextChangedUpdatesNoteBody() throws Exception {
        noteBodyTextInputTextChangeEventHandler.onTextChanged(NOTE_BODY, 0, 0, 0);

        String noteBodyBeingManipulated =
                (String) noteBodyBeingManipulatedField.get(noteEditingViewModel);

        assertEquals(NOTE_BODY, noteBodyBeingManipulated);
    }
}
