package com.example.notesapp.unitaries.user_interface.infrastructure.activity_starting_adapters;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID;
import static com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOTE_ID_INTENT_KEY;
import static com.example.notesapp.constants.user_interface.UserInterfaceConstants.NOT_EXISTING_NOTE_ID;
import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapp.user_interface.activities.NoteActivity;
import com.example.notesapp.user_interface.infrastructure.activity_starting_adapters.NoteActivityStartingAdapter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

@RunWith(RobolectricTestRunner.class)
public class NoteActivityStartingAdapterTest {
    @Test
    public void testIfMethodStartNoteActivityStartsNoteActivity() {
        try (ActivityController<AppCompatActivity> controller = Robolectric.buildActivity(AppCompatActivity.class)) {
            AppCompatActivity mockActivity = controller.create().get();

            NoteActivityStartingAdapter.startNoteActivity(NOTE_ID, mockActivity);

            Intent mainActivityIntent = new Intent(mockActivity, NoteActivity.class);

            Intent calledActivityIntent = shadowOf(mockActivity).getNextStartedActivity();
            
            int noteIdFromIntent =
                    calledActivityIntent.getIntExtra(NOTE_ID_INTENT_KEY, NOT_EXISTING_NOTE_ID);

            assertEquals(mainActivityIntent.getComponent(), calledActivityIntent.getComponent());
            assertEquals(NOTE_ID, noteIdFromIntent);
        }
    }
}
