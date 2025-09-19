package com.example.notesapp.unitaries.user_interface.infrastructure.start_fragment_on_container_command;

import static com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT;
import static org.junit.Assert.assertTrue;

import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.notesapp.R;
import com.example.notesapp.user_interface.fragments.NotesFragment;
import com.example.notesapp.user_interface.infrastructure.start_fragment_on_container_command.StartNotesFragmentOnContainerCommand;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class StartNotesFragmentTestOnContainerCommandTest {
    @Test
    public void testIfMethodExecuteStartsNotesFragmentOnActivity() {
        try (ActivityController<AppCompatActivity> controller = Robolectric.buildActivity(AppCompatActivity.class)) {
            AppCompatActivity activity = controller.setup().get();

            FrameLayout fragmentContainerLayout = new FrameLayout(activity);

            fragmentContainerLayout.setId(R.id.notes_fragment_layout);

            activity.setContentView(fragmentContainerLayout);

            StartNotesFragmentOnContainerCommand.execute(
                    List.of(NOTE_OBJECT),
                    R.id.notes_fragment_layout,
                    activity
            );

            Robolectric.flushForegroundThreadScheduler();

            Fragment fragmentContainer =
                    activity.getSupportFragmentManager().findFragmentById(R.id.notes_fragment_layout);

            assertTrue(fragmentContainer instanceof NotesFragment);
        }
    }
}
