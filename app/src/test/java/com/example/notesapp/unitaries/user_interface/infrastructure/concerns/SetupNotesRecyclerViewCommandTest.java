package com.example.notesapp.unitaries.user_interface.infrastructure.concerns;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.example.notesapp.data.external_models.Note;
import com.example.notesapp.user_interface.infrastructure.concerns.SetupNotesRecyclerViewCommand;
import com.example.notesapp.user_interface.view_holders.NoteItemViewHolder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class SetupNotesRecyclerViewCommandTest {
    private RecyclerView notesRecyclerView;
    private List<Note> listOfNotes;
    private Context context;

    @Before
    public void beforeEach() {
        notesRecyclerView = mock(RecyclerView.class);
        listOfNotes = mock(List.class);
        context = mock(Context.class);
    }

    @Test
    public void testIfMethodExecuteSetsUpNotesRecyclerView() {
        when(notesRecyclerView.getLayoutManager())
                .thenReturn(mock(LayoutManager.class));
        when(notesRecyclerView.getAdapter())
                .thenReturn(mock(Adapter.class));

        SetupNotesRecyclerViewCommand.execute(notesRecyclerView, listOfNotes, context);

        LayoutManager notesRecyclerViewLayoutManager = notesRecyclerView.getLayoutManager();
        Adapter<NoteItemViewHolder> notesRecyclerViewAdapter = notesRecyclerView.getAdapter();

        assertNotNull(notesRecyclerViewLayoutManager);
        assertNotNull(notesRecyclerViewAdapter);
    }
}
