package com.example.notesapp.unitaries.user_interface.events_handlers.click_event_handlers;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID;
import static com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_ID_INTENT_KEY;
import static com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOT_EXISTING_NOTE_ID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapp.R;
import com.example.notesapp.user_interface.activities.NoteActivity;
import com.example.notesapp.user_interface.events_handlers.click_event_handlers.NotesRecyclerViewNoteItemClickEventHandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

@RunWith(RobolectricTestRunner.class)
public class NotesRecyclerViewNoteItemClickEventHandlerTest {
    @Test
    public void testIfMethodOnClickStartsNoteActivityIfViewIdEqualsToNoteItemContainer() {
        View view = mock(View.class);

        try (ActivityController<AppCompatActivity> controller = Robolectric.buildActivity(AppCompatActivity.class)) {
            AppCompatActivity mockActivity = controller.create().get();

            when(view.getId()).thenReturn(R.id.note_item_container);
            when(view.getContext()).thenReturn(mockActivity);

            NotesRecyclerViewNoteItemClickEventHandler notesRecyclerViewNoteItemClickEventHandler =
                    new NotesRecyclerViewNoteItemClickEventHandler(NOTE_ID);

            notesRecyclerViewNoteItemClickEventHandler.onClick(view);

            Intent mainActivityIntent = new Intent(mockActivity, NoteActivity.class);

            Intent calledActivityIntent = shadowOf(mockActivity).getNextStartedActivity();
            
            int noteIdFromIntent =
                    calledActivityIntent.getIntExtra(NOTE_ID_INTENT_KEY, NOT_EXISTING_NOTE_ID);

            assertEquals(mainActivityIntent.getComponent(), calledActivityIntent.getComponent());
            assertEquals(NOTE_ID, noteIdFromIntent);
        }
    }
}
