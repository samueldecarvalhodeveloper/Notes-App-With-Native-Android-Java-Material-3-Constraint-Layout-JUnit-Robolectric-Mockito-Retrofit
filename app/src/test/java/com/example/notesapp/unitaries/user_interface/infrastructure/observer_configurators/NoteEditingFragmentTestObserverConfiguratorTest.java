package com.example.notesapp.unitaries.user_interface.infrastructure.observer_configurators;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.text.Editable;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;

import com.example.notesapp.user_interface.fragments.NoteEditingFragment;
import com.example.notesapp.user_interface.infrastructure.observer_configurators.NoteEditingFragmentObserverConfigurator;
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel;
import com.google.android.material.textfield.TextInputEditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class NoteEditingFragmentTestObserverConfiguratorTest {
    private TextInputEditText noteTitleTextInput;
    private TextInputEditText noteBodyTextInput;
    private NoteEditingViewModel noteEditingViewModel;
    private NoteEditingFragment noteEditingFragment;

    @Before
    public void beforeEach() {
        noteTitleTextInput = mock(TextInputEditText.class);
        noteBodyTextInput = mock(TextInputEditText.class);
        noteEditingViewModel = mock(NoteEditingViewModel.class);
        noteEditingFragment = mock(NoteEditingFragment.class);
    }

    @Test
    public void testIfMethodSetObserversSetsAllViewModelObservers() {
        when(noteEditingFragment.getLifecycle())
                .thenReturn(mock(Lifecycle.class));
        when(noteEditingViewModel.getNote(NOTE_ID))
                .thenReturn(new MutableLiveData<>(NOTE_OBJECT));
        when(noteTitleTextInput.getText())
                .thenReturn(mock(Editable.class));
        when(noteTitleTextInput.getText().toString())
                .thenReturn(NOTE_TITLE);
        when(noteBodyTextInput.getText())
                .thenReturn(mock(Editable.class));
        when(noteBodyTextInput.getText().toString())
                .thenReturn(NOTE_BODY);

        NoteEditingFragmentObserverConfigurator.setObservers(
                NOTE_ID,
                noteTitleTextInput,
                noteBodyTextInput,
                noteEditingViewModel,
                noteEditingFragment
        );

        assertEquals(NOTE_TITLE, noteTitleTextInput.getText().toString());
        assertEquals(NOTE_BODY, noteBodyTextInput.getText().toString());
    }
}
