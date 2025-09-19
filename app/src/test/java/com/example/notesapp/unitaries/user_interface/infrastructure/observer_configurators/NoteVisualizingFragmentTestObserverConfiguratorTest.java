package com.example.notesapp.unitaries.user_interface.infrastructure.observer_configurators;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.text.Editable;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;

import com.example.notesapp.user_interface.fragments.NoteVisualizingFragment;
import com.example.notesapp.user_interface.infrastructure.observer_configurators.NoteVisualizingFragmentObserverConfigurator;
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class NoteVisualizingFragmentTestObserverConfiguratorTest {
    private TextView noteTitleText;
    private TextView noteBodyText;
    private NoteEditingViewModel noteEditingViewModel;
    private NoteVisualizingFragment noteVisualizingFragment;

    @Before
    public void beforeEach() {
        noteTitleText = mock(TextView.class);
        noteBodyText = mock(TextView.class);
        noteEditingViewModel = mock(NoteEditingViewModel.class);
        noteVisualizingFragment = mock(NoteVisualizingFragment.class);
    }

    @Test
    public void testIfMethodSetObserversSetsAllViewModelObservers() {
        when(noteVisualizingFragment.getLifecycle())
                .thenReturn(mock(Lifecycle.class));
        when(noteEditingViewModel.getNote(NOTE_ID))
                .thenReturn(new MutableLiveData<>(NOTE_OBJECT));
        when(noteTitleText.getText())
                .thenReturn(mock(Editable.class));
        when(noteTitleText.getText().toString())
                .thenReturn(NOTE_TITLE);
        when(noteBodyText.getText())
                .thenReturn(mock(Editable.class));
        when(noteBodyText.getText().toString())
                .thenReturn(NOTE_BODY);

        NoteVisualizingFragmentObserverConfigurator.setObservers(
                NOTE_ID,
                noteTitleText,
                noteBodyText,
                noteEditingViewModel,
                noteVisualizingFragment
        );

        assertEquals(NOTE_TITLE, noteTitleText.getText().toString());
        assertEquals(NOTE_BODY, noteBodyText.getText().toString());
    }
}
