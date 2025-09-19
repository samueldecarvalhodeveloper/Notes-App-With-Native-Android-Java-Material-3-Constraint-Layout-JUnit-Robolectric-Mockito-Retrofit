package com.example.notesapp.unitaries.user_interface.infrastructure.start_fragment_on_container_command;

import static org.junit.Assert.assertTrue;

import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.notesapp.R;
import com.example.notesapp.user_interface.fragments.NoNoteFragment;
import com.example.notesapp.user_interface.infrastructure.start_fragment_on_container_command.StartNoNoteFragmentOnContainerCommand;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

@RunWith(RobolectricTestRunner.class)
public class StartNoNoteFragmentTestOnContainerCommandTest {
    @Test
    public void testIfMethodExecuteStartsNoNotesFragmentOnActivity() {
        try (ActivityController<AppCompatActivity> controller = Robolectric.buildActivity(AppCompatActivity.class)) {
            AppCompatActivity activity = controller.setup().get();

            FrameLayout fragmentContainerLayout = new FrameLayout(activity);

            fragmentContainerLayout.setId(R.id.notes_fragment_layout);
            
            activity.setContentView(fragmentContainerLayout);

            StartNoNoteFragmentOnContainerCommand.execute(R.id.notes_fragment_layout, activity);

            Robolectric.flushForegroundThreadScheduler();

            Fragment fragmentContainer =
                    activity.getSupportFragmentManager().findFragmentById(R.id.notes_fragment_layout);

            assertTrue(fragmentContainer instanceof NoNoteFragment);
        }
    }
}
