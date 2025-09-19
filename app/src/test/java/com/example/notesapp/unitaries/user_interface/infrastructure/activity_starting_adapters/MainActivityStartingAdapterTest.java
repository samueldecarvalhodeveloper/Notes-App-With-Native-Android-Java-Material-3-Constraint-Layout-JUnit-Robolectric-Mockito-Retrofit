package com.example.notesapp.unitaries.user_interface.infrastructure.activity_starting_adapters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapp.user_interface.activities.MainActivity;
import com.example.notesapp.user_interface.infrastructure.activity_starting_adapters.MainActivityStartingAdapter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

@RunWith(RobolectricTestRunner.class)
public class MainActivityStartingAdapterTest {
    @Test
    public void testIfMethodStartMainActivityStartsMainActivity() {
        try (ActivityController<AppCompatActivity> controller = Robolectric.buildActivity(AppCompatActivity.class)) {

            AppCompatActivity mockActivity = controller.create().get();

            MainActivityStartingAdapter.startMainActivity(mockActivity);

            Intent mainActivityIntent = new Intent(mockActivity, MainActivity.class);

            Intent calledActivityIntent = shadowOf(mockActivity).getNextStartedActivity();

            assertEquals(mainActivityIntent.getComponent(), calledActivityIntent.getComponent());

            assertTrue(mockActivity.isFinishing());
        }
    }
}
