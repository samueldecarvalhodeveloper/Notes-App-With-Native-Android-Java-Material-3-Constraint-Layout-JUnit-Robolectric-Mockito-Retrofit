package com.example.notesapp.unitaries.user_interface.events_handlers.text_change_event_handlers;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE;
import static com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_TITLE_BEING_MANIPULATED_FIELD_NAME;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import com.example.notesapp.data.repositories.NoteRepository;
import com.example.notesapp.user_interface.events_handlers.text_change_event_handlers.NoteTitleTextInputTextChangeEventHandler;
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.lang.reflect.Field;

@RunWith(RobolectricTestRunner.class)
public class NoteTitleTextInputTextChangeEventHandlerTest {
    private NoteEditingViewModel noteEditingViewModel;
    private NoteTitleTextInputTextChangeEventHandler noteTitleTextInputTextChangeEventHandler;
    private static Field noteTitleBeingManipulatedField;

    @BeforeClass
    public static void beforeAll() throws Exception {
        noteTitleBeingManipulatedField =
                NoteEditingViewModel.class.getDeclaredField(NOTE_TITLE_BEING_MANIPULATED_FIELD_NAME);

        noteTitleBeingManipulatedField.setAccessible(true);
    }


    @Before
    public void beforeEach() {
        NoteRepository noteRepository = mock(NoteRepository.class);

        noteEditingViewModel = new NoteEditingViewModel(noteRepository);
        noteTitleTextInputTextChangeEventHandler =
                new NoteTitleTextInputTextChangeEventHandler(noteEditingViewModel);
    }

    @Test
    public void testIfMethodOnTextChangedUpdatesNoteTitle() throws Exception {
        noteTitleTextInputTextChangeEventHandler.onTextChanged(NOTE_TITLE, 0, 0, 0);

        String noteTitleBeingManipulated =
                (String) noteTitleBeingManipulatedField.get(noteEditingViewModel);

        assertEquals(NOTE_TITLE, noteTitleBeingManipulated);
    }
}
