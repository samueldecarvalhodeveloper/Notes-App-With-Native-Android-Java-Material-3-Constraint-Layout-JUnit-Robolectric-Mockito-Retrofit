package com.example.notesapp.unitaries.user_interface.infrastructure.start_fragment_on_container_command;

import static org.junit.Assert.assertTrue;

import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.notesapp.R;
import com.example.notesapp.user_interface.fragments.LoadingFragment;
import com.example.notesapp.user_interface.infrastructure.start_fragment_on_container_command.StartLoadingFragmentOnContainerCommand;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

@RunWith(RobolectricTestRunner.class)
public class StartLoadingFragmentTestOnContainerCommandTest {
    @Test
    public void testIfMethodExecuteStartsLoadingFragmentOnActivity() {
        try (ActivityController<AppCompatActivity> controller = Robolectric.buildActivity(AppCompatActivity.class)) {
            AppCompatActivity activity = controller.setup().get();

            FrameLayout fragmentContainerLayout = new FrameLayout(activity);

            fragmentContainerLayout.setId(R.id.notes_fragment_layout);

            activity.setContentView(fragmentContainerLayout);

            StartLoadingFragmentOnContainerCommand.execute(R.id.notes_fragment_layout, activity);

            Robolectric.flushForegroundThreadScheduler();

            Fragment fragmentContainer =
                    activity.getSupportFragmentManager().findFragmentById(R.id.notes_fragment_layout);

            assertTrue(fragmentContainer instanceof LoadingFragment);
        }
    }
}
