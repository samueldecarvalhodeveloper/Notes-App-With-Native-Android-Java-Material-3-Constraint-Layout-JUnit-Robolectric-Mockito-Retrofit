package com.example.notesapp.unitaries.user_interface.infrastructure.start_fragment_on_container_command;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.notesapp.R;
import com.example.notesapp.user_interface.fragments.NoteVisualizingFragment;
import com.example.notesapp.user_interface.infrastructure.start_fragment_on_container_command.StartNoteVisualizingFragmentOnContainerCommand;
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

@RunWith(RobolectricTestRunner.class)
public class StartNoteVisualizingFragmentTestOnContainerCommandTest {
    private static NoteEditingViewModel noteEditingViewModel;

    @BeforeClass
    public static void beforeEach() {
        noteEditingViewModel = mock(NoteEditingViewModel.class);
    }

    @Test
    public void testIfMethodExecuteStartsNoteVisualizingFragmentOnActivity() {
        when(noteEditingViewModel.getNote(NOTE_ID))
                .thenReturn(mock(LiveData.class));

        try (ActivityController<AppCompatActivity> controller = Robolectric.buildActivity(AppCompatActivity.class)) {
            AppCompatActivity activity = controller.setup().get();

            FrameLayout fragmentContainerLayout = new FrameLayout(activity);

            fragmentContainerLayout.setId(R.id.note_handling_fragment);

            activity.setContentView(fragmentContainerLayout);

            StartNoteVisualizingFragmentOnContainerCommand.execute(
                    NOTE_ID,
                    noteEditingViewModel,
                    R.id.note_handling_fragment,
                    activity
            );

            Robolectric.flushForegroundThreadScheduler();

            Fragment fragmentContainer =
                    activity.getSupportFragmentManager().findFragmentById(R.id.note_handling_fragment);

            assertTrue(fragmentContainer instanceof NoteVisualizingFragment);
        }
    }
}
