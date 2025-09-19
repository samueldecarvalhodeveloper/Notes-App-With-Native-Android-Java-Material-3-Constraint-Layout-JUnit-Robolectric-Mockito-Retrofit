package com.example.notesapp.unitaries.user_interface.view_holders;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY;
import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.notesapp.user_interface.view_holders.NoteItemViewHolder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class NoteItemViewHolderTest {
    private NoteItemViewHolder noteItemViewHolder;
    private LinearLayout root;
    private TextView noteItemTitleText;
    private TextView noteItemBodyText;

    @Before
    public void beforeEach() {
        root = mock(LinearLayout.class);
        noteItemBodyText = mock(TextView.class);
        noteItemTitleText = mock(TextView.class);

        noteItemViewHolder = new NoteItemViewHolder(root, noteItemBodyText, noteItemTitleText);
    }

    @Test
    public void testIfMethodSetTitleSetsTitleViewText() {
        when(noteItemTitleText.getText())
                .thenReturn(NOTE_TITLE);

        noteItemViewHolder.setTitle(NOTE_TITLE);

        assertEquals(NOTE_TITLE, noteItemTitleText.getText());
    }

    @Test
    public void testIfMethodSetBodySetsBodyViewText() {
        when(noteItemBodyText.getText())
                .thenReturn(NOTE_BODY);

        noteItemViewHolder.setBody(NOTE_BODY);

        assertEquals(NOTE_BODY, noteItemBodyText.getText());
    }

    @Test
    public void testIfMethodSetOnClickListenerSetsClickListenerToRootView() {
        when(root.hasOnClickListeners())
                .thenReturn(true);

        noteItemViewHolder.setOnClickListener(view -> {
        });

        assertTrue(root.hasOnClickListeners());
    }
}
