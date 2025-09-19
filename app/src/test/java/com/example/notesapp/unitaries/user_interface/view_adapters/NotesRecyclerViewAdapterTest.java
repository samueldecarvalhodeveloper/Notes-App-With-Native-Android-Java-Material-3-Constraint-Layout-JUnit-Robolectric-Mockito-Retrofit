package com.example.notesapp.unitaries.user_interface.view_adapters;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.notesapp.data.external_models.Note;
import com.example.notesapp.user_interface.view_adapters.NotesRecyclerViewAdapter;
import com.example.notesapp.user_interface.view_holders.NoteItemViewHolder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class NotesRecyclerViewAdapterTest {
    private NotesRecyclerViewAdapter notesRecyclerViewAdapter;
    private LinearLayout root;
    private NoteItemViewHolder noteItemViewHolder;
    private TextView noteItemTitleText;
    private TextView noteItemBodyText;
    private List<Note> listOfNotes;

    @Before
    public void beforeEach() {
        root = mock(LinearLayout.class);
        noteItemBodyText = mock(TextView.class);
        noteItemTitleText = mock(TextView.class);

        listOfNotes = List.of(NOTE_OBJECT);

        notesRecyclerViewAdapter = mock(NotesRecyclerViewAdapter.class);

        noteItemViewHolder = mock(NoteItemViewHolder.class);
    }

    @Test
    public void testIfMethodOnCreateViewHolderCreatesAnInstanceOfNotesItemViewHolder() {
        when(notesRecyclerViewAdapter.onCreateViewHolder(root, 0))
                .thenReturn(mock(NoteItemViewHolder.class));

        assertTrue(notesRecyclerViewAdapter.onCreateViewHolder(root, 0) instanceof NoteItemViewHolder);
    }

    @Test
    public void testIfMethodOnBindViewHolderBindsTheValueOfNoteToNoteItemViews() {
        when(noteItemTitleText.getText())
                .thenReturn(NOTE_TITLE);
        when(noteItemBodyText.getText())
                .thenReturn(NOTE_BODY);
        when(root.hasOnClickListeners())
                .thenReturn(true);

        notesRecyclerViewAdapter.onBindViewHolder(noteItemViewHolder, 0);

        assertEquals(noteItemTitleText.getText(), NOTE_TITLE);
        assertEquals(noteItemBodyText.getText(), NOTE_BODY);
        assertTrue(root.hasOnClickListeners());
    }

    @Test
    public void testIfMethodGetItemCount() {
        when(notesRecyclerViewAdapter.getItemCount())
                .thenReturn(listOfNotes.size());

        assertEquals(notesRecyclerViewAdapter.getItemCount(), listOfNotes.size());
    }
}
